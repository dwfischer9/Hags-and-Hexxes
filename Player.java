import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * This class is a subclass of {@link Entity} to be used for storing the data of
 * the player character
 */
public class Player extends Entity {
    KeyHandler keyH;
    boolean attacking;
    public Rectangle attackArea = new Rectangle();
    public int hasKey = 0;

    public Player(Window window, KeyHandler keyH, String name, int level, float health,
            float maxHealth) {
        super(name, level, health, maxHealth);
        this.keyH = keyH;
        this.window = window;
        setDefaultValues();
        attackArea.width = 36;
        attackArea.height = 36;

    }

    public String toString() {
        return String.format("%s, HP: %.0f / %.0f\n", this.getName(), this.getHealth(), this.getMaxHealth());
    }

    public void setDefaultValues() {
        this.hitBoxDefeaultY = 16;
        this.hitBoxDefeaultX = 8;
        this.hitBox = new Rectangle(8, 16, 32, 32);
        this.setWorldX(Window.tileSize * 23);
        this.setWorldY(Window.tileSize * 21);
        this.setSpeed(4);
        this.direction = "down";
        attacking = false;
    }

    public void damageMonster(int i) {
        if (i != 999) {
            System.out.println("Hit detected");
        }
    }

    public void update() {
        if (keyH.upPressed == true)
            direction = "up";
        else if (keyH.downPressed == true)
            direction = "down";

        else if (keyH.leftPressed == true)
            direction = "left";
        else if (keyH.rightPressed == true) {
            direction = "right";
        } else {
            direction = "";
        }
        // Checking tile colllision
        collisionOn = false;
        Window.cDetection.checkTile(this);

        // checking object collision
        int objIndex = Window.cDetection.checkObject(this, true);
        pickUpObject(objIndex);

        int npcIndex = Window.cDetection.checkEntity(this, window.npc);

        interactNPC(npcIndex);
        if (this.getName() != "player")
            Window.cDetection.checkPlayer(this);
        // if collision is flase, player can move
        if (collisionOn == false) {
            switch (direction) {
                case "up":
                    this.setWorldY(this.getWorldY() - this.getSpeed());

                    break;
                case "down":
                    this.setWorldY(this.getWorldY() + this.getSpeed());
                    break;
                case "left":
                    this.setWorldX(this.getWorldX() - this.getSpeed());
                    break;
                case "right":
                    this.setWorldX(this.getWorldX() + this.getSpeed());
                    break;
            }
        }

    }

    public void draw(Graphics2D g2) {
        g2.drawImage(this.getImage(), SCREEN_X, SCREEN_Y, Window.tileSize, Window.tileSize, null);

    }

    public void interactNPC(int i) {
        if (i != 999)
            System.out.println("interaction triggered");
        if (keyH.ePressed) {
            if (i != 999) {
                Window.gameState = Window.dialogueState;
                window.npc[i].speak();
            }

        }

    }

    /**
     * @param i the index of the object in {@link Window#items}
     */
    public void pickUpObject(int i) {
        if (i != 999) { // Must exclude the obejcts that we don't want picked
            String objectName = window.items[i].getName();
            switch (objectName) {
                case "chest":
                    break;
                case "key":
                    window.items[i] = null;
                    this.hasKey++;
                    break;
                case "lockeddoor":
                    if (this.hasKey > 0) {
                        window.items[i] = null;
                        this.hasKey--;
                    }
                    break;

            }
        }
    }
}
