<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>VolatilitySurf</title>
</head>
<body>
	<h1></h1>
	<form action="/options" method="POST">
		<label for="symbol">Search Options by Ticker:</label>
		<input type="text" name="symbol"/>
		<input type="submit" value="search" class="btn"/>
	</form>
</body>
</html>