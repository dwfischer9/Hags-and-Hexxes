import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * ObjectChest
 */
public class ObjectChest extends AbstractObject{

    public ObjectChest(){
    this.name = "Chest";
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/assets/chest.png"));
            
        }
         catch (IOException e) {
            System.err.println("Something went wrong");
        }
        collision = true;
    }

}