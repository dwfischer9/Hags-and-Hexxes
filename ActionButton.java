
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import javax.swing.*;

public class ActionButton extends JButton implements EventListener {
    ActionPanel actionPanel;
    private static ActionButton attack1 = new ActionButton("Attack 3");
     private static ActionButton attack2 = new ActionButton("Attack 3");
    // private static ActionButton attack3 = new ActionButton("Attack 3");
    // private static ActionButton attack4 = new ActionButton("Attack 4");


    public ActionButton(String text) {
        super(text);
    }

    /**
     * Displays the moves known by the player character and puts them on the screen. 
     * These buttons trigger an attack and deal with the damage and rereshing the stats of the foe.
     */
    public void drawAttackButtons() {
        attack1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // try {
                //     float damage = OverWorldEntity.attack(Window.player.getMoves()[0]);
                //     Player.damageEnemy(damage);
                //     Window.updateFoeHealth();
                //     Game.foeTurn(Entity.testEnemy, Player.playerCharacter);
                // } catch (IOException e1) {
                //     // TODO Auto-generated catch block
                //     e1.printStackTrace();
                // }

            }
        });
        attack2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // float damage = OverWorldEntity.attack(Window.player.getMoves()[1]);
                // OverWorldEntity.damageEnemy(damage);
                // Window.updateFoeHealth();
                //TODO: implement attack and dealing damage
                //Game.foeTurn(Window.entity, Window.player);
                System.out.println('c');

            }
        });
        actionPanel.add(attack1);
        actionPanel.add(attack2);
    }
    

}
