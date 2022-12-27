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
    KeyHandler keyH = new KeyHandler();
    
    public int attack = 10;
    public boolean attacking = false;
    public Rectangle attackArea;
    public Entity[] friends = {Window.player};
    public Entity[] foes = {window.boxGuy};
    private BattleManager bm = new BattleManager(window, friends,foes);
    // new Rectangle(8, 16, window.tileSize, window.tileSize);
    public int hasKey = 0;
    public BufferedImage image = getImage();
    int tempScreenX = SCREEN_X;
    int tempScreenY = SCREEN_Y;
    public Weapon longSword = new Weapon(new Rectangle(0, 0, Window.tileSize * 3, Window.tileSize * 1),
            new Rectangle(0, 0, Window.tileSize * 3, Window.tileSize * 1),
            new Rectangle(0, 0, Window.tileSize * 1, Window.tileSize * 3),
            new Rectangle(0, 0, Window.tileSize, Window.tileSize * 3), "Longsword",
            30);

    public Player(Window window, KeyHandler keyH, String name, int level, int health,
            int maxHealth) {
        super(window, name, level, health, maxHealth);
        this.keyH = keyH;
        this.weapon = longSword;
        this.window = window;
        this.image = getImage();
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
        this.setWorldX(Window.tileSize * 23);
        this.setWorldY(Window.tileSize * 21);
        this.setSpeed(4);
        attacking = false;
    }

    public void damageMonster(int index) {
        if (index != 999 && Window.monster[index].invincible != true) {

            Window.monster[index].setHealth(Window.monster[index].getHealth() - 10);
            Window.monster[index].invincible = true;
            System.out.println("Hit detected");
            System.out.println(Window.monster[index].getHealth());
            if (Window.monster[index].getHealth() == 0) {
                Window.monster[index] = null;

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
            int monsterIndex = window.cDetection.checkAttackEntity(this, Window.monster);
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
        tempScreenX = SCREEN_X;
        tempScreenY = SCREEN_Y;
        switch (getDirection()) { // handles the direction that the sprite is facing.
            case "up":
                if (attacking == false) {
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                }
                if (attacking == true) {
                    tempScreenY = SCREEN_Y - Window.tileSize;
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
                    tempScreenX = SCREEN_X - Window.tileSize;
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
            switch (direction) {
                case "up":

                    this.attackArea = this.weapon.hitBoxUp;

                    break;
                case "down":

                    this.attackArea = this.weapon.hitBoxDown;
                    break;
                case "left":
                    this.attackArea = this.weapon.hitBoxLeft;
                    break;
                case "right":
                    this.attackArea = this.weapon.hitBoxRight;
                    break;
            }
            // Checking tile colllision
            collisionOn = false;
            window.cDetection.checkTile(this);

            // checking object collision
            int objIndex = window.cDetection.checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = window.cDetection.checkEntity(this, Window.npc);
            System.out.println(npcIndex);
            interactNPC(npcIndex);
            if (this.getName() != "player")
                window.cDetection.checkPlayer(this);

            int monsterIndex = window.cDetection.checkEntity(this, Window.monster);
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
                tempScreenY = getSCREEN_Y() + Window.tileSize;
                break;
            case "left":
                tempScreenX = SCREEN_X - attackArea.width;
                break;
            case "right":
                tempScreenX = SCREEN_X + Window.tileSize;
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
            if (keyH.ePressed == true) {
                System.out.println("hit!!");
                bm.startBattle();

            }
    }

    /**
     * @param i the index of the object in {@link window#items}
     */
    public void pickUpObject(int i) {
        if (i != 999) { // Must exclude the obejcts that we don't want picked
            String objectName = Window.items[i].getName();
            switch (objectName) {
                case "chest":
                    break;
                case "key":
                    Window.items[i] = null;
                    this.hasKey++;
                    break;
                case "lockeddoor":
                    if (this.hasKey > 0) {
                        Window.items[i] = null;
                        this.hasKey--;
                    }
                    break;

            }
        }
    }
}
