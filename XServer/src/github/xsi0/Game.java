package github.xsi0;

import java.net.Socket;
import java.util.Random;

public class Game {

	public static int N = -1;
	public static int O = 0;
	public static int X = 1;
	
	Player p1;
	Player p2;
	Socket p1Connection;
	Socket p2Connection;
	int p1Signal;
	int p2Signal;
	int[][] board = new int[][] {
		{N, N, N},
		{N, N, N},
		{N, N, N}};
	
	public void checkGameEnd() {}
	
	public void updateBoard(int rindex, int cindex, int piece) {
		board[rindex][cindex] = piece;}
	
	public void start() {
		p1.send(Signals.SETPIECE0);
		p2.send(Signals.SETPIECEX);
		p1.send(Signals.STARTGAME);
		p2.send(Signals.STARTGAME);
		p1.inGameState = PlayerStates.ENEMYSTURN;
		p2.inGameState = PlayerStates.MAKINGMOVE;
		Thread readFromP1 = new Thread(new Runnable() {
			public void run() {
				while(true) {
					System.out.println("");
					System.out.println("Waiting to receive any signal from p1...");
					p1Signal = p1.receive();
					System.out.println("A signal received from p1!");
					if(p1Signal == Signals.MADEMOVE) {		// After player makes a move,
						int matrixRowIndex = p1.receive();	// it sends the indices for the
						int matrixColIndex = p1.receive();	// row and col it ticked
						p1.inGameState = PlayerStates.ENEMYSTURN;	// Changes state FOR SERVER ONLY
						p2.inGameState = PlayerStates.MAKINGMOVE;	// The client changes the state on its own
						p2.send(Signals.YOURTURN);					// Unfreezes that player's UI
						updateBoard(matrixRowIndex, matrixColIndex, O);}
					else {
						/* Concede? Exit? End Game? */
						System.out.println("Game ended weirdly. Closing thread.");
						break;
						}}}});
		Thread readFromP2 = new Thread(new Runnable() {
			public void run() {
				while(true) {
					System.out.println("Waiting to receive any signal from p2...");
					p2Signal = p2.receive();
					System.out.println("A signal received from p2!");
					if(p2Signal == Signals.MADEMOVE) {		// After player makes a move,
						int matrixRowIndex = p2.receive();	// it sends the indices for the
						int matrixColIndex = p2.receive();	// row and col it ticked
						p2.inGameState = PlayerStates.ENEMYSTURN;
						p1.inGameState = PlayerStates.MAKINGMOVE;
						p1.send(Signals.YOURTURN);
						updateBoard(matrixRowIndex, matrixColIndex, O);}
					else {
						/* Concede? Exit? End Game? */
						System.out.println("Game ended weirdly. Closing thread.");
						break;
						}}}});
		readFromP1.start();
		readFromP2.start();
	}
	
	public void initialize(int p1Index, int p2Index) throws Exception{
		p1 = XServer.connectedPlayers[p1Index];
		p2 = XServer.connectedPlayers[p2Index];
		p1Connection = XServer.clientConnections[p1Index];
		p2Connection = XServer.clientConnections[p2Index];
		p1.setSocket(p1Connection);
		p2.setSocket(p2Connection);
	}
	
}
