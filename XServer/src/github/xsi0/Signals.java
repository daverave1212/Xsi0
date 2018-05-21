package github.xsi0;

public class Signals {

	/* Clientul si Serverul comunica intre ele pe baza unor semnale
	 * Serverul trimite semnale catre client si vice versa
	 * Clasa asta e aceeasi si pentru Server si pentru Client */
	
	/* From server to client */
	public static final int SETPIECE0	= 0;	// Sets client's piece to 0
	public static final int SETPIECEX	= 1;	// Sets client's piece to X
	public static final int STARTGAME	= 2;	// Switches client's interface to game one
	public static final int YOURTURN	= 3;	// unfreezes UI for that player
	
	/* From client to server */
	public static final int MADEMOVE  = 10;
	
}
