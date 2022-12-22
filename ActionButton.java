
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import javax.swing.*;

public class ActionButton extends JButton implements EventListener {
    static ActionPanel actionPanel = new ActionPanel(ActionPanel.window);
    public static ActionButton attack1 = new ActionButton("Attack 1");
    public static ActionButton attack2 = new ActionButton("Attack 2");
    private static ActionButton attack3 = new ActionButton("Attack 3");
    private static ActionButton attack4 = new ActionButton("Attack 4");
    public static ActionButton escapeButton = new ActionButton("Escape");

    public ActionButton(String text) {
        super(text);
    }

    /**
     * Displays the moves known by the player character and puts them on the screen. 
     * These buttons trigger an attack and deal with the damage and rereshing the stats of the foe.
     */
    public static void drawAttackButtons(JPanel attackPanel) {
        attack1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Attack 1");
            }
        });
        attack2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               System.out.println("Attack 2");
            }
        });
        attack3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Attack 3");
            }
        });
        attack4.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Attack 4");
            }
        });
        escapeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Escape");
                Window.endBattle();
            }
        });

        escapeButton.setBackground(Color.magenta);
        ActionPanel.actionPanel.add(escapeButton);
        
        ActionPanel.attackPanel.add(attack1);
        ActionPanel.attackPanel.add(attack2);
        ActionPanel.attackPanel.add(attack3);
        ActionPanel.attackPanel.add(attack4);
        ActionPanel.attackPanel.add(escapeButton);
    }
    

}
