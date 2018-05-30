package github.xserver;

public class Player {
	
	public static int IDLE	  = -1;
	public static int WAITING = 0;
	public static int PLAYING = 1;

	public String username;
	
	public Player enemyPlayer;
	public int state = WAITING;
	public boolean isGameReady = false;
	public boolean isMyTurnNow = false;
	public boolean autoWin = false;
	public Game game;
	public int piece = Game.N;
}
