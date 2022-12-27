
import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class Window extends JPanel implements Runnable {
    protected final static Color backgroundColor = new Color(26, 26, 26);
    // ||---Screen Settings---||\\

    private static final int originalTileSize = 16;// 16x16 tiles
    private static final int scale = 3; // 960 x 576
    protected final static int tileSize = originalTileSize * scale;
    public final static int maxScreenCol = 20;
    public final static int maxScreenRow = 12;
    public final static int screenWidth = tileSize * maxScreenCol;
    public final static int screenHeight = tileSize * maxScreenRow;

    public final int SCREEN_X = screenWidth / 2 - tileSize / 2;
    public final int SCREEN_Y = screenHeight / 2 - tileSize / 2;
    private static final int FPS = 60;
    public  KeyHandler keyH = new KeyHandler();
    public static Window overWorldPanel = new Window();
    public static JPanel statusBar = new JPanel();
    public static Player player;
    public Entity slime = new Entity(this, "slime", 4, 90, 90);
    public  Entity boxGuy = new Entity(this, "boxguy", 10, 40, 40);
    public JPanel foeBar = new JPanel();
    public JLabel foeHealth = new JLabel();
    public JLabel victoryLabel = new JLabel("Victory!");

    public Thread gameThread = new Thread(this);

    public static UI ui = new UI();
    public AssetSetter assetSetter = new AssetSetter(overWorldPanel);
    public CollisionDetection cDetection = new CollisionDetection();
    public static Entity monster[] = new Entity[10];
    public static Entity npc[] = new Entity[10];
    private final Dimension winSize = new Dimension(screenWidth, screenHeight);
    int playerSpeed = 4;
    public static TileManager tileM = new TileManager();
    // WORLD SETTINGS

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    public static int gameState;
    static JFrame frame = new JFrame();
    public final static int dialogueState = 0;
    public final static int playState = 1;
    public final static int pauseState = 2;
    public final static int battleState = 3;
    public final static int startState = 4;
    int menuState;
    public static Item items[] = new Item[20];

    /**
     * Constructs a new instance of Window and sets some defeault properties
     */
    public Window() {
        this.setPreferredSize(winSize);
        this.setBackground(backgroundColor);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);
        this.setVisible(true);

    }

    public void setupGame() throws IOException {
        player = new Player(overWorldPanel, keyH, "player", 1,
                90, 90);
        player.setDefaultValues();
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setPlayer();
        gameState = startState;
        
        initialize();
    }

    public void initialize() throws IOException {

        startGameThread();
        player.setDefaultValues();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set close behavior to stop the program when the window
        // // is closed
        frame.setResizable(false); // I don't want to allow resizing of the window yet
        frame.setTitle("Hags and Hexxes "); // setting the title of the window, this is pretty temporary
        overWorldPanel();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    /**
     * initializes the overworld panel. Should be called at the end of a battle
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
        System.out.println(gameState);
    }

    /**
     * update graphics
     */
    public void update() {
        if (gameState == playState) {
            boxGuy.update();
            slime.update();
            player.update();
        }
    }

    /**
     * This is the method that controls the game loop.
     */
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {
            if (gameState == playState) {
                overWorldPanel.update();
                overWorldPanel.repaint();
                player.update();
            }
            if(gameState == startState){
                overWorldPanel.update();
                overWorldPanel.repaint();
            }

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
                e.printStackTrace();
                System.err.println("Something went wrong when drawing the timer");
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        long drawStart = System.nanoTime();
        Graphics2D g2 = (Graphics2D) g.create();
        if (gameState == playState) { // Tilesheet
            
            tileM.draw(g2);
            // Objects
            for (int i = 0; i < items.length; i++) // for each item we have loaded in ,
            // we need to draw it to the screen
            {
                if (items[i] != null) {
                    items[i].draw(g2, this);
                }
            }

            // NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].draw(g2);
                }
            }

            // Monsters
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    monster[i].draw(g2);
                }

                // Player
                player.draw(g2);
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setColor(backgroundColor);
                g2.drawString("Draw Time:" + passed, 10, 400);
            }

        }
        
        ui.draw(g2);
        g2.dispose();
    }
}
