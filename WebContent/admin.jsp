<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="ConnectionDB.DBCon"%>
<%@ page import="model.*"%>
<%@ page import="dao.*"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
User hamza = (User) request.getSession().getAttribute("hamza");
if (hamza != null) {
	request.setAttribute("hamza", hamza);
}
ReportDAO reportDAO = new ReportDAO(DBCon.getConnection());
// Retrieve the list of reports from the database or a data source

List<Report> reports = reportDAO.getReports();
request.setAttribute("reports", reports);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Page</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<style>
.card-title {
	color: blue;
	font-weight: bold;
}

.btn-custom {
	background-color: #808080; /* Medium gray */
	border: 2px solid #808080; /* Medium gray */
	border-radius: 20px;
	color: #fff; /* White text color */
	margin-left: 30px;
}

.btn-custom:hover {
	background-color: #707070; /* Slightly darker gray on hover */
	border-color: #707070; /* Slightly darker gray on hover */
}
</style>
</head>
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
		<div class="card-header my-3 text-white bg-dark">All Reports</div>

	</div>
	<div class="container mt-4">
		<%-- Check if there are reports to display --%>
		<c:if test="${not empty reports}">
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
						<th>Updated Status</th>
					</tr>
				</thead>
				<tbody>
					<%-- Iterate over the list of reports and generate table rows --%>
					<c:forEach var="report" items="${reports}">
						<tr>
							<td>${report.phoneNumber}</td>
							<td>${report.date}</td>
							<td>${report.country}</td>
							<td>${report.city}</td>
							<td>${report.violationType}</td>
							<td>${report.media}</td>
							<td>${report.status}</td>
							<td>
								<form action="update-report" method="post">
									<input type="hidden" name="reportId" value="${report.id}">
									<select name="approvalStatus">
										<option value="approved">Approve</option>
										<option value="disapproved">Disapprove</option>
									</select>
									<button class="btn btn-custom" type="submit">Update</button>
									<td>
								</form>
							</td>
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
