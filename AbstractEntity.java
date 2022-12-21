import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;



abstract class AbstractEntity {
    public BufferedImage up1;
    public int worldX,worldY;
    private String name;
    private int level;
    private float maxHealth;
    protected float health;
    private Move[] moves;

    /**
     * @param name      The name of the creature.
     * @param type      The type of creature.
     * @param moves     The moves the creature knows.
     * @param level     The level of the creature.
     * @param health    The current health of the creature.
     * @param maxHealth The maximum health of the creature.
     * 
     */
    public AbstractEntity(String name, Type type, Move[] moves, int level, float health, float maxHealth) {
        this.name = name;
        this.health = health;
        this.level = level;
        this.maxHealth = maxHealth;
        this.moves = moves;
    }
    public String toString() {
        return String.format("%s, HP: %.0f / %.0f\n", this.getName(), this.getHealth(), this.getMaxHealth());
     } // This is
    /**
 * Retreive the name of the creature.
 * 
 * @return the name of the creature.
 */


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

    /** Sets the name of this creature
     * @param name must have length of [2,20]
     *             Set the name of the creature.
     */
    public void setName(String name) {
        if (name.length() >= 2 && name.length() <= 20)
            this.name = name;
        else
            System.err.println("The entered level name is not of valid length.");
    }
    /**Sets the health of this creature
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
    abstract void levelUp();
    abstract BufferedImage getImage();
    abstract float foeAttack() throws IOException;

    /**
     * @param imagePath the name of the image
     * @return scaledImage the scaled image.
     */
    public BufferedImage setup(String imagePath){
        UtilityTools uTool = new UtilityTools();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + imagePath + ".png"));
            image = uTool.scaleImage(image, Window.tileSize, Window.tileSize);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       return image;
    }
    public void draw(Graphics2D g2) {

        int screenX = worldX - Window.player.worldX + Window.player.screenX;
        int screenY = worldY - Window.player.worldY + Window.player.screenY;
        if( worldX + Window.tileSize > Window.player.worldX - Window.player.screenX && 
        worldX - Window.tileSize < Window.player.worldX + Window.player.screenX && 
        worldY + Window.tileSize > Window.player.worldY - Window.player.screenY && 
        worldY - Window.tileSize < Window.player.worldY + Window.player.screenY){ // only render tiles in the camera view

        g2.drawImage(this.getImage(),screenX,screenY,Window.tileSize,Window.tileSize,null);    
        }      
    }
}