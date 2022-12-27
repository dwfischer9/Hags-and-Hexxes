import java.io.IOException;
import java.awt.Rectangle;

/**
 * 
 * This class handles creating various assets and placing them
 * on the world map. They are not drawn here, however.
 * 
 * @author Daniel Fischer
 */
public class AssetSetter {

    Window window = Window.overWorldPanel;
    Player player = Window.player;

    public AssetSetter(Window window) {
        this.window = window;
    }

    /**
     * Instantiates and sets the world position of each Item to be on the map.
     * 
     * @throws IOException
     */
    public void setObject() {
        Window.items[0] = new Item(Window.overWorldPanel, "chest", true, Window.tileSize * 20, Window.tileSize * 20);
        Window.items[1] = new Item(Window.overWorldPanel, "lockeddoor", true, Window.tileSize * 23,
                Window.tileSize * 25);
        Window.items[2] = new Item(Window.overWorldPanel, "key", true, Window.tileSize * 21, Window.tileSize * 20);

    }

    /**
     * Instantiates and sets the world position of each NPC to appear on the map.
     */
    public void setNPC() {
        Window.npc[0] = window.boxGuy;
        Window.npc[0].dialogues[0] = "HI!";
        Window.npc[0].setup();
        Window.npc[0].setWorldX(Window.tileSize * 23);
        Window.npc[0].setWorldY(Window.tileSize * 20);
        Window.monster[0] = window.slime;
        Window.monster[0].isMonster = true;
        Window.monster[0].hitBoxDefeaultX = 6;
        Window.monster[0].hitBoxDefeaultY = 18;
        Window.monster[0].hitBox = new Rectangle(6, 18, 38, 30);
        Window.monster[0].setup();
        Window.monster[0].setSpeed(2);
        Window.monster[0].setWorldX(Window.tileSize * 24);
        Window.monster[0].setWorldY(Window.tileSize * 28);
    }

    public void setPlayer() {
        Window.player.setup();
    }
}