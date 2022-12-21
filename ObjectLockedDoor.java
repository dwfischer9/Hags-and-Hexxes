import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * ObjectLockedDoor
 */
public class ObjectLockedDoor extends AbstractObject{
    Window window;
    public ObjectLockedDoor(Window window){
    this.name = "LockedDoor";
        try {
            setImage(ImageIO.read(getClass().getResourceAsStream("assets/lockeddoor.png")));
            uTool.scaleImage(getImage(), Window.tileSize, Window.tileSize);
        }
         catch (IOException e) {
            System.err.println("Something went wrong");
        }
        collision = true;
    }

}