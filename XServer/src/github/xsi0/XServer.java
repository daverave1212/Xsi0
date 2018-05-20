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

		public static int portNumber = 8080;
		
		public static void startServer() throws Exception{
			serverSocket		= new ServerSocket(portNumber);
			clientConnections	= new Socket[maxPlayers];
			connectedPlayers	= new Player[maxPlayers];
			activeGames			= new Game[maxPlayers/2];
			System.out.println("Server successfully started!");
			/* Accepta in continuu conexiuni */
			AcceptSocketThread acceptConnections = new AcceptSocketThread();
			System.out.println("Starting to accept players...");
			acceptConnections.start();
		}
		
		public static void main(String[] args) throws Exception{
			startServer();
		}
		
		public static void setupLastPlayer() throws Exception{
			connectedPlayers[nPlayers - 1] = new Player();
			System.out.println("New Player successfully created...");
			Player lastAcceptedPlayer = connectedPlayers[nPlayers - 1];
			lastAcceptedPlayer.setState(PlayerStates.WAITING);
			System.out.println("Pinging client.");
			//lastAcceptedPlayer.send(1337);
			System.out.println("Set state to WAITING (in server only)");
			// sendMessageToLastPlayer("Please wait for a match")
			XServer.matchPlayers();
		}
		
		public static void matchPlayers() throws Exception {
			System.out.println("Trying to find 2 players to match...");
			if(XServer.nPlayers <= 1) {
				System.out.println("Therea are not enough players connected.");
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
					System.out.println("There is no other available player atm.");
					/* Nu sunt destui playeri valabili */}
				else {
					System.out.println("Match found!");
					System.out.println("p1 = " + firstFoundPlayerIndex);
					System.out.println("p2 = " + secondFoundPlayerIndex);
					Game lastCreatedGame = XServer.activeGames[nActiveGames];
					firstPlayer	.setState(PlayerStates.PLAYING);
					secondPlayer.setState(PlayerStates.PLAYING);
					lastCreatedGame = new Game();
					lastCreatedGame.initialize(firstFoundPlayerIndex, secondFoundPlayerIndex);
					System.out.println("Starting game...");
					lastCreatedGame.start();
					
				}
			}
			
		}

}
