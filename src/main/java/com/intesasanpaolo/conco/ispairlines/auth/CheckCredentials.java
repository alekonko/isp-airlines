package com.intesasanpaolo.conco.ispairlines.auth;

public class CheckCredentials {

	public static boolean getBasicAuth(String username, String password) {
		if (username.equals("admin") & password.equals("sanpaolo")) { return true; }
		else {return false;}
	}

/*	public static boolean getDBAuth(String username, String password) {
		
		UserDao dao =  new UserDao();
		
		User user = dao.getUserByUserName(username);
		dao.CloseConnection();
		
		String hash="";
		try{
			hash=AeSimpleSHA1.SHA1(password);
			}
		catch(Exception e){
			hash=password;
			}
		
		
		if ( user.getUserName().equalsIgnoreCase(username) & user.getPassword().equals(hash)) 
			{
			return true; 
			}
		else 
			{
			return false;
			}
		
	}
	*/
	
}
