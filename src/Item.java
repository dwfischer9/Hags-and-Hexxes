import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class handles the drawing of the various items in this program.
 * To create a new item, call the constructor in the {@link AssetSetter} class.
 */
public class Item {

    protected final Rectangle hitBox = new Rectangle(0, 0, 24, 24);
    protected final Integer hitBoxDefaultX = 0;
    protected final Integer hitBoxDefaultY = 0;
    protected BufferedImage image;

    protected String name;
    protected boolean collision;
    protected Integer worldX, worldY;
    private final String description;
    private final String filename;
    protected final UtilityTools uTool = new UtilityTools();

    public Item(String name, String description, Boolean collision, int gold, int startingX, int startingY,String filename) {
        this.name = name;
        this.description = description;
        this.worldX = startingX * Tile.TILESIZE;
        this.worldY = startingY * Tile.TILESIZE;
        this.collision = collision;
        this.filename = filename;
        this.image = setup();
    }
    public Item(Item original) {
        this.name = original.name;
        this.description = original.description;
        this.worldX = original.worldX;
        this.worldY = original.worldY;
        this.collision = original.collision;
        this.filename = original.filename;
        this.image = original.image;
    }
    public BufferedImage getImage() {
        return image;
    }

    /**
     * retrieves and scales the image to be used for this item.
     * 
     * @return the scaled image of this {@link Item}.
     */
    public BufferedImage setup() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("assets/items/" +filename));
            image = uTool.scaleImage(image, Tile.TILESIZE, Tile.TILESIZE);
            System.out.println("Loaded assets for item " + name);
        } catch (IOException e) {
            System.err.println("Error getting image for item: " + name);
        }
        return image;
    }

    /**
     * 
     * @return the name of the {@link Item}.
     */
    public String getName() {
        return name;
    }

    public void draw(Graphics2D g2) {
        Player player = Game.getInstance().getPlayer();
        if (player == null) return;
        
        int screenX = worldX - player.getWorldX() + Window.SCREEN_X ;
        int screenY = worldY - player.getWorldY() + Window.SCREEN_Y;
        if (worldX + Tile.TILESIZE > player.getWorldX() - Window.SCREEN_X &&
                worldX - Tile.TILESIZE < player.getWorldX() + Window.SCREEN_X &&
                worldY + Tile.TILESIZE > player.getWorldY() - Window.SCREEN_Y &&
                worldY - Tile.TILESIZE < player.getWorldY() + Window.SCREEN_Y) // only render tiles in
                                                                                        // the
            // camera view
            g2.drawImage(image, screenX, screenY, Tile.TILESIZE, Tile.TILESIZE, null);
    }
    /**
     * 
     * This method is to be called when an item is picked up from the map.
     */
    public void remove(){
    }

    public void resetHitBox() {
        this.hitBox.x = hitBoxDefaultX;
        this.hitBox.y = hitBoxDefaultY;
    }
}
