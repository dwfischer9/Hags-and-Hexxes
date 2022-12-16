import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class Game{
    
    private static Creature creatureAlly = Creature.geoff;
    private static Creature creatureFoe = Creature.geon;

    private static void takeTurn(Creature currentCreature, Creature currentFoe) throws IOException{
        System.out.format("%s, HP: %.0f / %.0f\n", currentCreature.getName(),currentCreature.getHealth(),currentCreature.getMaxHealth());
        System.out.format("%s, HP: %.0f / %.0f\n", currentFoe.getName(),currentFoe.getHealth(),currentFoe.getMaxHealth());
        float damage = currentCreature.attack();
        currentFoe.setHealth(currentFoe.getHealth() - damage);
        System.out.format("%s took %.0f damage! HP: %.0f", currentFoe.getName(), damage,currentFoe.getHealth());
        if(currentFoe.getHealth() > 0)
            foeTurn(currentFoe,currentCreature);
        else 
          victory();
            
    }
    private static void foeTurn(Creature currentFoe, Creature currentCreature) throws IOException{
        System.out.println(currentFoe.toString());
        System.out.println(currentCreature.toString());
        float damage = currentFoe.foeAttack();
        currentCreature.setHealth(currentCreature.getHealth() - damage);
        System.out.format("%s took %.0f damage! HP: %.0f\n", currentCreature.getName(), damage,currentCreature.getHealth());
        if(currentCreature.getHealth() > 0)
            takeTurn(currentCreature, currentFoe);
        else 
          defeat();
    }
    private static void defeat(){
        System.out.println("You have been defeated!");

    }
    private static void victory(){
        System.out.format("The enemy %s has been defeated!\n", creatureFoe.getName());
    }


    public static void main(String[] args) throws IOException {
        Window window = new Window();
        window.initialize();
        window.addCreatureInfo(creatureAlly, window.allyHealth);
        window.addCreatureInfo(creatureFoe, window.foeHealth);
        takeTurn(creatureAlly, creatureFoe);
    }
    
}
