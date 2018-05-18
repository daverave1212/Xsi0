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
		
		public XServer() throws Exception {
			serverSocket		= new ServerSocket(portNumber);
			clientConnections	= new Socket[maxPlayers];
			connectedPlayers	= new Player[maxPlayers];
			activeGames			= new Game[maxPlayers/2];
			
			/* Accepta in continuu conexiuni */
			AcceptSocketThread acceptConnections = new AcceptSocketThread();
			acceptConnections.start();	
		}
		
		public static void setupLastPlayer() throws Exception{
			connectedPlayers[nPlayers - 1] = new Player();
			Player lastAcceptedPlayer = connectedPlayers[nPlayers - 1];
			lastAcceptedPlayer.setState(PlayerStates.WAITING);
			// sendMessageToLastPlayer("Please wait for a match")
			XServer.matchPlayers();
		}
		
		public static void matchPlayers() throws Exception {
			if(XServer.nPlayers <= 1) {
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
					/* Nu sunt destui playeri valabili */}
				else {
					Game lastCreatedGame = XServer.activeGames[nActiveGames];
					firstPlayer	.setState(PlayerStates.PLAYING);
					secondPlayer.setState(PlayerStates.PLAYING);
					lastCreatedGame = new Game();
					lastCreatedGame.initialize(firstFoundPlayerIndex, secondFoundPlayerIndex);
					lastCreatedGame.start();
					
				}
			}
			
		}

}
