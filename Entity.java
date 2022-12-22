
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 
 * OverWorldEntity
 */
public class Entity extends AbstractEntity {
    protected float health;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public Window window;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
    public int hitBoxDefeaultX, hitBoxDefeaultY;
    public boolean collisionOn = false;

    public Entity(String name, Type normal, Move[] moves, int level, float health, float maxHealth) {

        super(name, Type.normal, new Move[] { Move.slap, Move.tackle }, 5, (float) 90, (float) 90);
    }

    // abstract float attack(Move moveChoice) throws IOException;
    public void levelUp() {
    }

    public String getName() {
        return name;
    }

    public float foeAttack() throws IOException {
        return health;
    }

}