package com.intesasanpaolo.conco.ispairlines.tickets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.intesasanpaolo.conco.ispairlines.model.Biglietto;
import com.intesasanpaolo.conco.ispairlines.model.Passeggero;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ContextListener() {	
    	
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 

    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent evento)  { 
    	
    	ServletContext servletContext = evento.getServletContext();
    	
    	// salvo in contesto Lista Passeggeri
    	@SuppressWarnings("unchecked")
    	//controllo se ho parametro passeggeri (al primo boot no), se vuoto inizializzo e creo HashMap
    	Map<String,Passeggero> mappaPasseggeriInContext = (Map<String,Passeggero>) servletContext.getAttribute("passeggeri");    	
    	if (mappaPasseggeriInContext == null) {
        	System.out.println("InitMyContext: Attributo <passeggeri> non esistente nel contex. lo creo e gli associo  mappaPasseggeri"); 
    		mappaPasseggeriInContext = new HashMap<>();
    		servletContext.setAttribute("passeggeri",mappaPasseggeriInContext);
    	}
    	
    	
        @SuppressWarnings("unchecked")
		List<Biglietto> listaBigliettiInContext =  (List<Biglietto>) servletContext.getAttribute("biglietti");

        if (listaBigliettiInContext == null) { 
        	System.out.println("InitMyContext: Attributo <biglietti> non esistente nel contex. lo creo e gli associo  listaBiglietti"); 
        	listaBigliettiInContext = new ArrayList<>();
        	servletContext.setAttribute("biglietti", listaBigliettiInContext);
        }
    	
    }
	
}
