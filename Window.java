
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Window extends JPanel implements ActionListener, Runnable {
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
    public static ActionButton victoryButton = new ActionButton("Continue");
    public static Window gamePanel = new Window();
    public static JFrame frame = new JFrame(); // Initialization of the window
    public Thread gameThread;
    public static KeyHandler keyH = new KeyHandler();
    public static Window victoryPanel = new Window();
    CollisionDetection cDetection = new CollisionDetection(this);
    public static Window overWorldPanel = new Window();
    static OverWorldPlayer player = new OverWorldPlayer(overWorldPanel, keyH);
    
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

    public Window() {
        this.setPreferredSize(winSize);
        this.setBackground(backgroundColor);
        this.setDoubleBuffered(true);
        this.setLayout(null);
        this.setFocusable(true);
        this.addKeyListener(keyH);
    }

    public static void updateFoeHealth() {
        Window.foeHealth.setText(Entity.testEnemy.toString());
    }

    public static void updatePlayerHealth() {
        Window.playerHealth.setText(Player.playerCharacter.toString());
    }

    public void initialize() {
        startGameThread();
        player.setDefaultValues();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set close behavior to stop the program when the window // is closed
        frame.setResizable(false); // I don't want to allow resizing of the window yet
        frame.setTitle("Hags and Hexxes "); // setting the title of the window, this is pretty temporary
        overWorldPanel();


        // drawStatusBar();
        // drawFoeBar();
        // gamePanel.setBackground(Color.WHITE);
        // ActionPanel.drawActionPanel();
        // frame.add(gamePanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void drawStatusBar() {
        statusBar.add(playerHealth);
        statusBar.setBackground(backgroundColor);
        statusBar.setBounds(new Rectangle(0, 0, tileSize * 5, tileSize * 2));
        playerHealth.setForeground(Color.white);
        gamePanel.add(statusBar);
    }

    public void drawFoeBar() {
        foeHealth.setForeground(Color.white);
        foeBar.add(foeHealth);
        foeBar.setBackground(backgroundColor);
        foeBar.setBounds(new Rectangle(tileSize * 11, tileSize * 0, tileSize * 5, tileSize * 2));
        gamePanel.add(foeBar);
    }

    public static void victoryPanel() {

        gamePanel.setVisible(false);

        victoryLabel.setForeground(Color.WHITE);
        victoryLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 32));
        victoryLabel.setBounds(screenWidth / 3, screenHeight / 4, 200, 40);
        victoryButton.setBounds(screenWidth / 2, screenHeight / 2, 200, 20);
        victoryButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                victoryPanel.setVisible(false);
                frame.remove(victoryPanel);
                overWorldPanel();

                overWorldPanel.requestFocus();
            }

        });
        victoryPanel.add(victoryButton);

        victoryPanel.add(victoryLabel);
        frame.add(victoryPanel);
        victoryPanel.setVisible(true);

    }

    public static void overWorldPanel() {
        overWorldPanel.setName("overWorldPanel");
        frame.add(overWorldPanel);
        overWorldPanel.setVisible(true);
        overWorldPanel.repaint();
        

    }

    public void addCreatureInfo(Entity creature, JLabel creatureLabel) {
        creatureLabel.setText(creature.toString());
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        System.out.println("Started game thread");
    }

    public void update() {
      player.update();
    }

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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        player.draw(g2);
        
       
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

    // g2.dispose();
    // }
}
