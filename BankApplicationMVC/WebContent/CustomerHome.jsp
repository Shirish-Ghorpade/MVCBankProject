<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Home</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<style>
body {
	background-color: #f8f9fa;
	color: #333;
	padding: 0;
	margin: 0;
	box-sizing: border-box;
}

.navbar {
	background: linear-gradient(to top left, #39b385, #9be15d);
	padding: 10px 20px;
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	z-index: 1000;
}

.navbar-brand img {
	height: 36px; /* Adjust logo size */
}

.navbar .navbar-brand {
	color: #fff;
	font-size: 1.5rem; /* Adjust font size */
	font-weight: bold;
}

.navbar-nav {
	margin-left: auto; /* Pushes links to the right */
}

.navbar-nav .nav-link {
	color: #fff;
	font-size: 1.25rem; /* Increase font size */
	margin-left: 20px; /* Space between links */
}

.navbar-nav .nav-link:hover {
	color: #d4f1e3;
}

.container {
	background-color: #ffffff;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 40px;
	max-width: 900px;
	width: 90%;
	margin: 100px auto 0; /* Margin to account for fixed navbar */
}

h2 {
	text-align: center;
	margin-bottom: 30px;
	font-size: 2rem;
}

.card-container {
	display: flex;
	flex-wrap: wrap;
	gap: 20px; /* Space between cards */
	justify-content: center; /* Center cards horizontally */
}

.card {
	width: 100%;
	max-width: 300px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	border: none;
}

.card-body {
	text-align: center;
}

.card-button {
	background: linear-gradient(to top left, #39b385, #9be15d);
	color: white;
	border: none;
	padding: 10px;
	cursor: pointer;
	border-radius: 4px;
	transition: background 0.3s ease, transform 0.2s ease;
}

.card-button:hover {
	background: linear-gradient(to top left, #2f8a68, #82d743);
	transform: scale(1.05);
}
</style>
</head>
<body>
	<%
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage.jsp");
		}
	%>
	<nav class="navbar navbar-expand-lg navbar-light"> <a
		class="navbar-brand" href="#"> <img src="./img/logo.webp"
		alt="Bankist logo" id="logo" />
	</a>
	<div class="collapse navbar-collapse">
		<ul class="navbar-nav ml-auto">
			<li class="nav-item"><a class="nav-link"
				href="<%=request.getContextPath()%>/logoutreq">Logout</a></li>
		</ul>
	</div>
	</nav>
	<div class="container">
		<h2>
			<%
				String username = (String) session.getAttribute("username");
			%>
			<%=(username != null) ? "Welcome " + username : "Welcome, Guest"%>
		</h2>
		<div class="card-container">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">Pass Book</h5>
					<p class="card-text">View and download your pass book.</p>
					<form action="customercontroller" method="get">
						<input class="card-button" name="passbook" type="submit"
							value="View Pass Book">
					</form>
				</div>
			</div>
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">New Transactions</h5>
					<p class="card-text">Initiate new transactions.</p>
					<form action="customercontroller" method="get">
						<input class="card-button" name="newtransaction" type="submit"
							value="Start New Transaction">
					</form>
				</div>
			</div>
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">Edit Profile</h5>
					<p class="card-text">Update your profile information.</p>
					<form action="customercontroller" method="get">
						<input class="card-button" name="editprofile" type="submit"
							value="Edit Your Profile">
					</form>
				</div>
			</div>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>
</html>
