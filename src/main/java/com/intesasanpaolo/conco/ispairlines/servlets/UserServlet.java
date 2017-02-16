package com.intesasanpaolo.conco.ispairlines.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intesasanpaolo.conco.ispairlines.dao.JdbcUserDao;
import com.intesasanpaolo.conco.ispairlines.model.User;
import com.intesasanpaolo.conco.ispairlines.util.SimpleLog;
import com.intesasanpaolo.conco.ispairlines.service.UserService;


@WebServlet("/Users")
public class UserServlet extends HttpServlet {
	
	
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/WEB-INF/jsp/user/user.jsp";
    private static String LIST_USER = "/WEB-INF/jsp/user/listUser.jsp";

	private UserService userService = new UserService();
    private JdbcUserDao dao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        writeHtml(resp.getWriter());
    }

    private void writeHtml(PrintWriter out) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getServletContext().getResourceAsStream("/WEB-INF/users.html"), "UTF-8"));
        try {
            String line;
            boolean insideLoop = false;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("<!-- begin repeat for each user -->")) {
                    insideLoop = true;
                } else if (line.trim().equals("<!-- end repeat for each user -->")) {
                    insideLoop = false;
                    String userTemplate = sb.toString();
                    for (User user : userService.getAllEntries()) {
                        out.println(
                                userTemplate
                                        .replace("{{ summary }}", escapeHtml(user.getSummary()))
                                        .replace("{{ description }}", escapeHtml(user.getDescription()))
                        );
                    }
                } else if (insideLoop) {
                    sb.append(line).append("\n");
                } else {
                    out.println(line);
                }
            }
        } finally {
            reader.close();
        }
    }

    private String escapeHtml(String text) {
        return text.replace("<", "&lt;");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String summary = req.getParameter("summary");
        String description = req.getParameter("description");

        userService.addEntry(new User(summary, description));

        resp.sendRedirect("users.html");
    }
    
    
    protected void doGet_(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.dao = new JdbcUserDao();
    	String forward="";
        String action = request.getParameter("action");
        
        SimpleLog.toFileINFO("UserServlet doGet Action:" + action);
        
        request.setAttribute("message", "");
        request.setAttribute("errorState", false);
        
        // aggiunto per avere un'action di default se non specificata 
        if (action == null)
        {
            forward = LIST_USER;
            request.setAttribute("users", dao.getAllUsers());
        }
        else
        {
	        if (action.equalsIgnoreCase("delete")){
	            int userId = Integer.parseInt(request.getParameter("userId"));
	            
	            if (userId == 1){
		            request.setAttribute("errorState", true);
		            request.setAttribute("message", "Attenzione impossibile eliminare userId 1 !!"); 
	            } else {
		            try{
		            	dao.deleteUser(userId);request.setAttribute("message", "User eliminato.");
		            } catch (Exception e) {
			            request.setAttribute("errorState", true);
			            request.setAttribute("message", e.getMessage());  
		            }
	            
	        	}
	            
	            forward = LIST_USER;
	            request.setAttribute("users", dao.getAllUsers());
	        } else if (action.equalsIgnoreCase("edit")){
	            forward = INSERT_OR_EDIT;
	            int userId = Integer.parseInt(request.getParameter("userId"));
	            try{
		            User user = dao.getUserById(userId);
		            request.setAttribute("user", user);
	            	request.setAttribute("message", "");
		            request.setAttribute("action", "Edit"); // serve per Titolo della jsp INSERT_OR_EDIT
	            } catch (Exception e) {
		            request.setAttribute("errorState", false);
		            request.setAttribute("message", e.getMessage());
	            }
	        } else if (action.equalsIgnoreCase("listUser")){
	            forward = LIST_USER;
	            request.setAttribute("users", dao.getAllUsers());
	        } else {
	            forward = INSERT_OR_EDIT;
	            request.setAttribute("action", "Crea"); // serve per Titolo della jsp INSERT_OR_EDIT
	        }
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
        dao.CloseConnection();
    }

    protected void doPost_(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        this.dao = new JdbcUserDao();
    	
        request.setAttribute("errorState", false);
    	request.setAttribute("message", "");
    	
    	User user = new User();
		user.setUserName(request.getParameter("userName"));

		user.setPassword(request.getParameter("password"));
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
		
		String isAdmin = request.getParameter("isadmin");
		if (isAdmin.equalsIgnoreCase("true") || isAdmin.equalsIgnoreCase("false")) {
			user.setIsAdmin(Boolean.valueOf(isAdmin));		     
		} else {
			user.setIsAdmin(false);
		}
		
		
        try {
            Date dob = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("dob"));
            user.setDob(dob);
        } catch (ParseException e) {
            e.printStackTrace();
            try	{
            	Date dob = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/1900");
            	user.setDob(dob);
            } catch (ParseException e2) {
            		e2.printStackTrace();
            }
        }
        user.setEmail(request.getParameter("email"));

        String userid = request.getParameter("userid");
        
        if(userid == null || userid.isEmpty())
        {
        	try{
	            dao.addUser(user);
	        	request.setAttribute("message", "Utente Censito.");
	        } catch (Exception e) {
	            request.setAttribute("errorState", true);
	            request.setAttribute("message", e.getMessage());
	        } 
        }
        else
        {
            user.setUserid(Integer.parseInt(userid));
            try{
            	dao.updateUser(user);
            	request.setAttribute("message", "Utente Modificato.");
	        } catch (Exception e) {
	            request.setAttribute("errorState", true);
	            request.setAttribute("message", e.getMessage());
	        } 
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
        request.setAttribute("users", dao.getAllUsers());
        view.forward(request, response);
        dao.CloseConnection();
    }
}