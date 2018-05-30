package github.xserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/XServer")
public class XServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

      
    public XServer() {
        super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//	System.out.println(request.getRequestURL().append('?').append(request.getQueryString()));
		try {
			handleRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		//System.out.println(request.getRequestURL().append('?').append(request.getQueryString()));
		try {
			handleRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String action	= request.getParameter("action");
		if(action.equals( "RESET")) {
			playersConnected = new HashMap<String, Player>();
			playersConnectedUsernames = new ArrayList<String>();
			return;}
		if(action.equals("USERS")) {
			response.getWriter().append("" + playersConnectedUsernames.size());
			return;}
		String username = request.getParameter("username");
		System.out.print("Players: " + playersConnectedUsernames.size() + " ");
		Player thisPlayer;
		if(action.equals( Net.LOGIN )){
			String password = request.getParameter("password");
			checkAndValidateLogin(username, password, response);}
		thisPlayer = playersConnected.get(username);
		if(action.equals("WHO")) {
			response.getWriter().append(thisPlayer.enemyPlayer.username);}
		if(action.equals( Net.PLAY )) {
			thisPlayer.state = Player.WAITING;
			matchPlayers();
			response.getWriter().append("OK");}
		if(action.equals( Net.ISGAMEREADY )) {
			boolean isThisGameReady = playersConnected.get(username).isGameReady;
			if( isThisGameReady ){	response.getWriter().append(Net.YES);}
			if(!isThisGameReady ){	response.getWriter().append(Net.NO);}}
		if(action.equals( Net.MYPIECE )) {
			if(thisPlayer.piece == Game.X){	response.getWriter().append(Net.X);}
			if(thisPlayer.piece == Game.O){	response.getWriter().append(Net.O);}}
		if(action.equals( Net.ISMYTURN )) {
			if( thisPlayer.autoWin) {
				response.getWriter().append(Net.YOUWIN);}
			else if( thisPlayer.game.isGameOver()) {
				response.getWriter().append(Net.YOULOSE);
				disconnectPlayer(thisPlayer.username);
			}
			else if( thisPlayer.isMyTurnNow ){	response.getWriter().append(Net.YES);}
			else if(!thisPlayer.isMyTurnNow ){	response.getWriter().append(Net.NO);}}
		if(action.equals( Net.GETBOARD )) {
			String boardAsString = thisPlayer.game.boardToString();
			response.getWriter().append(boardAsString);}
		if(action.equals( Net.MOVE )) {
			if(thisPlayer.autoWin) {
				response.getWriter().append(Net.YOUWIN);}
			else {
				int square = Integer.parseInt(request.getParameter("square"));
				int row = square/3;
				int col = square%3;
				thisPlayer.game.updateBoard(row, col, thisPlayer.piece);
				boolean isGameOver = thisPlayer.game.isGameOver();
				if( isGameOver ) {response.getWriter().append(Net.YOUWIN);
					disconnectPlayer(thisPlayer.username);}
				if(!isGameOver ) {response.getWriter().append(Net.ENEMYTURN);
					thisPlayer.isMyTurnNow = false;
					thisPlayer.enemyPlayer.isMyTurnNow = true;}}}
		if(action.equals(Net.CONCEDE)) {
			if(thisPlayer.state == Player.IDLE) {
				disconnectPlayer(username);}
			else if(thisPlayer.state == Player.WAITING) {
				thisPlayer.state = Player.IDLE;
				disconnectPlayer(username);}
			else if(thisPlayer.state == Player.PLAYING) {
				thisPlayer.enemyPlayer.autoWin = true;
				disconnectPlayer(username);
			}
		}
		
		
	}
	
	/* OTHER SERVER METHODS */
	
	public static HashMap<String, Player> playersConnected = new HashMap<String, Player>();
	public static ArrayList<String> playersConnectedUsernames = new ArrayList<String>();
	
	public static void connectPlayer(String username) {
		playersConnected.put(username, new Player());
		playersConnectedUsernames.add(username);
		playersConnected.get(username).state = Player.IDLE;
		playersConnected.get(username).username = username;}
	
	public static void disconnectPlayer(String username) {
		int indexInUsernames = findStringInArrayList(playersConnectedUsernames, username);
		playersConnectedUsernames.remove(indexInUsernames);
		playersConnected.remove(username);}
	
	public static int findStringInArrayList(ArrayList<String> a, String s) {
		for(int i = 0; i<a.size(); i++) {
			if(a.get(i).equals(s)) {
				return i;}}
		return -1;}
	
	 public static void checkAndValidateLogin(String username, String password, HttpServletResponse response) throws Exception{
		boolean isLoginAccepted = Databases.validateLogin(username, password);
		if(isLoginAccepted) {
			response.getWriter().append(Net.LOGINACCEPTED);
			connectPlayer(username);}
		else {
			response.getWriter().append(Net.NO);}}
	 
	 public static void matchPlayers() {
		 String p1username = "~";
		 String p2username = "~";
		 
		 for(int i = 0; i<playersConnectedUsernames.size(); i++) {
			 String currentUsername = playersConnectedUsernames.get(i);
			 Player currentPlayer	= playersConnected.get(currentUsername);
			 if(currentPlayer.state == Player.WAITING) {
				 if(p1username == "~") {
					 p1username = currentUsername;}
				 else {
					 p2username = currentUsername;}}}
		 if(p2username != "~") {
			 // 2 players were found!
			 Player p1 = playersConnected.get(p1username);
			 System.out.println(p1.username);
			 Player p2 = playersConnected.get(p2username);
			 System.out.println(p2.username);
			 createGame(p1, p2);}
		 else {/* Not enough players were found */}}
	 
	 public static void createGame(Player p1, Player p2) {
		 p1.isGameReady = false;
		 p2.isGameReady = false;
		 p1.state = Player.PLAYING;
		 p2.state = Player.PLAYING;
		 p1.enemyPlayer = p2;
		 p2.enemyPlayer = p1;
		 p1.game = new Game();
		 p2.game = p1.game;
		 p1.game.p1 = p1;
		 p2.game.p2 = p2;
		 p1.piece = Game.X;
		 p2.piece = Game.O;
		 p1.isMyTurnNow = true;
		 p2.isMyTurnNow = false;
		 p1.isGameReady = true;
		 p2.isGameReady = true;}

}
