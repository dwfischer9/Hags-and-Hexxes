import java.util.EventListener;

import javax.swing.*;
import java.awt.*;

public class ActionPanel extends JPanel implements EventListener{

    private Color backgroundColor = new Color(24,243,89);
    public ActionPanel(){
        this.setLayout(null);
        this.setBackground(backgroundColor);
    }

    public void drawActionPanel(){
        ActionPanel action = new ActionPanel();
        statusBar.setBounds(new Rectangle(0,0,tileSize*5,tileSize*2));
    }
}