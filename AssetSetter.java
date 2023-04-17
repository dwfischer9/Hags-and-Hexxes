import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.awt.Rectangle;

/**
 * 
 * This class handles creating various assets and placing them
 * on the world map. They are not drawn here, however.
 * 
 * @author Daniel Fischer
 */

public class AssetSetter {
    private final String itemsFilePath = "assets/items.csv";
    Window window;
    Player player = Window.player;

    public AssetSetter(Window window) {
        this.window = window;
        readItems();
    }

    private HashMap<String, Item> readItems() {
        String line = "";
        Item item;
        HashMap<String, Item> items = new HashMap<String, Item>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(itemsFilePath));

            while ((line = reader.readLine()) != null) {
                String[] itemData = line.split(",");
                item = new Item(itemData[0], itemData[1], (Integer.parseInt(itemData[2]) == 1),
                        Integer.parseInt(itemData[3]), Integer.parseInt(itemData[4]), Integer.parseInt(itemData[5]),
                        itemData[6]);
                items.put(itemData[0], item);
            }
            reader.close();

        } catch (FileNotFoundException e) {
            System.err.println("Items file not found.");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Instantiates and sets the world position of each Item to be on the map.
     * 
     * @throws IOException
     */
    public void setObject() {

        window.items = readItems();
    }

    /**
     * Instantiates and sets the world position of each NPC to appear on the map.
     */
    public void setNPC() {
        Entity tutorialNPC = new Entity(window, "tutorialNPC", 10, 40, 40);
        tutorialNPC.dialogues[0] = "Hi there adventurer! Welcome to the tavern.\n We have cold drinks, warm beds, and food that you can eat.";
        tutorialNPC.dialogues[1] = "But I can see that you're not here for just food.\n Your lot comes to me looking for rumors.";
        tutorialNPC.dialogues[2] = "I've heard talk of a slime wandering around the inn.\n I can't have it hurting my customers. Go take care of it!";
        Quest slimeQuest = new Quest(window, 20, "My First Job",
                "The inkeeper of Hod's Respite asked me to take care of a wandering slime. I should deal with it soon.",
                window.items.get("Slime Heart Gem"), 1);
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
        slime.dropTable.put(window.items.get("Slime Heart Gem"), 1.0);
        window.monster[0] = slime;

    }

    public void setPlayer() {
        Window.player.setup();
    }
}