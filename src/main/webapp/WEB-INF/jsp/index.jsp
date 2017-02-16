<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="css/site.css">
		<title>HomePage </title>
	</head>
	<body>
	<div class="header">
			<h1 align="center"><img src="img/logo-intesasanpaolo.png" width="100px"/> Java Airlines</h1>
	</div>
	<div class="panelHeader">
		<ul id="menu">
			<li><a href="<%=request.getContextPath()%>/VisualizzaPasseggeriWeb">Visual Passeggeri</a></li>
			<li><a href="<%=request.getContextPath()%>/VisualizzaBigliettoWeb">Visual Tickets</a></li>
			<li><a href="<%=request.getContextPath()%>/AggiungiPasseggeroWeb">Crea Passeggeri</a></li>
			<li><a href="<%=request.getContextPath()%>/AggiungiBigliettoWeb">Crea Tickets</a></li>
		</ul>
	</div>
	<div class="panelHeader">
		<p align="center">Welcome in ISP Airlines</p>
	</div>
	<div class="panel">
		  <img src="img/travel-sfondo.jpg" width="350px" alt="travel-sfondo"/>
	</div>
	</body>
</html>