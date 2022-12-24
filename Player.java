import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * This class is a subclass of {@link Entity} to be used for storing the data of
 * the player character
 */
public class Player extends Entity {
    KeyHandler keyH;

    public int attack = 10;
    public boolean attacking = false;
    public Rectangle attackArea = new Rectangle(8, 16, Window.tileSize, Window.tileSize);
    public int hasKey = 0;
    public BufferedImage image = getImage();

    public Player(Window window, KeyHandler keyH, String name, int level, float health,
            float maxHealth) {
        super(name, level, health, maxHealth);
        this.keyH = keyH;
        this.window = window;
        this.image = getImage();
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

    public void damageMonster(int index) {
        if (index != 999 && window.monster[index].invincible != true) {

            window.monster[index].setHealth(window.monster[index].getHealth() - this.attack);
            window.monster[index].invincible = true;
            System.out.println("Hit detected");
            System.out.println(window.monster[index].getHealth());
            if (window.monster[index].getHealth() == 0) {
                window.monster[index] = null;

            }
        }
    }

    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 80) {
            spriteNum = 2;
            // save hitbox before the attack
            int currentWorldX = this.getWorldX();
            int currentWorldY = this.getWorldY();
            int hitBoxWidth = this.hitBox.width;
            int hitBoxHeight = this.hitBox.height;
            // adjust hitbox for the attack area
            switch (direction) {
                case "up":
                    this.setWorldY(this.getWorldY() - attackArea.height);
                    break;
                case "down":
                    this.setWorldY(this.getWorldY() + attackArea.height);
                    break;
                case "left":
                    this.setWorldX(this.getWorldX() - attackArea.width);
                    break;
                case "right":
                    this.setWorldX(this.getWorldX() + attackArea.width);
                    break;
            }
            hitBox.width = attackArea.width;
            hitBox.height = attackArea.height;

            // check collision
            int monsterIndex = Window.cDetection.checkAttackEntity(this, window.monster);
            damageMonster(monsterIndex);

            this.setWorldX(currentWorldX);
            this.setWorldY(currentWorldY);
            hitBox.width = hitBoxWidth;
            hitBox.height = hitBoxHeight;

        }
        if (spriteCounter > 80) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }

    }

    public BufferedImage getImage() {
        switch (getDirection()) { // handles the direction that the sprite is facing.
            case "up":
                if (attacking == false) {
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                }
                if (attacking == true) {
                    if (spriteNum == 1)
                        image = attackup1;
                    if (spriteNum == 2)
                        image = attackup2;
                }

                break;
            case "down":
                if (attacking == false) {
                    if (spriteNum == 1)
                        image = down1;
                    if (spriteNum == 2)
                        image = down2;
                }
                if (attacking == true) {
                    if (spriteNum == 1)
                        image = attackdown1;
                    if (spriteNum == 2)
                        image = attackdown2;
                }

                break;
            case "left":
                if (attacking == false) {
                    if (spriteNum == 1)

                        if (spriteNum == 2)
                            image = left2;
                }
                if (attacking == true) {
                    if (spriteNum == 1)
                        image = attackleft1;
                    if (spriteNum == 2)
                        image = attackleft2;
                }

                break;
            case "right":
                if (attacking == false) {
                    if (spriteNum == 1)
                        image = right1;
                    if (spriteNum == 2)
                        image = right2;
                }
                if (attacking == true) {
                    if (spriteNum == 1)
                        image = attackright1;
                    if (spriteNum == 2)
                        image = attackright2;
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

    public void update() {
        if (this.invincible == true) {
            this.invincibleCounter++;
            if (this.invincibleCounter > 60) {
                this.invincible = false;
                this.invincibleCounter = 0;

            }
        }
        if (Window.keyH.spacePressed == true) {
            this.attacking = true;
        }
        if (attacking == true) {
            attacking();
        } else {
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

            int monsterIndex = Window.cDetection.checkEntity(this, window.monster);
            contactMonster(monsterIndex);
            // if collision is flase, player can move
            if (collisionOn == false && keyH.spacePressed == false) {
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
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(this.getImage(), SCREEN_X, SCREEN_Y, null);

    }

    public void interactNPC(int i) {
        if (i != 999) {
            if (keyH.ePressed) {

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
