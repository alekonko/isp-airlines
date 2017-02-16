package com.intesasanpaolo.conco.ispairlines.auth;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.intesasanpaolo.conco.ispairlines.auth.CheckCredentials;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    response.setContentType("text/html");	          
		    String userName = request.getParameter("userName");
		    String password = request.getParameter("password");
		    String targetPage = "";
		    int TTL = 1800; // seconds

		    if ( CheckCredentials.getBasicAuth(userName,password) ) {
		    //TODO : implementare check user su dn
		    //if ( CheckCredentials.getDBAuth(userName,password) ) {
		    	HttpSession session = request.getSession();
	            session.setAttribute("userName", userName);
	            session.setMaxInactiveInterval(TTL);
	   			Cookie logonCookie=new Cookie("ispAirlinesUserName",userName);
	   			logonCookie.setMaxAge(TTL);
	   			response.addCookie(logonCookie);
	   			//targetPage = "/WEB-INF/jsp/auth/loggedon.jsp";
	   			//targetPage = "WEB-INF/jsp/aggiungiBiglietto.jsp";
	   	        request.setAttribute("userName", userName);	     
	   	        request.setAttribute("corretto", true);
	   	        response.sendRedirect(request.getContextPath() + "/AggiungiBigliettoWeb");
	   	        
	    	} else {
	   			targetPage = "/index.jsp";
	   			request.setAttribute("error", "ACCESS DENIED. Check your credentials");
	    		RequestDispatcher view = request.getRequestDispatcher(targetPage);
	   	        view.forward(request, response);
	    	}
		    
		    
		    
    } 
    
}