package github.xclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Net {
	
	    public static String defaultServerAddress = "";
	   
		// Requests from client to server
		public static String LOGIN			= "LOGIN";
		public static String ISGAMEREADY	= "ISGAMEREADY";
		public static String MYPIECE		= "MYPIECE";
		public static String ISMYTURN		= "ISMYTURN";
		public static String GETBOARD		= "GETBOARD";
		public static String MOVE			= "MOVE";
		public static String PLAY			= "PLAY";
		public static String CONCEDE		= "CONCEDE";

		// Responses from server to client
		public static String LOGINACCEPTED	= "LOGINACCEPTED";
		public static String NO				= "NO";
		public static String YES			= "YES";
		public static String X				= "X";
		public static String O				= "O";
		public static String YOUWIN			= "YOUWIN";
		public static String YOULOSE		= "YOULOSE";
		public static String ENEMYTURN		= "ENEMYTURN";

	   public static void setDefaultServerAddress(String address) {
		   defaultServerAddress = address;}
	   
	   public static String get(String urlToRead) throws Exception {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("POST");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);}
	      rd.close();
	      return result.toString();}
	   
	   public static String request(String action, String otherParams) throws Exception{
		   String username = XClient.username;
		   return Net.get(defaultServerAddress + "?username=" + username + "&action=" + action + "&" + otherParams);
	   }
	
}
