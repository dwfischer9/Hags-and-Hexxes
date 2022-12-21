/**
 * AbstractObject
 */
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
    public class AbstractObject {

    protected final Rectangle HITBOX = new Rectangle(0,0,24,24);
    protected final Integer HITBOXDEFAULTX = 0;
    protected final Integer HITBOXDEFAULTY = 0;
    private BufferedImage image;

    protected String name;
    protected boolean collision = false;
    protected Integer worldX,worldY;
    protected final UtilityTools uTool = new UtilityTools();

    public BufferedImage getImage(){
        return this.image;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    
    public void draw(Graphics2D g2, Window window){
        int screenX = worldX - Window.player.worldX + Window.player.screenX;
        int screenY = worldY - Window.player.worldY + Window.player.screenY;
        if( worldX + Window.tileSize > Window.player.worldX - Window.player.screenX && 
           worldX - Window.tileSize < Window.player.worldX + Window.player.screenX && 
           worldY + Window.tileSize > Window.player.worldY - Window.player.screenY && 
           worldY - Window.tileSize < Window.player.worldY + Window.player.screenY) // only render tiles in the camera view
                g2.drawImage(image,screenX,screenY,Window.tileSize,Window.tileSize,null);    
            }
    }
