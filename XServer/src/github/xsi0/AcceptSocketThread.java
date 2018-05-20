package github.xsi0;

public class AcceptSocketThread extends Thread{
	
	public void run() {
		/* Momentan, ia doar ultimul element din array ca noua conexiune
		 * Daca se elibereaza alta, nu ii pasa
		 * O sa trebuiasca sa schimbam asta */
		for(int i = 0; i < XServer.maxPlayers; i++) {
			try {
				System.out.println("Waiting for any player to connect...");
				XServer.clientConnections[i] = XServer.serverSocket.accept();
				XServer.nPlayers++;
				System.out.println("Player connected! Setting up...");
				XServer.setupLastPlayer();
			} catch (Exception e) {
				e.printStackTrace();}
		}
	}

	
}
