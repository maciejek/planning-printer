<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title" /></title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
</head>
<body>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras" prefix="tilesx" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<tilesx:useAttribute name="current"/>

	<div class="container">

		<!-- Static navbar -->
		<nav class="navbar navbar-default" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href='<spring:url value="/"></spring:url>'>Planning Printer</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="${current == 'index' ? 'active' : ''}"><a href='<spring:url value="/"></spring:url>'>Home</a></li>
					<security:authorize access="hasRole('ROLE_ADMIN')"><li class="${current == 'users' ? 'active' : ''}"><a href="<spring:url value="/account.html" />">My account</a></li></security:authorize>
					<li class="${current == 'register' ? 'active' : ''}"><a href="<spring:url value="/register.html" />">Register</a></li>
					
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="active"><a href="./">Default <span
							class="sr-only">(current)</span></a></li>
					<security:authorize access="!isAuthenticated()"><li class="${current == 'login' ? 'active' : ''}"><a href="<spring:url value="/login.html" />">Login</a></li></security:authorize>
					<security:authorize access="isAuthenticated()"><li><a href="<spring:url value="/logout"/>">Logout</a></li></security:authorize>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
		<!--/.container-fluid --> </nav>

		<!-- Main component for a primary marketing message or call to action -->
		<div class="jumbotron">
			<h1>Marcin, serwer nie dziala!</h1>
			<p>Cos ladnego</p>
			<p>
				<a class="btn btn-lg btn-primary" href="../../components/#navbar"
					role="button">View navbar docs &raquo;</a>
			</p>
		</div>

		<tiles:insertAttribute name="body" />

		<br>
		<br>

		<center>
			<tiles:insertAttribute name="footer" />
		</center>
	</div>
	<!-- /container -->

</body>
</html>