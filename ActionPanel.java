import java.util.EventListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPanel extends JPanel implements EventListener{
    private JFrame frame = Window.frame;
    private Window window;
    private ActionButton escapeButton = new ActionButton("Escape");
    private ActionButton ab;
    private JPanel attackPanel = new JPanel();
    private JPanel foePanel = new JPanel();
    private JLabel foeHealth = new JLabel();
    private BattleManager battleManager;
    private Player player;

    public ActionPanel(Window window, Player player, BattleManager battleManager){
        this.setBackground(BACKGROUND_COLOR);
        this.window = window;
        this.setVisible(true);
        this.battleManager = battleManager;
        this.setLayout(null);
        this.setBounds(0,0,Window.screenWidth,Window.screenHeight);
        this.player = player;
    }
    
    private final Color BACKGROUND_COLOR = new Color(0, 0, 0);


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
        frame.add(actionPanel);
        System.out.println("Battle start");
    }
    /**
     * 
     * Draw the {@link ActionButton}s for various actions that can be performed in battle. Their actionlisteners are implemented here as well.
     * @param actionPanel the panel that the buttons are to be drawn in
     */
    private void drawAttackButtons(ActionPanel actionPanel){
        ActionButton attack1 = new ActionButton(this.player.getMoves()[0].getMoveName());
            attack1.addActionListener(new ActionListener() {
             
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Attack 1");
                    battleManager.waitingForAttack = false;
                }
            });
        ActionButton attack2 = new ActionButton(this.player.getMoves()[1].getMoveName());
            attack2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   System.out.println("Attack 2");
                   battleManager.waitingForAttack = false;
                }
            });
        ActionButton attack3 = new ActionButton(this.player.getMoves()[2].getMoveName());
            attack3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Attack 3");
                    battleManager.waitingForAttack = false;
                }
            });
        ActionButton attack4 = new ActionButton(this.player.getMoves()[3].getMoveName());
            attack4.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Attack 4");    
                    battleManager.waitingForAttack = false;
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
            attackPanel.add(attack1);
            attackPanel.add(attack2);
            attackPanel.add(attack3);
            attackPanel.add(attack4);
            attackPanel.add(escapeButton);
        }
    }
