<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="ConnectionDB.DBCon"%>
<%@ page import="model.*"%>
<%@ page import="dao.*"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
User hamza = (User) request.getSession().getAttribute("hamza");
if (hamza != null) {
	request.setAttribute("hamza", hamza);
}
ReportDAO reportDAO = new ReportDAO(DBCon.getConnection());

// Delete expired reports
reportDAO.deleteExpiredReports();

// Retrieve the list of approved reports from the database or a data source
List<Report> approvedReports = reportDAO.getApprovedReports();
request.setAttribute("approvedReports", approvedReports);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Approved Reports</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
</head>
<body>
	<style>
.card-title {
	color: blue;
	font-weight: bold;
}
</style>
<body style="background-color: #f8f9fa;">


	<nav class="navbar navbar-expand-lg navbar-light bg-dark">
		<div class="container">
			<a class="navbar-brand text-white" href="admin.jsp">A Tourist
				Guide for Traffic and Litter Violations</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav ml-auto">

					<li class="nav-item active"><a class="nav-link text-white"
						href="admin.jsp">Admin</a></li>
					<li class="nav-item"><a class="nav-link text-white"
						href="approved-reports.jsp">Approved Reports </a></li>
					<li class="nav-item"><a class="nav-link text-white"
						href="log-out">Logout</a></li>

				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<div class="card-header my-3 text-white bg-dark">Approved
			Reports</div>
	</div>
	<div class="container mt-4">

		<%-- Check if there are approved reports to display --%>
		<c:if test="${not empty approvedReports}">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Phone Number</th>
						<th>Date</th>
						<th>Country</th>
						<th>City</th>
						<th>Violation Type</th>
						<th>Media</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody>
					<%-- Iterate over the list of approved reports and generate table rows --%>
					<c:forEach var="report" items="${approvedReports}">
						<tr>
							<td>${report.phoneNumber}</td>
							<td>${report.date}</td>
							<td>${report.country}</td>
							<td>${report.city}</td>
							<td>${report.violationType}</td>
							<td>${report.media}</td>
							<td>${report.status}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>

	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
</body>
</html>
