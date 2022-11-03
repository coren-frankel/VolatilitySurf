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
<!-- BOOTSTRAP & JQUERY-->
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<script src="/webjars/jquery/jquery.min.js"></script>

<link rel="stylesheet" href="css/styles.css" type="text/css">

<!-- DATA TABLES -->
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs5/dt-1.12.1/fh-3.2.4/r-2.3.0/sc-2.0.7/datatables.min.css"/>
 
<script type="text/javascript" src="https://cdn.datatables.net/v/bs5/dt-1.12.1/fh-3.2.4/r-2.3.0/sc-2.0.7/datatables.min.js"></script>


 
<script type="text/javascript" src="js/datatable.js">
</script>
<title><c:out value="${ticker.getShortName()}"/> Stock Options</title>
</head>
<body class="m-4">
	<div class="d-flex justify-content-between align-items-center">
		<h3><c:out value="${ticker.getShortName()}"/> (<c:out value="${ticker.getSymbol()}"/>)</h3>
		<div class="d-flex justify-content-between">
			<a href="/volsurf" class="btn btn-lg btn-outline-dark me-3">Volatility Surface</a>
			<a href="/" class="btn btn-lg btn-outline-dark">Home</a>
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
			<c:forEach var="expiry" items="${expirations}">
				<c:choose>
					<c:when test="${selectedExpiry == expiry}">
						<option value="${expiry}" selected><fmt:formatDate pattern="MMMM d, YYYY" timeZone="UTC" value="${expiry}"/></option>
					</c:when>
					<c:otherwise>
						<option value="${expiry}"><fmt:formatDate pattern="MMMM d, YYYY" timeZone="UTC" value="${expiry}"/></option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
		</form>
		<div class="d-inline p-2 rounded-1 ms-2" style="background-color:#CFE1FF;">In The Money</div>
	</div>
	
	<!-- CALLS TABLE -->
	<div class="d-flex justify-start align-items-center">
		<strong class="me-2">Calls</strong>for 
		<fmt:formatDate pattern="MMMM d, YYYY" timeZone="UTC"  value="${selectedExpiry}"/>
		<strong class="ms-5">List |</strong> <a href="#" class="ms-1">Straddle</a>
	</div>
	<table class="table table-secondary table-hover text-center" style="font-size:60%;" id="callTable">
		<thead>
			<tr style="font-size:85%;">
				<th class="text-start">Contract Name</th>
				<th class="text-end">Last Trade Date</th>
				<th class="text-end">Strike</th>
				<th class="text-end">Last Price</th>
				<th class="text-end">Bid</th>
				<th class="text-end">Ask</th>
				<th class="text-end">Change</th>
				<th class="text-end">% Change</th>
				<th class="text-end">Volume</th>
				<th class="text-end">Open Interest</th>
				<th class="text-end">Implied Volatility</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="option" items="${calls}">
			<!-- WHEN IN THE MONEY, RENDER BLUE HIGHLIGHT -->
		<c:choose>
			<c:when test="${option.getInTheMoney()}">
				<tr class="table-primary lh-1">
			</c:when>
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
	
	<!-- PUTS TABLE -->
	<div class="d-flex justify-start align-items-center mt-1">
		<strong class="me-2">Puts</strong>for 
		<fmt:formatDate pattern="MMMM d, YYYY" timeZone="UTC"  value="${selectedExpiry}"/>
	</div>
	<table class="table table-secondary table-hover text-center" style="font-size:60%;" id="putTable">
		<thead>
			<tr style="font-size:85%;">
				<th class="text-start">Contract Name</th>
				<th class="text-end">Last Trade Date</th>
				<th class="text-end">Strike</th>
				<th class="text-end">Last Price</th>
				<th class="text-end">Bid</th>
				<th class="text-end">Ask</th>
				<th class="text-end">Change</th>
				<th class="text-end">% Change</th>
				<th class="text-end">Volume</th>
				<th class="text-end">Open Interest</th>
				<th class="text-end">Implied Volatility</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="option" items="${puts}">
			<!-- WHEN IN THE MONEY, RENDER BLUE HIGHLIGHT -->
		<c:choose>
			<c:when test="${option.getInTheMoney()}">
				<tr class="table-primary lh-1">
			</c:when>
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