
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JPanel implements Runnable {
    protected final static Color BG = new Color(26, 26, 26);
    // ||---Screen Settings---||\\

    private static final int ORIGINALTILESIZE = 16;// 16x16 tiles
    private static final int SCALING = 3; // 960 x 576
    protected final static int TILESIZE = ORIGINALTILESIZE * SCALING;
    public final static int MAXSCREENCOL = 20;
    public final static int MAXSCREENROW = 12;
    public final static int SCREENWIDTH = TILESIZE * MAXSCREENCOL;
    public final static int SCREENHEIGHT = TILESIZE * MAXSCREENROW;

    private static final int FPS = 60;
    public static KeyHandler keyH = new KeyHandler();
    public static Window overWorldPanel = new Window();
    public static JPanel statusBar = new JPanel();
    public static Player player;
    public static UI ui = new UI();
    public static TileManager tileM = new TileManager();
    // WORLD SETTINGS
    public static int gameState;
    static JFrame frame = new JFrame();
    public final static int DIALOGUESTATE = 0;
    public final static int PLAYSTATE = 1;
    public final static int PAUSESTATE = 2;
    public final static int BATTLESTATE = 3;
    public final static int STARTSTATE = 4;
    public final static int MENUSTATE = 5;
    public static Item items[] = new Item[20];

    /**
     * initializes the overworld panel. Should be called at the end of a battle
     */
    public static void overWorldPanel() {
        overWorldPanel.setName("overWorldPanel");
        frame.add(overWorldPanel);
        overWorldPanel.setVisible(true);
    }

    public final int SCREEN_X = SCREENWIDTH / 2 - TILESIZE / 2;
    public final int SCREEN_Y = SCREENHEIGHT / 2 - TILESIZE / 2;
    public Entity slime = new Entity(this, "slime", 4, 90, 90);
    public Entity tutorialNPC = new Entity(this, "tutorialNPC", 10, 40, 40);

    public JPanel foeBar = new JPanel();
    public JLabel foeHealth = new JLabel();
    public JLabel victoryLabel = new JLabel("Victory!");
    public Thread gameThread = new Thread(this);

    public AssetSetter assetSetter = new AssetSetter(overWorldPanel);
    public CollisionDetection cDetection = new CollisionDetection();
    public Entity monster[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    private final Dimension winSize = new Dimension(SCREENWIDTH, SCREENHEIGHT);
    int playerSpeed = 4;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = TILESIZE * maxWorldCol;

    public final int worldHeight = TILESIZE * maxWorldRow;

    /**
     * Constructs a new instance of Window and sets some defeault properties
     */
    public Window() {
        setFocusTraversalKeysEnabled(false);
        this.setPreferredSize(winSize);
        this.setBackground(BG);
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
        gameState = STARTSTATE;
        player.isPlayer = true;
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
        if (gameState == PLAYSTATE) {
            tutorialNPC.update();
            slime.update();
            player.update();
        }
    }

    /**
     * This is the method that controls the game loop.
     */
    @Override
    public void run() {
        final double drawInterval = 1000000000 / FPS; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {
            overWorldPanel.repaint();
            if (gameState == PLAYSTATE) {
                overWorldPanel.update();
                player.update();
            }
            if (gameState == DIALOGUESTATE)
                if (keyH.ePressed) {
                    keyH.ePressed = false;
                    if (player.currentInteraction.dialogues[player.currentInteraction.dialogueCounter] != null) {
                        ui.currentDialogue = player.currentInteraction.dialogues[player.currentInteraction.dialogueCounter++];

                    } else
                        gameState = PLAYSTATE;
                }

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if (remainingTime < 0)
                    remainingTime = 0; // we don't need a sleep if the time is used up

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
                // pause the game loop so that we only draw 60 times per second
            } catch (final InterruptedException e) {
                e.printStackTrace();
                System.err.println("Something went wrong when drawing the timer");
            }

        }
    }

    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final long drawStart = System.nanoTime();
        final Graphics2D g2 = (Graphics2D) g.create();
        // Tilesheet
        tileM.draw(g2);
        // Objects
        for (int i = 0; i < items.length; i++) // for each item we have loaded in ,
            // we need to draw it to the screen
            if (items[i] != null)
                items[i].draw(g2, this);

        // NPC
        for (int i = 0; i < npc.length; i++)
            if (npc[i] != null)
                npc[i].draw(g2);

        // Monsters
        for (int i = 0; i < monster.length; i++)
            if (monster[i] != null)
                monster[i].draw(g2);

        // Player
        player.draw(g2);
        final long drawEnd = System.nanoTime();
        final long passed = drawEnd - drawStart;
        g2.setColor(BG);
        g2.drawString("Draw Time:" + passed, 10, 400);

        ui.draw(g2);
    }
}
