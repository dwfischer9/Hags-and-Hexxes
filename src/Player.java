import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

class Player extends Entity {
    public Entity currentInteraction;
    private int strength = 10;
    private boolean attacking = false;
    public Rectangle attackArea;
    public int hasKey = 0;
    public HashMap<Item, Integer> inventory = new HashMap<Item, Integer>(); // this hashMap will be used to store the
                                                                            // player's inventory. Item is the item,
                                                                            // Integer is the quantity of the item.
    public int inventorySize = 10; // default inventory size.
    public BufferedImage image = getImage();
    int tempScreenX = Window.SCREEN_X;
    int tempScreenY = Window.SCREEN_Y;
    private Window window;
    public Weapon longSword = new Weapon(new Rectangle(0, 0, Tile.TILESIZE * 3, Tile.TILESIZE * 1),
            new Rectangle(0, 0, Tile.TILESIZE * 3, Tile.TILESIZE * 1),
            new Rectangle(0, 0, Tile.TILESIZE * 1, Tile.TILESIZE * 3),
            new Rectangle(0, 0, Tile.TILESIZE, Tile.TILESIZE * 3), "Longsword",
            2, 7);

    public boolean isPlayer;

    public Player(String name, int level, int health,
            int maxHealth) {
        super(name, level, health, maxHealth);
        this.weapon = longSword;
        this.image = getImage();
        this.isFriendly = true;
        this.attackArea = this.weapon.hitBoxLeft;
        setDefaultValues();
    }

    public String toString() {
        return String.format("%s, HP: %.0f / %.0f\n", this.getName(), this.getHealth(), this.getMaxHealth());
    }

    public void setDefaultValues() {
        this.hitBoxDefeaultY = 16;
        this.hitBoxDefeaultX = 8;
        this.window = Game.window;
        this.setHitBox(new Rectangle(8, 16, 32, 32));
        this.setWorldX(Tile.TILESIZE * 23);
        this.setWorldY(Tile.TILESIZE * 21);
        this.setSpeed(6);
        attacking = false;
    }

