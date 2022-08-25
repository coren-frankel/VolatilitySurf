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
	<h1>Testing Phase** Currently will save any new ticker upload to DB</h1>
	<h3>Be sure to avoid search tickers redundantly for now</h3>
	<form action="/fetch" method="POST">
		<label for="symbol">Search Options by Ticker:</label>
		<input type="text" name="symbol"/>
		<input type="submit" value="search" class="btn"/>
	</form>
</body>
</html>