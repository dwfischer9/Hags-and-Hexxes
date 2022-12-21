import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ObjectKey
 */
public class ObjectKey extends AbstractObject{
    Window window;
    public ObjectKey(Window window){
        this.name = "Key";
            try {
                setImage(ImageIO.read(getClass().getResourceAsStream("assets/key.png")));

                //scale the image before the game loop so that we can save some performance.
                uTool.scaleImage(getImage(), Window.tileSize, Window.tileSize);
            }
             catch (IOException e) {
                System.err.println("Something went wrong");
                e.printStackTrace();
            }
            collision = true;
        }
}