import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * This class is used to serve as a parent class to the {@link Entity} class.
 */
abstract class AbstractEntity {
    protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, attackup1, attackup2, attackdown1,
            attackdown2, attackleft1, attackleft2, attackright1, attackright2;
    private int worldX, worldY;
    HashMap<Item, Double> dropTable = new HashMap<>();
    private String name;
    public CollisionDetection cDetection;
    public int attackAreaDefaultX = 0;
    public int attackAreaDefaultY = 0;
    public Rectangle attackArea = new Rectangle(0, 0, 32, 32);
    public boolean isFriendly = false;
    private String direction;
    private float health;
    public boolean attacking;
    public int dialogueCounter = 0;
    protected BufferedImage image;
    int spriteCounter = 0;
    int spriteNum = 1;
    public int invincibleCounter = 0;
    public Window window;

    private int hitBoxDefaultX = 8, hitBoxDefaultY = 16;
    private final Rectangle hitBox = new Rectangle(hitBoxDefaultX, hitBoxDefaultY, 32, 32);
    // Entity Properties
    public boolean collisionOn = false; // true if the entity is currently colliding with something.
    public boolean invincible;// true if the entity is currently invincible, typically enabled due to taking
                              // damage.1
    // Stats
    private int level;
    private int maxHealth;
    private int speed = 2; // Sets speed in the overworld as well as impoving the chance of moving first.
                           // Default value is 2.
    public int strength = 2; // Strength modifies the damage that the entity deals with physical based
                             // attacks. 
    public int defense = 2; // Defense reduces the damge that the entity takes from attacks.
    public int magic = 0; // Magic modifies the damage that the entity deals with magic based attacks. It
                          // also increases the entity's maximum mana.
    public int mana = 20; // Mana is used to cast spells and is harder to restore than health.

    /**
     * @param name      The name of the creature.
     * @param type      The type of creature.
     * @param moves     The moves the creature knows.
     * @param level     The level of the creature.
     * @param health    The current health of the creature.
     * @param maxHealth The maximum health of the creature.
     * 
     */
    public AbstractEntity(final String name, final int level,
            final int maxHealth) {
        this.name = name;
        this.health = maxHealth;
        this.level = level;
        this.direction = "down";
        this.maxHealth = maxHealth;
        this.image = up1;
        this.window = Game.getInstance().getWindow();
        this.cDetection = new CollisionDetection(this.window);
    }

    public Rectangle getHitBox() {
        return hitBox;

    }

    public void setHitBox(Rectangle hitBox) {
        if (hitBox != null) {
            this.hitBox.setBounds(hitBox);
        }
    }

    @Override
    public String toString() {
        return String.format("%s, HP: %.0f / %.0f\n", this.name, this.getHealth(), this.getMaxHealth());
    }

    public String getName() {
        return this.name;
    }

    public int getLevel() {
        return this.level;
    }

    public float getHealth() {
        return this.health;
    }

    public float getMaxHealth() {
        return this.maxHealth;
    }

    public int setLevel(final int newLevel) {
        if (newLevel > 0 && newLevel <= 100)
            this.level = newLevel;
        else
            System.err.println("The entered level is not a valid level.");
        return this.level;
    }

    public void setName(final String name) {
        if (name.length() >= 2 && name.length() <= 20)
            this.name = name;
        else
            System.err.println("The entered level name is not of valid length.");
    }

    public void setHealth(final float newHealth) {
        if (newHealth <= this.maxHealth)
            this.health = newHealth;
        else
            System.err.println("The entered health is greater than the maximum health of the creature.");
        if (this.health < 0)
            this.health = 0;

    }

    // abstract float attack(Move moveChoice) throws IOException;
    @SuppressWarnings("unused")
    abstract void levelUp();

