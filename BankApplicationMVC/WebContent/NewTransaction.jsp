<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Transaction</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<style>
body {
	background-color: #f8f9fa;
	color: #333;
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	box-sizing: border-box;
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
	height: 36px; /* Increase logo size */
}

.navbar .navbar-brand {
	color: #333;
	font-size: 1.75rem; /* Increase font size */
	font-weight: bold;
}

.navbar-nav {
	margin-left: auto; /* Pushes links to the right */
}

.navbar-nav .nav-link {
	color: #333;
	font-size: 1.25rem; /* Increase font size */
	margin-left: 20px; /* Space between links */
}

.navbar-nav .nav-link:hover {
	color: #39b385;
}

.container {
	background: #ffffff;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	padding: 40px;
	max-width: 600px;
	width: 90%;
	margin: 100px auto 0; /* Margin to account for fixed navbar */
}

.form-group label {
	font-weight: bold;
}

.form-group input, .form-group select {
	border-radius: 4px;
	border: 1px solid #ccc;
	padding: 0.5rem;
	width: 100%;
}

.btn-custom {
	background: linear-gradient(to top left, #39b385, #9be15d);
	color: #fff;
	border: none;
	padding: 10px 20px;
	border-radius: 4px;
	transition: background 0.3s ease, transform 0.2s ease;
}

.btn-custom:hover {
	background: linear-gradient(to top left, #2f8a68, #82d743);
	transform: scale(1.05);
}

.btn-secondary {
	background: #f8f9fa;
	color: #333;
	border: 1px solid #ced4da;
}

.btn-secondary:hover {
	background: #e2e6ea;
}

.modal-content {
	border-radius: 8px;
}
</style>
<script>
    function toggleInputField() {
        var transactionType = document.getElementById("transactionType").value;
        var transferField = document.getElementById("transferField");

        if (transactionType === "transfer") {
            transferField.style.display = "block";
        } else {
            transferField.style.display = "none";
        }
    }
</script>
</head>
<body>
	<%
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage.jsp");
		}
	%>
	<nav class="navbar navbar-expand-lg navbar-light"> <a
		class="navbar-brand" href="<%=request.getContextPath()%>/customerurl"> <img src="./img/logo.webp"
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
		<h2>Transaction</h2>
		<form action="newtransaction" method="post">
			<div class="form-group mb-3">
				<label for="transactionType">Type of Transaction:</label> <select
					id="transactionType" name="transactionType" class="form-control"
					onChange="toggleInputField()">
					<option value="None">None</option>
					<option value="credit">Credit</option>
					<option value="debit">Debit</option>
					<option value="transfer">Transfer</option>
				</select>
			</div>
			<div class="form-group mb-3">
				<label for="amount">Amount:</label> <input type="text" id="amount"
					name="amount" class="form-control" placeholder="Amount">
			</div>
			<div id="transferField" style="display: none;"
				class="form-group mb-3">
				<label for="accountNumber">Enter Account Number:</label> <input
					type="text" id="accountNumber" name="receiverAccountNumber"
					class="form-control" placeholder="Account Number">
			</div>
			<button type="submit" class="btn btn-custom">Submit</button>
			<button type="reset" class="btn btn-secondary">Cancel</button>
		</form>
	</div>

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

