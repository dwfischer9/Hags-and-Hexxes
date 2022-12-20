
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * 
 * OverWorldEntity
 */
public class Entity {
    private String name;
    private int level;
    private float maxHealth;
    protected float health;
    private Move[] moves;

    public int worldX,worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    Window window;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public Rectangle hitBox = new Rectangle(0,0,48,48);
    public boolean collisionOn = false;
    @Override
    public String toString() {
        return String.format("%s, HP: %.0f / %.0f\n", this.getName(), this.getHealth(), this.getMaxHealth()); // This is
                                                                                                              // the
                                                                                                              // creature's
                                                                                                              // status.
    }
    public Entity(Window window){
        this.window = window;

    }
public Move[] getMoves() {
    return this.moves;
}

/**
 * Retreive the name of the creature.
 * 
 * @return the name of the creature.
 */
public String getName() {
    return this.name;
}

/**
 * Retrieve the level of the creature.
 * 
 * @return the level of the creature.
 */
public int getLevel() {
    return this.level;
}

/**
 * Retrieve the health of the creature.
 * 
 * @return the health of the creature.
 */
public float getHealth() {
    return this.health;
}

/**
 * @return Float the max health of the creature 
 */
public float getMaxHealth() {
    return this.maxHealth;
}

/**
 * Set the creature's level to the given level.
 * 
 * @param newLevel must be in the range (0,100].
 * @return the new level of the creature.
 */
public int setLevel(int newLevel) {
    if (newLevel > 0 && newLevel <= 100)
        this.level = newLevel;
    else
        System.err.println("The entered level is not a valid level.");
    return this.level;
}

/**
 * @param name must have length of [2,20]
 *             Set the name of the creature.
 */
public void setName(String name) {
    if (name.length() >= 2 && name.length() <= 20)
        this.name = name;
    else
        System.err.println("The entered level name is not of valid length.");
}

/**e
 * @param health must be a nonzero integer less than or equal to
 *               the maximum health of the creature.
 */
public void setHealth(float newHealth) {
    if (newHealth <= this.maxHealth)
        this.health = newHealth;
    else
        System.err.println("The entered health is greater than the maximum health of the creature.");
    if (this.health < 0)
        this.health = 0;

}

// abstract float attack(Move moveChoice) throws IOException;
public void levelUp(){}

public void foeAttack() throws IOException{}
}