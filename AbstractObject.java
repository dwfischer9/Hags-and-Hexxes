/**
 * AbstractObject
 */
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
    public class AbstractObject {

    public Rectangle hitBox = new Rectangle(0,0,24,24);
    public int hitBoxDefeaultX = 0;
    public int hitBoxDefeaultY = 0;
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX,worldY;

    public BufferedImage getImage() {
        return this.image;
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
