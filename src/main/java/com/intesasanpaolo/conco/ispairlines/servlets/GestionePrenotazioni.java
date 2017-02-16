package com.intesasanpaolo.conco.ispairlines.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intesasanpaolo.conco.ispairlines.model.Passeggero;



/**
 * Servlet implementation class GestionePrenotazioni
 */
@WebServlet("/GestionePrenotazioni")
public class GestionePrenotazioni extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private Map<String, Passeggero> listaPasseggeri;

	
    public GestionePrenotazioni() {
        super();
        //listaPasseggeri = new HashMap<>();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();			
		ServletContext sc = getServletContext();				
	
		// EVITO CHE DUE TREAD MODIFICHINO LA LISTA IN TEMPI DIVERSI
		synchronized (this) { 
			// la salvo lista passeggeri da contesto a locale
			listaPasseggeri = (Map<String,Passeggero>) sc.getAttribute("passeggeri");
		}

		//System.out.println("ti aggiorni in automatico?!?!?!?!?!?!");
		
		
		/*
		if (listaPasseggeri.size() == 0) 
			out.println("Non ci sono ancora passeggeri");
		else
			for(Passeggero p: listaPasseggeri.values()) 
				out.println(p);
		*/
		
		String tmpJSON = "[";
		if(listaPasseggeri.size() == 0)
			out.println("{\"Buongiorno\": \"Non ci sono i passeggeri\"}");
		else { 			
			for(Passeggero p: listaPasseggeri.values())
				tmpJSON += p.toJSON() + ",";
			tmpJSON = tmpJSON.substring(0, tmpJSON.length()-1) + "]";
			out.println(tmpJSON);
			System.out.println("TRACE LOG (doGet): " + tmpJSON);
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//PUNTO DI INGRESSO QUANDO VIENE ESEGUITA UNA POST VERSO QUESTA SERVLET
		String nome, cognome, dataDiNascita, codiceFiscale, numeroPassaporto;

		PrintWriter out = response.getWriter();

		nome = request.getParameter("nome");
		cognome = request.getParameter("cognome");
		dataDiNascita = request.getParameter("data_di_nascita");
		codiceFiscale = request.getParameter("codice_fiscale");
		numeroPassaporto = request.getParameter("passaporto");

		if (nome != null && cognome != null && dataDiNascita != null && numeroPassaporto != null) {
			Passeggero p = creaPasseggero(nome,cognome,dataDiNascita,codiceFiscale,numeroPassaporto);
			
			if (p != null) {
				
				//Recupero il contesto
				ServletContext sc = getServletContext();
				
				//Salvo la lista passeggeri in una variabile
				listaPasseggeri = (Map<String,Passeggero>) sc.getAttribute("passeggeri");
				
				if (listaPasseggeri.get(codiceFiscale) == null) {
					
					//Modifico la lista passeggeri
					listaPasseggeri.put(codiceFiscale, p);
					
					//Aggiorno la lista passeggeri nel contesto
					sc.setAttribute("passeggeri", listaPasseggeri);
					
					out.println("Oggetto creato correttamente");
					out.println(p);
				}
				
				//GESTISCO LA MEMORIZZAZIONE DEI BIGLIETTI
				
			} else {
				out.println("Dati mancanti");
			}
		}
		
	}
	
	private Passeggero creaPasseggero(String nome, String cognome, String dataDiNascita, String codiceFiscale, String numeroPassaporto) {
		try {
			return new Passeggero(nome, cognome, dataDiNascita, codiceFiscale, numeroPassaporto);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

}
