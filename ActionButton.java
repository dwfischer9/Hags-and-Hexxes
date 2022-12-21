
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
                

            }
        });
        attack2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println('c');

            }
        });
        ActionPanel.attackPanel.add(attack1);
        attack1.setVisible(true);
        ActionPanel.attackPanel.add(attack2);
        attack2.setVisible(true);
        ActionPanel.attackPanel.add(attack3);
        ActionPanel.attackPanel.add(attack4);
    }
    

}
