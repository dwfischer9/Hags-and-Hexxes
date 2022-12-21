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
     * Instantiates and sets the world position of each object to be on the map.
     * @throws IOException
     */
    public void setObject(){
        window.obj[0] = new Item(window,"chest",true);
        window.obj[0].worldX = Window.tileSize * 20;
        window.obj[0].worldY = Window.tileSize * 20;
        window.obj[1] = new Item(window,"lockeddoor",true);
        window.obj[1].worldX = Window.tileSize *23;
        window.obj[1].worldY = Window.tileSize *25;
        window.obj[2] = new Item(window,"key",true);
        window.obj[2].worldX = Window.tileSize*21;
        window.obj[2].worldY = Window.tileSize *20;

    }
    /**
     * Instatntiates and sets the world position of each NPC to appear on the map.
     */
    public void setNPC(){
        window.npc[0] = new Entity(window);
        window.npc[0].worldX = Window.tileSize * 23;
        window.npc[0].worldY = Window.tileSize * 20;
    }
}