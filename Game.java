
import java.io.IOException;
import java.util.HashMap;

public class Game implements Runnable {

    static Entity monster[] = new Entity[10];
    static Entity npc[] = new Entity[10];
    public static Player player;
    public static Window window;
    public static KeyHandler keyH;
    private static Game game;
    public static HashMap<String, Item> items = new HashMap<String, Item>();
    private static final int FPS = 60;
    public final static int STARTSTATE = 0;
    public final static int PLAYSTATE = 1;
    public final static int MENUSTATE = 2;
    public final static int PAUSESTATE = 3;
    public final static int DIALOGUESTATE = 4;
    public final static int GAMEOVERSTATE = 5;
    public static int gameState;
    public Thread gameThread;

    public Game() {
    }

    public static void main(String[] args) throws IOException {
        keyH = new KeyHandler();
        window = new Window();
        player = new Player("player", 1,
                10, 10);

        game = new Game();
        game.setupGame();
    }

    private void setupGame() {
        Window.setupFrame(window);
        gameState = STARTSTATE;
        startGameThread();
        AssetSetter assetSetter = new AssetSetter();
        player.setDefaultValues();
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setPlayer();

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
     * update Player, NPC & Monster entities. Only called during PLAYSTATE
     */
    public void update() {
        if (gameState == PLAYSTATE) {
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    if (npc[i].getHealth() == 0) {
                        npc[i].dropItems();
                        System.out.println(npc[i].getName() + " has been defeated!");
                        npc[i] = null; // nulling the entity effectively 'kills' it
                    } else {
                        npc[i].update();
                    }
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (monster[i].getHealth() == 0) {
                        monster[i].dropItems();
                        System.out.println(monster[i].getName() + " has been defeated!");
                        monster[i] = null;
                    } else {
                        monster[i].update();
                    }
                }
            }
            player.update(keyH);
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
            window.repaint();
            switch (gameState) {
                case PLAYSTATE:
                    update(); // While the game is not paused, update all entities, items, etc.
                    break;
                case DIALOGUESTATE:
                    Entity curr = player.currentInteraction;
                    if (keyH.ePressed) {
                        keyH.ePressed = false;
                        if (curr.dialogues[curr.dialogueCounter] != null) {
                            window.ui.currentDialogue = curr.dialogues[curr.dialogueCounter++];
                        } else {
                            gameState = PLAYSTATE;
                        }
                    }
                case MENUSTATE:
                    if (keyH.escapePressed)
                        gameState = PLAYSTATE;
            }

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;
                if (remainingTime < 0) // we don't need a sleep if the time is used up
                    remainingTime = 0;

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
                // pause the game loop so that we only draw 60 times per second
            } catch (final InterruptedException e) {
                e.printStackTrace();
                System.err.println("Something went wrong when drawing the timer.");
            }
        }

    }

}
