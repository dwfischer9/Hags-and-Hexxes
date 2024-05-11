
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel {
    // ||---Screen Settings---||\\
    public static final int SCALING = 3; // 960 x 576
    public final static int MAXSCREENCOL = 32;
    public final static int MAXSCREENROW = 18;
    public final static int SCREENWIDTH = Tile.TILESIZE * MAXSCREENCOL;
    public final static int SCREENHEIGHT = Tile.TILESIZE * MAXSCREENROW;
    // ||---Defining Constants---||\\
    protected final static Color BG = new Color(26, 26, 26);
    public final int worldWidth = Tile.TILESIZE * TileManager.maxWorldCol;
    public final int worldHeight = Tile.TILESIZE * TileManager.maxWorldRow;
    public final int SCREEN_X = SCREENWIDTH / 2 - Tile.TILESIZE / 2;
    public final int SCREEN_Y = SCREENHEIGHT / 2 - Tile.TILESIZE / 2;
    public CollisionDetection cDetection = new CollisionDetection(this);
    public UI ui;
    public TileManager tileM = new TileManager(this);
    // WORLD SETTINGS

    private final static Dimension winSize = new Dimension(SCREENWIDTH, SCREENHEIGHT);
    int playerSpeed = 6;

    /**
     * Constructs a new instance of Window and sets some defeault properties
     */
    public Window() {
        this.ui = new UI(this);
        setFocusTraversalKeysEnabled(false);
        this.setPreferredSize(winSize);
        this.setBackground(BG);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(Game.keyH);
        this.setVisible(true);

    }

    static void setupFrame(final Window window) {
        final JFrame frame = new JFrame();
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

    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final long drawStart = System.nanoTime();
        final Graphics2D g2 = (Graphics2D) g.create();
        // Tilesheet
        tileM.draw(g2);
        // Objects
        for (final Item item : Game.items.values()) {
            item.draw(g2, this);
        }
        // NPC
        for (int i = 0; i < Game.npc.length; i++) {
            if (Game.npc[i] != null) {
                Game.npc[i].draw(g2);
            }
        }

        // Monsters
        for (int i = 0; i < Game.monster.length; i++) {
            if (Game.monster[i] != null) {
                Game.monster[i].draw(g2);
            }
        }
        // Player
        Game.player.draw(g2);
        final long drawEnd = System.nanoTime();
        final long passed = drawEnd - drawStart;
        g2.setColor(Color.red);
        g2.drawString("Draw Time:" + passed, 10, 400);
        ui.draw(g2);
    }

}
