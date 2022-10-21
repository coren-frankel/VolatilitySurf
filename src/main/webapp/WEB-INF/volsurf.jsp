<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<script src='https://cdn.plot.ly/plotly-2.14.0.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.17/d3.min.js'></script>

<script>
	xdata = ${xdata};
	ydata = ${ydata};
	zdata = ${zdata};
</script>
<script src="/js/script.js" defer></script>

<title><c:out value="${ticker.getSymbol()}"/>'s Volatility Surface</title>
</head>
<body class="m-4">
	<div class="d-flex justify-content-between align-items-center">
		<h3><c:out value="${ticker.getShortName()}"/> (<c:out value="${ticker.getSymbol()}"/>)</h3>
		<div class="d-flex justify-content-between">
			<a href="/options" class="btn btn-sm btn-outline-dark me-3">Back to Options</a>
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
		<c:when test="${now le ticker.getRegularMarketTime()}">
			<small>As of <fmt:formatDate pattern="hh:mma z" timeZone="US/Eastern" value="${ticker.getRegularMarketTime()}"/> Market Open</small>
		</c:when>
		<c:otherwise><small class="text-secondary">At close: <fmt:formatDate pattern="hh:mma z" timeZone="US/Eastern" value="${ticker.getRegularMarketTime()}"/></small></c:otherwise>
	</c:choose>
	</fmt:timeZone>
	<div id="plot" class="d-flex justify-content-center"></div>
</body>
</html>
	