
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Window  extends JPanel{
    private final Color backgroundColor = Color.black;
    private final int originalTileSize = 16;// 16x16 tiles
    private final int scale = 3;

    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;
    public JLabel allyHealth = new JLabel();
    public JLabel foeHealth = new JLabel();
    public Window(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(backgroundColor);
        this.setDoubleBuffered(true);
    }
    public void initialize(){
        JFrame window = new JFrame(); //Initialization of the window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set close behavior to stop the program when the window is closed
        window.setResizable(false); // I don't want to allow resizing of the window yet
        window.setTitle("Creature Brawler"); // setting the title of the window, this is pretty temporary
        Window gamePanel = new Window();
        window.add(gamePanel);
        gamePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(5,5,5,5);
        c.gridx = 2;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 2;
        c.gridwidth = 2;
        foeHealth.setForeground(Color.white);
         // add this component to the game panel

        allyHealth.setForeground(Color.white);
        gamePanel.add(allyHealth);

        c.gridx = 4;
        c.gridy = 4;
        gamePanel.add(foeHealth,c);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    public void addCreatureInfo(Creature creature, JLabel creatureLabel ){
        creatureLabel.setText(creature.toString());
    }
}
