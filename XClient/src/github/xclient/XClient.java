package github.xclient;

public class XClient {
	
	private static String username = "~@!~";
	private static String password;
	private static ClientFrame client;
	private static String piece;
	
	public static String getPiece() {
		return piece;}
	
	public static void setPiece(String s) {
		piece = s;}
	
	public static String getUsername() {
		return username;}
	
	public static void setUsername(String u) {
		username = u;}
	
	public static int X = 1;
	public static int O = 0;
	public static int N = -1;
	
	public static int[][] board = new int[][] {
		{N, N, N},
		{N, N, N},
		{N, N, N}};
		
	public static void updateBoard(String query) {
		switch(query.charAt(0)) {
		case 'N': board[0][0] = N; break;
		case 'X': board[0][0] = X; break;
		case 'O': board[0][0] = O; break;}
		switch(query.charAt(1)) {
		case 'N': board[0][1] = N; break;
		case 'X': board[0][1] = X; break;
		case 'O': board[0][1] = O; break;}
		switch(query.charAt(2)) {
		case 'N': board[0][2] = N; break;
		case 'X': board[0][2] = X; break;
		case 'O': board[0][2] = O; break;}
		switch(query.charAt(3)) {
		case 'N': board[1][0] = N; break;
		case 'X': board[1][0] = X; break;
		case 'O': board[1][0] = O; break;}
		switch(query.charAt(4)) {
		case 'N': board[1][1] = N; break;
		case 'X': board[1][1] = X; break;
		case 'O': board[1][1] = O; break;}
		switch(query.charAt(5)) {
		case 'N': board[1][2] = N; break;
		case 'X': board[1][2] = X; break;
		case 'O': board[1][2] = O; break;}
		switch(query.charAt(6)) {
		case 'N': board[2][0] = N; break;
		case 'X': board[2][0] = X; break;
		case 'O': board[2][0] = O; break;}
		switch(query.charAt(7)) {
		case 'N': board[2][1] = N; break;
		case 'X': board[2][1] = X; break;
		case 'O': board[2][1] = O; break;}
		switch(query.charAt(8)) {
		case 'N': board[2][2] = N; break;
		case 'X': board[2][2] = X; break;
		case 'O': board[2][2] = O; break;}
		updateInterfaceButtons();}
	
	public static void updateInterfaceButtons() {
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				if(board[i][j] == N) {client.getButton(i, j).setText("N");}
				if(board[i][j] == X) {client.getButton(i, j).setText("X");}
				if(board[i][j] == O) {client.getButton(i, j).setText("O");}}}
		client.revalidate();
		client.repaint();}
	
	public static void handleActionRequest(String action){ try {
		String response = "NULL";
		if(action.equals(Net.LOGIN)) {	// On click pe loginButton
			username = client.getUsername();
			password = client.getPassword();
			response = Net.request(Net.LOGIN, "password=" + password);
			System.out.println("Login: " + response);
			if(response.equals(Net.LOGINACCEPTED)) {
				client.setStage(ClientFrame.PLAYPANEL);}}
		if(action.equals(Net.PLAY)) {
			response = Net.request(Net.PLAY, "other=filler");
			System.out.println("Play:" + response);
			client.setStage(ClientFrame.WAITPANEL);}
		}catch(Exception e) {e.printStackTrace();}}
	
	public static void handleActionRequest(String action, int row, int col){ try {
		String response = "NULL";
		int sentIndex = row * 3 + col;
		response = Net.request(Net.MOVE, "square=" + sentIndex);
		System.out.println("Received " + response);
		if(response.equals(Net.ENEMYTURN)) {
			response = Net.request(Net.GETBOARD, "allyourbase=belongtous");
			//updateBoard(response);
			//updateInterfaceButtons();
			client.setGameplayState(ClientFrame.ENEMYTURN);}
		else if(response.equals(Net.YOUWIN)) {
			client.setStage(ClientFrame.GAMEOVERPANEL);
			client.getGameOverLabel().setText("YOU WIN!");
			client.revalidate();
			client.repaint();}
		
		}catch(Exception e) {e.printStackTrace();}}


	
	
	public static void main(String[] args) throws Exception{
		Net.setDefaultServerAddress("http://localhost:8080/XServer/XServer");
		client = new ClientFrame();
		
		
	}
	
}
