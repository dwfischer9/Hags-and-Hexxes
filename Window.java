
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Window extends JPanel {
    protected final Color backgroundColor = new Color(26,26,26);
    private static final int originalTileSize = 16;// 16x16 tiles
    private static final int scale = 3; //768 x 576
    protected final static int tileSize = originalTileSize * scale;
    private static final int maxScreenCol = 16;
    private static final int maxScreenRow = 12;
    private static final int screenWidth = tileSize * maxScreenCol;
    private static final int screenHeight = tileSize * maxScreenRow;
   
    public static JPanel statusBar = new JPanel();
    public static JLabel allyHealth = new JLabel();
    public static JPanel foeBar = new JPanel();
    public static JLabel foeHealth = new JLabel();
    public static JPanel actionPanel = new JPanel();
    public static Window gamePanel = new Window();
    public Window() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.setLayout(null);
    }

    public void initialize() {
        JFrame frame = new JFrame(); // Initialization of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set close behavior to stop the program when the window                                                   // is closed
        frame.setResizable(false); // I don't want to allow resizing of the window yet
        frame.setTitle("Hags and Hexxes "); // setting the title of the window, this is pretty temporary
        drawStatusBar();
        drawFoeBar();
        
        ActionPanel.drawActionPanel();
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void drawStatusBar(){
        statusBar.add(allyHealth);
        statusBar.setBackground(backgroundColor);
        statusBar.setBounds(new Rectangle(0,0,tileSize*5,tileSize*2));
        allyHealth.setForeground(Color.white);
        gamePanel.add(statusBar);
    }
    public void drawFoeBar(){
        foeHealth.setForeground(Color.white);
        foeBar.add(foeHealth);
        foeBar.setBackground(backgroundColor);
        foeBar.setBounds(new Rectangle(tileSize * 11, tileSize * 0, tileSize * 5, tileSize * 2));
        gamePanel.add(foeBar);
    }

    

    public void addCreatureInfo(Creature creature, JLabel creatureLabel) {
        creatureLabel.setText(creature.toString());
    }
}
