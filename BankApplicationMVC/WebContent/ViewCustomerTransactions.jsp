<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transaction List</title>
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

/* Container and Table Styles */
.container {
	background-color: #ffffff; /* White background for the container */
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 20px;
	max-width: 800px;
	margin: 80px auto; /* Center align and account for fixed navbar */
	position: relative; /* For absolute positioning of the dropdown */
}

.sort-dropdown {
	position: absolute;
	top: 80px; /* Distance from the navbar */
	right: 20px; /* Distance from the right edge */
	width: 200px; /* Set width for the dropdown */
}

.table {
	margin-top: 20px;
}

table, th, td {
	border: 1px solid #dee2e6;
	text-align: center; /* Center align table content */
}

th, td {
	padding: 12px;
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

/* Select Dropdown Styles */
.form-select {
	border: 1px solid #ced4da;
	border-radius: 4px;
	color: black;
}

.form-select option {
	background-color: #ffffff;
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
		<a class="navbar-brand" href="<%=request.getContextPath()%>/customerurl">
			<img src="./img/logo.webp" alt="Bankist logo" id="logo" />
		</a>
		<div class="collapse navbar-collapse">
			<ul class="navbar-nav ml-auto">
				<li class="nav-item"><a class="nav-link"
					href="<%=request.getContextPath()%>/logoutreq">Logout</a></li>
			</ul>
		</div>
	</nav>

	<!-- Form for sorting -->
	<form action="passbook" method="get">
		<div class="sort-dropdown">
			<select name="sort" class="form-select" onchange="this.form.submit()">
				<option value="">Select Option</option>
				<option value="date">Sort by Date</option>
				<option value="type">Sort by Type</option>
				<option value="amount">Sort by Amount</option>
			</select>
		</div>
		<div class="container">
			<!-- Transaction Table -->
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>Sender Acc No</th>
						<th>Receiver Acc No</th>
						<th>Type of Transaction</th>
						<th>Amount</th>
						<th>Date</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="Transaction" items="${transactionsList}">
						<tr>
							<td>${Transaction.getSenderAccountNumber()}</td>
							<td>${Transaction.getReceiverAccountNumber()}</td>
							<td>${Transaction.getTypeOfTransaction()}</td>
							<td>${Transaction.getAmount()}</td>
							<td>${Transaction.getDate()}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</form>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>
</html>
