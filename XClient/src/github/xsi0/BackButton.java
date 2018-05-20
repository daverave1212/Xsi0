package github.xsi0;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BackButton extends JMenu {
    BackButton() {
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                XClient.switchToMainUI();
            }
        });
    }
}
