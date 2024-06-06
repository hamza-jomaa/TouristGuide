<nav class="navbar navbar-expand-lg navbar-light bg-dark">
	<div class="container">
		<a class="navbar-brand text-white" href="#">A Tourist
			Guide for Traffic and Litter Violations</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav ml-auto">
				<%
				if (hamza != null) {
				%>
				<li class="nav-item active"><a class="nav-link text-white"
					href="home.jsp">Home</a></li>
				<li class="nav-item"><a class="nav-link text-white"
					href="report.jsp">Report</a></li>
				<li class="nav-item"><a class="nav-link text-white"
					href="log-out">Logout</a></li>
				<%
				} else {
				%>
				<li class="nav-item"><a class="nav-link text-white"
					href="LogIn.jsp">Login</a></li>
				<%
				}
				%>
			</ul>
		</div>
	</div>
</nav>
