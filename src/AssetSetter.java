import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * 
 * This class handles creating various assets and placing them
 * on the world map. They are not drawn here, however.
 * 
 * @author Daniel Fischer
 */

public class AssetSetter {
    String basePath = System.getProperty("user.dir"); // Gets the current working directory

// Build paths dynamically - check if we're in src directory
    private final String itemsFilePath = basePath.contains("/src") || basePath.contains("\\src") 
        ? basePath + "/assets/items.csv" 
        : basePath + "/src/assets/items.csv";
    private final String npcFilePath = basePath.contains("/src") || basePath.contains("\\src") 
        ? basePath + "/assets/npc.csv" 
        : basePath + "/src/assets/npc.csv";
    private final UtilityTools tools;
    private Game game;
    
    public AssetSetter() {
        tools = new UtilityTools();
    }
    
    public AssetSetter(Game game) {
        this.game = game;
        tools = new UtilityTools();
    }

    HashMap<String, Item> readItems() {
        String line;
        Item item;
        HashMap<String, Item> items = new HashMap<>();

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(itemsFilePath))) {
                while ((line = reader.readLine()) != null) {
                    String[] itemData = line.split(",");
                    item = new Item(itemData[0], itemData[1], (Integer.parseInt(itemData[2]) == 1),
                            Integer.parseInt(itemData[3]), Integer.parseInt(itemData[4]), Integer.parseInt(itemData[5]),
                            itemData[6]);
                    items.put(itemData[0], item);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Items file not found.");
        } catch (IOException e) {
            // TODO: Auto-generated catch block
        }
        return items;
    }

    /**
     * Instantiates and sets the world position of each Item to be on the map.
     * 
     * @throws IOException
     */
    public void setObject() {
        if (game != null) {
            game.getItems().putAll(readItems());
        }
    }

    /**
     * Instantiates and sets the world position of each NPC to appear on the map.
     */
    public void setNPC() {
        String line;
        String[] entityData;
        Entity npc;
        BufferedReader reader;
        int xCoord, yCoord;
        
        System.out.println("Loading NPCs from: " + npcFilePath);
        
        try {
            reader = new BufferedReader(new FileReader(npcFilePath));
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skip empty lines
                
                entityData = line.split(",");
                if (entityData.length < 7) {
                    System.err.println("Invalid NPC data line: " + line);
                    continue;
                }
                
                npc = new Entity(entityData[0], Integer.parseInt(entityData[1]), Integer.parseInt(entityData[2]));
                npc.setDialogues(entityData[3].split(";"));
                xCoord = Integer.parseInt(entityData[4].split(";")[0]);
                yCoord = Integer.parseInt(entityData[4].split(";")[1]);
                npc.setWorldX(Tile.TILESIZE * xCoord);
                npc.setWorldY(Tile.TILESIZE * yCoord);
                npc.setSpeed(Integer.parseInt(entityData[5]));
                npc.setup();
                // entityData 6 is a 'flag' to denote if entity is an NPC or monster.
                boolean npcFlag = (Integer.parseInt(entityData[6]) == 0);
                if (npcFlag) {
                    if (game != null) {
                        game.addNpc(npc);
                    }
                } else {
                    npc.isMonster = true;
                    npc.setHitBoxDefaultX(6);
                    npc.setHitBoxDefaultY(18);
                    npc.setHitBox(new Rectangle(6, 18, Tile.TILESIZE, Tile.TILESIZE));
                    if (game != null) {
                        game.addMonster(npc);
                    }
                }
            }
            reader.close();

        } catch (FileNotFoundException err) {
            System.err.println("NPC file not found at: " + npcFilePath);
            System.err.println("Current working directory: " + System.getProperty("user.dir"));
        } catch (IOException err) {
            System.err.println("Error reading NPC file: " + err.getMessage());
        } catch (NumberFormatException err) {
            System.err.println("Invalid number format in NPC data: " + err.getMessage());
        } catch (ArrayIndexOutOfBoundsException err) {
            System.err.println("Invalid NPC data format: " + err.getMessage());
        }
        // TODO: replcate droptable using another column in CSV referencing items name.
        // Make sure items are loaded BEFORE entities in assetsetter call.
        // slime.dropTable.put(Game.items.get("Slime Heart Gem"), 1.0);
    }

    public void setPlayer() {
        if (game != null && game.getPlayer() != null) {
            game.getPlayer().setup();
        }
    }

    public void setLights() {
        if (tools != null) {
            tools.addLight(tools.new LightingNode(21, 23, 4));
        }
    }

}