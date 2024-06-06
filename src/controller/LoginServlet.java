package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import dao.UserDAO;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ConnectionDB.DBCon;

@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("LogIn.jsp");
	}

	// ...

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			String phone = request.getParameter("Login-phone");
			String password = request.getParameter("Login-password");
			try {
				UserDAO udao = new UserDAO(DBCon.getConnection());
				User user = udao.userLogin(phone, password);
				System.out.println("User: " + user); // Debugging statement

				if (user != null) {
					if (user.isBlocked()) {
						System.out.println("User is blocked."); // Debugging statement
						request.setAttribute("message",
								"Your account has been blocked. Please contact the administrator.");
						request.getRequestDispatcher("LogIn.jsp").forward(request, response);
						return;
					} else {
						if (user.getName().equals("admin")) {
							System.out.println("User is admin."); // Debugging statement
							request.getSession().setAttribute("admin", user);
							response.sendRedirect("admin.jsp");
							return;
						} else {
							System.out.println("User is regular user."); // Debugging statement
							request.getSession().setAttribute("hamza", user);
							response.sendRedirect("home.jsp");
							return;
						}
					}
				} else {
					System.out.println("User not found."); // Debugging statement
					request.setAttribute("message", "Invalid phone number or password. Please try again.");
					request.getRequestDispatcher("LogIn.jsp").forward(request, response);
					return;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
