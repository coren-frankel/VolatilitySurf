<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- for Bootstrap CSS -->
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<!-- For any Bootstrap that uses JS or jQuery-->
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<title>Options Page</title>
</head>
<body class="m-4">
	<div class="d-flex justify-content-between align-items-center">
		<h1><c:out value="${ticker.getShortName()}"/> (<c:out value="${ticker.getSymbol()}"/>)</h1>
		<div class="d-flex justify-content-between">
			<a href="#" class="btn btn-sm btn-outline-dark me-3">Volatility Surface</a>
			<a href="/" class="btn btn-sm btn-outline-dark">Home</a>
		</div>
	</div>
	<br>
	<h3>
		<c:set var="currency" value="${ticker.getCurrency()}"/>
		<c:choose>
			<c:when test="${currency == 'USD'}">$</c:when>
			<c:when test="${currency == 'GBP'}">£</c:when>
			<c:when test="${currency == 'EUR'}">€</c:when>
			<c:otherwise>$<c:out value="${currency}"/></c:otherwise>
		</c:choose>
		<c:out value="${ticker.getRegularMarketPrice()}"/> 
		(<c:if test="${ticker.getRegularMarketChangePercent() > 0}"><span class="text-success">+</c:if>
		<c:if test="${ticker.getRegularMarketChangePercent() < 0}"><span class="text-danger"></c:if>
		<fmt:formatNumber type="number" pattern="##.##%" value="${ticker.getRegularMarketChangePercent()}"/></span>)
	</h3>
	<h3>Closing price <fmt:formatDate type="date" value="${ticker.getRegularMarketTime()}"/></h3>
	<table class="table table-striped table-secondary" style="font-size:50%;">
		<thead>
			<tr>
				<th>Contract Name</th>
				<th>Last Trade Date</th>
				<th>Strike</th>
				<th>Last Price</th>
				<th>Bid</th>
				<th>Ask</th>
				<th>Change</th>
				<th>% Change</th>
				<th>Volume</th>
				<th>Open Interest</th>
				<th>Implied Volatility</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="option" items="${ticker.getOptions()}">
			<tr>
				<td><c:out value="${option.getContractSymbol()}"/></td>
				<td><c:out value="${option.getLastTradeDate()}"/></td>
				<td><c:out value="${option.getStrike()}"/></td>
				<td><c:out value="${option.getLastPrice()}"/></td>
				<td><c:out value="${option.getBid()}"/></td>
				<td><c:out value="${option.getAsk()}"/></td>
				<td><c:out value="${option.getAbsoluteChange()}"/></td>
				<td><c:out value="${option.getPercentChange()}"/></td>
				<td><c:out value="${option.getVolume()}"/></td>
				<td><c:out value="${option.getOpenInterest()}"/></td>
				<td><c:out value="${option.getImpliedVolatility()}"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>