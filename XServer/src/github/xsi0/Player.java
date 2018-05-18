package github.xsi0;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Player {
	
	/* Dupa ce playerului i-a fost facut setSocket,
	 * acesta poate sa primeasca si sa trimita semnale
	 * (din clasa Signals) catre clientul respectiv */

	public int state;
	public int inGameState;
	public int piece;
	public Socket connection;
	public Scanner scanner;
	public PrintStream printStream;
	
	public void setState(int s) {
		state = s;}
	
	public void setSocket(Socket s) throws Exception {
		connection = s;
		scanner = new Scanner(connection.getInputStream());
		printStream = new PrintStream(connection.getOutputStream());}
	
	public void send(int message) {
		printStream.println(message);}
	
	public int receive() {
		int receivedSignal = scanner.nextInt();
		return receivedSignal;}
	
}
