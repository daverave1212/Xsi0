package github.xsi0;


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
	private static JPanel grid;
	
	public static GameButton[] button;
	
	public static int[][] gameBoard = new int[][] {
		{N, N, N},
		{N, N, N},
		{N, N, N}};

	public static void updateBoard(int rindex, int cindex, int piece) {
		System.out.println("Updating board...");
		String buttonText = "Error :<";
		if(piece == X) {buttonText = "XXXXXX";}
		if(piece == O) {buttonText = "OOOOOOO";}
		if(piece == N) {System.out.println("AYYYY COWBOY THIS AINT WORKED");}
		
		button[rindex * 3 + cindex].setText("XXXX");
		frame.setSize(310,210);
		frame.setSize(300,200);
		
		gameBoard[rindex][cindex] = piece;}
		
	public static void send(int signal) {
		printStream.println(signal);}
	
	public static int receive() {
		int receivedSignal = scanner.nextInt();
		return receivedSignal;}
	
	public static void clickedOnSquare(int rowIndex, int colIndex) {
		updateBoard(rowIndex, colIndex, myPiece);
		send(Signals.MADEMOVE);
		send(rowIndex);
		send(colIndex);
		System.out.println("Clicked on square: " + rowIndex + ", " + colIndex);
		}
	
	public static void startReceiving() {
	//	int pinged = receive();
	//	System.out.println("Server pinged client successfully. Now the real fun begins.");
		Thread receivingSignals = new Thread(new Runnable() {
			public void run() {
				while(true) {
					System.out.println("Waiting to receive any signal from server...");
					lastReceivedMessage = receive();
					if(lastReceivedMessage == Signals.SETPIECE0) {
						System.out.println("My piece is O");
						myPiece = O;}
					if(lastReceivedMessage == Signals.SETPIECEX) {
						System.out.println("My piece is X");
						myPiece = X;}
					if(lastReceivedMessage == Signals.STARTGAME) {
						System.out.println("Game started!");
						switchToGameUI();}
					if(lastReceivedMessage == Signals.YOURTURN) {
						System.out.println("MY TURN NOW!!!!");
						unfreezeUI();}
					if(lastReceivedMessage == Signals.UPDATEBOARD) {
						System.out.print("Update board: ");
						int rowIndex = receive();
						int colIndex = receive();
						System.out.println(rowIndex + ", " + colIndex);
						int recPiece = receive();
						System.out.print("   (alos received a piece)");
						XClient.updateBoard(rowIndex, colIndex, recPiece);
					}
				}}});
		receivingSignals.start();
		
	}

	public static void startClient() throws Exception{
		connection	= new Socket(ip, port);
		printStream	= new PrintStream(connection.getOutputStream());
		scanner		= new Scanner(connection.getInputStream());
		startReceiving();
	}

	// GUI
	private static void setupPlay(){
	    //Aici punem butonul de play
		PlayButton go=new PlayButton();
		go.setText("Play");
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		c.weightx=0.5;//idk tbh
		c.weighty=0.5;
		c.fill=GridBagConstraints.BOTH;//resizeabe
		play.add(go,c);
	}

	private static void buildGrid(JPanel game){
	    //Asta ne construieste grid-ul propriu zis de X si 0
		button = new GameButton[9];//the grid
		game.setLayout(new GridBagLayout());

		for(int i=0;i<9;i++){
			button[i]=new GameButton();
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = i%3;//linia din grid
			c.gridy = i/3;
			button[i].setRow(i%3);
			button[i].setCol(i/3);//coloana din grid, stocata in obiect
			c.weightx=0.5;
			c.weighty=0.5;
			c.fill=GridBagConstraints.BOTH;//resizeble
			game.add(button[i], c);
		}


	}

	public static void initializeUI() {
		frame = new JFrame("X si O");//creates a window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ca sa nu putem inchide jocul fara sa revenim
        //la ecranul principal cu play
        //TODO: un buton de go back pe grid in loc de close, ca sa putem pune cod la revenirea din joc.
		frame.setLayout(new CardLayout());

		play = new JPanel();
		play.setLayout(new GridBagLayout());
		setupPlay();//ne construieste ecranul principal
        frame.add(play,PLAY);

		//Displays the window
		frame.setSize(300,200);
		frame.setVisible(true);
	}
	
	public static void switchToGameUI() {
		
		/* Se schimba din "Play" in interfata cu 9 patrate */
		grid = new JPanel();
		buildGrid(grid);
		frame.remove(play);
		frame.add(grid,GRID);

		//Workaround, nu afiseaza grid-ul fara un resize.
		//frame.repaint();
		frame.setSize(310,210);
		frame.setSize(300,200);

	}
	
	public static void freezeUI() {
		/* toate butonaele se blocheaza, teoretic */
		System.out.println("FREEZE");
		GameGrid.FROZEN=true;
	}
	
	public static void unfreezeUI() {
		/* toate butoanele se deblocheaza, teoretic */
		System.out.println("unFREEZE");
		GameGrid.FROZEN=false;
	}
	
	public static void main(String[] args) throws Exception{
		XClient.initializeUI();
	}
}
