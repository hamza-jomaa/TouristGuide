<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="ConnectionDB.DBCon"%>
<%@ page import="model.*"%>
<%@ page import="java.util.*"%>
<%
User hamza = (User) request.getSession().getAttribute("hamza");
if (hamza != null) {
	response.sendRedirect("home.jsp");
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>A Tourist Guide for Traffic and Litter Violations Login</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
</head>
<body style="background-color: #f8f9fa;">
	<%
	if (request.getAttribute("message") != null) {
	%>
	<div class="alert alert-danger text-center" role="alert">
		<%=request.getAttribute("message")%>
	</div>
	<%
	}
	%>

	<%@include file="includes/navbar.jsp"%>

	<form class="vh-100 gradient-custom" method="post" action="user-login">
		<div class="container py-5 h-100">
			<div
				class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-12 col-md-8 col-lg-6 col-xl-8">
					<div class="card bg-dark text-white" style="border-radius: 1rem;">
						<div class="card-body p-5 text-center">

							<div class="mb-md-5 mt-md-4 pb-5">

								<h2 class="fw-bold mb-2 text-uppercase">Login</h2>
								<p class="text-white-50 mb-5">Please enter your phone number
									and password!</p>

								<div class="form-outline form-white mb-4">
									<label class="form-label" for="Login-phone">Phone
										Number</label> <input type="tel" id="Login-phone"
										class="form-control form-control-lg" name="Login-phone" />
								</div>

								<div class="form-outline form-white mb-4">
									<label class="form-label" for="Login-password">Password</label>
									<input type="password" id="Login-password"
										class="form-control form-control-lg" name="Login-password" />

								</div>

								<p class="small mb-5 pb-lg-2">
									<a class="text-white-50" href="#!">Forgot password?</a>
								</p>

								<button class="btn btn-outline-light btn-lg px-5" type="submit">Login</button>

								<div class="d-flex justify-content-center text-center mt-4 pt-1">
									<a href="#!" class="text-white"><i
										class="fab fa-facebook-f fa-lg"></i></a> <a href="#!"
										class="text-white"><i
										class="fab fa-twitter fa-lg mx-4 px-2"></i></a> <a href="#!"
										class="text-white"><i class="fab fa-google fa-lg"></i></a>
								</div>

							</div>

							<div>
								<p class="mb-0">
									Don't have an account? <a href="#!"
										class="text-white-50 fw-bold">Sign Up</a>
								</p>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</form>

	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
</body>
</html>
