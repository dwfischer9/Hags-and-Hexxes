import java.io.IOException;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    Window overWorldPanel;

    static KeyHandler keyH = new KeyHandler();
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
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }
    public static void update(){
        if(keyH.upPressed == true){
            direction = "up";
            Window.playerY-= speed;
        }
        else if(keyH.downPressed = true){
            direction = "down";
            Window.playerY +=speed;
        }
        else if(keyH.leftPressed == true){
            direction = "left";
            Window.playerX-=speed;
        }
        else if(keyH.rightPressed == true){
            direction = "right";
            Window.playerX += speed;
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        
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
        g2.drawImage(image, Window.playerX,Window.playerY,Window.tileSize,Window.tileSize,null);
    }

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
        Window.gamePanel.repaint();
    }
}
