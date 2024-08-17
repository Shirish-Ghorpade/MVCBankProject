<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<title>Login</title>
<style>
body {
	background: linear-gradient(to top left, #39b385, #9be15d);
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
	color: #333;
}

.container {
	background-color: #ffffff;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 20px;
	max-width: 400px;
	width: 100%;
	text-align: center;
}

h1 {
	margin-bottom: 20px;
	color: #39b385;
}

.form-label {
	font-weight: bold;
}

.form-control {
	border-radius: 4px;
	border: 1px solid #ced4da;
	padding: 10px;
}

.form-control:focus {
	box-shadow: none;
	border-color: #39b385;
}

.btn-login {
	background: linear-gradient(to top left, #39b385, #9be15d);
	color: white;
	border: none;
	border-radius: 4px;
	padding: 10px 20px;
	font-size: 16px;
	width: 100%;
	margin-top: 10px;
	transition: background 0.3s, transform 0.3s;
}

.btn-login:hover {
	background: linear-gradient(to top left, #2f8a68, #82d743);
	transform: scale(1.05);
}

.btn-secondary {
	background: #f8f9fa;
	color: #333;
	border: 1px solid #ced4da;
	border-radius: 4px;
	padding: 10px 20px;
	font-size: 16px;
	width: 100%;
	margin-top: 10px;
	transition: background 0.3s;
}

.btn-secondary:hover {
	background: #e2e6ea;
}

.select-custom {
	border-radius: 4px;
	border: 1px solid #ced4da;
	padding: 10px;
	width: 100%;
	background: #ffffff;
	color: #333;
	transition: border-color 0.3s;
}

.select-custom:focus {
	border-color: #39b385;
	outline: none;
}

.btn-gradient {
	background: linear-gradient(to top left, #39b385, #9be15d);
	color: white;
	border: none;
	border-radius: 4px;
	padding: 10px 20px;
	font-size: 16px;
	transition: background 0.3s, transform 0.3s;
}

.btn-gradient:hover {
	background: linear-gradient(to top left, #2f8a68, #82d743);
	transform: scale(1.05);
}

.modal-content {
	border-radius: 8px;
}

.modal-body {
	background-color: #ffffff;
	color: #333;
}
</style>
</head>
<body>
	<div class="container">
		<form id="loginForm" action="login" method="post">
			<div class="mb-3">
				<label for="emailID" class="form-label">Email ID</label> <input
					type="email" class="form-control" id="email" name="username"
					required>
			</div>
			<div class="mb-3">
				<label for="password" class="form-label">Password</label> <input
					type="password" class="form-control" id="password" name="password"
					required>
			</div>
			<div class="mb-3">
				<label for="loginAs" class="form-label">Login As</label> <select
					id="loginAs" name="role" class="form-control select-custom"
					required>
					<option value="" disabled selected>Select Role</option>
					<option value="admin">Admin</option>
					<option value="customer">Customer</option>
				</select>
			</div>
			<button type="submit" class="btn btn-login">Login</button>
			<button type="reset" class="btn btn-secondary">Reset</button>
		</form>
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
					<%=request.getAttribute("error")%></div>
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
