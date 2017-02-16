package com.intesasanpaolo.conco.ispairlines.auth;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/SiteHome")
public class SiteHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SiteHomeServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String userName = request.getParameter("userName");
	    String targetPage = "/WEB-INF/jsp/auth/loggedon.jsp";
   	    request.setAttribute("userName", userName);
		RequestDispatcher view = request.getRequestDispatcher(targetPage);
	    view.forward(request, response);
	}

}