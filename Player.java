import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * This class is a subclass of {@link Entity} to be used for storing the data of
 * the player character
 */
public class Player extends Entity {
    KeyHandler keyH;
    public int attack = 10;
    public boolean attacking = false;
    public Rectangle attackArea;
    // new Rectangle(8, 16, window.tileSize, window.tileSize);
    public int hasKey = 0;
    public BufferedImage image = getImage();
    int tempScreenX = getSCREEN_X();
    int tempScreenY = getSCREEN_Y();
    public Weapon longSword = new Weapon(new Rectangle(0, 0, window.tileSize * 3, window.tileSize * 1),
            new Rectangle(0, 0, window.tileSize * 3, window.tileSize * 1),
            new Rectangle(0, 0, window.tileSize * 1, window.tileSize * 3),
            new Rectangle(0, 0, window.tileSize, window.tileSize * 3), "Longsword",
            30);

    public Player(Window window, KeyHandler keyH, String name, int level, float health,
            float maxHealth) {
        super(window,name, level, health, maxHealth);
        this.keyH = keyH;
        this.weapon = longSword;
        this.image = getImage();
        this.window = window;
        this.attackArea = this.weapon.hitBoxLeft;
        setDefaultValues();
        // attackArea.width = 36;

        // attackArea.height = 36;

    }

    public String toString() {
        return String.format("%s, HP: %.0f / %.0f\n", this.getName(), this.getHealth(), this.getMaxHealth());
    }

    public void setDefaultValues() {
        this.hitBoxDefeaultY = 16;
        this.hitBoxDefeaultX = 8;
        this.hitBox = new Rectangle(8, 16, 32, 32);
        this.setWorldX(window.tileSize * 23);
        this.setWorldY(window.tileSize * 21);
        this.setSpeed(4);
        attacking = false;
    }

    public void damageMonster(int index) {
        if (index != 999 && window.monster[index].invincible != true) {

            window.monster[index].setHealth(window.monster[index].getHealth() - 10);
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

            // check collision
            int monsterIndex = window.cDetection.checkAttackEntity(this, window.monster);
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
        tempScreenX = getSCREEN_X();
        tempScreenY = getSCREEN_Y();
        switch (getDirection()) { // handles the direction that the sprite is facing.
            case "up":
                if (attacking == false) {
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                }
                if (attacking == true) {
                    tempScreenY = getSCREEN_Y() - window.tileSize;
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
                    tempScreenX = getSCREEN_X() - window.tileSize;
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
        if (window.keyH.spacePressed == true) {
            this.attacking = true;
        }
        if (attacking == true) {
            attacking();
        } else {
            if (keyH.upPressed == true) {

                direction = "up";
                this.attackArea = this.weapon.hitBoxUp;
            } else if (keyH.downPressed == true) {

                direction = "down";
                this.attackArea = this.weapon.hitBoxDown;
            } else if (keyH.leftPressed == true) {

                direction = "left";
                this.attackArea = this.weapon.hitBoxLeft;
            } else if (keyH.rightPressed == true) {

                direction = "right";
                this.attackArea = this.weapon.hitBoxRight;
            }
            // Checking tile colllision
            collisionOn = false;
            window.cDetection.checkTile(this);

            // checking object collision
            int objIndex = window.cDetection.checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = window.cDetection.checkEntity(this, window.npc);
            interactNPC(npcIndex);
            if (this.getName() != "player")
                window.cDetection.checkPlayer(this);

            int monsterIndex = window.cDetection.checkEntity(this, window.monster);
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
        // DEBUG
        // AttackArea
        tempScreenX = getSCREEN_X() + attackArea.x;
        tempScreenY = getSCREEN_Y() + attackArea.y;
        switch (direction) {
            case "up":
                tempScreenY = getSCREEN_Y() - attackArea.height;
                break;
            case "down":
                tempScreenY = getSCREEN_Y() + window.tileSize;
                break;
            case "left":
                tempScreenX = getSCREEN_X() - attackArea.width;
                break;
            case "right":
                tempScreenX = getSCREEN_X() + window.tileSize;
                break;
        }
        g2.setColor(Color.red);
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(tempScreenX, tempScreenY, attackArea.width, attackArea.height);

        // Draw player
        g2.drawImage(this.getImage(), tempScreenX, tempScreenY, null);

    }

    public void interactNPC(int i) {
        if (i != 999)
            if (keyH.ePressed) {
                window.gameState = window.dialogueState;
                window.npc[i].speak();
            }
    }

    /**
     * @param i the index of the object in {@link window#items}
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
