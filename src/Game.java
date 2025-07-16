import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game implements Runnable {
    // Game state constants
    public final static int STARTSTATE = 0;
    public final static int PLAYSTATE = 1;
    public final static int MENUSTATE = 2;
    public final static int PAUSESTATE = 3;
    public final static int DIALOGUESTATE = 4;
    public final static int GAMEOVERSTATE = 5;
    
    // Game loop constants
    private static final int FPS = 60;
    private static final int MILLISECONDS_PER_FRAME = 1000 / FPS;
    private static final int SHUTDOWN_TIMEOUT_SECONDS = 60;
    
    // Game state management
    private static Game instance;
    private int gameState;
    
    // Game entities and components - now instance-based
    private final ArrayList<Entity> monsters = new ArrayList<>();
    private final ArrayList<Entity> npcs = new ArrayList<>();
    private final HashMap<String, Item> items = new HashMap<>();
    private Player player;
    private Window window;
    private KeyHandler keyH;
    private ScheduledExecutorService scheduler;
    private DialogueSystem dialogueSystem;

    public Game() {}

    public static void main(String[] args) throws IOException {
        try {
            instance = new Game();
            instance.initializeGame();
            instance.setupGame();
        } catch (Exception e) {
            System.err.println("Failed to start game: " + e.getMessage());
            System.err.println("Stack trace: " + e);
            System.exit(1);
        }
    }
    

    
    /**
     * Initializes the core game components
     */
    private void initializeGame() {
        player = new Player("player", 1, 10);
        keyH = new KeyHandler();
        window = new Window(this);
        dialogueSystem = new DialogueSystem(this);
    }

    private void setupGame() {
        try {
            Window.setupFrame(window);
            setGameState(STARTSTATE);
            startGameScheduler();
            
            AssetSetter assetSetter = new AssetSetter(this);
            player.setDefaultValues();
            assetSetter.readItems();
            assetSetter.setObject();
            assetSetter.setNPC();
            assetSetter.setPlayer();
        } catch (Exception e) {
            System.err.println("Failed to setup game: " + e.getMessage());
            System.err.println("Stack trace: " + e);
            stopScheduler();
            System.exit(1);
        }
    }

    /**
     * Starts the scheduled task to run the game loop at a fixed rate.
     */
    private void startGameScheduler() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this, 0, MILLISECONDS_PER_FRAME, TimeUnit.MILLISECONDS);
        if (window != null && window.getGui() != null) {
            window.getGui().printDebug("Started game scheduler");
        }
    }

    public void update() {
        if (npcs != null) {
            updateEntities(npcs);   // Update NPCs
        }
        if (monsters != null) {
            updateEntities(monsters); // Update Monsters
        }
        if (player != null && keyH != null) {
            player.update(keyH);    // Update Player
        }
        
        // Update UI
        if (window != null && window.getGui() != null) {
            window.getGui().update();
        }
    }

    private void updateEntities(List<Entity> entities) {
        if (entities == null) {
            return;
        }
        
        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity != null) {
                if (entity.getHealth() <= 0) {
                    entity.dropItems();
                    System.out.println(entity.getName() + " has been defeated!");
                    if (window != null && window.getGui() != null) {
                        window.getGui().printDebug(entity.getName() + " has been defeated!");
                    }
                    iterator.remove(); // Safe removal of the entity
                } else {
                    entity.update(); // Update the entity if still alive
                }
            }
        }
    }

    /**
     * This method controls the game loop and is executed periodically by the scheduler.
     */
    @Override
    public void run() {
        try {
            if (window != null) {
                window.repaint();
            }

            switch (getGameState()) {
                case PLAYSTATE -> update(); // Update entities, items, etc.

                case DIALOGUESTATE -> handleDialogueState();

                case MENUSTATE -> {
                    if (keyH != null && keyH.isPressed("MENU")) {
                        setGameState(PLAYSTATE); // Exit to PLAYSTATE from MENUSTATE
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error in game loop: " + e.getMessage());
            System.err.println("Stack trace: " + e);
        }
    }
    
    /**
     * Handles the dialogue state logic
     */
    private void handleDialogueState() {
        if (dialogueSystem != null) {
            dialogueSystem.update();
            dialogueSystem.handleKeyInput(keyH);
            
            if (!dialogueSystem.isActive()) {
                setGameState(PLAYSTATE);
            }
        } else {
            setGameState(PLAYSTATE);
        }
    }

 
    
    public void addMonster(Entity monster) {
        if (monsters != null) {
            monsters.add(monster);
        }
    }
    
    public void addNpc(Entity npc) {
        if (npcs != null) {
            npcs.add(npc);
        }
    }
    
    public void addItem(String key, Item item) {
        if (items != null) {
            items.put(key, item);
        }
    }
    
    public DialogueSystem getDialogueSystem() {
        return dialogueSystem;
    }

    /**
     * Shuts down the scheduler gracefully when the game ends.
     */
    public void stopScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Cleanup method to be called when the game is shutting down
     */
    public static void cleanup() {
        if (instance != null) {
            instance.stopScheduler();
        }
    }
        // Get the singleton instance of the game
     
    public static Game getInstance() {
        return instance;
    }
       // Getters and setters for game state
       public int getGameState() {
        return gameState;
    }

    public void setGameState(int gs) {
        gameState = gs;
    }
    
    // Getters for game components
    public Player getPlayer() {
        return player;
    }
    
    public Window getWindow() {
        return window;
    }
    
    public KeyHandler getKeyHandler() {
        return keyH;
    }
    
    public ArrayList<Entity> getMonsters() {
        return monsters;
    }
    
    public ArrayList<Entity> getNpcs() {
        return npcs;
    }
    
    public HashMap<String, Item> getItems() {
        return items;
    }
}
