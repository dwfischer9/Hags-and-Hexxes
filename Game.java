import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class Game {
 

    public static void foeTurn(Entity currentFoe, Player player) throws IOException {
        System.out.println(currentFoe.toString());
        System.out.println(player.toString());
        float damage = currentFoe.foeAttack();
        Player.damagePlayer(damage);
        Window.updatePlayerHealth();
        System.out.format("%s took %.0f damage! HP: %.0f\n", player.getName(), damage,
                player.getHealth());
        if (Player.testEnemy.getHealth() <= 0)
            victory();
    }

    private static void defeat() {
        System.out.println("You have been defeated!");

    }

    /**
     * When the enemy is defeated, hide the battle panel and start the victory screen.
     */
    protected static void victory() {
        System.out.format("The enemy %s has been defeated!\n", Entity.testEnemy.getName());
        Window.gamePanel.setVisible(false);
        Window.victoryPanel();
        
    }

    public static void main(String[] args) throws IOException {
        Window window = new Window();
        window.initialize();
        window.addCreatureInfo(Player.playerCharacter, Window.playerHealth);
        window.addCreatureInfo(Entity.testEnemy, Window.foeHealth);
    }

}
