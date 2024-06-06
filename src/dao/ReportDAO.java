package dao;

import model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Report;

public class ReportDAO {
	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;

	public ReportDAO(Connection con) {
		this.con = con;
	}

	public void insertReport(Report report) throws SQLException {
		query = "INSERT INTO report (phone_number, date, country, city, media, violation_type, status) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		pst = this.con.prepareStatement(query);
		pst.setString(1, report.getPhoneNumber());
		pst.setTimestamp(2, java.sql.Timestamp.valueOf(report.getDate()));
		pst.setString(3, report.getCountry());
		pst.setString(4, report.getCity());
		pst.setString(5, report.getMedia());
		pst.setString(6, report.getViolationType());
		pst.setString(7, report.getStatus());

		pst.executeUpdate();
		pst.close();
		// Increment the false_violations count for the user
		incrementFalseViolations(report.getPhoneNumber());

		// Check if the user needs to be blocked
		User user = checkUserBlock(report.getPhoneNumber());

		// If the user is blocked, return them to the login page
		if (user != null && user.isBlocked()) {
			throw new SQLException("User is blocked");
		}
	}

	private void incrementFalseViolations(String phoneNumber) throws SQLException {
		query = "UPDATE users SET false_violations = false_violations + 1 WHERE phone_number = ?";
		pst = this.con.prepareStatement(query);
		pst.setString(1, phoneNumber);
		pst.executeUpdate();
		pst.close();
	}

	private User checkUserBlock(String phoneNumber) throws SQLException {
		query = "SELECT false_violations, is_blocked FROM users WHERE phone_number = ?";
		pst = this.con.prepareStatement(query);
		pst.setString(1, phoneNumber);
		rs = pst.executeQuery();

		User user = null;
		if (rs.next()) {
			int falseViolations = rs.getInt("false_violations");
			boolean isBlocked = rs.getBoolean("is_blocked");

			if (falseViolations >= 3) {
				blockUser(phoneNumber);
				isBlocked = true;
			}

			user = new User(phoneNumber, isBlocked);
		}

		rs.close();
		pst.close();

		return user;
	}

	private void blockUser(String phoneNumber) throws SQLException {
		query = "UPDATE users SET is_blocked = 1 WHERE phone_number = ?";
		pst = this.con.prepareStatement(query);
		pst.setString(1, phoneNumber);
		pst.executeUpdate();
		pst.close();
	}

	public List<Report> getReports() throws SQLException {
		List<Report> reports = new ArrayList<>();

		query = "SELECT * FROM report";
		pst = this.con.prepareStatement(query);
		rs = pst.executeQuery();

		while (rs.next()) {
			Report report = new Report();
			report.setId(rs.getInt("id"));
			report.setPhoneNumber(rs.getString("phone_number"));
			report.setDate(rs.getTimestamp("date").toLocalDateTime());
			report.setCountry(rs.getString("country"));
			report.setCity(rs.getString("city"));
			report.setMedia(rs.getString("media"));
			report.setViolationType(rs.getString("violation_type"));
			report.setStatus(rs.getString("status"));

			reports.add(report);
		}

		rs.close();
		pst.close();

		return reports;
	}

	public List<Report> getApprovedReports() throws SQLException {
		List<Report> approvedReports = new ArrayList<>();

		query = "SELECT * FROM report WHERE status = 'approved'";
		pst = this.con.prepareStatement(query);
		rs = pst.executeQuery();

		while (rs.next()) {
			Report report = new Report();
			report.setId(rs.getInt("id"));
			report.setPhoneNumber(rs.getString("phone_number"));
			report.setDate(rs.getTimestamp("date").toLocalDateTime());
			report.setCountry(rs.getString("country"));
			report.setCity(rs.getString("city"));
			report.setMedia(rs.getString("media"));
			report.setViolationType(rs.getString("violation_type"));
			report.setStatus(rs.getString("status"));

			approvedReports.add(report);
		}

		rs.close();
		pst.close();

		return approvedReports;
	}

	public Report getReportById(int reportId) throws SQLException {
		Report report = null;

		query = "SELECT * FROM report WHERE id = ?";
		pst = this.con.prepareStatement(query);
		pst.setInt(1, reportId);
		rs = pst.executeQuery();

		if (rs.next()) {
			report = new Report();
			report.setId(rs.getInt("id"));
			report.setPhoneNumber(rs.getString("phone_number"));
			report.setDate(rs.getTimestamp("date").toLocalDateTime());
			report.setCountry(rs.getString("country"));
			report.setCity(rs.getString("city"));
			report.setMedia(rs.getString("media"));
			report.setViolationType(rs.getString("violation_type"));
			report.setStatus(rs.getString("status"));
		}

		rs.close();
		pst.close();

		return report;
	}

	public void updateReport(Report report) throws SQLException {
		query = "UPDATE report SET status = ? WHERE id = ?";
		pst = this.con.prepareStatement(query);
		pst.setString(1, report.getStatus());
		pst.setInt(2, report.getId());
		pst.executeUpdate();
		pst.close();
	}

	public void deleteReport(Report report) throws SQLException {
		query = "DELETE FROM report WHERE id = ?";
		pst = this.con.prepareStatement(query);
		pst.setInt(1, report.getId());
		pst.executeUpdate();
		pst.close();
	}

	public void deleteExpiredReports() throws SQLException {
		query = "DELETE FROM report WHERE date < DATE_SUB(NOW(), INTERVAL 1 MONTH)";
		pst = this.con.prepareStatement(query);
		pst.executeUpdate();
		pst.close();
	}

	public static int getViolationCount(String cityName, String violationType, Connection con) throws SQLException {
		String query = "SELECT COUNT(*) AS violation_count FROM report WHERE city = ? AND violation_type = ?";
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setString(1, cityName);
			pst.setString(2, violationType);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("violation_count");
				}
			}
		}
		return 0;
	}

}
