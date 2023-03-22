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
        Window.items[0] = new Item("chest", true, 20, 20);
        Window.items[1] = new Item("lockeddoor", true, 23,
                Window.TILESIZE * 25);
        Window.items[2] = new Item("key", true, 21, 20);

    }

    /**
     * Instantiates and sets the world position of each NPC to appear on the map.
     */
    public void setNPC() {
        window.npc[0] = window.tutorialNPC;
        window.npc[0].dialogues[0] = "Hi there adventurer! Welcome to the tavern.\n We have cold drinks, warm beds, and food that you can eat.";
        window.npc[0].dialogues[1] = "But I can see that you're not here for just food.\n Your lot comes to me looking for rumors.";
        window.npc[0].dialogues[2] = "Go take a look at the quest board over in the dining area.\n I'll get you a drink in the meantime.";
        window.npc[0].currentDialogue = window.npc[0].dialogues[0];
        window.npc[0].setSpeed(0);
        window.npc[0].setup();
        window.npc[0].setWorldX(Window.TILESIZE * 23);
        window.npc[0].setWorldY(Window.TILESIZE * 20);

        window.monster[0] = window.slime;
        window.monster[0].isMonster = true;
        window.monster[0].hitBoxDefeaultX = 6;
        window.monster[0].hitBoxDefeaultY = 18;
        window.monster[0].hitBox = new Rectangle(6, 18, Window.TILESIZE, Window.TILESIZE);
        window.monster[0].setup();
        window.monster[0].setWorldX(Window.TILESIZE * 24);
        window.monster[0].setWorldY(Window.TILESIZE * 28);
        window.monster[0].setSpeed(2);
    }

    public void setPlayer() {
        Window.player.setup();
    }
}