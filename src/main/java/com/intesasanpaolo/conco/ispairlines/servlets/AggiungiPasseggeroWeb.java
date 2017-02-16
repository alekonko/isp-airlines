package com.intesasanpaolo.conco.ispairlines.servlets;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intesasanpaolo.conco.ispairlines.model.Passeggero;

//
//E' la gestione Prenotazione ma utilizzando un lister centrale che ha in carico la gestione lista passeggeri e la sua init
//
//


/**
 * Servlet implementation class AddReservation
 */

@WebServlet("/AggiungiPasseggeroWeb")
public class AggiungiPasseggeroWeb extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//private Map<String, Passeggero> listaPasseggeri;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AggiungiPasseggeroWeb() {
		super();
		// non inizializzo la lista, la recupero dal listenerContext
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		initJSP(request);
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/jsp/aggiungiPasseggero.jsp");		
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);

		System.out.println("Eseguo POST");
		//doGet(request, response);
		// PUNTO DI INGRESSO DA POST

		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String codiceFiscale = request.getParameter("codiceFiscale");
		String dataDiNascita = request.getParameter("dataDiNascita");
		String numeroPassaporto = request.getParameter("numeroPassaporto");

		// GESTISCO LA MEMORIZZAZIONE DEI BIGLIETTI
		
		Boolean errore = false;
		initJSP(request);
		
		if (nome.length() == 0){
			request.setAttribute("nomeMancante",  true);
			errore = true;
		}

		if (cognome.length() == 0){
			request.setAttribute("cognomeMancante",  true);
			errore = true;
		}

		if (dataDiNascita.length() == 0){
			request.setAttribute("dataDiNascitaMancante",  true);
			errore = true;
		}

		if (codiceFiscale.length() == 0){
			request.setAttribute("codiceFiscaleMancante",  true);
			errore = true;
		}

		if (numeroPassaporto.length() == 0){
			request.setAttribute("numeroPassaportoMancante",  true);
			errore = true;
		}

		if (errore){
			request.setAttribute("errori",  true);
			request.setAttribute("nome", nome);
			request.setAttribute("cognome", cognome);
			request.setAttribute("dataDiNascita", dataDiNascita);
			request.setAttribute("codiceFiscale", codiceFiscale);
			request.setAttribute("numeroPassaporto", numeroPassaporto);
			
		} else {
			
			Passeggero passeggero = creaPasseggero( nome, cognome, dataDiNascita, codiceFiscale, numeroPassaporto);

			if (passeggero != null ){
				// va a recuperare il contesto e listaPasseggeri da Listener che ho creato
				ServletContext servletContext = getServletContext();				
				Boolean esistente = false;

				Map<String, Passeggero> mappaPasseggeri = (Map<String,Passeggero>) servletContext.getAttribute("passeggeri");
				
				// EVITO CHE DUE TREAD MODIFICHINO LA LISTA IN TEMPI DIVERSI
				mappaPasseggeri = (Map<String,Passeggero>) servletContext.getAttribute("passeggeri");
				synchronized (mappaPasseggeri) { 
					// la salvo lista passeggeri da contesto a locale
					if(mappaPasseggeri.get(codiceFiscale) == null) {
						// Aggiungo Passeggero in HashMap con codiceFiscale come chiave
						mappaPasseggeri.put(codiceFiscale, passeggero);						
						// Aggiorno la lista passeggeri nel contesto ! NO! � fatto byReference modifico l'oggetto nel context
						//servletContext.setAttribute("passeggeri", mappaPasseggeri);
					} else {
						esistente = true;
					}
				}

				// system log
				System.out.println("TRACE LOG: " + passeggero.toJSON() );
				
				initJSP(request);			
				if (esistente){
					request.setAttribute("errori", true);
					request.setAttribute("esistente", true);
				} else {
					request.setAttribute("corretto", true);
					// porto l'oggetto passeggero sulla jsp
					request.setAttribute("passeggeroSalvato", passeggero);
				}
			}
		}

		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/jsp/aggiungiPasseggero.jsp");
		view.forward(request, response);

	}


	
	private Passeggero creaPasseggero(String nome, String cognome, String dataDiNascita, String numeroPassaporto, String codiceFiscale) {
		try{
			return new Passeggero(nome, cognome, dataDiNascita, numeroPassaporto, codiceFiscale);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	private void initJSP(HttpServletRequest request) {
		//reset variabili per check error State
		request.setAttribute("errori", false);
		request.setAttribute("corretto", false);
		request.setAttribute("esistente", false);
		
		request.setAttribute("nomeMancante", false);
		request.setAttribute("cognomeMancante", false);
		request.setAttribute("dataDiNascitaMancante", false);
		request.setAttribute("codiceFiscaleMancante", false);
		request.setAttribute("numeroPassaportoMancante", false);

		//reset variabili
		request.setAttribute("nome", "");
		request.setAttribute("cognome", "");
		request.setAttribute("dataDiNascita", "");
		request.setAttribute("codiceFiscale", "");
		request.setAttribute("numeroPassaporto", "");
		
	}
	
}