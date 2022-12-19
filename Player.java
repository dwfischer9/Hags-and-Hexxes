import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    Window overWorldPanel;
    int playerX = 100,playerY=100,playerSpeed=100;
    String direction;
    public static Player playerCharacter = new Player("Hero", Type.normal, new Move[] { Move.slap, Move.tackle }, 5, 90,
            90);

    public Player(String name, Type type, Move[] moves, int level, float health, float maxHealth) {
        super(name, type, moves, level, health, maxHealth);
        // TODO Auto-generated constructor stub
    }

    public static float attack(Move moveChoice, Entity target) throws IOException {
        float damage = moveChoice.getPower(); // return the damage dealt by the attack
        damageEnemy(damage);
        return damage;
    }
    public static void damagePlayer(float damage){
       playerCharacter.setHealth(playerCharacter.getHealth() - damage);
    }
    public void setDefaultValue(){
        playerX = 100;
        playerY = 100;
        playerSpeed = 4;
        direction = "down";
    }
    // public static void update(){
    //     if(keyH.upPressed == true){
    //         System.out.println("up!");
    //         direction = "up";
    //         playerY-= speed;
    //     }
    //     else if(keyH.downPressed = true){
    //         direction = "down";
    //         playerY +=speed;
    //     }
    //     else if(keyH.leftPressed == true){
    //         direction = "left";
    //         playerX-=speed;
    //     }
    //     else if(keyH.rightPressed == true){
    //         direction = "right";
    //         playerX += speed;
    //     }
    // }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        getPlayerImage();
        switch(direction){
        case "up":
            image = up1;
            break;
        case "down":
            image = up1;
            break;
        case "left":
            image = up1;
            break;
        case "right":
            image = up1;
            break;
        }
        g2.drawImage(image, playerX,playerY,Window.tileSize,Window.tileSize,null);
    }

    /**
     * Update global variables
     */
    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("assets/Knight...png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("assets/Knight...png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("assets/Knight...png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("assets/Knight...png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("assets/Knight...png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("assets/Knight...png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("assets/Knight...png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("assets/Knight...png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void repaint() {
        Window.overWorldPanel.repaint();
    }
}
