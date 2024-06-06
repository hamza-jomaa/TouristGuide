package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import dao.ReportDAO;
import ConnectionDB.DBCon;
import model.Report;

@WebServlet("/update-report")
public class UpdateReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int reportId = Integer.parseInt(request.getParameter("reportId"));
		String approvalStatus = request.getParameter("approvalStatus");

		try {
			Connection con = DBCon.getConnection();
			ReportDAO reportDAO = new ReportDAO(con);
			Report report = reportDAO.getReportById(reportId);

			if (approvalStatus.equals("approved")) {
				// Update the status of the report to "approved" in the database
				report.setStatus("approved");
				reportDAO.updateReport(report);
			} else if (approvalStatus.equals("disapproved")) {
				// Delete the report from the database
				reportDAO.deleteReport(report);
			}
		} catch (SQLException | ClassNotFoundException e) {
			// Handle the SQL exception (e.g., log the error, show an error message, etc.)
			e.printStackTrace();
			// You can redirect the user to an error page or display an error message here
			response.sendRedirect("error.jsp");
			return;
		}

		response.sendRedirect("admin.jsp");
	}
}
