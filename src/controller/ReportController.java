package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import ConnectionDB.DBCon;
import dao.ReportDAO;
import model.Report;
import model.User;

@WebServlet("/Report-Controller")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			User hamza = (User) request.getSession().getAttribute("hamza");
			if (hamza != null) {
				request.setAttribute("hamza", hamza);
			}

			String phoneNumber = request.getParameter("phone");
			LocalDateTime date = LocalDateTime.now();
			// Code for test expire report
			//LocalDateTime date = LocalDateTime.now().minusMonths(1);
			String country = request.getParameter("country");
			String city = request.getParameter("city");
			String violationType = request.getParameter("violationType");
			String status = "pending";

			// Create the "uploads" directory if it doesn't exist
			String contextPath = getServletContext().getRealPath("");
			String directoryPath = contextPath + File.separator + "uploads";
			File directory = new File(directoryPath);
			boolean created = directory.mkdirs();

			if (created) {
				System.out.println("Directory created successfully: " + directoryPath);
			} else {
				System.out.println("Failed to create the directory: " + directoryPath);
			}

			// Process uploaded file
			Part mediaPart = request.getPart("media");
			String mediaFileName = extractFileName(mediaPart);
			String mediaFilePath = directoryPath + File.separator + mediaFileName;
			mediaPart.write(mediaFilePath);

			try {
				ReportDAO reportDAO = new ReportDAO(DBCon.getConnection());
				Report report = new Report();
				report.setPhoneNumber(phoneNumber);
				report.setDate(date);
				report.setCountry(country);
				report.setCity(city);
				report.setMedia(mediaFileName);
				report.setViolationType(violationType);
				report.setStatus(status);

				reportDAO.insertReport(report);
				// Set success message attribute
				request.setAttribute("successMessage", "Report submitted successfully");

				// Forward the request to Report.jsp
				request.getRequestDispatcher("report.jsp").forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				if (e.getMessage().equals("User is blocked")) {
					// User is blocked, redirect to the login page or display an appropriate message
					response.sendRedirect("LogIn.jsp?blocked=true");
				} else {
					// Handle other exceptions or display an appropriate error message
					e.printStackTrace();
					out.print("An error occurred while submitting the report: " + e.getMessage());
				}
			}
		}
	}

	// Extracts the file name from the content-disposition header of the part
	private String extractFileName(Part part) {
		String contentDisposition = part.getHeader("content-disposition");
		String[] items = contentDisposition.split(";");

		for (String item : items) {
			if (item.trim().startsWith("filename")) {
				return item.substring(item.indexOf("=") + 2, item.length() - 1);
			}
		}
		return "";
	}
}
