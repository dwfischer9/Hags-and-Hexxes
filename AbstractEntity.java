import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * This class is used to serve as a parent class to the {@link Entity} class. 
 */
abstract class AbstractEntity {
    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public int worldX,worldY;
    public boolean collisionOn;
    protected String name;
    private int level;
    private float maxHealth;
    String direction;
    protected float health;
    private Move[] moves;
    public BufferedImage image;
    int spriteCounter = 0;
    int spriteNum = 1;
    public int hitBoxDefeaultX = 0;
    public final int hitBoxDefeaultY = 0;
    public Window window;
    public int speed;
    public final int screenX = Window.screenWidth/2 - Window.tileSize/2;
    public final int screenY = Window.screenHeight/2 - Window.tileSize/2;
    
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
        this.direction = "down";
        this.maxHealth = maxHealth;
        this.moves = moves;
        this.image = getImage();
    }
    public String toString() {
        return String.format("%s, HP: %.0f / %.0f\n", this.getName(), this.getHealth(), this.getMaxHealth());
     } 
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

    public BufferedImage getImage(){
        switch(this.getDirection()){ //handles the direction that the sprite is facing.
           case "up": 
            if(this.spriteNum == 1)
                this.image = this.up1;
            if(this.spriteNum == 2)
                this.image = this.up2;
            break;
           case "down":
            if(this.spriteNum == 1)
               this.image = this.down1;
            if(this.spriteNum == 2)
                this.image = this.down2;
               break;
           case "left":
            if(this.spriteNum == 1)
               this.image = this.left1;
            if(this.spriteNum == 2)
               this.image = this.left2;   
            break;
           case "right":
            if(this.spriteNum == 1)
               this.image = this.right1;
            if(this.spriteNum == 2)
                this.image = this.right2;
            break;
           }
          this.spriteCounter ++; // switches between sprite 1 and 2 for the direction.
           if(this.spriteCounter > 90){ //serves as an idle animation. The player image
               //will change every 12 frames.
               if(this.spriteNum == 1){
                this.spriteNum = 2 ;
               }
               else if(this.spriteNum == 2){
                this.spriteNum = 1;
               }
               this.spriteCounter = 0;
           }
       return this.image;

    }
    public String getDirection(){
        return this.direction;
    }
    abstract float foeAttack() throws IOException;

    /**
     * @param imagePath the name of the image
     * @return scaledImage the scaled image.
     */
    public void setup(){
        UtilityTools uTool = new UtilityTools();
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + this.getName() + "/up1.png"));
            this.up1 = uTool.scaleImage(image, Window.tileSize, Window.tileSize);

            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + this.getName() + "/up2.png"));
            this.up2 = uTool.scaleImage(image, Window.tileSize, Window.tileSize);

            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + this.getName() + "/down1.png"));
            this.down1 = uTool.scaleImage(image, Window.tileSize, Window.tileSize);
            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + this.getName() + "/down2.png"));
            this.down2 = uTool.scaleImage(image, Window.tileSize, Window.tileSize);

            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + this.getName() + "/left1.png"));
            this.left1 = uTool.scaleImage(image, Window.tileSize, Window.tileSize);

            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + this.getName() + "/left2.png"));
            this.left2 = uTool.scaleImage(image, Window.tileSize, Window.tileSize);

            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + this.getName() + "/right1.png"));
            this.right1 = uTool.scaleImage(image, Window.tileSize, Window.tileSize);

            image = ImageIO.read(getClass().getResourceAsStream("/assets/" + this.getName() + "/right2.png"));
            this.right2 = uTool.scaleImage(image, Window.tileSize, Window.tileSize);
            
            System.out.println("Finished loading assets for " + this.getName());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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