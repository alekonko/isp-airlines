package com.intesasanpaolo.conco.ispairlines.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date; 

public class Biglietto {

	public Biglietto(Classe classeBiglietto, Passeggero passeggero, String aeroportoPartenza, 
			String aeroportoDestinazione, String dataVolo) throws ParseException {
		this.passeggero = passeggero;
		this.aeroportoPartenza = aeroportoPartenza;
		this.aeroportoDestinazione = aeroportoDestinazione;
		this.classeBiglietto = classeBiglietto;
		this.dataVolo = stringToDate(dataVolo);
	}
	
	private Date stringToDate(String dataVoloString) throws ParseException {
		Date dataNascita = dateFormat.parse(dataVoloString);
		return dataNascita;
	}
	
	private Classe classeBiglietto;
	private Passeggero passeggero;
	private String aeroportoPartenza;
	private String aeroportoDestinazione;
	private Date dataVolo;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	
	
	public Passeggero getPasseggero() {
		return passeggero;
	}
	public void setPasseggero(Passeggero passeggero) {
		this.passeggero = passeggero;
	}
	public String getAeroportoPartenza() {
		return aeroportoPartenza;
	}
	public void setAeroportoPartenza(String aeroportoPartenza) {
		this.aeroportoPartenza = aeroportoPartenza;
	}
	public String getAeroportoDestinazione() {
		return aeroportoDestinazione;
	}
	public void setAeroportoDestinazione(String aeroportoDestinazione) {
		this.aeroportoDestinazione = aeroportoDestinazione;
	}
	public Classe getClasseBiglietto() {
		return classeBiglietto;
	}
	public void setClasseBiglietto(Classe classeBiglietto) {
		this.classeBiglietto = classeBiglietto;
	}
	public Date getDataVolo() {
		return dataVolo;
	}
	public void setDataVolo(Date dataVolo) {
		this.dataVolo = dataVolo;
	}
	
	@Override
	public String toString() {
		String datiVolo = "";
		datiVolo += aeroportoPartenza + " - " + aeroportoDestinazione + " " + dateFormat.format(dataVolo) + " " + classeBiglietto.name() + " " + passeggero;
		return datiVolo;
	}
	
	public String toJSON() {
		String myJson =  "{\"aeroportoPartenza\": \"" + aeroportoPartenza + "\", \"aeroportoDestinazione\": \"" + aeroportoDestinazione + "\", \"dataVolo\": \"" + dateFormat.format(dataVolo) + "\", \"classe\": \"" + classeBiglietto.name() + "\", \"passeggero\": \"" + passeggero + "\" }";
		return myJson;
	}
	
}
