import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.EventListener;
import javax.swing.*;

public class ActionButton extends JButton implements EventListener{

    private String title;
    private static ActionButton attack1 = new ActionButton(Game.creatureAlly.getMoves()[0].getMoveName());
    private static ActionButton attack2 = new ActionButton(Game.creatureAlly.getMoves()[1].getMoveName());
    private static ActionButton attack3 = new ActionButton("Attack 3");
    private static ActionButton attack4 = new ActionButton("Attack 4");

    private static ActionButton[] actions = {attack1,attack2};
    
    public ActionButton(String title){
        super(title);
    }
    
    public static void drawButtons(){
       attack1.addActionListener(new ActionListener(){
        
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Creature.attack(Game.creatureAlly.getMoves()[0]);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
            
        }});
        attack2.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    float damage = Creature.attack(Game.creatureAlly.getMoves()[1]);
                    Creature.damageEnemy(damage);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
                
            }});
            ActionPanel.action.add(attack1);
            ActionPanel.action.add(attack2);
    }


}
