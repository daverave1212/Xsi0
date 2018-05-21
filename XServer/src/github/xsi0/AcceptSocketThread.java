package github.xsi0;

public class AcceptSocketThread extends Thread{
	
	public void run() {
		/* Momentan, ia doar ultimul element din array ca noua conexiune
		 * Daca se elibereaza alta, nu ii pasa
		 * O sa trebuiasca sa schimbam asta */
		System.out.println("Waiting to receive a player...");
		for(int i = 0; i < XServer.maxPlayers; i++) {
			System.out.println("Trying to find player " + i);
			try {
				System.out.println("Waiting for any player to connect...");
				XServer.clientConnections[i] = XServer.serverSocket.accept();
				System.out.println("Player found! Setting up...");
				XServer.nPlayers++;
				System.out.println("Player connected! Setting up...");
				XServer.setupLastPlayer();
			} catch (Exception e) {
				System.out.println("ERROR: Something went wrong...");
				e.printStackTrace();}
		}
	}

	
}
