package github.xserver;

public class Player {
	
	public static int WAITING = 0;
	public static int PLAYING = 1;

	private String username;
	
	public Player enemyPlayer;
	public int state = WAITING;
	public boolean isGameReady = false;
	public boolean isMyTurnNow = false;
	public Game game;
	public int piece = Game.N;
}
