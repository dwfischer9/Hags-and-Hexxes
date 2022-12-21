import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
public class UtilityTools {
    public BufferedImage scaleImage(BufferedImage original, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height,original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0,0,Window.tileSize,Window.tileSize,null);
        g2.dispose();
        return scaledImage;
    }
}