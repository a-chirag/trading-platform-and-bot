<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- sdjkfjkh -->
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>Home Page</title>

<!-- Bootstrap core CSS -->
<link href="/book/bootstrap.min.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="/book/dashboard.css" rel="stylesheet">
<link rel="stylesheet" href="/book/jquery-ui.css">
  <script src="https://d3js.org/d3.v3.js"></script>
</head>

<body>
	<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
		<a class="navbar-brand" href="#">BOOK</a>
		<button class="navbar-toggler d-lg-none" type="button"
			data-toggle="collapse" data-target="#navbarsExampleDefault"
			aria-controls="navbarsExampleDefault" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarsExampleDefault">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link" href="#">Home
						<span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="#">${user.username }
				</a></li>
				<li class="nav-item"><button id="balance"
						class="btn btn-outline-success my-2 my-sm-0">$
						${userdata.balance }</button></li>
				<%-- <li class="nav-item"><button id="netWorth"
						class="btn btn-outline-success my-2 my-sm-0" onclick='netWorth(${userdata.userId})'><script type="text/javascript"></script></button></li> --%>
			</ul>
			<form class="form-inline mt-2 mt-md-0">
				<input class="form-control mr-sm-2" type="text" placeholder="Search"
					aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
				<input type="button" id="loginbutton"
					class="btn btn-outline-danger my-2 my-sm-0" onclick="logout()"
					value="logout">
			</form>
		</div>
	</nav>

	<div class="container-fluid">
		<div class="row">
			<nav class="col-sm-3 col-md-2 d-none d-sm-block bg-light sidebar">
				<ul class="nav nav-pills flex-column">
					<li class="nav-item"><a class="nav-link" href="#overview"
						onclick='overview()' data-toggle="tab">Overview <span
							class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item"><a class="nav-link " href="#addorder"
						onclick='addorder()' data-toggle="tab">Add Order</a></li>
					<li class="nav-item"><a class="nav-link " href="#portfolio"
						onclick='portfolio()' data-toggle="tab">Portfolio</a></li>
					<li class="nav-item"><a class="nav-link" href="#pending"
						onclick='pending()' data-toggle="tab">Pending Orders</a></li>
					<!-- <li class="nav-item"><a class="nav-link" href="#workplane"
						onclick='executed()' data-toggle="tab">Executed Orders</a></li> 
					<li class="nav-item"><a class="nav-link " href="home.jsp">Refresh</a></li>-->
				</ul>

			</nav>

			<main class="col-sm-9 ml-sm-auto col-md-10 pt-3" role="main">
			<div class="tab-content">
				<div id="addorder" class="tab-pane fade in"></div>
				<div id="portfolio" class="tab-pane fade in"></div>
				<div id="overview" class="tab-pane fade in"></div>
				<div id="pending" class="tab-pane fade in"></div>
				<div id="executed" class="tab-pane fade in"></div>
			</div>
			</main>
		</div>
	</div>

	<!-- Login Modal -->
	<div class="modal fade" id="loginModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header" style="padding: 35px 50px;">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4>
						<span class="glyphicon glyphicon-lock"></span> Login
					</h4>
				</div>
				<div class="modal-body" style="padding: 40px 50px;">
				<div id="invalid"></div>
					<form role="form" id="loginForm">
						<div class="form-group">
							<label for="username"><span
								class="glyphicon glyphicon-user"></span> Username</label> <input
								type="text" class="form-control" name="username" id="username"
								placeholder="Enter email">
						</div>
						<div class="form-group">
							<label for="password"><span
								class="glyphicon glyphicon-eye-open"></span> Password</label> <input
								type="text" class="form-control" name="password" id="password"
								placeholder="Enter password">
						</div>
						<div class="checkbox">
							<label><input type="checkbox" value="" checked>Remember
								me</label>
						</div>
						<input type="button" class="btn btn-success btn-block"
							value="login" onclick="login()">
					</form>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-danger btn-default pull-left"
						data-dismiss="modal">
						<span class="glyphicon glyphicon-remove"></span> Cancel
					</button>
					<p>
						Not a member?  <button onclick="register()">Sign Up</button>
						
					</p>
					<p>
						Forgot <a href="#">Password?</a>
					</p>
				</div>
			</div>
		</div>
	</div>
	
	<!-- Register Modal -->
	<div class="modal fade" id="registerModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header" style="padding: 35px 50px;">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4>
						<span class="glyphicon glyphicon-lock"></span> Register
					</h4>
				</div>
				<div class="modal-body" style="padding: 40px 50px;">
					<form role="form">
						<div class="form-group">
							<label for="rusername"><span
								class="glyphicon glyphicon-user"></span> Username</label> <input
								type="text" class="form-control" name="rusername" id="rusername"
								placeholder="Enter username">
						</div>
						<div class="form-group">
							<label for="rpassword"><span
								class="glyphicon glyphicon-eye-open"></span> Password</label> <input
								type="text" class="form-control" name="rpassword" id="rpassword"
								placeholder="Enter password">
						</div>
						<input type="button" class="btn btn-success btn-block"
							value="register" onclick="registerUser()">
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<div class="modal-content addOrder" id="addOrder">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						align='right'>&times;</button>

					<h6 class="modal-title">Order Added</h6>
				</div>
			</div>

		</div>
	</div>
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="/book/jquery-3.2.1.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="/book/jquery.min.js"><\/script>')
	</script>
	<script src="/book/popper.min.js"></script>
	<script src="/book/dashboard.js"></script>
	<script src="/book/utils.js"></script>

	<script src="/book/jquery-ui.js"></script>
	<script src="/book/bootstrap.min.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="/book/ie10-viewport-bug-workaround.js"></script>
</body>
</html>