    public BufferedImage getImage() {
        switch (getDirection()) { // handles the direction that the sprite is facing.
            case "up" -> {
                if (attacking == false) {
                    switch (spriteNum) {
                        case 1 -> image = up1;
                        case 2 -> image = up2;
                    }
                } else {
                    switch (spriteNum) {
                        case 1 -> image = attackup1;
                        case 2 -> image = attackup2;
                    }
                }
            }
            case "down" -> {
                if (!attacking) {
                    switch (spriteNum) {
                        case 1 -> image = down1;
                        case 2 -> image = down2;
                    }
                } else {
                    switch (spriteNum) {
                        case 1 -> image = attackdown1;
                        case 2 -> image = attackdown2;
                    }
                }
            }

            case "left" -> {
                if (!attacking) {
                    switch (spriteNum) {
                        case 1 -> image = left1;
                        case 2 -> image = left2;
                    }
                } else {
                    switch (spriteNum) {
                        case 1 -> image = attackleft1;
                        case 2 -> image = attackleft2;
                    }
                }
            }
            case "right" -> {
                if (!attacking) {
                    switch (spriteNum) {
                        case 1 -> image = right1;
                        case 2 -> image = right2;
                    }
                } else {
                    switch (spriteNum) {
                        case 1 -> image = attackright1;
                        case 2 -> image = attackright2;
                    }
                }
            }
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
            // Load basic movement sprites
            String[] directions = {"up", "down", "left", "right"};
            for (String dir : directions) {
                BufferedImage img1 = loadImage("/assets/" + name + "/" + dir + "1.png");
                BufferedImage img2 = loadImage("/assets/" + name + "/" + dir + "2.png");
                
                if (img1 != null && img2 != null) {
                    switch (dir) {
                        case "up" -> {
                            up1 = uTool.scaleImage(img1, Tile.TILESIZE, Tile.TILESIZE);
                            up2 = uTool.scaleImage(img2, Tile.TILESIZE, Tile.TILESIZE);
                        }
                        case "down" -> {
                            down1 = uTool.scaleImage(img1, Tile.TILESIZE, Tile.TILESIZE);
                            down2 = uTool.scaleImage(img2, Tile.TILESIZE, Tile.TILESIZE);
                        }
                        case "left" -> {
                            left1 = uTool.scaleImage(img1, Tile.TILESIZE, Tile.TILESIZE);
                            left2 = uTool.scaleImage(img2, Tile.TILESIZE, Tile.TILESIZE);
                        }
                        case "right" -> {
                            right1 = uTool.scaleImage(img1, Tile.TILESIZE, Tile.TILESIZE);
                            right2 = uTool.scaleImage(img2, Tile.TILESIZE, Tile.TILESIZE);
                        }
                    }
                }
            }
            
            // Load attack sprites for player only
            if ("player".equals(name)) {
                String[] attackDirections = {"attackup", "attackdown", "attackleft", "attackright"};
                for (String dir : attackDirections) {
                    BufferedImage img1 = loadImage("/assets/" + name + "/" + dir + "1.png");
                    BufferedImage img2 = loadImage("/assets/" + name + "/" + dir + "2.png");
                    
                    if (img1 != null && img2 != null) {
                        switch (dir) {
                            case "attackup" -> {
                                attackup1 = uTool.scaleImage(img1, Tile.TILESIZE, Tile.TILESIZE * 2);
                                attackup2 = uTool.scaleImage(img2, Tile.TILESIZE, Tile.TILESIZE * 2);
                            }
                            case "attackdown" -> {
                                attackdown1 = uTool.scaleImage(img1, Tile.TILESIZE, Tile.TILESIZE * 2);
                                attackdown2 = uTool.scaleImage(img2, Tile.TILESIZE, Tile.TILESIZE * 2);
                            }
                            case "attackleft" -> {
                                attackleft1 = uTool.scaleImage(img1, Tile.TILESIZE * 2, Tile.TILESIZE);
                                attackleft2 = uTool.scaleImage(img2, Tile.TILESIZE * 2, Tile.TILESIZE);
                            }
                            case "attackright" -> {
                                attackright1 = uTool.scaleImage(img1, Tile.TILESIZE * 2, Tile.TILESIZE);
                                attackright2 = uTool.scaleImage(img2, Tile.TILESIZE * 2, Tile.TILESIZE);
                            }
                        }
                    }
                }
            }

            System.out.println("Finished loading assets for " + name);
        } catch (final Exception e) {
            System.out.println("Error loading assets for " + name + ": " + e.getMessage());
        }
    }
    