    private void damageMonster(int i) {

        if (i != 999 && Game.monster.get(i).invincible != true) {

            Entity entity = Game.monster.get(i);
            int damage = calculateDamage(entity);
            entity.setHealth(entity.getHealth() - damage);
            entity.invincible = true;
            // knockback

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
            int currentWorldX = getWorldX();
            int currentWorldY = getWorldY();
            int hitBoxWidth = getHitBox().width;
            int hitBoxHeight = getHitBox().height;
            // adjust hitbox for the attack area
            switch (direction) {
                case "up":
                    setWorldY(getWorldY() - attackArea.height);
                    break;
                case "down":
                    setWorldY(getWorldY() + attackArea.height);
                    break;
                case "left":
                    setWorldX(getWorldX() - attackArea.width);
                    break;
                case "right":
                    setWorldX(getWorldX() + attackArea.width);
                    break;
            }
            // check collision
            damageMonster(window.cDetection.checkAttackEntity(this, Game.monster));
            this.setWorldX(currentWorldX);
            this.setWorldY(currentWorldY);
            getHitBox().width = hitBoxWidth;
            getHitBox().height = hitBoxHeight;
        }
        if (spriteCounter > 80) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public BufferedImage getImage() {
        tempScreenX = Window.SCREEN_X;
        tempScreenY = Window.SCREEN_Y;
        switch (direction) { // handles the direction that the sprite is facing.
            case "up":
                if (!attacking) {
                    if (spriteNum == 1)
                        image = up1;
                    if (spriteNum == 2)
                        image = up2;
                } else {
                    tempScreenY = Window.SCREEN_Y - Tile.TILESIZE;
                    if (spriteNum == 1)
                        image = attackup1;
                    if (spriteNum == 2)
                        image = attackup2;
                }
                break;
            case "down":
                if (!attacking) {
                    if (spriteNum == 1)
                        image = down1;
                    if (spriteNum == 2)
                        image = down2;
                } else {
                    if (spriteNum == 1)
                        image = attackdown1;
                    if (spriteNum == 2)
                        image = attackdown2;
                }

                break;
            case "left":
                if (!attacking) {
                    if (spriteNum == 1)

                        if (spriteNum == 2)
                            image = left2;
                } else {
                    tempScreenX = Window.SCREEN_X - Tile.TILESIZE;
                    if (spriteNum == 1)
                        image = attackleft1;
                    if (spriteNum == 2)
                        image = attackleft2;
                }

                break;
            case "right":
                if (!attacking) {
                    if (spriteNum == 1)
                        image = right1;
                    if (spriteNum == 2)
                        image = right2;
                } else {
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

    /**
     * Checks if if any bound keys have been pressed, and if so, changes the
     * property direction of the player object
     * 
     * @param keyH
     */
    private void updateKeys(KeyHandler keyH) {
        if (keyH.upPressed) {
            direction = "up";
        } else if (keyH.downPressed) {
            direction = "down";
        } else if (keyH.leftPressed) {
            direction = "left";
        } else if (keyH.rightPressed) {
            direction = "right";
        } else if (keyH.tabPressed) {
            if (Game.getGameState() == Game.PLAYSTATE)
                Game.setGameState(Game.MENUSTATE);
            ;
        }

    }

    public void update(KeyHandler keyH) {
        if (Game.getGameState() == Game.PLAYSTATE) {
            updateKeys(keyH);
            if (invincible == true) {
                invincibleCounter++;
                if (invincibleCounter > 60) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
            if (keyH.spacePressed == true) {
                attacking = true;
            }
        }
        if (getHealth() == 0) {
            Game.setGameState(Game.GAMEOVERSTATE); // head to the game over screen.
        }
        if (attacking == true) {
            attacking();
        } else {
            switch (direction) {
                case "up":
                    attackArea = weapon.hitBoxUp;
                    break;
                case "down":
                    attackArea = weapon.hitBoxDown;
                    break;
                case "left":
                    attackArea = weapon.hitBoxLeft;
                    break;
                case "right":
                    attackArea = weapon.hitBoxRight;
                    break;
            }
            // Checking tile colllision
            collisionOn = false;
            window.cDetection.checkTile(this);

            // checking object collision
            pickUpObject(window.cDetection.checkObject(this, true));
            interactNPC(window.cDetection.checkEntity(this, Game.npc), keyH);
            if (getName() != "player") // we don't want to check if the player is colliding with itself.
                window.cDetection.checkPlayer(this);
            contactMonster(window.cDetection.checkEntity(this, Game.monster));

            // if collision is false, player can moves
            // accordingly. should multiply speed by half for each key pressed
            if (collisionOn == false && keyH.spacePressed == false) {
                switch (direction) {
                    case "up":
                        setWorldY(getWorldY() - getSpeed());
                        break;
                    case "down":
                        setWorldY(getWorldY() + getSpeed());
                        break;
                    case "left":
                        setWorldX(getWorldX() - getSpeed());
                        break;
                    case "right":
                        setWorldX(getWorldX() + getSpeed());
                        break;
                    default:
                        break;
                }
                direction = "none"; // allows the player to stop once input key has been relesased. Without this
                                    // line the player keeps moving.
            }
        }
    }

    public void draw(Graphics2D g2) {
        // DEBUG
        // AttackArea
        tempScreenX = Window.SCREEN_X + attackArea.x;
        tempScreenY = Window.SCREEN_Y + attackArea.y;
        switch (direction) {
            case "up":
                tempScreenY = Window.SCREEN_Y - attackArea.height;
                break;
            case "down":
                tempScreenY = Window.SCREEN_Y + Tile.TILESIZE;
                break;
            case "left":
                tempScreenX = Window.SCREEN_X - attackArea.width;
                break;
            case "right":
                tempScreenX = Window.SCREEN_X + Tile.TILESIZE;
                break;

        }
        g2.setColor(Color.red);
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(tempScreenX, tempScreenY, attackArea.width, attackArea.height);
        // Draw player
        g2.drawImage(getImage(), tempScreenX, tempScreenY, null);

    }

    private void interactNPC(int i, KeyHandler keyH) {
        if (i != 999) {
            if (keyH.ePressed ) {
                Game.setGameState(Game.DIALOGUESTATE);
                currentInteraction = Game.npc.get(i);
                keyH.ePressed = false;
            }
        }
    }

    /**
     * @param i the index of the object in {@link window#items}
     */
    private void pickUpObject(Item item) {

        if (item != null) {
            switch (item.getName()) {
                case "Chest":
                    break;
                case "Key":
                case "Key_2":
                    inventory.put(new Item(item), 1);
                    item.worldX = -999;
                    System.out.println("Key obtained.");
                    break;
                case "Locked Door":
                    if (inventory.containsKey(item))
                        break;
            }
        }

    }

    private void contactMonster(int index) {
        if (index != 999 && invincible == false) {
            invincible = true;
            setHealth(getHealth() - 10);
        }
    }

    /**
     * Upon attacking a monster, calculate the damage that the player will deal to
     * the monster.
     * 
     * @param monster - the monster that the player is hitting
     * @return damage - the damage to the monster
     */
    private int calculateDamage(Entity monster) {
        int damage = 0;
        if (checkHit(monster)) {
            int weaponRoll = ThreadLocalRandom.current().nextInt(weapon.damageLBound, weapon.damageUBound + 1);
            damage = (int) ((strength * (2 * weaponRoll)) / (monster.defense));
            System.out.println(damage);
        }
        return damage;
    }

    /**
     * @param monster - the entity that the player is attacking
     * @return hit - if the attack hit or not.
     */
    private boolean checkHit(Entity monster) {
        boolean hit = true;
        return hit;
    }

    public int getStrength() {
        return strength;
    }

    public int getDefense() {
        return defense;
    }
}
