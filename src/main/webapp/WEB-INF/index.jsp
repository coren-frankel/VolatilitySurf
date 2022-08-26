<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>VolatilitySurf</title>
</head>
<body>
	<div class="container">
		<div class="row mt-5">
			<div class="col col-4 mx-auto">

				<h1>VolatilitySurf</h1>
				<form action="/fetch" method="POST">
					<div class="d-flex">
						<div class="input-group">
							<input type="text" name="symbol" placeholder="Search options by ticker" class="form-control"/>
						</div>
						<button type="submit" class="btn btn-primary"><i class="fa fa-search"></i></button>
					</div>
				</form>
			
			
			</div>
		</div>
	</div>

</body>
</html>