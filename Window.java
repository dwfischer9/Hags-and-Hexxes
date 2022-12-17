
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Window extends JPanel {
    private final Color backgroundColor = new Color(26,26,26);
    private final int originalTileSize = 16;// 16x16 tiles
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;
   
    public JPanel statusBar = new JPanel();
    public JLabel allyHealth = new JLabel();
    public JLabel foeHealth = new JLabel();
    public JPanel actionPanel = new JPanel();
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
        Window gamePanel = new Window();
        
        foeHealth.setForeground(Color.white);
        drawStatusBar();

        gamePanel.add(statusBar);
        

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
    }

    

    public void addCreatureInfo(Creature creature, JLabel creatureLabel) {
        creatureLabel.setText(creature.toString());
    }
}
