import java.io.IOException;
import java.util.Random;

/**
 * This class serves to handle the various entities that are not the player.
 * Extends the {@link AbstractEntity} class.
 */

public class Entity extends AbstractEntity {
    public int actionLock = 0;
    public boolean isMonster;
    public Weapon weapon;
    public String currentDialogue = "";
    public int dialogueCounter = 0;
    public boolean interactable;
    public int hitBoxDefeaultX = 8, hitBoxDefeaultY = 16;
    String dialogues[] = new String[20];
    public boolean highlight = false;

    public Entity(Window window, String name, int level, int health, int maxHealth) {
        super(window, name, 5, 90, 90);
        this.interactable = true;
    }

    public void update() {
        setAction();

        collisionOn = false;
        window.cDetection.checkTile(this);

        window.cDetection.checkPlayer(this);

        window.cDetection.checkEntity(this, window.npc);
        window.cDetection.checkEntity(this, window.monster);
        window.cDetection.checkObject(this, false);
        if (collisionOn == false && Window.gameState == Window.PLAYSTATE) {
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
        /// invincible timer
        if (this.invincible == true) {
            this.invincibleCounter++;
            if (this.invincibleCounter > 60) {
                this.invincible = false;
                this.invincibleCounter = 0;

            }
        }
    }

    public void setAction() {
        actionLock++;
        if (actionLock == 50 && Window.gameState == Window.PLAYSTATE) {
            Random rand = new Random();
            int i = rand.nextInt(100) + 1;// generate random number from 1 to 100
            if (i <= 25)
                this.direction = "up";
            if (i > 25 && i <= 50)
                this.direction = "down";
            if (i > 50 && i <= 75)
                this.direction = "left";
            if (i > 75 && i < 100)
                this.direction = "right";
            actionLock = 0;

        }
    }

    // abstract float attack(Move moveChoice) throws IOException;
    public void levelUp() {
    }

    public float foeAttack() throws IOException {
        return this.getHealth();
    }

}