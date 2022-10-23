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
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<title><c:out value="${ticker.getShortName()}"/> Stock Options</title>
</head>
<body class="m-4">
	<div class="d-flex justify-content-between align-items-center">
		<h3><c:out value="${ticker.getShortName()}"/> (<c:out value="${ticker.getSymbol()}"/>)</h3>
		<div class="d-flex justify-content-between">
			<a href="/volsurf" class="btn btn-sm btn-outline-dark me-3">Volatility Surface</a>
			<a href="/" class="btn btn-sm btn-outline-dark">Home</a>
		</div>
	</div>
	<small class="text-secondary"><c:out value="${ticker.getFullExchangeName()}"/> - <c:out value="${ticker.getFullExchangeName()}"/> Real Time Price. Currently in <c:out value="${ticker.getCurrency()}"/></small>
	<h1 class="mb-1"><!-- Do we want the currency symbol? Realized Yahoo doesn't have it. -->
		<fmt:formatNumber type="NUMBER" maxFractionDigits="2" minFractionDigits="2" value="${ticker.getRegularMarketPrice()}"/> 
		<c:if test="${ticker.getRegularMarketChangePercent() > 0}"><span class="text-success fs-5">+<fmt:formatNumber type="NUMBER" maxFractionDigits="2" minFractionDigits="2" value="${ticker.getRegularMarketChange()}"/> (+</c:if>
		<c:if test="${ticker.getRegularMarketChangePercent() < 0}"><span class="text-danger fs-5"><fmt:formatNumber type="NUMBER" maxFractionDigits="2" minFractionDigits="2" value="${ticker.getRegularMarketChange()}"/> (</c:if>
		<fmt:formatNumber type="PERCENT" maxFractionDigits="2" minFractionDigits="2" value="${ticker.getRegularMarketChangePercent()/100}"/>)</span>
	</h1>
	<fmt:timeZone value="US/Eastern"><!-- Hard Coding the timezone for NY Stock exchange -->
	<jsp:useBean id="now" class="java.util.Date"/>
	<c:set var="marketTime" value="${ticker.getRegularMarketTime()}"/>
	<fmt:formatDate var="td" value="${now}" pattern="E"/><!-- today variable to compare -->
	<fmt:formatDate var="mtd" value="${marketTime}" pattern="E"/><!-- marketTime day to compare -->
		<!-- Accounting for stock market holidays and weekends, render differently when market is open -->
	<c:choose>
		<c:when test="${ marketTime gt '09:30AM' && marketTime lt '04:00PM' && td == mtd }">
		<!-- last called market time is greater than 9:30am, less than 4pm & was today -->
			<small>As of <fmt:formatDate pattern="hh:mma z"  value="${marketTime}"/> Market Open</small>
		</c:when>
		<c:otherwise><small class="text-secondary">At close: <fmt:formatDate pattern="MMMM d hh:mma z"  value="${marketTime}"/></small></c:otherwise>
	</c:choose><br>
	<!-- Build out select drop down for options by expiry; Possible "straddle" toggle like yahoo finance? -->
	<div class="d-flex lh-1 my-2">
	<!-- ON CHANGE, RENDER LIST BY EXPIRY -->
		<form action="/setOptionsExpiry" method="POST">
		<select name="expiryDate" class="form-select" onchange="this.form.submit()">
			<c:forEach var="expiry" items="${session.expirations}">
				<option value="${expiry}">${expiry}</option>
			</c:forEach>
		</select>
		</form>
		<div class="d-inline p-2 rounded-1 ms-2" style="background-color:#CFE1FF;">In The Money</div>
	</div>
	<table class="table table-striped table-secondary text-end" style="font-size:60%;">
		<thead>
			<tr style="font-size:85%;">
				<th class="text-start">Contract Name</th>
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
		<c:choose>
			<c:when test="${option.getInTheMoney()}">
				<tr class="table-primary lh-1">
			</c:when>
			<!-- WHEN IN THE MONEY, RENDER BLUE HIGHLIGHT -->
			<c:otherwise>
				<tr class="lh-1">
			</c:otherwise>
		</c:choose>
					<td class="text-start">
						<c:out value="${option.getContractSymbol()}"/>
					</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd h:mma z"  value="${option.getLastTradeDate()}"/></td>
					<td><fmt:formatNumber type="NUMBER" maxFractionDigits="2" minFractionDigits="2" value="${option.getStrike()}"/></td>
					<td><fmt:formatNumber type="NUMBER" maxFractionDigits="2" minFractionDigits="2" value="${option.getLastPrice()}"/></td>
					<td><fmt:formatNumber type="NUMBER" maxFractionDigits="2" minFractionDigits="2" value="${option.getBid()}"/></td>
					<td><fmt:formatNumber type="NUMBER" maxFractionDigits="2" minFractionDigits="2" value="${option.getAsk()}"/></td>
					<c:choose>
						<c:when test="${option.getAbsoluteChange() > 0}">
							<td class="text-success">+<fmt:formatNumber type="NUMBER" maxFractionDigits="2" minFractionDigits="2" value="${option.getAbsoluteChange()}"/></td>
							<td class="text-success">+<fmt:formatNumber type="PERCENT" maxFractionDigits="2" minFractionDigits="2" value="${option.getPercentChange()/100}"/></td>
						</c:when>
						<c:when test="${option.getAbsoluteChange() < 0}">
							<td class="text-danger"><fmt:formatNumber type="NUMBER" maxFractionDigits="2" minFractionDigits="2" value="${option.getAbsoluteChange()}"/></td>
							<td class="text-danger"><fmt:formatNumber type="PERCENT" maxFractionDigits="2" minFractionDigits="2" value="${option.getPercentChange()/100}"/></td>
						</c:when>
						<c:otherwise>
							<td>0.00</td>
							<td> - </td>
						</c:otherwise>
					</c:choose>
					<td><c:out value="${option.getVolume()}"/></td>
					<td><c:out value="${option.getOpenInterest()}"/></td>
					<td><fmt:formatNumber type="PERCENT" maxFractionDigits="2" minFractionDigits="2" value="${option.getImpliedVolatility()}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</fmt:timeZone>
</body>
</html>