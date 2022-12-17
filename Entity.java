import java.io.IOException;

public class Entity extends AbstractEntity {
    public static Entity geoff = new Entity("Geoff", Type.normal, new Move[] { Move.slap, Move.tackle }, 13, 40,
            40);
    public static Entity testEnemy = new Entity("TestEnemy", Type.normal, new Move[] { Move.slap, Move.tackle }, 13, 40, 40);
    private float maxHealth;

    /**
     * @param name      The name of the creature.
     * @param type      The type of creature.
     * @param moves     The moves the creature knows.
     * @param level     The level of the creature.
     * @param health    The current health of the creature.
     * @param maxHealth The maximum health of the creature.
     * 
     */
    public Entity(String name, Type type, Move[] moves, int level, float health, float maxHealth) {
        super(name, type, moves, level, health, maxHealth);
        // TODO Auto-generated constructor stub
    }

    public String toString() {
        return String.format("%s, HP: %.0f / %.0f\n", this.getName(), this.getHealth(), this.getMaxHealth()); // This is
                                                                                                              // the
                                                                                                              // display
                                                                                                              // of the
                                                                                                              // friendly
                                                                                                              // creature's
                                                                                                              // status.
    }
    /**
     * @param health must be a nonzero integer less than or equal to
     *               the maximum health of the creature.
     */
    public void setHealth(float newHealth) {
        if (newHealth <= this.maxHealth)
            this.health = newHealth;
        else
            System.err.println("The entered health is greater than the maximum health of the creature.");
        if (this.health < 0){
            this.health = 0;
            Game.victory();
        }
    }
    public static float attack(Move moveChoice) throws IOException {
        float damage = 0;
        damage = moveChoice.getPower(); // chose the attack of the chosen number, or the first move in case of invalid
                                        // input
        return damage;
    }

    @Override
    public void levelUp() {
        if (this.getLevel() < 100) {
            this.setLevel(this.setLevel(this.getLevel() + 1));
        }

    }

    @Override
    float foeAttack() throws IOException {
        float damage = 0;
        final int numMoves = this.getMoves().length;
        for (int i = 0; i < numMoves; i++) {
            System.out.println(i + ". " + this.getMoves()[i].getMoveName());
        }
        Move moveChoice = Move.getRandomElement(this.getMoves());

        damage = moveChoice.getPower(); // chose the attack of the chosen number, or the first move in case of
                                              // invalid input
        return damage;
    }

    public static void damageEnemy(float damage) {
        testEnemy.setHealth(testEnemy.getHealth() - damage);

        System.out.println("\n" + testEnemy.toString());
    }

}