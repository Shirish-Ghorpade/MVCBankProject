<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Customer</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<style>
body {
	background-color: #ffffff; /* Set background color to white */
	color: #333;
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

.navbar {
	background: linear-gradient(to right, #39b385, #9be15d);
	/* Gradient background for navbar */
	padding: 8px 16px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	z-index: 1000;
}

.navbar-brand img {
	height: 32px; /* Logo size */
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
	font-size: 1.15rem; /* Font size for links */
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
	margin-top: 120px; /* Margin to account for fixed navbar */
}

h1 {
	text-align: center;
	margin-bottom: 20px;
}

.form-control, .form-control:focus {
	border: 1px solid #ced4da;
	border-radius: 4px;
}

button {
	border-radius: 4px;
	padding: 10px 20px;
	font-size: 16px;
	margin: 5px;
}

.btn-submit {
	background: linear-gradient(to top left, #39b385, #9be15d);
	color: white;
	border: none;
}

.btn-submit:hover {
	background: linear-gradient(to top left, #2f8a68, #82d743);
}

.btn-cancel {
	background: #f8f9fa;
	color: #333;
	border: 1px solid #ced4da;
}

.btn-cancel:hover {
	background: #e2e6ea;
}

.modal-content {
	background: #ffffff;
	border-radius: 8px;
	border: none;
}

.modal-header {
	background: linear-gradient(to right, #39b385, #9be15d);
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
					href="<%=request.getContextPath()%>/logoutreq">Logout</a></li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<h1>Add New Customer</h1>
		<form action="addcustomer" method="post">
			<div class="mb-3">
				<label for="firstName" class="form-label">Customer First
					Name</label> <input type="text" class="form-control" id="firstName"
					name="firstName" required>
			</div>
			<div class="mb-3">
				<label for="lastName" class="form-label">Customer Last Name</label>
				<input type="text" class="form-control" id="lastName"
					name="lastName" required>
			</div>
			<div class="mb-3">
				<label for="email_id" class="form-label">Email ID</label> <input
					type="email" class="form-control" id="email_id" name="email_id"
					required>
			</div>
			<div class="mb-3">
				<label for="password" class="form-label">Password</label> <input
					type="password" class="form-control" id="password" name="password"
					required>
			</div>
			<button type="submit" class="btn btn-submit">Submit</button>
			<button type="reset" class="btn btn-cancel">Cancel</button>
		</form>

		<!-- Modal Pop-Up -->
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
