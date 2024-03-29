import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * This class is used to serve as a parent class to the {@link Entity} class.
 */
abstract class AbstractEntity {
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, attackup1, attackup2, attackdown1,
            attackdown2, attackleft1, attackleft2, attackright1, attackright2;
    private int worldX, worldY;
    HashMap<Item, Double> dropTable = new HashMap<Item, Double>();
    private String name;
    public CollisionDetection cDetection;
    public Rectangle attackArea;
    public int attackAreaDefaultx = 0;
    public int attackAreaDefaulty = 0;
    public boolean isFriendly = false;
    public String direction;
    private float health;
    public boolean attacking;
    public int dialogueCounter = 0;
    protected BufferedImage image;
    int spriteCounter = 0;
    int spriteNum = 1;
    public int invincibleCounter = 0;
    public Window window;
    public Rectangle hitBox = new Rectangle(8, 16, 32, 32);
    public final int SCREEN_X = Window.SCREENWIDTH / 2 - Window.TILESIZE / 2;
    public final int SCREEN_Y = Window.SCREENHEIGHT / 2 - Window.TILESIZE / 2;
    // Entity Properties
    public boolean collisionOn = false; // true if the entity is currently colliding with something.
    public boolean invincible;// true if the entity is currently invincible, typically enabled due to taking
                              // damage.1
    // Stats
    private int level; // Level is a rough representation of an entity's power and capabilities. The
                       // player's goal is to levl up and accrue more stat and skill points.
    private int maxHealth; // The maxiumum amount of hit points that an entity can have, even it was healed
                           // for a value that overflows.
    private int speed = 2; // Sets speed in the overworld as well as impoving the chance of moving first.
                           // Default value is 2.
    public int strength = 2; // Strength modifies the damage that the entity deals with physical based
                             // attacks.
    public int defense = 2; // Defense reduces the damge that the entity takes from attacks.
    public int magic = 0; // Magic modifies the damage that the entity deals with magic based attacks. It
                          // also increases the entity's maximum mana.
    public int mana = 20; // Mana is used to case spells and is harder to restore than health.

    /**
     * @param name      The name of the creature.
     * @param type      The type of creature.
     * @param moves     The moves the creature knows.
     * @param level     The level of the creature.
     * @param health    The current health of the creature.
     * @param maxHealth The maximum health of the creature.
     * 
     */
    public AbstractEntity(final Window window, final String name, final int level, final int health,
            final int maxHealth) {
        this.name = name;
        this.health = health;
        this.level = level;
        this.window = window;
        this.direction = "down";
        this.maxHealth = maxHealth;
        this.image = up1;
        this.cDetection = window.cDetection;
    }

