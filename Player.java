


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
public class Player extends Entity {
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    private String name;
    private int level;
    private float maxHealth;
    protected float health;
    private Move[] moves;

    public Player(Window window, KeyHandler keyH,String name, Type type, Move[] moves, int level, float health, float maxHealth) {
        super(window);
        this.keyH = keyH;
        this.name = name;
        this.health = health;
        this.level = level;
        this.maxHealth = maxHealth;
        this.moves = moves;

        screenX = Window.screenWidth/2 - Window.tileSize/2;
        screenY = Window.screenHeight/2 - Window.tileSize/2;
        setDefaultValues();
        getPlayerImage();
        hitBox = new Rectangle(8,16,24,24);
    }


    public void setDefaultValues(){
        worldX = Window.tileSize * 23;
        worldY = Window.tileSize * 21;
        speed = 4;
        direction = "down";
    }
        /**
     * Update global variables
     */
    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("assets/player.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("assets/player.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("assets/player.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("assets/player.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("assets/player.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("assets/player.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("assets/player.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("assets/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        BufferedImage image = null;
        getPlayerImage();
        image = up1;
        // switch(direction){ //handles the direction that the sprite is facing.
        // case "up": 
        //     if(spriteNumber == 1)
        //         image = up1;
        //     if(spriteNumber == 2)
        //         image = up2;
        //     break;
        // case "down":
        //     if(spriteNumber == 1)
        //         image = down1;
        //     if(spriteNumber == 2)
        //         image = down2;
        //     break;
        // case "left":
        //     if(spriteNumber == 1)
        //         image = left1;
        //     if(spriteNumber == 2)
        //         image = left2;
        //     break;
        // case "right":
        //     if(spriteNumber == 1)
        //         image = right1;
        //     if(spriteNumber == 2)
        //         image = right2;
        //     break;
        // }
        g2.drawImage(image, screenX,screenY,Window.tileSize,Window.tileSize,null);
    
    }
}
