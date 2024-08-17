<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.aurionpro.entity.Customer"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Open Account</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<style>
body {
	background: #ffffff;
	color: #333;
	padding: 20px;
	margin: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
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
	max-width: 800px;
	width: 100%;
	background-color: #ffffff;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 20px;
	margin-top: 50px;
}

h2 {
	padding: 5%;
	text-align: center;
}

.btn-generate {
	background: #4CAF50;
	color: white;
	border: none;
	border-radius: 4px;
	padding: 10px 20px;
	font-size: 16px;
	margin-top: 20px;
	display: block;
	margin-left: auto;
	margin-right: auto;
}

.btn-generate:hover {
	background: #388E3C;
}

.form-control[readonly] {
	background-color: #f5f5f5;
}

.row-label {
	font-weight: bold;
	text-align: right;
}

.row-value {
	text-align: left;
}

.form-group {
	margin-bottom: 15px;
}

.row {
	margin-left: 0;
	margin-right: 0;
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
	<div class="container">
		<%
			Customer customerData = (Customer) session.getAttribute("customerData");
			if (customerData != null) {
		%>
		<h2>Customer Details</h2>
		<form action="generateaccount" method="get">
			<div class="row form-group">
				<div class="col-4 row-label">Customer ID:</div>
				<div class="col-6 row-value">
					<input type="text" class="form-control" name=cust-id
						value="<%=customerData.getCustomerID()%>" readonly />
				</div>
			</div>
			<div class="row form-group">
				<div class="col-4 row-label">First Name:</div>
				<div class="col-6 row-value">
					<input type="text" class="form-control"
						value="<%=customerData.getFirstName()%>" readonly />
				</div>
			</div>
			<div class="row form-group">
				<div class="col-4 row-label">Last Name:</div>
				<div class="col-6 row-value">
					<input type="text" class="form-control"
						value="<%=customerData.getLastName()%>" readonly />
				</div>
			</div>
			<div class="row form-group">
				<div class="col-4 row-label">Email ID:</div>
				<div class="col-6 row-value">
					<input type="text" class="form-control"
						value="<%=customerData.getEmailID()%>" readonly />
				</div>
			</div>
			<div class="row form-group">
				<div class="col-4 row-label">Balance:</div>
				<div class="col-6 row-value">
					<input type="text" class="form-control"
						placeholder="Minimum balance limit is 5000." name="balance" />
				</div>
			</div>
			<input class="btn btn-generate" type="submit" name="generate_account"
				value="Generate Account" id="generateAccountNumber" />
		</form>
		<%
			} else {
		%>
		<p>No customer data found in the session.</p>
		<%
			}
		%>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="showModal" tabindex="-1"
		aria-labelledby="showModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header"
					style="background: linear-gradient(to top left, #39b385, #9be15d); color: white;">
					<h5 class="modal-title" id="showModalLabel"><%=request.getAttribute("title")%></h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body" style="padding: 20px; color: #333;">
					<%=request.getAttribute("message")%></div>
				<div class="modal-footer" style="border-top: 1px solid #ced4da;">
					<button type="button" class="btn btn-gradient"
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
