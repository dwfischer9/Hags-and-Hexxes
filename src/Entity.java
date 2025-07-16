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
    private final String dialogues[] = new String[20];
    public boolean highlight = false;
    private Game game;

    public Entity(String name, int level, int maxHealth) {
        super(name, level, maxHealth);
        this.interactable = true;
    }
    
    public Entity(String name, int level, int maxHealth, Game game) {
        super(name, level, maxHealth);
        this.game = game;
        this.interactable = true;
    }

    public String[] getDialogues() {
        return dialogues;

    }

    public void setDialogues(String dialogues[]) {
        if (dialogues != null) {
            System.arraycopy(dialogues, 0, this.dialogues, 0, Math.min(dialogues.length, this.dialogues.length));
        }
    }

    public void update() {
        setAction();
        collisionOn = false;
        cDetection.checkTile(this);
        cDetection.checkPlayer(this);
        
        if (game != null) {
            cDetection.checkEntity(this, game.getNpcs());
            cDetection.checkEntity(this, game.getMonsters());
        }
        
        cDetection.checkObject(this, false);

        if (collisionOn == false && game != null && game.getGameState() == Game.PLAYSTATE) {
            switch (getDirection()) {
                case "up" -> this.setWorldY(this.getWorldY() - this.getSpeed());
                case "down" -> this.setWorldY(this.getWorldY() + this.getSpeed());
                case "left" -> this.setWorldX(this.getWorldX() - this.getSpeed());
                case "right" -> this.setWorldX(this.getWorldX() + this.getSpeed());
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
        if (actionLock == 50 && game != null && game.getGameState() == Game.PLAYSTATE) {
            Random rand = new Random();
            int i = rand.nextInt(100) + 1;// generate random number from 1 to 100
            if (i <= 25)
                setDirection("up");
            if (i > 25 && i <= 50)
                setDirection("down");
            if (i > 50 && i <= 75)
                setDirection("left");
            if (i > 75 && i < 100)
                setDirection("right");
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