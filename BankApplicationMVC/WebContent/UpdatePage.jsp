<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Profile</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<style>
body {
	font-family: Arial, sans-serif;
	background: #f8f9fa;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

.navbar {
	background: linear-gradient(to top left, #39b385, #9be15d);
	padding: 10px 20px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
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
	font-size: 1.75rem; /* Adjust font size */
	font-weight: bold;
}

.navbar-nav {
	margin-left: auto; /* Pushes links to the right */
}

.navbar-nav .nav-link {
	color: #fff;
	font-size: 1.25rem; /* Adjust font size */
	margin-left: 20px; /* Space between links */
}

.navbar-nav .nav-link:hover {
	color: #d4f1e3;
}

.container {
	background-color: white;
	border-radius: 8px;
	padding: 20px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	max-width: 400px;
	width: 100%;
	margin-top: 80px; /* Margin to account for fixed navbar */
}

h2 {
	color: #333;
	margin-bottom: 20px;
	text-align: center;
}

.form-group {
	display: flex;
	flex-direction: column;
	margin-bottom: 15px;
}

.form-group label {
	display: block;
	margin-bottom: 5px;
	color: #555;
}

.form-group input[type="text"], .form-group input[type="password"] {
	width: 100%;
	padding: 10px;
	border: 1px solid #ddd;
	border-radius: 4px;
}

.btn-login {
	background: linear-gradient(to top left, #39b385, #9be15d);
	border: none;
	color: white;
	font-size: 16px;
	cursor: pointer;
	padding: 10px;
	border-radius: 4px;
	margin-right: 10px;
}

.btn-login:hover {
	background: linear-gradient(to top left, #2f8a68, #82d743);
}

.btn-secondary {
	background-color: linear-gradient(to top left, #39b385, #9be15d);
	border: none;
	color: white;
	font-size: 16px;
	cursor: pointer;
	padding: 10px;
	border-radius: 4px;
	margin-left: 10px;
}

.btn-secondary:hover {
	background-color: linear-gradient(to top left, #39b385, #9be15d);
}

.button-container {
	display: flex;
	justify-content: center;
	margin-top: 20px;
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
		<a class="navbar-brand"
			href="<%=request.getContextPath()%>/customerurl"> <img
			src="./img/logo.webp" alt="Bankist logo" id="logo" />
		</a>
		<div class="collapse navbar-collapse">
			<ul class="navbar-nav ms-auto">
				<li class="nav-item"><a class="nav-link"
					href="<%=request.getContextPath()%>/logoutreq">Logout</a></li>
			</ul>
		</div>
	</nav>
	<%
		HttpSession session2 = request.getSession();	
	%>
	<div class="container">
		<h2>Edit Profile</h2>
		<form action="updatepage" method="post">
			<div class="form-group">
				<label for="firstName">First Name:</label> <input type="text"
					id="firstName" name="firstName"
					value=<%=session2.getAttribute("firstName")%>>
			</div>
			<div class="form-group">
				<label for="lastName">Last Name:</label> <input type="text"
					id="lastName" name="lastName"
					value="<%=session2.getAttribute("lastName")%>">
			</div>
			<div class="form-group">
				<label for="password">Password:</label> <input type="password"
					id="password" name="password" required>
			</div>
			<div class="form-group">
				<label for="password">New Password:</label> <input type="password"
					id="password" name="newpassword" required>
			</div>
			<div class="button-container">
				<button type="submit" class="btn-login">Submit</button>
				<button type="reset" class="btn-secondary">Cancel</button>
			</div>
		</form>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
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
