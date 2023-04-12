import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class handles the drawing of the various items in this program.
 * To create a new item, call the constructor in the {@link AssetSetter} class.
 */
public class Item {

    protected final Rectangle HITBOX = new Rectangle(0, 0, 24, 24);
    protected final Integer HITBOXDEFAULTX = 0;
    protected final Integer HITBOXDEFAULTY = 0;
    protected BufferedImage image;

    protected String name;
    protected boolean collision;
    protected Integer worldX, worldY;
    private String description;
    private String filename;
    protected final UtilityTools uTool = new UtilityTools();

    public Item(String name, String description, Boolean collision, int gold, int worldX, int worldY,String filename) {
        this.name = name;
        this.description = description;
        this.worldX = worldX * Window.TILESIZE;
        this.worldY = worldY * Window.TILESIZE;
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
            image = uTool.scaleImage(image, Window.TILESIZE, Window.TILESIZE);
            System.out.println("Loaded assets for item " + name);
        } catch (IOException e) {
            System.err.println("Error getting image for item: " + name);
            e.printStackTrace();
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

    public void draw(Graphics2D g2, Window window) {
        int screenX = worldX - Window.player.getWorldX() + window.SCREEN_X;
        int screenY = worldY - Window.player.getWorldY() + window.SCREEN_Y;
        if (worldX + Window.TILESIZE > Window.player.getWorldX() - window.SCREEN_X &&
                worldX - Window.TILESIZE < Window.player.getWorldX() + window.SCREEN_X &&
                worldY + Window.TILESIZE > Window.player.getWorldY() - window.SCREEN_Y &&
                worldY - Window.TILESIZE < Window.player.getWorldY() + window.SCREEN_Y) // only render tiles in
                                                                                        // the
            // camera view
            g2.drawImage(image, screenX, screenY, Window.TILESIZE, Window.TILESIZE, null);
    }
}
