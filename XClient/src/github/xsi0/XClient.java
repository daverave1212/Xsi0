package github.xsi0;

import com.sun.xml.internal.ws.handler.ServerLogicalHandlerTube;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import static javax.swing.JFrame.*;

public class XClient {

	public static int N = -1;
	public static int O = 0;
	public static int X = 1;
	private static String PLAY = "Play Game";
	private static String GRID = "X si 0";


	public static Socket connection;
	public static int port	= 8080;
	public static String ip	= "localhost"; 
	public static PrintStream printStream;
	public static Scanner scanner;
	public static int lastReceivedMessage;
	
	public static int myPiece = N;
	private static JFrame frame;
	private static JPanel play;

	public static void send(int signal) {
		printStream.println(signal);}
	
	public static int receive() {
		int receivedSignal = scanner.nextInt();
		return receivedSignal;}
	
	public static void clickedOnSquare(int rowIndex, int colIndex) {
		send(Signals.MADEMOVE);
		send(rowIndex);
		send(colIndex);}
	
	public static void startReceiving() {
		Thread receivingSignals = new Thread(new Runnable() {
			public void run() {
				while(true) {
					lastReceivedMessage = receive();
					if(lastReceivedMessage == Signals.SETPIECE0) {
						myPiece = O;}
					if(lastReceivedMessage == Signals.SETPIECEX) {
						myPiece = X;}
					if(lastReceivedMessage == Signals.STARTGAME) {
						switchToGameUI();}
					if(lastReceivedMessage == Signals.YOURTURN) {
						unfreezeUI();}}}});
		receivingSignals.start();
		
	}
	
	public XClient() throws Exception{
		connection	= new Socket(ip, port);
		printStream	= new PrintStream(connection.getOutputStream());
		scanner		= new Scanner(connection.getInputStream());
	}

	private static void setupPlay(){
		PlayButton go=new PlayButton();
		go.setText("Play");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.weightx=0.5;
		c.weighty=0.5;
		c.fill=GridBagConstraints.BOTH;
		play.add(go,c);
	}

	private static void buildGrid(JPanel game){
		GameButton[] button = new GameButton[9];//the grid
		game.setLayout(new GridBagLayout());//

		for(int i=0;i<9;i++){
			button[i]=new GameButton();
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = i%3;
			c.gridy = i/3;
			button[i].setRow(i%3);
			button[i].setCol(i/3);
			c.weightx=0.5;
			c.weighty=0.5;
			c.fill=GridBagConstraints.BOTH;
			game.add(button[i], c);
		}

	}

	public static void initializeUI() {
		frame = new JFrame("X si O");//creates a window
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLayout(new CardLayout());

		play = new JPanel();
		play.setLayout(new GridBagLayout());
		setupPlay();
        frame.add(play,PLAY);

		//Display the window.
		frame.setSize(300,200);
		frame.setVisible(true);

	}
	
	public static void switchToGameUI() {
		
		/* Se schimba din "Play" in interfata cu 9 patrate */
		JPanel grid = new JPanel();
		buildGrid(grid);
		frame.remove(play);
		frame.add(grid,GRID);

		//Workaround, nu afiseaza grid-ul fara un resize.
		frame.setSize(310,210);
		frame.setSize(300,200);



	}
	
	public static void freezeUI() {

		/* toate butonaele se blocheaza */
		System.out.println("FREEZE");
		GameGrid.FROZEN=true;
	}
	
	public static void unfreezeUI() {
	
		/* toate butoanele se deblocheaza */
		System.out.println("unFREEZE");
		GameGrid.FROZEN=false;
	}
}
