<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.intesasanpaolo.conco.ispairlines.model.Passeggero" %>
<%@page import="java.util.Map" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="css/site.css">
		<title>Visualizza Passeggeri WEB</title>
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
			<li class="current-page-li"><a href="<%=request.getContextPath()%>/VisualizzaPasseggeriWeb">Visual Passeggeri</a></li>
			<li><a href="<%=request.getContextPath()%>/VisualizzaBigliettoWeb">Visual Tickets</a></li>
			<li><a href="<%=request.getContextPath()%>/AggiungiPasseggeroWeb">Crea Passeggeri</a></li>
			<li><a href="<%=request.getContextPath()%>/AggiungiBigliettoWeb">Crea Tickets</a></li>
		</ul>
	</div>
	<!-- 
	<div class="panelHeader">
		<p align="center">Visualizza Passeggeri Web</p>
	</div>
	 -->
	<div class="panel">
		
		<ul class="form-style-1">
		<li><label>Elenco Passeggeri Censiti</label></li>
		<li><table>
			<tr><th>Nome</th><th>Cognome</th><th>DataDiNascita</th><th>Passaporto</th><th>Codice Fiscale</th></tr>
			<% @SuppressWarnings("unchecked")
				Map<String,Passeggero> mappaPasseggeri = (Map<String,Passeggero>) request.getAttribute("passeggeri"); 

				for(Passeggero p : mappaPasseggeri.values()) {%>
					<%=p.toHtml()%> 
				 <%} %> 
		</table></li>
		</ul>
	</div>
		<div class="loggedon">
	    <div align="right">Hi <%=session.getAttribute("userName")%></div>
	    <div align="right"><form class="logoutbttn" action="<%=request.getContextPath()%>/Logout" method="post"><input type="submit" value="Logout"></form></div>		
	</div>
	</body>
</html>