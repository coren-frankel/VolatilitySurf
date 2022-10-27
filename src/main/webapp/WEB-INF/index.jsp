<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/animejs/3.2.1/anime.min.js"></script>
<script src="/js/pend.js" defer></script>
<link rel="stylesheet" href="css/styles.css" type="text/css">
<meta charset="UTF-8">
<title>VolatilitySurf</title>
</head>
<body>
	<div class="container">
		<div class="row my-5">
			<div class="col col-5 mx-auto">

				<h1 class="text-center">VolatilitySurf</h1>
					<div class="d-flex justify-content-center">
				<form action="/fetch" method="POST" id="form">
						<div class="input-group">
							<input type="text" name="symbol" placeholder="Ticker Search" class="form-control"/>
							<button type="submit" id="search" class="btn btn-primary"><i class="fa fa-search"></i></button>
						</div>
				</form>
					</div>
					
			</div>
		</div>
	</div>
	<div id="loadingBar">
	</div>
</body>
</html>