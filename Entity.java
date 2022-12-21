
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * 
 * OverWorldEntity
 */
public class Entity extends AbstractEntity{
    protected float health;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    Window window;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle hitBox = new Rectangle(0,0,48,48);
    public int hitBoxDefeaultX,hitBoxDefeaultY;
    public boolean collisionOn = false;

    public Entity(Window window){
        super("Hero", Type.normal, new Move[] { Move.slap, Move.tackle }, 5, (float )90,(float)90);
        this.window = window;

    }

// abstract float attack(Move moveChoice) throws IOException;
public void levelUp(){}

public float foeAttack() throws IOException{
    return health;}

@Override
BufferedImage getImage() {
    // TODO Auto-generated method stub
    return null;
    }
}