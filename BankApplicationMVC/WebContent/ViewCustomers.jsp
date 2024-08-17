<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer List</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<style>
/* Navbar Styles */
.navbar {
	background: linear-gradient(to top left, #39b385, #9be15d);
	padding: 10px 20px;
}

.navbar-brand img {
	width: 80px; /* Adjust size as needed */
	height: auto;
}

.navbar-brand {
	color: white !important;
}

.navbar-nav {
	margin-left: auto; /* Pushes links to the right */
}

.navbar-nav .nav-link {
	color: white !important;
	margin-left: 20px;
}

.navbar-nav .nav-link:hover {
	color: #d4f1e3 !important; /* Slightly lighter on hover */
}

/* Container and Form Styles */
.container {
	background-color: #ffffff; /* White background for the container */
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 20px;
	max-width: 800px;
	margin: 80px auto; /* Center align and account for fixed navbar */
}

.input-group {
	margin-bottom: 20px;
}

.btn-primary {
	background: linear-gradient(to top left, #39b385, #9be15d);
	border: none;
}

.btn-primary:hover {
	background: linear-gradient(to top left, #2f9d76, #88d75e);
	/* Slightly darker on hover */
}

/* Table Styles */
.table {
	margin-top: 20px;
}

table, th, td {
	border: 1px solid #dee2e6;
}

th, td {
	padding: 12px;
	text-align: left;
	text-align: center; /* Center align table content */
}

th {
	background: linear-gradient(to top left, #39b385, #9be15d);
	color: white;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}

tr:hover {
	background-color: #e8f5e9;
}
</style>
</head>
<body>
<%
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage.jsp");
		}
	%>
	<nav class="navbar navbar-expand-lg navbar-light">
		<a class="navbar-brand" href="<%=request.getContextPath()%>/adminurl"> <img src="./img/logo.webp"
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
		<!-- Search Form -->
		<form action="customers" method="get" class="mb-4">
			<div class="input-group">
				<input type="text" class="form-control" name="search"
					placeholder="Search by name" value="${param.search}">
				<button class="btn btn-primary" type="submit">Search</button>
			</div>
		</form>

		<!-- Customer Table -->
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Account Number</th>
					<th>Balance</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="customer" items="${customerList}">
					<tr>
						<td>${customer.firstName}</td>
						<td>${customer.lastName}</td>
						<td>${customer.accountNumber}</td>
						<td>${customer.balance}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>
</html>
