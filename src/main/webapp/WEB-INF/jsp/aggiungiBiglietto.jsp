<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.intesasanpaolo.conco.ispairlines.model.Biglietto"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/site.css">

	
<title>Compra Biglietto</title>
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
	
		<!--
		<a href="#">Session ID:<%=sessionID %></a></li>
		 -->	

	<div class="header">
	<h1 align="center"><img src="img/logo-intesasanpaolo.png" width="100px"/> Java Airlines</h1>
	</div>
	<div class="panelHeader">
		<ul id="menu">
			<li><a href="<%=request.getContextPath()%>/VisualizzaPasseggeriWeb">Visual Passeggeri</a></li>
			<li><a href="<%=request.getContextPath()%>/VisualizzaBigliettoWeb">Visual Tickets</a></li>
			<li><a href="<%=request.getContextPath()%>/AggiungiPasseggeroWeb">Crea Passeggeri</a></li>
			<li class="current-page-li"><a href="<%=request.getContextPath()%>/AggiungiBigliettoWeb">Crea Tickets</a></li>
		</ul>
	</div>

	<!--
	<div class="panelHeader">
	<p align="center">Creazione Biglietti Web</p>
	</div>
	-->
	<div class="panel">
		
		<form action=AggiungiBigliettoWeb method="POST">
			<ul class="form-style-1">
				<li><label>CodiceFiscale <span class="required">*</span></label>
					<input type="text" name="codiceFiscale" class="field-long"
					value="<%= (String) request.getAttribute("codiceFiscale") %>" /></li>

				<li><label>Aeroporto di Partenza<span class="required">*</span></label>
					<input type="text" name="da" class="field-long"
					value="<%= (String) request.getAttribute("da") %>" /></li>

				<li><label>Aeroporto di Destinazione<span class="required">*</span></label>
				 <input type="text" name="a" class="field-long"	value="<%= (String) request.getAttribute("a") %>" /></li>

				<li><label>Orario Volo<span class="required">*</span></label> 
				  <!-- <input	type="text" name="data_volo" class="field-long"	value="<%= (String) request.getAttribute("data_volo") %>" /> -->
				<select name="data_volo">
						<option value="01/10/2017 09:00">01/10/2017 09:00</option>
						<option value="01/10/2017 10:00">01/10/2017 10:00</option>
						<option value="01/10/2017 11:00">01/10/2017 11:00</option>
				</select></li>
				<li><label>Tipo biglietto: </label>
				<select name="tipo_biglietto">
					<option value="ECONOMY">Economy</option>
					<option value="BUSINESS">Business</option>
					<option value="FIRST">First</option>
				</select></li>

				<li><input type="submit" value="Submit" /></li>
			</ul>
		</form>

		<% if ((Boolean) request.getAttribute("corretto") == true) {%>
		<fieldset class="corretto" >
			DATI SALVATI
			<p><%=(Biglietto) request.getAttribute("bigliettoSalvato") %></p>
		</fieldset>
		<% } %>
		<% if ((Boolean) request.getAttribute("errori") == true) {%>
		<fieldset class="errore" >
			ERRORE : <%=(String) request.getAttribute("errorMessage") %>
			<ul>
			
				<% if ((Boolean) request.getAttribute("codiceFiscaleMancante") == true ) {  %>
				<li>Cod Fiscale</li>
				<% } %>

				<% if ((Boolean) request.getAttribute("daMancante") == true ) {  %>
				<li>Cod Fiscale</li>
				<% } %>

				<% if ((Boolean) request.getAttribute("aMancante") == true ) {  %>
				<li>Cod Fiscale</li>
				<% } %>

				<% if ((Boolean) request.getAttribute("data_voloMancante") == true ) {  %>
				<li>Cod Fiscale</li>
				<% } %>								

				<% if ((Boolean) request.getAttribute("esistente") == true) { %>
				<li>IL BIGLIETTO ESISTE GIÀ (Controlla CODICE FISCALE)</li>
				<% } %>

			</ul>
		</fieldset>
		<% } %>

	</div>
	<div class="loggedon">
	    <div align="right">Hi <%=session.getAttribute("userName")%></div>
	    <div align="right"><form class="logoutbttn" action="<%=request.getContextPath()%>/Logout" method="post"><input type="submit" value="Logout"></form></div>		
	</div>

</body>
</html>