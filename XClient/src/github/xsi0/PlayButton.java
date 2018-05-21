package github.xsi0;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayButton extends JButton {
    PlayButton(){
        addMouseListener(new MouseAdapter() {
            public void mouseClicked (MouseEvent e){
                System.out.println("Works");
                XClient.switchToGameUI();
            }
        });
    }
}
