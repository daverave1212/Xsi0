package github.xserver;

public class Player {
	
	public static int IDLE	  = -1;
	public static int WAITING = 0;
	public static int PLAYING = 1;

	private String username;
	private Player enemyPlayer;
	private int state = WAITING;
	private boolean isGameReady = false;
	private boolean isMyTurnNow = false;
	private boolean autoWin = false;
	private Game game;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Player getEnemyPlayer() {
		return enemyPlayer;
	}
	public void setEnemyPlayer(Player enemyPlayer) {
		this.enemyPlayer = enemyPlayer;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public boolean isGameReady() {
		return isGameReady;
	}
	public void setGameReady(boolean isGameReady) {
		this.isGameReady = isGameReady;
	}
	public boolean isMyTurnNow() {
		return isMyTurnNow;
	}
	public void setMyTurnNow(boolean isMyTurnNow) {
		this.isMyTurnNow = isMyTurnNow;
	}
	public boolean isAutoWin() {
		return autoWin;
	}
	public void setAutoWin(boolean autoWin) {
		this.autoWin = autoWin;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public int getPiece() {
		return piece;
	}
	public void setPiece(int piece) {
		this.piece = piece;
	}
	private int piece = Game.N;
}
