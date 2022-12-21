import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * ObjectChest
 */
public class ObjectChest extends AbstractObject{
    Window window;
    public ObjectChest(Window window){
    this.name = "Chest";
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("/assets/chest.png")));
            uTool.scaleImage(getImage(), Window.tileSize, Window.tileSize);
        }
         catch (IOException e) {
            System.err.println("Something went wrong");
        }
        collision = true;
    }

}