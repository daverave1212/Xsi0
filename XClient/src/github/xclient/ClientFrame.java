package github.xclient;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientFrame extends JFrame{
	
	private CardLayout cardLayout;
	private JPanel clientFrameCards;

	private JPanel loginPanelCard;
	private JTextField loginUsernameField;
	private JTextField loginPasswordField;
	private JButton	loginButton;
	private JButton	registerButton;

	private JPanel playPanelCard;
	private JButton playButton;

	private JPanel waitPanelCard;

	private JPanel gamePanelCard;
	private JButton[][] xoButtons;
	private JMenuBar gameMenuBar;
	private JMenu gameMenuBar_File;
	private JMenuItem gameMenuBar_File_Exit;

	private JPanel gameOverPanelCard;
	private JLabel gameOverLabel;
	public JLabel getGameOverLabel() {return gameOverLabel;}
	
	private int currentGameplayState = 2;
	private boolean areButtonsFrozen = false;
	
	// Client states and cards
	public static final String LOGINPANEL	= "Login";
	public static final String PLAYPANEL	= "play";
	public static final String GAMEPANEL	= "game";
	public static final String WAITPANEL	= "wait";
	public static final String GAMEOVERPANEL= "over";
	
	public static final int MYTURN		= 1;
	public static final int ENEMYTURN	= 2;
	
	public String getUsername() { return loginUsernameField.getText(); }
	public String getPassword() { return loginPasswordField.getText(); }
	public void setGameOverLabelText(String s) { gameOverLabel.setText(s); }
	public JButton getButton(int i, int j) { return xoButtons[i][j]; }
	
	public void setStage(String stage) throws Exception {
		if(stage.equals(LOGINPANEL)) {
			cardLayout.show(clientFrameCards, stage);}
		if(stage.equals(PLAYPANEL)) {
			cardLayout.show(clientFrameCards, stage);}
		if(stage.equals(WAITPANEL)) {
			cardLayout.show(clientFrameCards, stage);
			revalidate();
			repaint();
			String response = "";
			while(true) {
				TimeUnit.SECONDS.sleep(1);
				response = Net.request(Net.ISGAMEREADY, "other=filler");
				if(response.equals(Net.YES)) {
					setStage(GAMEPANEL);
					System.out.println("My enemy: " + Net.request("WHO", "d=x"));
					break;}}}
		if(stage.equals(GAMEPANEL)) {
			cardLayout.show(clientFrameCards, stage);
			String response;
			response = Net.request(Net.MYPIECE, "other=filler");
			System.out.println("Received piece " + response);
			XClient.setPiece(response);
			getTurn();
			
		}
		if(stage.equals(GAMEOVERPANEL)) {
			cardLayout.show(clientFrameCards, stage);}}

	public void setGameplayState(int s) throws Exception{
		currentGameplayState = s;
		String response;
		if(currentGameplayState == MYTURN) {
			response = Net.request(Net.GETBOARD, "o=f");
			XClient.updateBoard(response);
			unfreezeButtons();}
		if(currentGameplayState == ENEMYTURN) {
			freezeButtons();
			Runnable getTurnRunnable = () -> {try{getTurn();}catch(Exception e){}};
			Thread getTurnThread = new Thread(getTurnRunnable);
			getTurnThread.start();
			getTurn();}}
	
	private void getTurn() throws Exception{
		String response = Net.request(Net.ISMYTURN, "other=filler");
		if(response.equals(Net.YES)) {
			System.out.println("My turn? YES");
			setGameplayState(MYTURN);}
		else if(response.equals(Net.YOUWIN)) {
			setStage(GAMEOVERPANEL);
			gameOverLabel.setText("You win. Your opponent conceded (smh)");
			revalidate();
			repaint();}
		else if(response.equals(Net.YOULOSE)) {
			setStage(GAMEOVERPANEL);}
		else if(response.equals(Net.NO)){
			freezeButtons();
			Runnable myTurnRun = () ->{
				try {
					while(true) {
						String resp = Net.request(Net.ISMYTURN, "nothing=absolutelynothing");
						TimeUnit.SECONDS.sleep(1);
						if(!resp.equals(Net.NO)) {
							System.out.println("Asked for my turn: YES");
							if(resp.equals(Net.YES)) {
								System.out.println("My turn? YES");
								setGameplayState(MYTURN);}
							else if(resp.equals(Net.YOUWIN)) {
								setStage(GAMEOVERPANEL);
								gameOverLabel.setText("You win. Your opponent conceded (smh)");
								revalidate();
								repaint();}
							else if(resp.equals(Net.YOULOSE)) {
								setStage(GAMEOVERPANEL);}
							break;}
						System.out.println("Asking for my turn: NO");}
				} catch(Exception e) {e.printStackTrace();}};
			Thread startAskingForTurn = new Thread(myTurnRun);
			startAskingForTurn.start();}}
	
	public void xoButtonClicked(int row, int col) {
		System.out.println("Clicked " + row + " " + col);
		if(XClient.board[row][col] == XClient.N) {
			XClient.handleActionRequest(Net.MOVE, row, col);}}
	
	private void freezeButtons() {
		if(!areButtonsFrozen) {
			System.out.println("Freezing buttons...");
			for(int i = 0; i<=2; i++) { for(int j = 0; j<=2; j++) {
				this.xoButtons[i][j].setEnabled(false);}}}
		areButtonsFrozen = true;}
	
	private void unfreezeButtons() {
		if(areButtonsFrozen) {
			System.out.println("Unfreezing buttons...");
			for(int i = 0; i<=2; i++) { for(int j = 0; j<=2; j++) {
				if(xoButtons[i][j].getText().equals(" ")) {
					xoButtons[i][j].setEnabled(true);
				}}}}
		areButtonsFrozen = false;}
	
	private void setupFrame() {
		setSize(400,  400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		add(clientFrameCards);}
	
	private void setupLoginCard() {
		loginPanelCard	= new JPanel();
		loginButton = new JButton("Login");
		registerButton = new JButton("Sign Up");
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		loginPanelCard.setLayout(new GridBagLayout());
		loginUsernameField = new JTextField();
		loginUsernameField.setColumns(12);
		loginPasswordField = new JTextField();
		loginPasswordField.setColumns(12);
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.anchor = GridBagConstraints.NORTH;
		loginPanelCard.add(new JLabel("Username:"), gridBagConstraints);
		gridBagConstraints.gridx = 7;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridwidth = 9;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.anchor = GridBagConstraints.NORTH;
		loginPanelCard.add(this.loginUsernameField, gridBagConstraints);
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.anchor = GridBagConstraints.NORTH;
		loginPanelCard.add(new JLabel("Password:"), gridBagConstraints);
		gridBagConstraints.gridx = 7;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.gridwidth = 9;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.anchor = GridBagConstraints.NORTH;
		loginPanelCard.add(this.loginPasswordField, gridBagConstraints);
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 10;
		gridBagConstraints.gridwidth = 6;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.NORTH;
		loginPanelCard.add(this.loginButton, gridBagConstraints);
		gridBagConstraints.gridx = 10;
		gridBagConstraints.gridy = 10;
		gridBagConstraints.gridwidth = 6;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.NORTH;
		loginPanelCard.add(this.registerButton, gridBagConstraints);
		setupLoginButton();
		setupRegisterButton();
		clientFrameCards.add(loginPanelCard,LOGINPANEL);}
	
	private void setupLoginButton() {
		loginButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		XClient.handleActionRequest(Net.LOGIN);
		}});}
	
	private void setupRegisterButton() {
		ActionListener actionLambda = (ddd)->XClient.handleActionRequest(Net.REGISTER);
		registerButton.addActionListener(actionLambda);}
	
	private void setupPlayCard() {
		playPanelCard = new JPanel();
		playButton = new JButton("PLAY NOW!!!");
		playPanelCard.add(playButton);
		clientFrameCards.add(playPanelCard, PLAYPANEL);
		setupPlayButton();}
	
	private void setupPlayButton() {
		playButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		XClient.handleActionRequest(Net.PLAY);}
		});}
	
	private void setupWaitCard() {
		JLabel label = new JLabel("Please wait for a game...");
		this.waitPanelCard = new JPanel();
		waitPanelCard.add(label);
		clientFrameCards.add(waitPanelCard, WAITPANEL);}
	
	private void setupGameCard() {
		gamePanelCard = new JPanel();
		gamePanelCard.setLayout(new GridLayout(3, 3));
		xoButtons = new JButton[3][3];
		for(int i = 0; i<=2; i++) { for(int j = 0; j<=2; j++) {
			xoButtons[i][j] = new JButton(" ");
			ButtonActionListener bal = new ButtonActionListener(i, j, this);
			xoButtons[i][j].addActionListener(bal);
			gamePanelCard.add(xoButtons[i][j]);}}
		setupMenuBar();
		clientFrameCards.add(gamePanelCard, GAMEPANEL);}
	
	private void setupGameOverCard() {
		gameOverPanelCard = new JPanel();
		gameOverLabel = new JLabel();
		gameOverPanelCard.add(gameOverLabel);
		clientFrameCards.add(gameOverPanelCard, GAMEOVERPANEL);
	}
	
	private void setupMenuBar() {
		gameMenuBar = new JMenuBar();
		gameMenuBar_File = new JMenu("File");
		gameMenuBar.add(gameMenuBar_File);
		gameMenuBar_File_Exit = new JMenuItem("Exit");
		gameMenuBar_File.add(gameMenuBar_File_Exit);
		gameMenuBar_File_Exit.addActionListener(new ExitActionListener(this));
		setJMenuBar(gameMenuBar);}
	
	
	


	
	public ClientFrame() {
		cardLayout		= new CardLayout();
		clientFrameCards= new JPanel(cardLayout);
		setupLoginCard();
		setupPlayCard();
		setupWaitCard();
		setupGameCard();
		setupGameOverCard();
		setupFrame();
		setupMenuBar();}
	
	
	
	
}
