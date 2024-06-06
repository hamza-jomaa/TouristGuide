package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;

public class UserDAO {

	private Connection con;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;

	public UserDAO(Connection con) {
		this.con = con;
	}

	public User userLogin(String phoneNumber, String password) {
		User user = null;
		try {
			query = "SELECT * FROM users WHERE phone_number=? AND password=?";
			pst = con.prepareStatement(query);
			pst.setString(1, phoneNumber);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setName(rs.getString("name"));
				user.setPhoneNumber(rs.getString("phone_number"));
				user.setBlocked(rs.getBoolean("is_blocked")); // Retrieve the 'is_blocked' status from the database
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
		}
		return user;
	}

}
