package com.intesasanpaolo.conco.ispairlines.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Passeggero {

	private String nome;
	private String cognome;
	private Date dataDiNascita;
	private String numeroPassaporto;
	private String codiceFiscale;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public Passeggero(){
		this.nome = "";
		this.cognome = "";
		this.dataDiNascita = null;
		this.numeroPassaporto = "";	
		this.codiceFiscale = "";
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Passeggero(String nome, String cognome, String dataDiNascita, String codiceFiscale, String numeroPassaporto) throws ParseException {

		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataDiNascita = stringToDate(dataDiNascita);
		this.numeroPassaporto = numeroPassaporto;
	}

	private Date stringToDate(String dateDiNascitaString) throws ParseException{		
		Date dataNascita = dateFormat.parse(dateDiNascitaString);
		return dataNascita;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public Date getDataDiNascita() {
		return dataDiNascita;
	}
	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	public String getNumeroPassaporto() {
		return numeroPassaporto;
	}
	public void setNumeroPassaporto(String numeroPassaporto) {
		this.numeroPassaporto = numeroPassaporto;
	}
	
	
	@Override
	public String toString() {
		String datiPasseggero = "";		
		datiPasseggero += nome + " " + cognome + " " + dateFormat.format(dataDiNascita) + " " + numeroPassaporto + " " + codiceFiscale;
		return datiPasseggero;
	}
	
	public String toJSON() {		
		String myJson =  "{\"nome\": \"" + nome + "\", \"cognome\": \"" + cognome + "\", \"dataNascita\": \"" + dateFormat.format(dataDiNascita) + "\", \"numeroPassaporto\": \"" + numeroPassaporto + "\", \"codiceFiscale\": \"" + codiceFiscale + "\" }";
		return myJson;
	}
	
	public String toHtml() {
		String toHtml = "";		
		toHtml += "<tr><td>"+ nome + "</td><td>" + cognome + "</td><td>" + dateFormat.format(dataDiNascita) + "</td><td>" + numeroPassaporto + "</td><td>" + codiceFiscale +"</td></tr>";
		return toHtml;
	}
	
}
