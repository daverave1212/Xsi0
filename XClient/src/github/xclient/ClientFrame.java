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
	
	public CardLayout cardLayout;
	public JPanel clientFrameCards;
	
	public JPanel loginPanelCard;
	public JTextField loginUsernameField;
	public JTextField loginPasswordField;
	public JButton	  loginButton;
	
	public JPanel playPanelCard;
	public JButton playButton;
	
	public JPanel waitPanelCard;
	
	public JPanel gamePanelCard;
	public JButton[][] xoButtons;
	public JMenuBar gameMenuBar;
	public JMenu gameMenuBar_File;
	public JMenuItem gameMenuBar_File_Exit;
	
	public JPanel gameOverPanelCard;
	public JLabel gameOverLabel;
	
	public static final String LOGINPANEL	= "Login";
	public static final String PLAYPANEL	= "play";
	public static final String GAMEPANEL	= "game";
	public static final String WAITPANEL	= "wait";
	public static final String GAMEOVERPANEL= "over";
	
	public static final int MYTURN		= 1;
	public static final int ENEMYTURN	= 2;
	
	public static int currentGameplayState = 2;
	
	public boolean areButtonsFrozen = false;
	
	public String getUsername() { return loginUsernameField.getText(); }
	public String getPassword() { return loginPasswordField.getText(); }
	
	public void setStage(String stage) throws Exception {
		if(stage.equals(LOGINPANEL)) {
			cardLayout.show(clientFrameCards, stage);}
		if(stage.equals(PLAYPANEL)) {
			cardLayout.show(clientFrameCards, stage);}
		if(stage.equals(WAITPANEL)) {
			cardLayout.show(clientFrameCards, stage);
			revalidate();
			repaint();
			String response;
			while(true) {
				TimeUnit.SECONDS.sleep(1);
				response = Net.request(Net.ISGAMEREADY, "other=filler");
				if(response.equals(Net.YES)) {
					break;}}
			System.out.println("My enemy: " + Net.request("WHO", "d=x"));
			setStage(GAMEPANEL);}
		if(stage.equals(GAMEPANEL)) {
			cardLayout.show(clientFrameCards, stage);
			String response;
			response = Net.request(Net.MYPIECE, "other=filler");
			System.out.println("Received piece " + response);
			XClient.piece = response;
			getTurn();}
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
			getTurn();}}
	
	public void getTurn() throws Exception{
		String response = Net.request(Net.ISMYTURN, "other=filler");
		if(response.equals(Net.YES)) {
			setGameplayState(MYTURN);}
		else if(response.equals(Net.YOUWIN)) {
			setStage(GAMEOVERPANEL);
			gameOverLabel.setText("You win. Your opponent conceded (smh)");
			revalidate();
			repaint();}
		else if(response.equals(Net.YOULOSE)) {
			setStage(GAMEOVERPANEL);
			}
		else if(response.equals(Net.NO)){
			System.out.println("Asked for my turn. Received " + response);
			TimeUnit.SECONDS.sleep(1);
			setGameplayState(ENEMYTURN);}}
	
	
	public void freezeButtons() {
		if(!areButtonsFrozen) {
			System.out.println("Freezing buttons...");}
		areButtonsFrozen = true;}
	
	public void unfreezeButtons() {
		if(areButtonsFrozen) {
			System.out.println("Unfreezing buttons...");}
		areButtonsFrozen = false;}
	
	public void setupFrame() {
		setSize(400,  400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		add(clientFrameCards);}
	
	public void setupLoginCard() {
		loginPanelCard	= new JPanel();
		loginButton = new JButton("Login");
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		loginPanelCard.setLayout(new GridBagLayout());
		loginUsernameField = new JTextField();
		loginUsernameField.setColumns(20);
		loginPasswordField = new JTextField();
		loginPasswordField.setColumns(20);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		loginPanelCard.add(new JLabel("Username:"), gridBagConstraints);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		loginPanelCard.add(this.loginUsernameField, gridBagConstraints);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		loginPanelCard.add(new JLabel("Password:"), gridBagConstraints);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		loginPanelCard.add(this.loginPasswordField, gridBagConstraints);
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		loginPanelCard.add(this.loginButton, gridBagConstraints);
		setupLoginButton();
		clientFrameCards.add(loginPanelCard,LOGINPANEL);}
	
	public void setupLoginButton() {
		loginButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		XClient.handleActionRequest(Net.LOGIN);
		}});}
	
	public void setupPlayCard() {
		playPanelCard = new JPanel();
		playButton = new JButton("PLAY NOW!!!");
		playPanelCard.add(playButton);
		clientFrameCards.add(playPanelCard, PLAYPANEL);
		setupPlayButton();}
	
	public void setupPlayButton() {
		playButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		XClient.handleActionRequest(Net.PLAY);}
		});}
	
	public void setupWaitCard() {
		JLabel label = new JLabel("Please wait for a game...");
		this.waitPanelCard = new JPanel();
		waitPanelCard.add(label);
		clientFrameCards.add(waitPanelCard, WAITPANEL);}
	
	public void setupGameCard() {
		gamePanelCard = new JPanel();
		gamePanelCard.setLayout(new GridLayout(3, 3));
		xoButtons = new JButton[3][3];
		for(int i = 0; i<=2; i++) { for(int j = 0; j<=2; j++) {
			xoButtons[i][j] = new JButton("Noth");
			ButtonActionListener bal = new ButtonActionListener(i, j, this);
			xoButtons[i][j].addActionListener(bal);
			gamePanelCard.add(xoButtons[i][j]);}}
		setupMenuBar();
		clientFrameCards.add(gamePanelCard, GAMEPANEL);}
	
	public void setupGameOverCard() {
		gameOverPanelCard = new JPanel();
		gameOverLabel = new JLabel();
		gameOverPanelCard.add(gameOverLabel);
		clientFrameCards.add(gameOverPanelCard, GAMEOVERPANEL);
	}
	
	public void setupMenuBar() {
		gameMenuBar = new JMenuBar();
		gameMenuBar_File = new JMenu("File");
		gameMenuBar.add(gameMenuBar_File);
		gameMenuBar_File_Exit = new JMenuItem("Exit");
		gameMenuBar_File.add(gameMenuBar_File_Exit);
		gameMenuBar_File_Exit.addActionListener(new ExitActionListener(this));
		setJMenuBar(gameMenuBar);}
	
	
	
	public void xoButtonClicked(int row, int col) {
		System.out.println("Clicked " + row + " " + col);
		if(XClient.board[row][col] == XClient.N) {
			XClient.handleActionRequest(Net.MOVE, row, col);}}

	
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
