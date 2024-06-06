<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="ConnectionDB.DBCon"%>
<%@ page import="model.*"%>
<%@ page import="dao.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.*"%>
<%@ page import="dao.CityDAO"%>
<%@ page import="dao.ReportDAO"%>
<%@ page import="model.City"%>

<%
User hamza = (User) request.getSession().getAttribute("hamza");
if (hamza != null) {
	request.setAttribute("hamza", hamza);
}
CityDAO cityDAO = new CityDAO(DBCon.getConnection());
List<City> cities = cityDAO.getAllCities();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome to Tourist Guide!</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<style>
/* CSS Styles */
.city-name {
	color: #795548;
	font-family: 'Arial', sans-serif;
	font-size: 18.5px;
}

.country-name {
	color: #795548;
	font-family: 'Arial', sans-serif;
	font-size: 18px;
}

.safe {
	color: green;
}

.dangerous {
	color: red;
}

.excellent {
	color: green;
}

.dirty {
	color: red;
}

.card {
	border: none;
	border-radius: 8px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.card-img-top {
	border-radius: 8px 8px 0 0;
}

.card-body {
	padding: 20px;
}

.safety {
	font-weight: bold;
}

.cleanliness {
	font-weight: bold;
}

.sane {
	font-weight: bold;
}
</style>
</head>
<body style="background-color: #f8f9fa;">

	<%@include file="includes/navbar.jsp"%>

	<div class="container">
		<div class="card-header my-3 text-white bg-dark">All Cities</div>
		<div class="row">
			<%
			if (!cities.isEmpty()) {
				int counter = 0; // Counter variable to control layout
				for (City c : cities) {

					String cityName = c.getCityName();
					int trafficLightViolationCount = ReportDAO.getViolationCount(cityName, "traffic_light", DBCon.getConnection());
					int stopSignViolationCount = ReportDAO.getViolationCount(cityName, "stop_sign", DBCon.getConnection());
					int jaywalkingViolationCount = ReportDAO.getViolationCount(cityName, "jaywalking", DBCon.getConnection());
					int litteringViolationCount = ReportDAO.getViolationCount(cityName, "littering", DBCon.getConnection());

					// Determine cleanliness and safety status based on violation counts
					boolean isClean = litteringViolationCount >= 3;
					boolean isSafe = trafficLightViolationCount < 3 && stopSignViolationCount < 3;

					// Determine sanity status based on jaywalking violation count
					boolean isSane = jaywalkingViolationCount < 3;
			%>
			<%
			if (counter % 3 == 0 && counter != 0) {
			%>
		</div>
		<div class="row">
			<%
			}
			%>
			<div class="col-md-4 my-3">
				<div class="card" style="width: 22rem;">
					<img class="card-img-top" src="<%=c.getImages()%>"
						alt="Card image cap" style="height: 200px; object-fit: cover;">
					<div class="card-body">
						<h6>Here is some information about the city:</h6>
						<h5 class="city-name">
							City:
							<%=c.getCityName()%>
						</h5>
						<h5 class="country-name">
							Country:
							<%=c.getCountry().getCountryName()%>
						</h5>
						<h5 class="safety <%=isSafe ? "safe" : "dangerous"%>">
							Safety:
							<%=isSafe ? "Safe" : "Dangerous"%>
						</h5>
						<h5 class="cleanliness <%=isClean ? "excellent" : "dirty"%>">
							Cleanliness:
							<%=isClean ? "Excellent" : "Dirty"%>
						</h5>
						<h5 class="sane <%=isSane ? "excellent" : "dirty"%>">
							Sanity:
							<%=isSane ? "Sane" : "Insane"%>
						</h5>
					</div>
				</div>
			</div>
			<%
			counter++;
			}
			}
			%>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
</body>
</html>
