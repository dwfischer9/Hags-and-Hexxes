
import java.awt.image.BufferedImage;

public class Tile {

    public static final int ORIGINALTILESIZE = 16;// 16x16 tiles
    protected final static int TILESIZE = Tile.ORIGINALTILESIZE * Window.SCALING;
    private BufferedImage image;
    private boolean collision = false;

    public Tile(final BufferedImage image, final boolean collision) {
        this.image = image;
        this.collision = collision;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(final BufferedImage image) {
        this.image = image;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(final boolean collision) {
        this.collision = collision;
    }

    @Override
    public String toString() {
        return "Tile [image=" + image + ", collision=" + collision + "]";
    }
}
