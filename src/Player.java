import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Player extends Entity {
    public Entity currentInteraction;
    private final int playerStrength = 10;
    private boolean attacking = false;
    public int hasKey = 0;
    private final int defaultWorldX = 23;
    private final int defaultWorldY = 21;
    private final Rectangle defaultHitBox = new Rectangle(8, 16, 32, 32);
    public HashMap<Item, Integer> inventory = new HashMap<>(); // this hashMap will be used to store the
                                                                            // player's inventory. Item is the item,
                                                                            // Integer is the quantity of the item.
    public int inventorySize = 10; // default inventory size.
    int tempScreenX;
    int tempScreenY;
    private Window window;
    public Weapon longSword = new Weapon(new Rectangle(0, 0, Tile.TILESIZE * 3, Tile.TILESIZE * 1),
            new Rectangle(0, 0, Tile.TILESIZE * 3, Tile.TILESIZE * 1),
            new Rectangle(0, 0, Tile.TILESIZE * 1, Tile.TILESIZE * 3),
            new Rectangle(0, 0, Tile.TILESIZE, Tile.TILESIZE * 3), "Longsword",
            2, 7);

    public boolean isPlayer;

    
    public Player(String name, int level, int maxHealth) {
        super(name, level, maxHealth);
        this.weapon = longSword;
        this.isFriendly = true;
        this.attackArea = this.weapon.hitBoxLeft;
        this.image = getImage();
    }

    @Override
    public String toString() {
        return String.format("%s, HP: %.0f / %.0f\n", this.getName(), this.getHealth(), this.getMaxHealth());
    }

    public void setDefaultValues() {
        Game game = Game.getInstance();
        this.window = game.getWindow();
        this.setHitBox(defaultHitBox);
        this.setWorldX(Tile.TILESIZE * defaultWorldX);
        this.setWorldY(Tile.TILESIZE * defaultWorldY);
        this.setSpeed(6);
        attacking = false;
    }

    private void damageMonster(int i) {
        Game game = Game.getInstance();
        if (i != 999) {
            ArrayList<Entity> monsters = game.getMonsters();
            if (monsters != null && i < monsters.size() && monsters.get(i).invincible != true) {
                Entity entity = monsters.get(i);
                int damage = calculateDamage(entity);
                entity.setHealth(entity.getHealth() - damage);
                entity.invincible = true;
                // knockback
            }
        }
    }

    public void attacking() {
        Game game = Game.getInstance();
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 80) {
            spriteNum = 2;
            // Check for attack collisions without moving the player
            if (game != null) {
                damageMonster(window.getCollisionDetection().checkAttackEntity(this, game.getMonsters()));
            }
        }
        if (spriteCounter > 80) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    @Override
    public BufferedImage getImage() {
        switch (getDirection()) {
            case "up" -> {
                if (!attacking) {
                    image = spriteNum == 1 ? up1 : up2;
                } else {
                    image = spriteNum == 1 ? attackup1 : attackup2;
                }
            }
            case "down" -> {
                if (!attacking) {
                    image = spriteNum == 1 ? down1 : down2;
                } else {
                    image = spriteNum == 1 ? attackdown1 : attackdown2;
                }
            }
            case "left" -> {
                if (!attacking) {
                    image = spriteNum == 1 ? left1 : left2;
                } else {
                    image = spriteNum == 1 ? attackleft1 : attackleft2;
                }
            }
            case "right" -> {
                if (!attacking) {
                    image = spriteNum == 1 ? right1 : right2;
                } else {
                    image = spriteNum == 1 ? attackright1 : attackright2;
                }
            }
        }
        
        spriteCounter++; // switches between sprite 1 and 2 for the direction.
        if (spriteCounter > 90) { // serves as an idle animation. The player image
            // will change every 12 frames.
            spriteNum = spriteNum == 1 ? 2 : 1;
            spriteCounter = 0;
        }
        return image;
    }

    /**
     * Updates player movement and actions based on key input
     */
    private void updateKeys(KeyHandler keyH) {
        Game game = Game.getInstance();
        
        // Update legacy fields for compatibility
        keyH.updateLegacyFields();
        
        // Handle movement with support for diagonal movement
        String direction = keyH.getMovementDirection();
        if (!"none".equals(direction)) {
            setDirection(direction);
        } else {
            // Stop movement when no keys are pressed
            setDirection("none");
        }
        
        // Handle menu toggle
        if (keyH.isPressed("INVENTORY")) {
            if (game != null && game.getGameState() == Game.PLAYSTATE) {
                game.setGameState(Game.MENUSTATE);
            }
        }
    }

    public void update(KeyHandler keyH) {
        Game game = Game.getInstance();
        if (game != null && game.getGameState() == Game.PLAYSTATE) {
            updateKeys(keyH);
            if (invincible == true) {
                invincibleCounter++;
                if (invincibleCounter > 60) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
            if (keyH.isPressed("ATTACK")) {
                attacking = true;
            }
        }
        if (getHealth() == 0 && game != null) {
            game.setGameState(Game.GAMEOVERSTATE); // head to the game over screen.
        }
        
        // Update attack area based on direction (regardless of attacking state)
        switch (getDirection()) {
            case "up" -> attackArea = weapon.hitBoxUp;
            case "down" -> attackArea = weapon.hitBoxDown;
            case "left" -> attackArea = weapon.hitBoxLeft;
            case "right" -> attackArea = weapon.hitBoxRight;
        }
        
        // Handle attack animation
        if (attacking == true) {
            attacking();
        }
        
        // Always check collisions and allow movement (even while attacking)
        collisionOn = false;
        window.getCollisionDetection().checkTile(this);

        // checking object collision
        pickUpObject(window.getCollisionDetection().checkObject(this, true));
        if (game != null) {
            interactNPC(window.getCollisionDetection().checkEntity(this, game.getNpcs()), keyH);
            if (!"player".equals(getName())) // we don't want to check if the player is colliding with itself.
                window.getCollisionDetection().checkPlayer(this);
            contactMonster(window.getCollisionDetection().checkEntity(this, game.getMonsters()));
        }
        moveEntity();
    }

    @Override
    public void draw(Graphics2D g2) {
        // DEBUG - AttackArea
        tempScreenX = Window.SCREEN_X + attackArea.x;
        tempScreenY = Window.SCREEN_Y + attackArea.y;
        switch (getDirection()) {
            case "up" -> tempScreenY = Window.SCREEN_Y - attackArea.height;
            case "down" -> tempScreenY = Window.SCREEN_Y + Tile.TILESIZE;
            case "left" -> tempScreenX = Window.SCREEN_X - attackArea.width;
            case "right" -> tempScreenX = Window.SCREEN_X + Tile.TILESIZE;
        }
        
        g2.drawImage(getImage(), Window.SCREEN_X, Window.SCREEN_Y, null);

        Rectangle attackAreaRect = new Rectangle(tempScreenX, tempScreenY, attackArea.width, attackArea.height);
        g2.setColor(Color.PINK);
        g2.setStroke(new BasicStroke(3));
        g2.draw(attackAreaRect); // draw attack area for debugging
    }

    private void interactNPC(int i, KeyHandler keyH) {
        Game game = Game.getInstance();
        if (game != null && i != 999) {
            if (keyH.isPressed("INTERACT")) {
                ArrayList<Entity> npcs = game.getNpcs();
                if (npcs != null && i < npcs.size()) {
                    currentInteraction = npcs.get(i);
                    // Start dialogue using the new dialogue system
                    if (game.getDialogueSystem() != null) {
                        game.getDialogueSystem().startDialogue(currentInteraction);
                    }
                }
                keyH.resetKey("INTERACT"); // Reset to prevent multiple triggers
            }
        }
    }

    /**
     * @param i the index of the object in {@link window#items}
     */
    private void pickUpObject(Item item) {

        if (item != null) {
            switch (item.getName()) {
                case "Chest" -> {
                }
                case "Key", "Key_2" -> {
                    inventory.put(new Item(item), 1);
                    item.worldX = -999;
                    System.out.println("Key obtained.");
                }
                case "Locked Door" -> {
                    // Check if player has any key in inventory
                    boolean playerHasKey = false;
                    for (Item inventoryItem : inventory.keySet()) {
                        if ("Key".equals(inventoryItem.getName()) || "Key_2".equals(inventoryItem.getName())) {
                            playerHasKey = true;
                            break;
                        }
                    }
                    
                    if (playerHasKey) {
                        // Remove one key from inventory
                        for (Item inventoryItem : inventory.keySet()) {
                            if ("Key".equals(inventoryItem.getName()) || "Key_2".equals(inventoryItem.getName())) {
                                int quantity = inventory.get(inventoryItem);
                                if (quantity > 1) {
                                    inventory.put(inventoryItem, quantity - 1);
                                } else {
                                    inventory.remove(inventoryItem);
                                }
                                break;
                            }
                        }

                        // Remove the locked door from the world
                        item.worldX = -999;
                        System.out.println("Door unlocked!");
                    }
                }
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
            damage = (int) ((playerStrength * (2 * weaponRoll)) / (monster.defense));
            System.out.println(damage + "was dealt to " + monster.getName());
        }
        return damage;
    }

    /**
     * @param monster - the entity that the player is attacking
     * @return hit - if the attack hit or not.
     */
    private boolean checkHit(Entity monster) {
        boolean hit = true; // currently, every attack always hits.
        return hit;
    }

    public int getStrength() {
        return playerStrength;
    }

    public int getDefense() {
        return defense;
    }
}
