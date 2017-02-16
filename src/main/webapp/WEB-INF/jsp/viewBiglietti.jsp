<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.intesasanpaolo.conco.ispairlines.model.Biglietto"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="css/site.css">
		<title>Visualizza Biglietti WEB</title>
	</head>
	<body>
  <%
	
   //allow access only if session exists
	String user = null;
	if(session.getAttribute("userName") == null){
	    response.sendRedirect(request.getContextPath() + "/index.jsp");
	} else user = (String) session.getAttribute("user");
	
	String userName = null;
	String sessionID = null;
	Cookie[] cookies = request.getCookies();
	if(cookies !=null){
		for(Cookie cookie : cookies){
	  	  if(cookie.getName().equals("ispAirlinesUserName")) userName = cookie.getValue();
	  	  if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
		}
	}
	
	%>
	<div class="header">
	<h1 align="center"><img src="img/logo-intesasanpaolo.png" width="100px"/> Java Airlines</h1>
	</div>
	<div class="panelHeader">
		<ul id="menu">
			<li><a href="<%=request.getContextPath()%>/VisualizzaPasseggeriWeb">Visual Passeggeri</a></li>
			<li class="current-page-li"><a href="<%=request.getContextPath()%>/VisualizzaBigliettoWeb">Visual Tickets</a></li>
			<li><a href="<%=request.getContextPath()%>/AggiungiPasseggeroWeb">Crea Passeggeri</a></li>
			<li><a href="<%=request.getContextPath()%>/AggiungiBigliettoWeb">Crea Tickets</a></li>
		</ul>
	</div>
	<!--
	<div class="panelHeader">
		<p align="center">Visualizza Biglietti Web</p>
	</div>
	-->
	<div class="panel">
		
		<ul class="form-style-1">
		<li><label>Elenco Biglietti già emessi</label></li>
			<% @SuppressWarnings("unchecked")
				List<Biglietto> listaBiglietti = (List<Biglietto>) request.getAttribute("biglietti"); 
				Iterator<Biglietto> myIteretor = listaBiglietti.iterator();			
				while(myIteretor.hasNext()){
					Biglietto b = myIteretor.next();{%>
					<li> <%=b.toJSON()%> </li><% 
					} 
				} %>

		</ul>
	</div>
		<div class="loggedon">
	    <div align="right">Hi <%=session.getAttribute("userName")%></div>
	    <div align="right"><form class="logoutbttn" action="<%=request.getContextPath()%>/Logout" method="post"><input type="submit" value="Logout"></form></div>		
	</div>
	</body>
</html>