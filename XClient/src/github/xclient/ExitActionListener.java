package github.xclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ExitActionListener implements ActionListener {

	ClientFrame parentClient;
	
	public ExitActionListener(ClientFrame cf) {
		parentClient = cf;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if(XClient.username.equals("~!@~")) {
			//just exit
		} else {
			try {
				Net.request(Net.CONCEDE, "o=3");
			} catch (Exception e) {
				e.printStackTrace();}}
		parentClient.dispatchEvent(new WindowEvent(parentClient, WindowEvent.WINDOW_CLOSING));
	}

}
