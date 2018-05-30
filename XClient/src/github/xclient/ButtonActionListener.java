package github.xclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonActionListener implements ActionListener {

	private final int row;
	private final int col;
	private ClientFrame parentClient;
	
	public ButtonActionListener(int r, int c, ClientFrame cf) {
		row = r;
		col = c;
		parentClient = cf;}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		parentClient.xoButtonClicked(row, col);}

}
