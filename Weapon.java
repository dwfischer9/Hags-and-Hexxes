import java.awt.image.BufferedImage;
import java.awt.Polygon;
import java.awt.Rectangle;

public class Weapon {
    public BufferedImage image;
    public Polygon hitArea;
    public Rectangle hitBoxLeft;
    public Rectangle hitBoxRight;
    public Rectangle hitBoxUp;
    public Rectangle hitBoxDown;
    public String name;
    public double knockBack;
    public int[] xPts = { 0, 32, 32, 0 };
    public int[] yPts = { 0, 0, 32, 32 };
    public int damageLowerBound;
    public int damageUpperBound;

    public Weapon(Rectangle hitBoxLeft, Rectangle hitBoxRight, Rectangle hitBoxUp, Rectangle hitBoxDown, String name,
            int damageLowerBound, int damageUpperBound) {
        this.knockBack = .1;
        this.hitBoxLeft = hitBoxLeft;
        this.hitBoxRight = hitBoxRight;
        this.hitBoxUp = hitBoxUp;
        this.hitBoxDown = hitBoxDown;
        this.name = name;
        this.damageLowerBound = damageLowerBound;
        this.damageUpperBound = damageUpperBound;
    }
}
