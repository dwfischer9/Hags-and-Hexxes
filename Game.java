import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class Game {

    private static void takeTurn(Entity currentCreature, Entity currentFoe) throws IOException {
        System.out.println(currentFoe.toString());
        System.out.println(currentFoe.toString());
        // if(currentFoe.getHealth() > 0)
        // //foeTurn(currentFoe,currentCreature);
        // else
        // victory();

    }

    public static void foeTurn(Entity currentFoe, Player player) throws IOException {
        System.out.println(currentFoe.toString());
        System.out.println(player.toString());
        float damage = currentFoe.foeAttack();
        Player.damagePlayer(damage);
        Window.updatePlayerHealth();
        System.out.format("%s took %.0f damage! HP: %.0f\n", player.getName(), damage,
                player.getHealth());
        if (player.getHealth() > 0)
            takeTurn(player, currentFoe);
        else
            defeat();
    }

    private static void defeat() {
        System.out.println("You have been defeated!");

    }

    protected static void victory() {
        System.out.format("The enemy %s has been defeated!\n", Entity.testEnemy.getName());
    }

    public static void main(String[] args) throws IOException {
        Window window = new Window();
        window.initialize();
        window.addCreatureInfo(Player.playerCharacter, Window.playerHealth);
        window.addCreatureInfo(Entity.testEnemy, Window.foeHealth);
        takeTurn(Player.playerCharacter, Entity.testEnemy);
    }

}
