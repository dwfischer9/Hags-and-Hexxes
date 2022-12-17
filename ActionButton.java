import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.EventListener;
import javax.swing.*;

public class ActionButton extends JButton implements EventListener {

    private String text;
    private static ActionButton attack1 = new ActionButton(Player.playerCharacter.getMoves()[0].getMoveName());
    private static ActionButton attack2 = new ActionButton(Player.playerCharacter.getMoves()[1].getMoveName());
    private static ActionButton attack3 = new ActionButton("Attack 3");
    private static ActionButton attack4 = new ActionButton("Attack 4");

    private static ActionButton[] actions = { attack1, attack2 };

    public ActionButton(String text) {
        super(text);
    }

    public static void drawButtons() {
        attack1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    float damage = Entity.attack(Player.playerCharacter.getMoves()[0]);
                    Player.damageEnemy(damage);
                    Window.updateFoeHealth();
                    Game.foeTurn(Entity.testEnemy, Player.playerCharacter);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
        attack2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    float damage = Entity.attack(Player.playerCharacter.getMoves()[1]);
                    Entity.damageEnemy(damage);
                    Window.updateFoeHealth();
                    
                    Game.foeTurn(Entity.testEnemy, Player.playerCharacter);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
        ActionPanel.action.add(attack1);
        ActionPanel.action.add(attack2);
    }

}
