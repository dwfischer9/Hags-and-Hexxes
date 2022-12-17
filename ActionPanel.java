import java.util.EventListener;

import javax.swing.*;
import java.awt.*;

public class ActionPanel extends JPanel implements EventListener{
    public static ActionPanel action = new ActionPanel();

    private Color backgroundColor = new Color(101, 87, 168);
    public ActionPanel(){
        this.setLayout(new GridLayout(2,2,5,5));
        this.setBackground(backgroundColor);
    }

    public static void drawActionPanel(){
        action.setBounds(new Rectangle(0,480,Window.tileSize*10,Window.tileSize*2));
        action.setVisible(true);
        Window.gamePanel.add(action);
        ActionButton.drawButtons();


    }

}