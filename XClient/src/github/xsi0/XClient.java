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
					lastReceivedMessage = receive();
					if(lastReceivedMessage == Signals.SETPIECE0) {
						myPiece = O;}
					if(lastReceivedMessage == Signals.SETPIECEX) {
						myPiece = X;}
					if(lastReceivedMessage == Signals.STARTGAME) {
						switchToGameUI();}
					if(lastReceivedMessage == Signals.YOURTURN) {
						unfreezeUI();}}}});
		receivingSignals.start();
		
	}
	
	public XClient() throws Exception{
		connection	= new Socket(ip, port);
		printStream	= new PrintStream(connection.getOutputStream());
		scanner		= new Scanner(connection.getInputStream());
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
