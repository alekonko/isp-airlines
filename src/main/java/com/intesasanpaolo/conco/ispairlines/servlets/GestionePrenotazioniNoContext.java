package com.intesasanpaolo.conco.ispairlines.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intesasanpaolo.conco.ispairlines.model.Passeggero;

/**
 * Servlet implementation class GestionePrenotazioni
 */
@WebServlet("/GestionePrenotazioniNoContext")
public class GestionePrenotazioniNoContext extends HttpServlet {
	private static final long serialVersionUID = 1L;
       	
	// intefaccia
	private Map<String, Passeggero> listaPasseggeri;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionePrenotazioniNoContext() {
        super();
        listaPasseggeri = new HashMap<>();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String tmpJSON = "[";
		if(listaPasseggeri.size() == 0)
			out.println("{\"Buongiorno\": \"Non ci sono i passeggeri\"}");
		else { 
			
			for(Passeggero p: listaPasseggeri.values())
				tmpJSON += p.toJSON() + ",";
			tmpJSON = tmpJSON.substring(0, tmpJSON.length()-1) + "]";
			out.println(tmpJSON);
			System.out.println("TRACE LOG (from doGet): " + tmpJSON);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Eseguo POST");
		//doGet(request, response);
		// PUNTO DI INGRESSO DA POST
		PrintWriter out = response.getWriter();
		
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String codiceFiscale = request.getParameter("codiceFiscale");
		String dataDiNascita = request.getParameter("dataDiNascita");
		String numeroPassaporto = request.getParameter("numeroPassaporto");

		// controllo parametri
		if (nome != null && cognome != null && dataDiNascita != null && numeroPassaporto != null  && codiceFiscale != null ) {
			Passeggero p = creaPasseggero( nome,  cognome,  dataDiNascita,  numeroPassaporto, codiceFiscale);
			
			if (p != null ){				
				if(listaPasseggeri.get(codiceFiscale) ==null) {
					listaPasseggeri.put(codiceFiscale, p);		
					//out.println("Oggetto creato correttamente");
					//out.println(p);
					out.println(p.toJSON());
					System.out.println("TRACE LOG: " + p.toJSON() );
				}
			}
			
		} else {
			out.println("ciaooo");
		}
		
	}

	private Passeggero creaPasseggero(String nome, String cognome, String dataDiNascita, String numeroPassaporto, String codiceFiscale) {
		try{
			 return new Passeggero(nome,cognome,dataDiNascita,numeroPassaporto,codiceFiscale);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} 
		
		
	}
	
}
