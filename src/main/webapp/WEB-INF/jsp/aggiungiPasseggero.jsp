<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@ page import="com.intesasanpaolo.conco.ispairlines.model.Passeggero" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/site.css">

<title>Aggiungi Passeggero</title>
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
			<li><a href="<%=request.getContextPath()%>/VisualizzaBigliettoWeb">Visual Tickets</a></li>
			<li class="current-page-li"><a href="<%=request.getContextPath()%>/AggiungiPasseggeroWeb">Crea Passeggeri</a></li>
			<li><a href="<%=request.getContextPath()%>/AggiungiBigliettoWeb">Crea Tickets</a></li>
		</ul>
	</div>
	<!-- <div class="panelHeader">
		<p align="center">Creazione Passeggeri Web</p>
	</div>
	 -->
	<div class="panel">
<form action="AggiungiPasseggeroWeb" method="POST">
<ul class="form-style-1">
    <li><label>Nome e Cognome<span class="required">*</span></label><input type="text" name="nome" class="field-divided" value="<%= (String) request.getAttribute("nome") %>" />&nbsp;<input type="text" name="cognome" class="field-divided" value="<%= (String) request.getAttribute("cognome") %>"/></li>
    <!--  <li><label>email </label><input type="email" name="field3" class="field-long" /></li> -->

    <li>
        <label>CodiceFiscale <span class="required">*</span></label>
        <input type="text" name="codiceFiscale" class="field-long" value="<%= (String) request.getAttribute("codiceFiscale") %>" />
    </li>

    <li>
        <label>Numero Passaporto <span class="required">*</span></label>
        <input type="text" name="numeroPassaporto" class="field-long" value="<%= (String) request.getAttribute("numeroPassaporto") %>" />
    </li>
    
    <li>
        <label>Data di Nascita <span class="required">*</span></label>
        <input type="text" name="dataDiNascita" class="field-long" value="<%= (String) request.getAttribute("dataDiNascita") %>" />
    </li>

    <li>
        <input type="submit" value="Submit" />
    </li>
</ul>
</form>

<% if ((Boolean) request.getAttribute("corretto") == true) {%>
	<fieldset class="corretto"> DATI SALVATI
		<p><%=(Passeggero) request.getAttribute("passeggeroSalvato") %></p>
	</fieldset>
<% } %>
<% if ((Boolean) request.getAttribute("errori") == true) {%>
	<fieldset class="errore"> ERRORI
		<ul>
			<% if ((Boolean) request.getAttribute("nomeMancante") == true ) {  %>
			<li>Nome Mancante</li>
			<% } %>
			
			<% if ((Boolean) request.getAttribute("cognomeMancante") == true ) {  %>
			<li>Cognome Mancante</li>
			<% } %>
			
			<% if ((Boolean) request.getAttribute("dataDiNascitaMancante") == true ) {  %>
			<li>Data Mancante</li>
			<% } %>
			
			<% if ((Boolean) request.getAttribute("codiceFiscaleMancante") == true ) {  %>
			<li>Cod Fiscale</li>
			<% } %>
			
			<% if ((Boolean) request.getAttribute("numeroPassaportoMancante") == true ) {  %>
			<li>Numero Passaporto Mancante</li>
			<% } %>
			
			<% if ((Boolean) request.getAttribute("esistente") == true) { %>
			<li> IL PASSEGGERO ESISTE GIÀ (Controlla CODICE FISCALE)</li>
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