package com.intesasanpaolo.conco.ispairlines.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
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
@WebServlet("/GestioneBiglietti")
public class GestioneBiglietti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private List<Biglietto> listaBiglietti;
	
    public GestioneBiglietti() {
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
		
		String tmpJSON = "[";
		if(listaBiglietti.size() == 0)
			out.println("{\"Buongiorno\": \"Non ci sono i biglietti\"}");
		else { 	

			Iterator<Biglietto> myIteretor = listaBiglietti.iterator();			

			synchronized (listaBiglietti) {
				while(myIteretor.hasNext()){
					Biglietto b = myIteretor.next();
					tmpJSON += b.toJSON() + ",";
				}
				tmpJSON = tmpJSON.substring(0, tmpJSON.length()-1) + "]";
				out.println(tmpJSON);
				System.out.println("TRACE LOG (doGet): " + tmpJSON);
			}
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}
	


}
