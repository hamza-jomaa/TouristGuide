<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="ConnectionDB.DBCon"%>
<%@ page import="model.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.*"%>
<%@ page import="java.time.LocalDate"%>
<%@ page import="java.time.LocalDateTime"%>

<%
User hamza = (User) request.getSession().getAttribute("hamza");
if (hamza != null) {
	request.setAttribute("hamza", hamza);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Traffic and Litter Violation Report Form</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">

<style>
/* CSS Styles */
body {
	font-family: Arial, sans-serif;
	background-color: #f8f9fa;
}

h1 {
	color: #333;
	text-align: center;
}

form {
	max-width: 400px;
	margin: 0 auto;
}

label {
	display: block;
	margin-bottom: 10px;
	font-weight: bold;
}

input[type="text"], select, input[type="file"] {
	width: 100%;
	padding: 4px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

input[type="submit"] {
	background-color: #4CAF50;
	color: white;
	padding: 10px 16px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-weight: bold;
	display: block;
	margin: 0 auto;
}

input[type="submit"]:hover {
	background-color: #45a049;
}

.error-message {
	color: red;
	margin-top: 5px;
}

.center-container {
	display: flex;
	align-items: center;
	justify-content: center;
	height: 15vh;
}

.submit-message {
	text-align: center;
	margin-top: 10px;
}
</style>
</head>
<body>

	<%@include file="includes/navbar.jsp"%>

	<h1>Report Violation</h1>
	<form action="Report-Controller" method="POST"
		enctype="multipart/form-data">
		<label for="date">Date:</label> <input type="text" id="date"
			name="date" value="<%=LocalDate.now()%>" readonly><br>
		<br> <label for="phone">Phone Number:</label> <input type="text"
			id="phone" name="phone" required><br>
		<br> <label for="country">Country:</label> <select id="country"
			name="country" required>
			<option value="">Select Country</option>
			<option value="Palestine">Palestine</option>
			<option value="United States">United States</option>
			<option value="United Kingdom">United Kingdom</option>
		</select><br>
		<br> <label for="city">City:</label> <select id="city"
			name="city" required>
			<option value="">Select City</option>
		</select><br>
		<br> <label for="violationType">Violation Type:</label> <select
			id="violationType" name="violationType" required>
			<option value="">Select Violation Type</option>
			<option value="redLightCrossing">traffic_light</option>
			<option value="stopSignViolation">stop_sign</option>
			<option value="jaywalking">jaywalking</option>
			<option value="littering">littering</option>
		</select><br>
		<br> <label for="media">Upload Picture/Video:</label> <input
			type="file" id="media" name="media" accept="image/*,video/*" required><br>
		<br> <input type="submit" value="Submit Report">
	</form>

	<div class="center-container">
		<%
		String successMessage = (String) request.getAttribute("successMessage");
		if (successMessage != null) {
		%>
		<div class="submit-message">
			<div class="alert alert-success" role="alert">
				<%=successMessage%>
			</div>
		</div>
		<%
		}
		%>
	</div>

	<script>
		// JavaScript code
		var countrySelect = document.getElementById("country");
		var citySelect = document.getElementById("city");

		// Add event listener to the country selection
		countrySelect.addEventListener("change", function() {
			citySelect.innerHTML = ""; // Clear the city options

			// Add the default "Select City" option
			addOption(citySelect, "Select City", "");

			// Populate the city options based on the selected country
			if (countrySelect.value === "Palestine") {
				addOption(citySelect, "Jerusalem", "Jerusalem");
				addOption(citySelect, "Haifa", "Haifa");
				addOption(citySelect, "Jaffa", "Jaffa");
			} else if (countrySelect.value === "United States") {
				addOption(citySelect, "New York", "New York");
				addOption(citySelect, "Los Angeles", "Los Angeles");
				addOption(citySelect, "Chicago", "Chicago");
			} else if (countrySelect.value === "United Kingdom") {
				addOption(citySelect, "Manchester", "Manchester");
				addOption(citySelect, "London", "London");
				addOption(citySelect, "Birmingham", "Birmingham");
			}
		});

		// Function to add an option to a select element
		function addOption(selectElement, text, value) {
			var option = document.createElement("option");
			option.text = text;
			option.value = value;
			selectElement.add(option);
		}
	</script>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
</body>
</html>
