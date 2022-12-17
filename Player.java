import java.io.IOException;

public class Player extends Entity {

    public static Player playerCharacter = new Player("Hero", Type.normal, new Move[] { Move.slap, Move.tackle }, 5, 90,
            90);

    public Player(String name, Type type, Move[] moves, int level, float health, float maxHealth) {
        super(name, type, moves, level, health, maxHealth);
        // TODO Auto-generated constructor stub
    }

    public static float attack(Move moveChoice, Entity target) throws IOException {
        float damage = moveChoice.getPower(); // return the damage dealt by the attack
        damageEnemy(damage);
        return damage;
    }
    public static void damagePlayer(float damage){
       playerCharacter.setHealth(playerCharacter.getHealth() - damage);
    }
}
