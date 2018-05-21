package github.xsi0;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameButton extends JButton{
    private int xOrO;//semnul de pe casuta
    private int row;
    private int col;
    GameButton(){
        xOrO=XClient.N;
        addMouseListener(new MouseAdapter() {//asa am vazut ca am putea pune eventu de mouse
            public void mouseClicked (MouseEvent e){
                System.out.println(row);
                System.out.println(col);
                if(xOrO==XClient.N) {//daca nu e pus nimic in casuta
                    if (!GameGrid.FROZEN) {//si e tura noastra
                        //XClient.clickedOnSquare(row,col);
                        XClient.freezeUI();
                    }
                }
            }
        });
    }



    public int getxOrO() {
        return xOrO;
    }

    public void setxOrO(int xOrO) {
        this.xOrO = xOrO;
    }

    public int getCol(){
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow(){
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

}
