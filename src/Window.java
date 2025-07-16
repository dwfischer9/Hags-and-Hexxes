import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel {
    // ||---Screen Settings---||\\
    protected static final int SCALING = 3; // 960 x 576
    protected final static int MAXSCREENCOL = 40;
    protected final static int MAXSCREENROW = 24;
    protected final static int SCREENWIDTH = Tile.TILESIZE * MAXSCREENCOL;
    protected final static int SCREENHEIGHT = Tile.TILESIZE * MAXSCREENROW;

    // ||---Defining Constants---||\\=
    public final int worldWidth = Tile.TILESIZE * TileManager.MAXWORLDCOL;
    public final int worldHeight = Tile.TILESIZE * TileManager.MAXWORLDROW;
    public static final int SCREEN_X = SCREENWIDTH / 2 - Tile.TILESIZE / 2;
    public static final int SCREEN_Y = SCREENHEIGHT / 2 - Tile.TILESIZE / 2;
    
    // Debug display constants
    private static final int DEBUG_TEXT_X = 10;
    private static final int DEBUG_TEXT_Y = 400;
    
    private CollisionDetection cDetection;
    private UI gui;
    private TileManager tileM;
    private Game game;
    // WORLD SETTINGS

    private final static Dimension winSize = new Dimension(SCREENWIDTH, SCREENHEIGHT);

    /**
     * Constructs a new instance of Window and sets some default properties
     */
    public Window(Game game) {
        this.game = Game.getInstance();
        this.gui = new UI(game);
        this.tileM = new TileManager(this);
        this.cDetection = new CollisionDetection(this);
        setFocusTraversalKeysEnabled(false);
        this.setPreferredSize(winSize);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(game.getKeyHandler());
        this.setVisible(true);
    }

    static void setupFrame(final Window window) {
        final JFrame frame = new JFrame();
        frame.setMinimumSize(winSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set close behavior to stop the program when the window
        // is closed
        frame.setResizable(false); // I don't want to allow resizing of the window yet
        frame.setTitle("Hags and Hexxes "); // setting the title of the window, this is pretty temporary
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.add(window);
        frame.setVisible(true);
    }

   @Override public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final long drawStart = System.nanoTime();
        final Graphics2D g2 = (Graphics2D) g.create();
        
        try {
            paintObjects(g2);
            final long drawEnd = System.nanoTime();
            final long passed = drawEnd - drawStart;
            g2.setColor(Color.red);
            g2.drawString("Draw Time:" + passed, DEBUG_TEXT_X, DEBUG_TEXT_Y);
            // gui.drawDebugString(g2); // Commented out to fix null pointer exception
            gui.draw(g2);
        } finally {
            g2.dispose();
        }
    }

    /**
     * This method paints the tiles, items and entities in the game.
     * 
     * @param g2 the graphics object
     */
    private void paintObjects(Graphics2D g2) {
        // Tilesheet
        if (tileM != null) {
            tileM.draw(g2);
        }
        
        // Objects
        if (game != null && game.getItems() != null) {
            for (final Item item : game.getItems().values()) {
                if (item != null) {
                    item.draw(g2);
                }
            }
        }
        
        // Entities - NPC and Monsters
        if (game != null) {
            ArrayList<Entity> npcs = game.getNpcs();
            ArrayList<Entity> monsters = game.getMonsters();
            if (npcs != null && monsters != null) {
                for (final ArrayList<Entity> entityList : Arrays.asList(npcs, monsters)) {
                    if (entityList != null) {
                        for (final Entity entity : entityList) {
                            if (entity != null) {
                                entity.draw(g2);
                            }
                        }
                    }
                }
            }
        }
        
        // Player
        if (game != null && game.getPlayer() != null) {
            game.getPlayer().draw(g2);
        }
    }

    // Getters
    public CollisionDetection getCollisionDetection() {
        return cDetection;
    }
    
    public UI getGui() {
        return gui;
    }
    
    public TileManager getTileManager() {
        return tileM;
    }
    
    public Game getGame() {
        return game;
    }
    
    // Setters
    public void setCollisionDetection(CollisionDetection cDetection) {
        this.cDetection = cDetection;
    }
    
    public void setGui(UI gui) {
        this.gui = gui;
    }
    
    public void setTileManager(TileManager tileM) {
        this.tileM = tileM;
    }
    
    public void setGame(Game game) {
        this.game = game;
    }
} 