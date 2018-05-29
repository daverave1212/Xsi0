package github.xclient;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.EventHandler;

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
	public JPanel gamePanelCard;
		public JButton[][] xoButtons;
	public JMenuBar gameMenuBar;
		public JMenu gameMenuBar_File;
		public JMenuItem gameMenuBar_File_Exit;
	
	public static final String LOGINPANEL	= "Login";
	public static final String PLAYPANEL	= "play";
	public static final String GAMEPANEL	= "game";
	
	public String getUsername() { return loginUsernameField.getText(); }
	public String getPassword() { return loginPasswordField.getText(); }
	
	public void setStage(String stage) {
		// TO DO : here
		cardLayout.show(clientFrameCards, stage);
	}
	
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
		clientFrameCards.add(playPanelCard, PLAYPANEL);}
	
	public void setupGameCard() {
		gamePanelCard = new JPanel();
		gamePanelCard.setLayout(new GridLayout(3, 3));
		xoButtons = new JButton[3][3];
		for(int i = 0; i<=2; i++) { for(int j = 0; j<=2; j++) {
			xoButtons[i][j] = new JButton("Noth");
			gamePanelCard.add(xoButtons[i][j]);}}
		setupMenuBar();
		clientFrameCards.add(gamePanelCard, GAMEPANEL);}
	
	public void setupMenuBar() {
		gameMenuBar = new JMenuBar();
		gameMenuBar_File = new JMenu("File");
		gameMenuBar.add(gameMenuBar_File);
		gameMenuBar_File_Exit = new JMenuItem("Exit");
		gameMenuBar_File.add(gameMenuBar_File_Exit);
		setJMenuBar(gameMenuBar);}
	
	public ClientFrame() {
		cardLayout		= new CardLayout();
		clientFrameCards= new JPanel(cardLayout);
		setupLoginCard();
		setupPlayCard();
		setupGameCard();
		setupFrame();
		setupMenuBar();}
	
	
	
	
}
