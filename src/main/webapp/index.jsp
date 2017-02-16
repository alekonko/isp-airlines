<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>Login to ISP Java Airlines</title>
	<!-- Stylesheets -->
	<link href='http://fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet'>
	<link rel="stylesheet" href="css/login.css">
	<!-- Optimize for mobile devices -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>  
</head>
<body>
	<%
	//allow access only if session exists
	String user = (String) session.getAttribute("user");
	
	String userName = null;
	String sessionID = null;
	Cookie[] cookies = request.getCookies();
	if(cookies !=null){
		for(Cookie cookie : cookies){
	  	  if(cookie.getName().equals("ispAirlinesUserName")) userName = cookie.getValue();
	  	  if(cookie.getName().equals("JSESSIONID")) { 
	  		  sessionID = cookie.getValue();
	  		  //imposto il redirect ad una url gia' esistente SiteHome. Ulteriore check messo dentro la SiteHomeServlet e file generic.tag
	  		  //  non funziona il redirect se si e' gia' loggati in quanto blocca la logout
	  		  //response.sendRedirect("SiteHome");
	  	  	}
		}
	}
	%>
	<!-- TOP BAR -->
	<div id="top-bar">	
		<div class="page-full-width">
			<!-- <a href="#" class="round button dark ic-left-arrow image-left ">Return to website</a> -->
		</div> <!-- end full-width -->	
	</div> <!-- end top-bar -->
	<!-- HEADER -->
	<div id="header">		
		<div class="page-full-width cf">
			<img id="logoimg" src="img/logo-intesasanpaolo.png" width="100px"/>
			<br><h1>Benvenuto in Java Airlines (v1.0 - prova update)</h1>
			<div id="login-intro" class="fl">
				
			</div> <!-- login-intro -->
		</div> <!-- end full-width -->	
	</div> <!-- end header -->
	<!-- MAIN CONTENT -->
	<div id="content">
		<form action="Login" method="post" id="login-form">		
			<fieldset>
				<p>
					<label for="userName">username</label>
					<input id="login-username" type="text" name="userName" value="" class="round full-width-input" />
				</p>
				<p>
					<label for="password">password</label>
					<input id="login-password" type="password" name="password" value="" class="round full-width-input" />
				</p>
				<input type="submit" value="LOG IN" class="button round blue image-right ic-right-arrow"/> 
				<c:choose>
					<c:when	test="${fn:containsIgnoreCase(error, 'ACCESS DENIED')}">
					  <br/><br/><div class="error-box round"><c:out value="${error}" /></div>
					</c:when>
			 		<c:otherwise>
			 		 <br/><br/><div class="information-box round">Premi LOG IN per continuare</div>
				   </c:otherwise>
			   </c:choose>
			</fieldset>
			<br/>
		</form>	
		
				
	</div> <!-- end content -->
	<!-- FOOTER -->
	<div id="footer">
		<p>&copy; Copyright 2017 <a href="#"> AleConco </a>. All rights reserved.</p>
	</div> <!-- end footer -->
    
    <!-- 
    <form action="Login" method="post">  
    Name:<input type="text" name="userName"/><br/>
    Password:<input type="password" name="password"/><br/>  
    <input type="submit" value="go"/>  
    </form> 
 	-->
 
</body>
</html>