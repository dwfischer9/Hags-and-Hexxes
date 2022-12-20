import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * ObjectLockedDoor
 */
public class ObjectLockedDoor extends AbstractObject{

    public ObjectLockedDoor(){
    this.name = "LockedDoor";
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("assets/lockeddoor.png"));
            
        }
         catch (IOException e) {
            System.err.println("Something went wrong");
        }
        collision = true;
    }

}