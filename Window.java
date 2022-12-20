
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;


public class Window extends JPanel implements Runnable {
    protected final static Color backgroundColor = new Color(26, 26, 26);
    private static final int originalTileSize = 16;// 16x16 tiles
    private static final int scale = 3; // 768 x 576
    protected static final int tileSize = originalTileSize * scale;
    public final static int maxScreenCol = 16;
    public final static int maxScreenRow = 12;
     public final static  int screenWidth = tileSize * maxScreenCol;
     public final static  int screenHeight = tileSize * maxScreenRow;
    private static final int FPS = 60;
    public static JPanel statusBar = new JPanel();
    public static JLabel playerHealth = new JLabel();
    public static JPanel foeBar = new JPanel();
    public static JLabel foeHealth = new JLabel();
    public static JLabel victoryLabel = new JLabel("Victory!");
    public static JPanel actionPanel = new JPanel();
    public static Window gamePanel = new Window();
    public static JFrame frame = new JFrame(); // Initialization of the window
    public Thread gameThread;
    public static KeyHandler keyH = new KeyHandler();
    public static Window victoryPanel = new Window();
    public static Window overWorldPanel = new Window();
    public AssetSetter assetSetter = new AssetSetter(overWorldPanel);
    CollisionDetection cDetection = new CollisionDetection(this);
    public static Player player = new Player(overWorldPanel, keyH, "Hero", Type.normal, new Move[] { Move.slap, Move.tackle }, 5, 90,
    90);
    public static Entity entity = new Entity(overWorldPanel);
    private final Dimension winSize = new Dimension(screenWidth, screenHeight);
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    public static TileManager tileM = new TileManager(overWorldPanel);
    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    public AbstractObject obj[] = new AbstractObject[10];
    /**
     * Constructs a new instance of Window and sets some defeault properties
     */
    public Window() {
        this.setPreferredSize(winSize);
        this.setBackground(backgroundColor);
        this.setDoubleBuffered(true);
        this.setLayout(null);
        this.setFocusable(true);
        this.addKeyListener(keyH);
    }
    

    public void setupGame() throws IOException{
        assetSetter.setObject();
    }
    public void initialize() throws IOException {
         //set up assets BEFORE thread starts

        startGameThread();
        player.setDefaultValues();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set close behavior to stop the program when the window // is closed
        frame.setResizable(false); // I don't want to allow resizing of the window yet
        frame.setTitle("Hags and Hexxes "); // setting the title of the window, this is pretty temporary
        overWorldPanel();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    /**
     *  initializes the overworld panel. Should be called at the end of a battle
     */
    public static void overWorldPanel() {
        overWorldPanel.setName("overWorldPanel");
        frame.add(overWorldPanel);
        overWorldPanel.setVisible(true);
    }

    /**
     * Initiates the game thread. Called in Game's main method
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        System.out.println("Started game thread");
    }
    /**
     * update graphics 
     */
    public void update() {
      player.update();
    }
    /** 
     * This is the method that controls the game loop.
     */
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {

            overWorldPanel.update();
            overWorldPanel.repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if (remainingTime < 0) {
                    remainingTime = 0; // we don't need a sleep if the time is used up
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
                // pause the game loop so that we only draw 60 times per second
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    } 

    /* (non-Javadoc)
     * Paint method is called when repaint is called
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        //Tilesheet
        tileM.draw(g2);
        //Objects
        for (int i = 0; i < obj.length; i++) //for each object we have loaded in obj, 
        //we need to draw it to the screen
        {
           if(obj[i] != null) {
               obj[i].draw(g2, this);
            }
       }
      
        //Player
        player.draw(g2);
        
       
        
        
    }



    // g2.dispose();
    // }
}
