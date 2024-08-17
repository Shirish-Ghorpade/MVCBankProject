<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Bank Account</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<style>
body {
	background-color: #ffffff; /* White background for the body */
	color: #333;
	padding: 0;
	margin: 0;
	box-sizing: border-box;
}

.navbar {
	background: linear-gradient(to top left, #39b385, #9be15d);
	/* Gradient background for the navbar */
	padding: 10px 20px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	z-index: 1000;
}

.navbar-brand img {
	height: 36px; /* Logo size */
}

.navbar .navbar-brand {
	color: #fff; /* Text color for the brand */
	font-size: 1.75rem; /* Font size for the brand */
	font-weight: bold;
}

.navbar-nav {
	margin-left: auto; /* Pushes links to the right */
}

.navbar-nav .nav-link {
	color: #fff; /* Text color for links */
	font-size: 1.25rem; /* Font size for links */
	margin-left: 20px; /* Space between links */
}

.navbar-nav .nav-link:hover {
	color: #f1f1f1; /* Hover color for links */
}

.container {
	background-color: #ffffff; /* White background for the container */
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 20px;
	max-width: 600px;
	width: 100%;
	margin: 80px auto 0 auto; /* Margin to account for fixed navbar */
}

h2 {
	text-align: center;
	margin-bottom: 20px;
}

.input-group {
	margin-bottom: 20px;
}

.btn-custom {
	background: linear-gradient(to top left, #39b385, #9be15d);
	/* Gradient background */
	color: white;
	border: none;
	border-radius: 4px;
	padding: 10px 20px;
	font-size: 16px;
}

.btn-custom:hover {
	background: linear-gradient(to top left, #2f8a68, #82d743);
	/* Darker gradient on hover */
	color: white;
}

.btn-generate {
	background: #4CAF50; /* A solid green color for the generate button */
	color: white;
	border: none;
	border-radius: 4px;
	padding: 10px 20px;
	font-size: 16px;
	margin-top: 20px; /* Space between buttons */
}

.btn-generate:hover {
	background: #388E3C; /* Darker green for hover effect */
}

table {
	width: 100%;
	margin-top: 20px;
	border-collapse: collapse;
}

table, th, td {
	border: 1px solid #dee2e6;
}

th, td {
	padding: 12px;
	text-align: center; /* Center-align text in table cells */
}

th {
	background: linear-gradient(to top left, #39b385, #9be15d);
	/* Gradient background for table header */
	color: white;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}

tr:hover {
	background-color: #e8f5e9;
}

p {
	color: #333;
	font-size: 16px;
}

.modal-content {
	background: #ffffff;
	border-radius: 8px;
	border: none;
}

.modal-header {
	background: linear-gradient(to top left, #39b385, #9be15d);
	/* Gradient background for modal header */
	color: white;
	border-bottom: none;
}

.modal-footer {
	border-top: none;
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
		<a class="navbar-brand" href="<%=request.getContextPath()%>/adminurl">
			<img src="./img/logo.webp" alt="Bankist logo" id="logo" />
		</a>

		<div class="collapse navbar-collapse">
			<ul class="navbar-nav ml-auto">
				<li class="nav-item"><a class="nav-link"
					href="<%=request.getContextPath()%>/logoutreq" type="submit">Logout</a></li>
			</ul>
		</div>
	</nav>

	<div class="container text-center">
		<form action="addaccount" method="post">
			<div class="input-group mb-3">
				<input type="text" class="form-control" name="searchByCustomerID"
					placeholder="Enter Customer ID" required>
				<button class="btn btn-custom" type="submit" id="button-addon2">Submit</button>
			</div>
		</form>

		<h2>Customer Details</h2>

		<!-- Check if customer attribute is available -->
		<c:choose>
			<c:when test="${not empty customer}">
				<table>
					<thead>
						<tr>
							<th>Attribute</th>
							<th>Value</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Customer ID</td>
							<td>${customer.customerID}</td>
						</tr>
						<tr>
							<td>First Name</td>
							<td>${customer.firstName}</td>
						</tr>
						<tr>
							<td>Last Name</td>
							<td>${customer.lastName}</td>
						</tr>
						<tr>
							<td>Email</td>
							<td>${customer.emailID}</td>
						</tr>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<p>No customer data available.</p>
			</c:otherwise>
		</c:choose>

		<!-- Added "Generate Account Number" button below the form -->
		<form action="openaccount" method="get">
			<input class="btn btn-generate" type="submit" name="generate_account"
				value="Generate Account" id="generateAccountNumber" />
		</form>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="showModal" tabindex="-1"
		aria-labelledby="showModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="showModalLabel"><%=request.getAttribute("title")%></h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body"><%=request.getAttribute("message")%></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>

	<script type="text/javascript">
		document.addEventListener('DOMContentLoaded', function () {
			var showModal = '<%=request.getAttribute("showModal")%>';
			if (showModal === 'true') {
				var modal = new bootstrap.Modal(document
						.getElementById('showModal'));
				modal.show();
			}
		});
	</script>
</body>
</html>
