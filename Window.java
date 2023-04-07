
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JPanel implements Runnable {
    // ||---Screen Settings---||\\
    private static final int ORIGINALTILESIZE = 16;// 16x16 tiles
    private static final int SCALING = 3; // 960 x 576
    protected final static int TILESIZE = ORIGINALTILESIZE * SCALING;
    public final static int MAXSCREENCOL = 20;
    public final static int MAXSCREENROW = 12;
    public final static int SCREENWIDTH = TILESIZE * MAXSCREENCOL;
    public final static int SCREENHEIGHT = TILESIZE * MAXSCREENROW;
    // ||---Defining Constants---||\\
    public final static int DIALOGUESTATE = 0;
    public final static int PLAYSTATE = 1;
    public final static int PAUSESTATE = 2;
    public final static int BATTLESTATE = 3;
    public final static int STARTSTATE = 4;
    public final static int MENUSTATE = 5;
    private static final int FPS = 60;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = TILESIZE * maxWorldCol;
    public final int worldHeight = TILESIZE * maxWorldRow;
    public final int SCREEN_X = SCREENWIDTH / 2 - TILESIZE / 2;
    public final int SCREEN_Y = SCREENHEIGHT / 2 - TILESIZE / 2;
    protected final static Color BG = new Color(26, 26, 26);

    public CollisionDetection cDetection = new CollisionDetection(this);
    public KeyHandler keyH = new KeyHandler(this);
    public static JPanel statusBar = new JPanel();
    public static Player player;
    public UI ui = new UI(this);
    public TileManager tileM = new TileManager(this);
    // WORLD SETTINGS
    public int gameState;
    public Item items[] = new Item[20];

    public JPanel foeBar = new JPanel();
    public JLabel foeHealth = new JLabel();
    public JLabel victoryLabel = new JLabel("Victory!");
    public Thread gameThread = new Thread(this);

    public AssetSetter assetSetter;
    public Entity monster[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Quest quest[] = new Quest[10];
    private final Dimension winSize = new Dimension(SCREENWIDTH, SCREENHEIGHT);
    int playerSpeed = 4;

    /**
     * Constructs a new instance of Window and sets some defeault properties
     */
    public Window() {
        assetSetter = new AssetSetter(this);
        setFocusTraversalKeysEnabled(false);
        this.setPreferredSize(winSize);
        this.setBackground(BG);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(keyH);
        this.setVisible(true);

    }

    public void setupGame() throws IOException {
        setupFrame(this);
        player = new Player(this, keyH, "player", 1,
                90, 90);
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setPlayer();
        gameState = STARTSTATE;

        initialize();
    }

    private void setupFrame(Window window) {
        JFrame frame = new JFrame();
        frame.setMinimumSize(winSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set close behavior to stop the program when the window
        // // is closed
        frame.setResizable(false); // I don't want to allow resizing of the window yet
        frame.setTitle("Hags and Hexxes "); // setting the title of the window, this is pretty temporary
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.add(window);
        frame.setVisible(true);
    }

    public void initialize() throws IOException {

        startGameThread();
        player.setDefaultValues();

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
        if (gameState == PLAYSTATE) {
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    if (npc[i].getHealth() == 0) {
                        npc[i].dropItems();
                        npc[i] = null;
                    } else {
                        npc[i].update();
                    }
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (monster[i].getHealth() == 0) {
                        monster[i].dropItems();
                        monster[i] = null;
                    } else {
                        monster[i].update();
                    }
                }
            }
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
            this.repaint();
            switch (gameState) {
                case PLAYSTATE:
                    update();
                    player.update();
                    break;
                case DIALOGUESTATE:
                    if (keyH.ePressed) {
                        keyH.ePressed = false;
                        if (player.currentInteraction.dialogues[player.currentInteraction.dialogueCounter] != null) {
                            ui.currentDialogue = player.currentInteraction.dialogues[player.currentInteraction.dialogueCounter++];
                        } else {
                            gameState = PLAYSTATE;
                        }
                    }
                    break;
            }

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;
                if (remainingTime < 0) {// we don't need a sleep if the time is used up
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
                // pause the game loop so that we only draw 60 times per second
            } catch (final InterruptedException e) {
                e.printStackTrace();
                System.err.println("Something went wrong when drawing the timer.");
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
        for (int i = 0; i < items.length; i++) { // for each item we have loaded in ,
            // we need to draw it to the screen
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
        }
        // Player
        player.draw(g2);
        final long drawEnd = System.nanoTime();
        final long passed = drawEnd - drawStart;
        g2.setColor(Color.red);
        g2.drawString("Draw Time:" + passed, 10, 400);
        ui.draw(g2);
    }
}
