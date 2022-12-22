


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Player extends Entity {
    KeyHandler keyH;
    
    public final int screenX = Window.screenWidth/2 - Window.tileSize/2;
    public final int screenY = Window.screenHeight/2 - Window.tileSize/2;
    private float maxHealth;
    protected float health;
    public int hasKey = 0;
    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public Player(Window window, KeyHandler keyH,String name, Type type, Move[] moves, int level, float health, float maxHealth) {
        super(name, type,moves, level,health,maxHealth);
        this.keyH = keyH;
        this.name = name;
        this.window = window;
        this.health = health;
        this.maxHealth = maxHealth;
        setDefaultValues();
       
    }
    public String toString() {
        return String.format("%s, HP: %.0f / %.0f\n", this.getName(), this.getHealth(), this.getMaxHealth());
    }
    public String getName() {
        return this.name;
    }
    public void setDefaultValues(){
        hitBox = new Rectangle(8,16,24,24);
        hitBoxDefeaultX = hitBox.x;
        hitBoxDefeaultY = hitBox.y;
        worldX = Window.tileSize * 23;
        worldY = Window.tileSize * 21;
        speed = 4;
        this.direction = "down";
        this.setup();
    }
    public float getMaxHealth(){
        return this.maxHealth;
    }
    public void update(){
        if(keyH.upPressed == true)
            direction = "up";
        else if(keyH.downPressed == true) 
            direction = "down";
        
       else if(keyH.leftPressed == true)
           direction = "left";
        else if(keyH.rightPressed == true){
             direction = "right";
        }
        else{
            direction = "";
        }
        //Checkig tile colllision
        collisionOn = false;
        window.cDetection.checkTile(this);

        //checking object collision
        int objIndex = window.cDetection.checkObject(this, true);
        pickUpObject(objIndex);
       // if collision is flase, player can move
        if(collisionOn == false){
            switch(direction){
                case "up":
                    worldY-= speed;
                    break;
                case "down":
                    worldY +=speed;
                    break;
                case "left":
                    worldX -=speed;
                    break;
                case "right":
                    worldX +=speed;
                    break;
            }
        }

        spriteCounter ++; // switches between sprite 1 and 2 for the direction.
        if(spriteCounter > 12){ //serves as an idle animation. The player image
            //will change every 12 frames.
            if(spriteNumber == 1){
                spriteNumber = 2 ;
            }
            else if(spriteNumber == 2){
                spriteNumber = 1;
            }
            spriteNumber = 0;
        }
    }
    public void draw(Graphics2D g2){
        g2.drawImage(this.getImage(), screenX,screenY,Window.tileSize,Window.tileSize,null);
    
    }
    public void pickUpObject(int i){
        if(i != 999){ // Must exclude the obejcts that we don't want picked
            String objectName = window.items[i].getName();
            switch(objectName){
                case"chest":
                    Window.startBattle(Window.player, Window.testEntity);
                    window.items[i] = null;
                    break;
                case "key":
                    window.items[i] = null;
                    this.hasKey++;
                    break;
                case "lockeddoor":
                    if(this.hasKey >0){
                        window.items[i] = null;
                        this.hasKey--;
                    }
                    break;
                
            }
        }
    }
}
