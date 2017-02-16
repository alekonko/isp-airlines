<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>
    <jsp:attribute name="header">
     	<jsp:include page="../includes/header.jsp"/>
    </jsp:attribute>
    <jsp:attribute name="footer">
      <jsp:include page="../includes/footer.jsp"/>
    </jsp:attribute>
	<jsp:body>
		<div class="tabella" style="height: 300px;">
				    <table>
			       <thead>
			           <tr>
						   <th><p class="title">Logged On ISP Airlines</p></th>						   
			           </tr>
			       </thead>
			       <tbody>
			         <tr><td align="left">
		   				Ciao <c:out value="${userName}" /> how r u ?
		   			</td></tr>
			       </tbody>
			   </table>
			   <img id="cloudlogoimg" src="img/Clouds-with-Globe-tr.png" alt="" border="0">
		</div>
</jsp:body>
</t:genericpage>