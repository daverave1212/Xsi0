package github.xclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XClient {
	
	public static String username;
	public static String password;
	public static ClientFrame client;
	
	public static void handleActionRequest(String action){ try {
		String response = "NULL";
		if(action.equals(Net.LOGIN)) {	// On click pe loginButton
			username = client.getUsername();
			password = client.getPassword();
			response = Net.request(Net.LOGIN, "password=" + password);
			if(response.equals(Net.LOGINACCEPTED)) {
				client.setStage(ClientFrame.PLAYPANEL);}}
		// TO DO : action events for other button
		
		}catch(Exception e) {e.printStackTrace();}}
		//endHandleActionRequest
	
	public static void main(String[] args) throws Exception{
		Net.setDefaultServerAddress("http://5.12.112.34:8080/XServer/XServer");
		client = new ClientFrame();
		
		
	}
	
}
