package github.xserver;

public class Game {

	public static int X = 1;
	public static int O = 0;
	public static int N = -1;
	
	
	Player p1;
	Player p2;
	
	public int[][] board = new int[][] {
		{N, N, N},
		{N, N, N},
		{N, N, N}};
		
	public String boardToString() {
		StringBuffer stringToSend = new StringBuffer("");
		for(int i = 0; i<=2; i++) {
			for(int j = 0; j<=2; j++) {
				switch(board[i][j]) {
				case 1: stringToSend.append("X"); break;
				case 0: stringToSend.append("O"); break;
				case-1: stringToSend.append("N"); break;}}}
		return stringToSend.toString();}
		
	public void updateBoard(int row, int col, int piece) {
		
	}
	
	public boolean isGameOver() {
		return false;
	}
	
	
}
