package github.xserver;

public class Game {

	public static int X = 1;
	public static int O = 0;
	public static int N = -1;
	
	
	private Player p1;
	private Player p2;
	
	public Player getP1() { return p1;}
	public void setP1(Player p1) { this.p1 = p1;}
	public Player getP2() { return p2;}
	public void setP2(Player p2) { this.p2 = p2;}

	private int[][] board = new int[][] {
		{N, N, N},
		{N, N, N},
		{N, N, N}};
		
	public int getBoard(int i, int j) {
		return board[i][j];}
		
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
		board[row][col] = piece;}
	
	public boolean isGameOver() {
		int piece;
		for(int i = 0; i<=2; i++) {
			piece = board[i][0];
			if(board[i][0] == piece && board[i][1] == piece && board[i][2] == piece && piece != N) {
				return true;}
			piece = board[0][i];
			if(board[0][i] == piece && board[1][i] == piece && board[2][i] == piece && piece != N) {
				return true;}}
		if(board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[2][2] != N) {
			return true;}
		if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[2][0] != N) {
			return true;}
		return false;
	}
	
	
}