    /**
     * Helper method to load images with fallback paths
     */
    private BufferedImage loadImage(String path) {
        try {
            // Try loading from classpath first
            BufferedImage img = ImageIO.read(getClass().getResourceAsStream(path));
            if (img != null) return img;
            
            // Try loading from file system as fallback
            String basePath = System.getProperty("user.dir");
            String filePath = basePath + "/src" + path;
            return ImageIO.read(new java.io.File(filePath));
        } catch (Exception e) {
            System.out.println("Could not load image: " + path);
            return null;
        }
    }

    public void draw(final Graphics2D g2) {
        Player p = Game.getInstance().getPlayer();

        final int screenX = worldX - p.getWorldX() + Window.SCREEN_X;
        final int screenY = worldY - p.getWorldY() + Window.SCREEN_Y;
        if (worldX + Tile.TILESIZE > p.getWorldX() - Window.SCREEN_X &&
                worldX - Tile.TILESIZE < p.getWorldX() + Window.SCREEN_X &&
                worldY + Tile.TILESIZE > p.getWorldY() - Window.SCREEN_Y &&
                worldY - Tile.TILESIZE < p.getWorldY() + Window.SCREEN_Y) { // only render tiles in the camera view
            g2.drawImage(getImage(), screenX, screenY, null);

        }
        // HP BAR
        if (this.maxHealth != this.health)
            drawHealthBar(g2, screenX, screenY);

    }

    public void dropItems() {

        for (Item item : dropTable.keySet()) {
            Item dropItem = new Item(item);
            dropItem.worldX = this.worldX;
            dropItem.worldY = this.worldY;
        }
    }

    public void drawHealthBar(final Graphics2D g2, final int screenX, final int screenY) {
        g2.setColor(Color.RED);
        g2.fillRoundRect(screenX, screenY - 5, Tile.TILESIZE, 10, 2, 2);
        g2.setColor(Color.GREEN);
 
        g2.fillRoundRect(screenX, screenY - 5, (int) (Tile.TILESIZE * this.getHealth() / this.getMaxHealth()), 10, 2,
                2);
        g2.setColor(new Color(26, 26, 26));
        g2.drawRoundRect(screenX, screenY - 5, Tile.TILESIZE, 10, 2, 2);
    }
    public void moveEntity(){
        if (collisionOn == false && !"none".equals(this.direction)) {
            switch (this.direction) {
                case "up" -> setWorldY(getWorldY() - getSpeed());
                case "down" -> setWorldY(getWorldY() + getSpeed());
                case "left" -> setWorldX(getWorldX() - getSpeed());
                case "right" -> setWorldX(getWorldX() + getSpeed());
                case "up-right" -> {
                    setWorldY(getWorldY() - getSpeed());
                    setWorldX(getWorldX() + getSpeed());
                }
                case "up-left" -> {
                    setWorldY(getWorldY() - getSpeed());
                    setWorldX(getWorldX() - getSpeed());
                }
                case "down-right" -> {
                    setWorldY(getWorldY() + getSpeed());
                    setWorldX(getWorldX() + getSpeed());
                }
                case "down-left" -> {
                    setWorldY(getWorldY() + getSpeed());
                    setWorldX(getWorldX() - getSpeed());
                }
            }
        }
    }
    /**
     * @param e1 - the entity to compare against this entity
     * @return the absolute distance between the two entities, in tiles.
     */
    public int getDistance(final AbstractEntity e1) {
        final int distanceY = Math.abs(e1.worldY - this.worldY);
        final int distanceX = Math.abs(e1.worldX - this.worldX);
        return (distanceX + distanceY) / Tile.TILESIZE;
    }

    public void resetHitBox() {
        this.hitBox.x = hitBoxDefaultX;
        this.hitBox.y = hitBoxDefaultY;
    }

    public void resetAttackArea() {
        this.attackArea.x = attackAreaDefaultX;
        this.attackArea.y = attackAreaDefaultY;
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
    public void setHitBoxDefaultX(final int X) {
        this.hitBoxDefaultX = X;
    }
    public void setHitBoxDefaultY(final int Y) {
        this.hitBoxDefaultY = Y;
    }

}