package com.intesasanpaolo.conco.ispairlines.util;


import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SimpleLog {
	private static final Logger logger = Logger.getLogger("CloudPortalUtils");	

	//public SimpleLog(){
	// }
	
	public static void toFileINFO(String logString)
		{
		try{
		 System.out.println(logString); 
        } catch (Exception e){
        	e.printStackTrace();
        }
        //

		}
	
		public static void toFileINFOv1(String logString)
		{
		 logger.setUseParentHandlers(false);
		 logger.setLevel(Level.INFO);
		 FileHandler fh;
	    try {
	        // This block configure the logger with handler and formatter  
	        // temp directory
	    	// fh = new FileHandler("%t/CloudPortalUtils-log.%u.%g.txt",1024 * 1024, 10);  
	    	fh = new FileHandler("../server/default/log/CloudPortalUtils.%u.%g.log",10485760, 1);
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
	        logger.info(logString);
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {
	        e.printStackTrace();  
	    } catch (Exception e){
	    	e.printStackTrace();
	    }
	    //
	
		}
	
}
