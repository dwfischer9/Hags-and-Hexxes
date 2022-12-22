import java.util.EventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPanel extends JPanel implements EventListener{

    public static Window window = new Window();
    public static ActionButton attack1 = new ActionButton("Attack 1");
    public static ActionButton attack2 = new ActionButton("Attack 2");
    static ActionButton attack3 = new ActionButton("Attack 3");
    static ActionButton attack4 = new ActionButton("Attack 4");
    public static ActionButton escapeButton = new ActionButton("Escape");
    public static ActionPanel actionPanel = new ActionPanel(window);
    public static ActionButton ab;
    public static JPanel attackPanel = new JPanel();
    public static JPanel foePanel = new JPanel();
    public static JLabel foeHealth = new JLabel();
    public  BattleManager bm = window.bm;
    public ActionPanel(Window window){
        this.setBackground(backgroundColor);
        this.setVisible(true);
        this.setLayout(null);
        this.setBounds(0,0,Window.screenWidth,Window.screenHeight);
    }
    
    private Color backgroundColor = new Color(0, 0, 0);


    public  void setup(ActionPanel actionPanel){
        attackPanel.setBackground(Color.WHITE);
        attackPanel.setLayout(new GridLayout(3,2));
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
        
        drawAttackButtons(actionPanel);
        Window.frame.add(actionPanel);
        System.out.println("Battle start");
    }
    public void drawAttackButtons(ActionPanel actionPanel){

            attack1.addActionListener(new ActionListener() {
             
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Attack 1");
                    bm.waitingForAttack = false;
                    
                }
            });
            attack2.addActionListener(new ActionListener() {
    
                @Override
                public void actionPerformed(ActionEvent e) {
                   System.out.println("Attack 2");
                   bm.waitingForAttack = false;
                }
            });
            attack3.addActionListener(new ActionListener() {
    
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Attack 3");
    
                    bm.waitingForAttack = false;
                }
            });
            attack4.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Attack 4");
    
                    bm.waitingForAttack = false;
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
            actionPanel.add(escapeButton);
            
            attackPanel.add(attack1);
            attackPanel.add(attack2);
            attackPanel.add(attack3);
            attackPanel.add(attack4);
            attackPanel.add(escapeButton);
        }
    }
