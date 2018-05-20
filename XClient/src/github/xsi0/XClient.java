package github.xsi0;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class XClient {

	public static int N = -1;
	public static int O = 0;
	public static int X = 1;
	
	public static Socket connection;
	public static int port	= 8080;
	public static String ip	= "localhost"; 
	public static PrintStream printStream;
	public static Scanner scanner;
	public static int lastReceivedMessage;
	
	public static int myPiece = N;
	
	public static void send(int signal) {
		printStream.println(signal);}
	
	public static int receive() {
		int receivedSignal = scanner.nextInt();
		return receivedSignal;}
	
	public static void clickedOnSquare(int rowIndex, int colIndex) {
		send(Signals.MADEMOVE);
		send(rowIndex);
		send(colIndex);}
	
	public static void startReceiving() {
		Thread receivingSignals = new Thread(new Runnable() {
			public void run() {
				while(true) {
					System.out.println("Waiting to receive any signal from server...");
					lastReceivedMessage = receive();
					System.out.print("Received: ");
					if(lastReceivedMessage == Signals.SETPIECE0) {
						System.out.println("SETPIECE0");
						myPiece = O;}
					if(lastReceivedMessage == Signals.SETPIECEX) {
						System.out.println("SETPIECEX");
						myPiece = X;}
					if(lastReceivedMessage == Signals.STARTGAME) {
						System.out.println("STARTGAME");
						switchToGameUI();}
					if(lastReceivedMessage == Signals.YOURTURN) {
						System.out.println("YOURTURN");
						unfreezeUI();}}}});
		receivingSignals.start();
	}
	
	public static void startClient() throws Exception{
		System.out.println("Starting client. Forming connection to server...");
		connection	= new Socket(ip, port);
		System.out.println("Connection success! Starting PrintStream...");
		printStream	= new PrintStream(connection.getOutputStream());
		System.out.println("PrintStream success! Starting Scanner...");
		scanner		= new Scanner(connection.getInputStream());
		System.out.println("Scanner success!");
		System.out.println("Starting to receive input from server...");
		startReceiving();
	}
	
	
	public static void main(String[] args) throws Exception{
		startClient();
		// TESTED AND WORKS AS INTENDED
	}
	
	
	
	
	
	
	
	
	public static void initializeUI() {
		
		/* Aici vine codul pentru tot ce tine de interfata grafica */
		
	}
	
	public static void switchToGameUI() {
		
		/* Se schimba din "Play" in interfata cu 9 patrate */
		
	}
	
	public static void freezeUI() {
		
		/* toate butonaele se blocheaza */
		
	}
	
	public static void unfreezeUI() {
	
		/* toate butoanele se deblocheaza */

	}
	
	
	
	
	
}
