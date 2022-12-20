import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * ObjectChest
 */
public class ObjectChest extends AbstractObject{
    BufferedImage image;
    public ObjectChest(){
    this.collision = true;
    this.name = "Chest";
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/chest.png"));
        } catch (IOException e) {
            System.err.println("Something went wrong");
        }
    }
    public BufferedImage getImage(){
        return this.image;
    }
}