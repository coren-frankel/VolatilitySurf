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
	<c:choose>
		<c:when test="${now ge '09:30AM' && now le '04:00PM' }">
			<small>As of <fmt:formatDate pattern="hh:mma z"  value="${ticker.getRegularMarketTime()}"/> Market Open</small>
		</c:when>
		<c:otherwise><small class="text-secondary">At close: <fmt:formatDate pattern="MMMM d hh:mma z"  value="${ticker.getRegularMarketTime()}"/></small></c:otherwise>
	</c:choose>
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
			<tr>
				<td class="text-start"><c:out value="${option.getContractSymbol()}"/></td>
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