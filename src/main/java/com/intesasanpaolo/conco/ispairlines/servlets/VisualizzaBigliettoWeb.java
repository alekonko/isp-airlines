package com.intesasanpaolo.conco.ispairlines.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intesasanpaolo.conco.ispairlines.model.Biglietto;

/**
 * Servlet implementation class GestionePrenotazioni
 */
@WebServlet("/VisualizzaBigliettoWeb")
public class VisualizzaBigliettoWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private List<Biglietto> listaBiglietti;
	
    public VisualizzaBigliettoWeb() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();			
		ServletContext servletContext = getServletContext();				
		
		// EVITO CHE DUE TREAD MODIFICHINO LA LISTA IN TEMPI DIVERSI
				 
		// leggo lista biglietti da contesto a locale
		listaBiglietti = (List<Biglietto>) servletContext.getAttribute("biglietti");	

		if(listaBiglietti.size() == 0){
			out.println("Non sono stati ancora emessi biglietti. CONCO");
			//TODO finire init lista vuota
			//listaBiglietti.add(new Biglietto());
		}
		//AGGIUNGO ALLA REQUEST LA LISTA DI listaBiglietti
		request.setAttribute("biglietti", listaBiglietti);	
		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/jsp/viewBiglietti.jsp");
		view.forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}
	


}
