import java.util.EventListener;

import javax.swing.*;
import java.awt.*;

public class ActionPanel extends JPanel implements EventListener{
     ActionButton butt;
    Window window;
    public ActionPanel(Window window){
        this.window = window;
    }

    public ActionPanel action = new ActionPanel(window);

    private Color backgroundColor = new Color(101, 87, 168);
    public ActionPanel(){
        this.setLayout(new GridLayout(2,2,5,5));
        this.setBackground(backgroundColor);
    }

    public void drawActionPanel(Window gamePanel){
        action.setBounds(new Rectangle(0,480,window.tileSize*10,window.tileSize*2));
        action.setVisible(true);
        gamePanel.add(action);
        butt.drawAttackButtons();


    }

}