    public String toString() {
        return String.format("%s, HP: %.0f / %.0f\n", this.getName(), this.getHealth(), this.getMaxHealth());
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
    public int setLevel(final int newLevel) {
        if (newLevel > 0 && newLevel <= 100)
            this.level = newLevel;
        else
            System.err.println("The entered level is not a valid level.");
        return this.level;
    }

    /**
     * Sets the name of this creature
     * 
     * @param name must have length of [2,20]
     *             Set the name of the creature.
     */
    public void setName(final String name) {
        if (name.length() >= 2 && name.length() <= 20)
            this.name = name;
        else
            System.err.println("The entered level name is not of valid length.");
    }

    /**
     * Sets the health of this creature
     * 
     * @param health must be a nonzero integer less than or equal to
     *               the maximum health of the creature.
     */
    public void setHealth(final float newHealth) {
        if (newHealth <= this.maxHealth)
            this.health = newHealth;
        else
            System.err.println("The entered health is greater than the maximum health of the creature.");
        if (this.health < 0)
            this.health = 0;

    }

    // abstract float attack(Move moveChoice) throws IOException;
    abstract void levelUp();

    public BufferedImage getImage() {
        switch (getDirection()) { // handles the direction that the sprite is facing.
            case "up":
                if (attacking == false) {
                    switch (spriteNum) {
                        case 1:
                            image = up1;
                            break;
                        case 2:
                            image = up2;
                            break;
                    }
                } else {
                    switch (spriteNum) {
                        case 1:
                            image = attackup1;
                            break;
                        case 2:
                            image = attackup2;
                            break;
                    }
                }

                break;
            case "down":
                if (!attacking) {
                    switch (spriteNum) {
                        case 1:
                            image = down1;
                            break;
                        case 2:
                            image = down2;
                            break;
                    }
                } else {
                    switch (spriteNum) {
                        case 1:
                            image = attackdown1;
                            break;
                        case 2:
                            image = attackdown2;
                            break;
                    }
                }
                break;

            case "left":
                if (!attacking) {
                    switch (spriteNum) {
                        case 1:
                            image = left1;
                            break;
                        case 2:
                            image = left2;
                            break;
                    }
                } else {
                    switch (spriteNum) {
                        case 1:
                            image = attackleft1;
                            break;
                        case 2:
                            image = attackleft2;
                            break;
                    }
                }

                break;
            case "right":
                if (!attacking) {
                    switch (spriteNum) {
                        case 1:
                            image = right1;
                            break;
                        case 2:
                            image = right2;
                            break;
                    }
                } else {
                    switch (spriteNum) {
                        case 1:
                            image = attackright1;
                            break;
                        case 2:
                            image = attackright2;
                            break;
                    }
                }

                break;
        }
        spriteCounter++; // switches between sprite 1 and 2 for the direction.
        if (spriteCounter > 90) { // serves as an idle animation. The player image
            // will change every 12 frames.
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        return image;

    }

    public String getDirection() {
        return direction;
    }

    /**
     * @param imagePath the name of the image
     * @return scaledImage the scaled image.
     */
    public void setup() {
        final UtilityTools uTool = new UtilityTools();
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/up1.png"));
            up1 = uTool.scaleImage(image, Window.TILESIZE, Window.TILESIZE);

            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/up2.png"));
            up2 = uTool.scaleImage(image, Window.TILESIZE, Window.TILESIZE);

            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/down1.png"));
            down1 = uTool.scaleImage(image, Window.TILESIZE, Window.TILESIZE);
            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/down2.png"));
            down2 = uTool.scaleImage(image, Window.TILESIZE, Window.TILESIZE);

            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/left1.png"));
            left1 = uTool.scaleImage(image, Window.TILESIZE, Window.TILESIZE);

            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/left2.png"));
            left2 = uTool.scaleImage(image, Window.TILESIZE, Window.TILESIZE);

            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/right1.png"));
            right1 = uTool.scaleImage(image, Window.TILESIZE, Window.TILESIZE);

            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/right2.png"));
            right2 = uTool.scaleImage(image, Window.TILESIZE, Window.TILESIZE);
            if (name == "player") {
                image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/attackup1.png"));
                attackup1 = uTool.scaleImage(image, Window.TILESIZE, Window.TILESIZE * 2);

                image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/attackup2.png"));
                attackup2 = uTool.scaleImage(image, Window.TILESIZE, Window.TILESIZE * 2);

                image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/attackdown1.png"));
                attackdown1 = uTool.scaleImage(image, Window.TILESIZE, Window.TILESIZE * 2);
                image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/attackdown2.png"));
                attackdown2 = uTool.scaleImage(image, Window.TILESIZE, Window.TILESIZE * 2);

                image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/attackleft1.png"));
                attackleft1 = uTool.scaleImage(image, Window.TILESIZE * 2, Window.TILESIZE);

                image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/attackleft2.png"));
                attackleft2 = uTool.scaleImage(image, Window.TILESIZE * 2, Window.TILESIZE);

                image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/attackright1.png"));
                attackright1 = uTool.scaleImage(image, Window.TILESIZE * 2, Window.TILESIZE);

                image = ImageIO.read(getClass().getResourceAsStream("/assets/" + getName() + "/attackright2.png"));
                attackright2 = uTool.scaleImage(image, Window.TILESIZE * 2, Window.TILESIZE);
            }

            System.out.println("Finished loading assets for " + getName());
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void draw(final Graphics2D g2) {
        final int screenX = worldX - Window.player.getWorldX() + this.SCREEN_X;
        final int screenY = worldY - Window.player.getWorldY() + this.SCREEN_Y;
        if (worldX + Window.TILESIZE > Window.player.getWorldX() - this.SCREEN_X &&
                worldX - Window.TILESIZE < Window.player.getWorldX() + this.SCREEN_X &&
                worldY + Window.TILESIZE > Window.player.getWorldY() - this.SCREEN_Y &&
                worldY - Window.TILESIZE < Window.player.getWorldY() + this.SCREEN_Y) { // only render tiles in
                                                                                        // the
            // camera view
            g2.drawImage(getImage(), screenX, screenY, null);

        }
        // HP BAR
        if (this.maxHealth != this.health)
            drawHealthBar(g2, screenX, screenY);

    }

    public void dropItems() {

        for (Item item : dropTable.keySet()) {
            Item dropItem = new Item(item);
            dropItem.worldX = worldX;
            dropItem.worldY = worldY;
        }
    }

    public void drawHealthBar(final Graphics2D g2, final int screenX, final int screenY) {
        g2.setColor(Color.red);
        g2.fillRoundRect(screenX, screenY - 5, Window.TILESIZE, 10, 2, 2);
        g2.setColor(Color.green);

        g2.fillRoundRect(screenX, screenY - 5, (int) (Window.TILESIZE * this.getHealth() / this.getMaxHealth()), 10, 2,
                2);
        g2.setColor(new Color(26, 26, 26));
        g2.drawRoundRect(screenX, screenY - 5, Window.TILESIZE, 10, 2, 2);
    }

    /**
     * @param e1 - the entity to compare against this entity
     * @return the absolute distance between the two entities, in tiles.
     */
    public int getDistance(final AbstractEntity e1) {
        final int distanceY = Math.abs(e1.worldY - this.worldY);
        final int distanceX = Math.abs(e1.worldX - this.worldX);
        return (distanceX + distanceY) / Window.TILESIZE;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(final int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(final int worldY) {
        this.worldY = worldY;
    }

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public void setCollisionOn(final boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public void setMaxHealth(final int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setDirection(final String direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(final int speed) {
        this.speed = speed;
    }

    public int getSCREEN_X() {
        return SCREEN_X;
    }

    public int getSCREEN_Y() {
        return SCREEN_Y;
    }
}