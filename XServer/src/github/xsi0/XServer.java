package github.xsi0;

import java.net.ServerSocket;
import java.net.Socket;

public class XServer {
	
		public static ServerSocket serverSocket;
		public static Socket[] clientConnections;
		public static Player[] connectedPlayers;
		public static Game[] activeGames;
		public static int maxPlayers	= 10;
		public static int nPlayers		= 0;
		public static int nActiveGames	= 0;
		public static int portNumber	= 8080;
		
		public static void startServer() throws Exception {
			System.out.println("Starting server on port " + portNumber);
			serverSocket		= new ServerSocket(portNumber);
			System.out.println("Initializing empty Socket array...");
			clientConnections	= new Socket[maxPlayers];
			System.out.println("Initializing empty Player array");
			connectedPlayers	= new Player[maxPlayers];
			System.out.println("Initializing empty Game array");
			activeGames			= new Game[maxPlayers/2];
			/* Accepta in continuu conexiuni */
			AcceptSocketThread acceptConnections = new AcceptSocketThread();
			acceptConnections.start();	}
		
		public static void setupLastPlayer() throws Exception{
			connectedPlayers[nPlayers - 1] = new Player();
			System.out.println("Created player " + (nPlayers - 1));
			Player lastAcceptedPlayer = connectedPlayers[nPlayers - 1];
			System.out.println("Setting it's state to WAITING in server...");
			lastAcceptedPlayer.setState(PlayerStates.WAITING);
			// sendMessageToLastPlayer("Please wait for a match")
			System.out.println("Matching players...");
			XServer.matchPlayers();
		}
		
		public static void matchPlayers() throws Exception {
			if(XServer.nPlayers <= 1) {
				System.out.println("There are not enough players to match");
				/* There are not enough players to match */}
			else {	// Cauta primii 2 playeri available
				int firstFoundPlayerIndex = -1;
				int secondFoundPlayerIndex = -1;
				for(int i = 0; i<nPlayers; i++) {
					if(connectedPlayers[i].state == PlayerStates.WAITING) {
						if(firstFoundPlayerIndex == -1) {
							firstFoundPlayerIndex = i;}
						else {
							secondFoundPlayerIndex = i;
							break;}}}
				Player firstPlayer	= XServer.connectedPlayers[firstFoundPlayerIndex];
				Player secondPlayer	= XServer.connectedPlayers[secondFoundPlayerIndex];
				
				if(secondFoundPlayerIndex == -1) {
					System.out.println("No other waiting players found");
					/* Nu sunt destui playeri valabili */}
				else {
					System.out.println("Found players!");
					System.out.println("Player 1:" + firstFoundPlayerIndex);
					System.out.println("Player 2:" + secondFoundPlayerIndex);
					System.out.println("Creating new game...");
					XServer.activeGames[nActiveGames] = new Game();
					Game lastCreatedGame = XServer.activeGames[nActiveGames];
					System.out.println("Setting player states in server");
					firstPlayer	.setState(PlayerStates.PLAYING);
					secondPlayer.setState(PlayerStates.PLAYING);
					System.out.println("initializing game...");
					lastCreatedGame.initialize(firstFoundPlayerIndex, secondFoundPlayerIndex);
					lastCreatedGame.start();}}}
		
		public static void main(String[] args) throws Exception{
			XServer.startServer();
			// TESTED AND WORKS AS INTENDED
		}

}
