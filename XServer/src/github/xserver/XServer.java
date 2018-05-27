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
		response.getWriter().append("This was from doGet() ");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		try {
			handleRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String username = request.getParameter("username");
		String action	= request.getParameter("action");
		Player thisPlayer;
		if(action.equals( Net.LOGIN )){
			String password = request.getParameter("password");
			checkAndValidateLogin(username, password, response);}
		
		thisPlayer = playersConnected.get(username);
		
		if(action.equals( Net.PLAY )) {
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
			if( thisPlayer.isMyTurnNow ){	response.getWriter().append(Net.YES);}
			if(!thisPlayer.isMyTurnNow ){	response.getWriter().append(Net.NO);}}
		if(action.equals( Net.GETBOARD )) {
			String boardAsString = thisPlayer.game.boardToString();
			response.getWriter().append(boardAsString);}
		if(action.equals( Net.MOVE )) {
			int col = Integer.parseInt(request.getParameter("col"));
			int row = Integer.parseInt(request.getParameter("row"));
			thisPlayer.game.updateBoard(row, col, thisPlayer.piece);
			boolean isGameOver = thisPlayer.game.isGameOver();
			if( isGameOver ) {response.getWriter().append(Net.YOUWIN);
				finalizeGame(thisPlayer);}
			if(!isGameOver ) {response.getWriter().append(Net.ENEMYTURN);
				thisPlayer.isMyTurnNow = false;
				thisPlayer.enemyPlayer.isMyTurnNow = true;}}
		
		
	}
	
	/* OTHER SERVER METHODS */
	
	public static HashMap<String, Player> playersConnected = new HashMap<String, Player>();
	public static ArrayList<String> playersConnectedUsernames = new ArrayList<String>();
	
	public static void connectPlayer(String username) {
		playersConnected.put(username, new Player());
		playersConnectedUsernames.add(username);
		playersConnected.get(username).state = Player.WAITING;}
	
	 public static void checkAndValidateLogin(String username, String password, HttpServletResponse response) throws Exception{
		boolean isLoginAccepted = Databases.validateLogin(username, password);
		if(isLoginAccepted) {
			response.getWriter().append(Net.YES);
			connectPlayer(username);
			}
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
			 Player p2 = playersConnected.get(p2username);
			 createGame(p1, p2);}
		 else {/* Not enough players were found */}}
	 
	 public static void createGame(Player p1, Player p2) {
		 p1.isGameReady = false;
		 p2.isGameReady = false;
		 p1.game = new Game();
		 p2.game = p1.game;
		 p1.piece = Game.X;
		 p2.piece = Game.O;
		 p1.isMyTurnNow = true;
		 p2.isMyTurnNow = false;
		 p1.isGameReady = true;
		 p2.isGameReady = true;}

	 public static void finalizeGame(Player p) {
		 
	 }
}
