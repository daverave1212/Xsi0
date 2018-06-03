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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			handleRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR HERE");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		try {
			handleRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR HERE");
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
		Player thisPlayer;
		if(action.equals( Net.LOGIN )){
			String password = request.getParameter("password");
			checkAndValidateLogin(username, password, response);}
		if(action.equals( Net.REGISTER )) {
			boolean accountCreated = Databases.createAccount(request.getParameter("username"), request.getParameter("password"));
			if(accountCreated) {
				response.getWriter().append(Net.REGISTERSUCCESS);}
			else response.getWriter().append(Net.REGISTERFAIL);}
		thisPlayer = playersConnected.get(username);
		if(action.equals("WHO")) {
			response.getWriter().append(thisPlayer.getEnemyPlayer().getUsername());}
		if(action.equals( Net.PLAY )) {
			thisPlayer.setState(Player.WAITING);
			matchPlayers();
			response.getWriter().append("OK");}
		if(action.equals(Net.MYSTATS)) {
			String playerStats = "";
			playerStats += Databases.getPlayerWins(username);
			playerStats += "-";
			playerStats += Databases.getPlayerGames(username);}
		if(action.equals( Net.ISGAMEREADY )) {
			boolean isThisGameReady = playersConnected.get(username).isGameReady();
			if( isThisGameReady ){	response.getWriter().append(Net.YES);}
			if(!isThisGameReady ){	response.getWriter().append(Net.NO);}}
		if(action.equals( Net.MYPIECE )) {
			if(thisPlayer.getPiece() == Game.X){	response.getWriter().append(Net.X);}
			if(thisPlayer.getPiece() == Game.O){	response.getWriter().append(Net.O);}}
		if(action.equals( Net.ISMYTURN )) {
			if( thisPlayer.isAutoWin()) {
				response.getWriter().append(Net.YOUWIN);}
			else if( thisPlayer.getGame().isGameOver() == Game.GAMEOVER) {
				response.getWriter().append(Net.YOULOSE);
				disconnectPlayer(thisPlayer.getUsername());}
			else if( thisPlayer.getGame().isGameOver() == Game.DRAW) {
				response.getWriter().append(Net.DRAW);
				disconnectPlayer(thisPlayer.getUsername());}
			else if( thisPlayer.isMyTurnNow() ){	response.getWriter().append(Net.YES);}
			else if(!thisPlayer.isMyTurnNow() ){	response.getWriter().append(Net.NO);}}
		if(action.equals( Net.GETBOARD )) {
			String boardAsString = thisPlayer.getGame().boardToString();
			response.getWriter().append(boardAsString);}
		if(action.equals( Net.MOVE )) {
			if(thisPlayer.isAutoWin()) {
				response.getWriter().append(Net.YOUWIN);
				Databases.addWinForPlayer(username);}
			else {
				int square = Integer.parseInt(request.getParameter("square"));
				int row = square/3;
				int col = square%3;
				thisPlayer.getGame().updateBoard(row, col, thisPlayer.getPiece());
				int isGameOver = thisPlayer.getGame().isGameOver();
				if( isGameOver == Game.GAMEOVER ) {response.getWriter().append(Net.YOUWIN);
					disconnectPlayer(username);}
				if( isGameOver == Game.NOTGAMEOVER ) {response.getWriter().append(Net.ENEMYTURN);
					thisPlayer.setMyTurnNow(false);
					thisPlayer.getEnemyPlayer().setMyTurnNow(true);}
				if( isGameOver == Game.DRAW) {
					response.getWriter().append(Net.DRAW);
					disconnectPlayer(username);}
			}}
		if(action.equals(Net.CONCEDE)) {
			if(thisPlayer.getState() == Player.IDLE) {
				disconnectPlayer(username);}
			else if(thisPlayer.getState() == Player.WAITING) {
				thisPlayer.setState(Player.IDLE);
				disconnectPlayer(username);}
			else if(thisPlayer.getState() == Player.PLAYING) {
				thisPlayer.getEnemyPlayer().setAutoWin(true);
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
		playersConnected.get(username).setState(Player.IDLE);
		playersConnected.get(username).setUsername(username);}

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
			if(currentPlayer.getState() == Player.WAITING) {
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
		p1.setGameReady(false);
		p2.setGameReady(false);
		p1.setState(Player.PLAYING);
		p2.setState(Player.PLAYING);
		p1.setEnemyPlayer(p2);
		p2.setEnemyPlayer(p1);
		p1.setGame(new Game());
		p2.setGame(p1.getGame());
		p1.getGame().setP1(p1);
		p2.getGame().setP2(p2);
		p1.setPiece(Game.X);
		p2.setPiece(Game.O);
		p1.setMyTurnNow(true);
		p2.setMyTurnNow(false);
		p1.setGameReady(true);
		p2.setGameReady(true);
		Databases.addGameForPlayer(p1.getUsername());
		Databases.addGameForPlayer(p2.getUsername());
	}

}
