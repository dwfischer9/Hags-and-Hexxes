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

    Window window;
    Player player = window.player;

    public AssetSetter(Window window) {
        this.window = window;
    }

    /**
     * Instantiates and sets the world position of each Item to be on the map.
     * 
     * @throws IOException
     */
    public void setObject() {
        window.items[0] = new Item(window, "chest", true, window.tileSize * 20, window.tileSize * 20);
        window.items[1] = new Item(window, "lockeddoor", true, window.tileSize * 23, window.tileSize * 25);
        window.items[2] = new Item(window, "key", true, window.tileSize * 21, window.tileSize * 20);

    }

    /**
     * Instatntiates and sets the world position of each NPC to appear on the map.
     */
    public void setNPC() {
        window.npc[0] = window.boxGuy;
        window.npc[0].dialogues[0] = "HI!";
        window.npc[0].setup();
        window.npc[0].setWorldX(window.tileSize * 23);
        window.npc[0].setWorldY(window.tileSize * 20);

        window.monster[0] = window.slime;
        window.monster[0].isMonster = true;
        window.monster[0].hitBoxDefeaultX = 6;
        window.monster[0].hitBoxDefeaultY = 18;
        window.monster[0].hitBox = new Rectangle(6, 18, 38, 30);
        window.monster[0].setup();
        window.monster[0].setSpeed(0);
        window.monster[0].setWorldX(window.tileSize * 24);
        window.monster[0].setWorldY(window.tileSize * 18);
    }

    public void setPlayer() {
        player.setup();
    }
}