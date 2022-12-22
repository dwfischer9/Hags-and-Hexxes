import java.util.EventListener;

import javax.swing.*;
import java.awt.*;

public class ActionPanel extends JPanel implements EventListener{

    public static Window window = new Window();
    public static ActionPanel actionPanel = new ActionPanel(window);
    public static JPanel attackPanel = new JPanel();
    public static JPanel foePanel = new JPanel();
    public static JLabel foeHealth = new JLabel();
    public ActionPanel(Window window){
        this.setBackground(backgroundColor);
        this.setVisible(true);
        this.setLayout(null);
        this.setBounds(0,0,Window.screenWidth,Window.screenHeight);
    }
    private Color backgroundColor = new Color(0, 0, 0);


    public static void setup(ActionPanel actionPanel){
        attackPanel.setBackground(Color.WHITE);
        attackPanel.setBounds(new Rectangle(0,Window.tileSize * 10,Window.tileSize * 6,Window.tileSize * 2));
        attackPanel.setVisible(true);

        foeHealth.setText(Window.testEntity.toString());
        foeHealth.setForeground(Color.RED);
        foePanel.setLayout(null);
        foePanel.setBounds(0,Window.tileSize*15,Window.tileSize *6, Window.tileSize * 2);
        foePanel.setVisible(true);
        foePanel.setBackground(Color.WHITE);
        actionPanel.add(foePanel);
        actionPanel.add(attackPanel);
        
        ActionButton.drawAttackButtons(actionPanel);
        Window.frame.add(actionPanel);
        System.out.println("Battle start");
    }
}