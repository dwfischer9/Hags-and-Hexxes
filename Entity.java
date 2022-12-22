import java.io.IOException;
import java.awt.Rectangle;
/**
 * This class serves to handle the various entities that are not the player.
 * Extends the {@link AbstractEntity} class.
 */

 
public class Entity extends AbstractEntity {
    public Rectangle hitBox = new Rectangle(0, 0, 48, 48);
    public int hitBoxDefeaultX, hitBoxDefeaultY;

    public Entity(String name, Type normal, Move[] moves, int level, float health, float maxHealth) {

        super(name, Type.normal, new Move[] { Move.slap, Move.tackle }, 5, (float) 90, (float) 90);
    }
    // abstract float attack(Move moveChoice) throws IOException;
    public void levelUp() {
    }
    public float foeAttack() throws IOException {
        return health;
    }

}