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
        window.items[0] = new Item("chest", true, 20, 20);
        window.items[1] = new Item("lockeddoor", true, 23,
                Window.TILESIZE * 25);
        window.items[2] = new Item("key", true, 21, 20);

    }

    /**
     * Instantiates and sets the world position of each NPC to appear on the map.
     */
    public void setNPC() {
        Entity tutorialNPC = new Entity(window, "tutorialNPC", 10, 40, 40);
        tutorialNPC.dialogues[0] = "Hi there adventurer! Welcome to the tavern.\n We have cold drinks, warm beds, and food that you can eat.";
        tutorialNPC.dialogues[1] = "But I can see that you're not here for just food.\n Your lot comes to me looking for rumors.";
        tutorialNPC.dialogues[2] = "I've heard talk of a slime wandering around the inn.\n I can't have it hurting my customers. Go take care of it!";
        tutorialNPC.setSpeed(0);
        tutorialNPC.setup();
        tutorialNPC.setWorldX(Window.TILESIZE * 23);
        tutorialNPC.setWorldY(Window.TILESIZE * 20);
        window.npc[0] = tutorialNPC;

        Entity slime = new Entity(window, "slime", 4, 90, 90);
        slime.isMonster = true;
        slime.hitBoxDefeaultX = 6;
        slime.hitBoxDefeaultY = 18;
        slime.hitBox = new Rectangle(6, 18, Window.TILESIZE, Window.TILESIZE);
        slime.setup();
        slime.setWorldX(Window.TILESIZE * 24);
        slime.setWorldY(Window.TILESIZE * 28);
        slime.setSpeed(2);
        slime.dropTable.put(new Item("key", true, 0, 0), 1.00);
        window.monster[0] = slime;

    }

    public void setPlayer() {
        Window.player.setup();
    }
}