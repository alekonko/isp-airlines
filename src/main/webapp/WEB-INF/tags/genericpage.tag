<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<html>
<head>
<title>Servizi IaaS - Gestione Portale Cloud Utils</title>
<jsp:include page="<%="../includes/style.jsp"%>"></jsp:include>
<jsp:include page="<%="../includes/function.jsp"%>"></jsp:include>
	<script type="text/javascript">
		// Gestione eventi: render result in current page	
		var j = jQuery.noConflict();
		var message = "${message}"
		var errorState = "${errorState}" 
		
		j(document).ready(function(){
			console.log("message: -" + message + "-");
			console.log("errorState:"+ errorState);
			if (message != "")
				{		
				showMyDialog(message,"Operation Result",errorState);
				}
		});
	 </script>
	 
	 	<script type="text/javascript">
		// Usato nelle pagine di assegnazione rimozione associazioni oggetti (es mapNetClusterVmware.jsp)
		var j = jQuery.noConflict();
		 j(document).ready(function() {
			 
		    j('#btnRight').click(function(e) {
		        var selectedOpts = j('#oggettidisp option:selected');
		        if (selectedOpts.length == 0) {
		            alert("Nothing to move.");
		            e.preventDefault();
		        }
		
		        j('#oggettinuovi').append(j(selectedOpts).clone());
		        j(selectedOpts).remove();
		        e.preventDefault();
		    });
		
		    j('#btnLeft').click(function(e) {
		        var selectedOpts = j('#oggettinuovi option:selected');
		        if (selectedOpts.length == 0) {
		            alert("Nothing to move.");
		            e.preventDefault();
		        }
		
		        j('#oggettidisp').append(j(selectedOpts).clone());
		        j(selectedOpts).remove();
		        e.preventDefault();
		    });
		    
		    // mapForm è la form usata per modificare i mapping
		    j( "#mapForm" ).submit(function( event ) {
		    	j('#oggettinuovi  option').prop('selected', true);
		    	j('#oggettiallocati  option').prop('selected', true);
		    	});
		});
	 </script> 
	 
</head>
  <body>
	<%
	//allow access only if session exists
	String user = null;
	if(session.getAttribute("userName") == null){
	    response.sendRedirect("index.jsp");
	} else user = (String) session.getAttribute("user");
	
	String userName = null;
	String sessionID = null;
	Cookie[] cookies = request.getCookies();
	if(cookies !=null){
		for(Cookie cookie : cookies){
	  	  if(cookie.getName().equals("CloudPortalUtilsUserName")) userName = cookie.getValue();
	  	  if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
		}
	}
	
	%>
  <div id="top-bar">
		<div class="page-full-width cf">
			<ul id="nav" class="fl">
				<li>
				<form action="/CloudPortalUtils/Logout" method="post">
					<input type="submit" value="Logout" class="round button dark menu-logoff image-left">
				</form></li>
				<li class="v-sep"><a href="#" class="round button dark menu-user image-left">Logged in as <strong><c:out value="${userName}" /></strong>. </a>
					<ul>
						<li><a href="#">Session ID:<%=sessionID %></a></li>
						<li><a href="/CloudPortalUtils/Logout">Log out</a></li>
					</ul> 
				</li>
				<!-- <li><a href="#" class="round button dark menu-email-special image-left">3 new messages</a></li> -->
			</ul> <!-- end nav -->
		</div> <!-- end full-width -->
	</div> <!-- end top-bar -->
	
	<div id="container">
     <div id="pageheader">
      <jsp:invoke fragment="header"/>
    </div>
    <div id="navigation" class="tabella">
        <ul>
			<li class="menu">IaaS VMWare</li>
			<li><a href="/CloudPortalUtils/ClusterVmWare">Cluster</a></li>
			<li><a href="/CloudPortalUtils/ServiceOnVmWare">Servizi</a></li>  
			<li><a href="/CloudPortalUtils/vCenter">vCenter</a></li>
			<li><a href="/CloudPortalUtils/StorageLevel">StorageLevel</a></li>
			<li>&nbsp;</li>
			<li>&nbsp;</li>
			<li class="menu">IaaS HyperV</li>
			<li><a href="/CloudPortalUtils/Cloud">Cloud</a></li>
			<li><a href="/CloudPortalUtils/ServiceOnHyperV">Servizi</a></li>  
			<li><a href="/CloudPortalUtils/VMManager">VMManager</a></li>
			<li>&nbsp;</li>
			<li>&nbsp;</li>
			<li class="menu">Misc</li>
			<li><a href="/CloudPortalUtils/Network">Network</a></li>
			<li><a href="/CloudPortalUtils/ServiceOwner">Service Owner</a></li>
			<li class="tbd">WBE</li>
			<li><a href="/CloudPortalUtils/User">Gestione Utenze</a></li>
			<li>&nbsp;</li>
			<li>&nbsp;</li>
			<li class="menu">Report</li>
			<li><a href="/CloudPortalUtils/Network?action=allnetreport">Report Network</a></li>
			<li><a href="/CloudPortalUtils/Network?action=allnetreport&ambiente=prod">Report Reti Produzione</a></li>
			<li><a href="/CloudPortalUtils/Network?action=allnetreport&ambiente=sys">Report Reti System-Test</a></li>
        </ul>
    </div>
    <div id="extra" class="tabella">
        <div class="newsbox">
            <h2>News</h2>
            <p>Simple WebApp Java per gestione tabelle censimento.</p>
        </div>
        <div class="newsbox" class="tabella">
            <h2>Help</h2>
            <p><A href="javascript:openNewPage('mailto:alessandro.conconi@intesasanpaolo.com');"><img src="images/help.png" width="40px"> AleConco</A></p>
        </div>
    </div>
    <div id="content">
      <jsp:doBody/>
    </div>
    <div id="pagefooter">
      <jsp:invoke fragment="footer"/>
    </div>
</div>
  </body>
</html>