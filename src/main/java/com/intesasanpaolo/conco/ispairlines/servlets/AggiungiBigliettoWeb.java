package com.intesasanpaolo.conco.ispairlines.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intesasanpaolo.conco.ispairlines.model.Biglietto;
import com.intesasanpaolo.conco.ispairlines.model.Classe;
import com.intesasanpaolo.conco.ispairlines.model.Passeggero;

/**
 * Servlet implementation class AddReservation
 */

@WebServlet("/AggiungiBigliettoWeb")
public class AggiungiBigliettoWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AggiungiBigliettoWeb() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initJSP(request);
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/jsp/aggiungiBiglietto.jsp");		
		view.forward(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletContext servletContext = getServletContext();
		
		Map<String, Passeggero> mappaPasseggeri = (Map<String,Passeggero>) servletContext.getAttribute("passeggeri");
		Biglietto ticket;
		
		String codiceFiscale = request.getParameter("codiceFiscale");

		if (codiceFiscale != null && codiceFiscale.length() > 0) {
			Passeggero p = mappaPasseggeri.get(codiceFiscale);
			
			String da = request.getParameter("da");
			String a = request.getParameter("a");
			String data = request.getParameter("data_volo");
			
			Classe classe = Classe.valueOf(request.getParameter("tipo_biglietto"));	
			
			if (p != null) {
				try {
					ticket = new Biglietto(classe, p, da, a, data);
					System.out.println("TRACE: creo Biglietto " + ticket.toJSON());					

					// porto l'oggetto bigliettoSalvato sulla jsp
					request.setAttribute("bigliettoSalvato", ticket);

					servletContext = getServletContext();
					List<Biglietto> listaBiglietti = (List<Biglietto>) servletContext.getAttribute("biglietti");

					synchronized(listaBiglietti) {
						// Metto in synchronized listaBiglietti e non this. listabiglietti � ByRef e quindi ho puntatore.
						// Mi assicuro che le modifiche al contenuto siano in lock e non la query
						listaBiglietti.add(ticket);
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("TRACE: Non ci sono Passeggeri con quel codice fiscale");

				initJSP(request);
				request.setAttribute("errori", true);
				request.setAttribute("errorMessage", "CodiceFiscale non trovato in memoria");
				request.setAttribute("codiceFiscale",codiceFiscale);				
				request.setAttribute("da", da);
				request.setAttribute("a", a);
				request.setAttribute("data_volo", data);

			}
			
			initJSP(request);
			request.setAttribute("corretto", true);

			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/jsp/aggiungiBiglietto.jsp");		
			view.forward(request, response);			
			
			
		}
	}

	private void initJSP(HttpServletRequest request) {
		//reset variabili per check error State
		request.setAttribute("errori", false);
		request.setAttribute("corretto", false);
		request.setAttribute("esistente", false);
		request.setAttribute("errorMessage", "");
		
		request.setAttribute("codiceFiscaleMancante", false);
		request.setAttribute("daMancante", false);
		request.setAttribute("aMancante", false);
		request.setAttribute("data_voloMancante", false);

		//reset variabili
		request.setAttribute("codiceFiscale","");
		request.setAttribute("da", "");
		request.setAttribute("a", "");
		request.setAttribute("data_volo", "");
		
		
	}
}