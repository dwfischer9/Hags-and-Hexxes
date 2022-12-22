import java.io.IOException;
/**
 * 
 * This class handles creating various assets and placing them
 *  on the world map. They are not drawn here, however.
 * @author Daniel Fischer
 */
public class AssetSetter {

    Window window;
    public AssetSetter(Window window) {
        this.window = window;
    }

    /**
     * Instantiates and sets the world position of each Item to be on the map.
     * @throws IOException
     */
    public void setObject(){
        window.items[0] = new Item(window,"chest",true);
        window.items[0].worldX = Window.tileSize * 20;
        window.items[0].worldY = Window.tileSize * 20;
        window.items[1] = new Item(window,"lockeddoor",true);
        window.items[1].worldX = Window.tileSize *23;
        window.items[1].worldY = Window.tileSize *25;
        window.items[2] = new Item(window,"key",true);
        window.items[2].worldX = Window.tileSize*21;
        window.items[2].worldY = Window.tileSize *20;

    }
    /**
     * Instatntiates and sets the world position of each NPC to appear on the map.
     */
    public void setNPC(){
        window.npc[0] = new Entity("boxguy",Type.normal, new Move[]{Move.slap,Move.tackle},10,40,40);
        window.npc[0].setup();
        window.npc[0].worldX = Window.tileSize * 23;
        window.npc[0].worldY = Window.tileSize * 20;
    }
}