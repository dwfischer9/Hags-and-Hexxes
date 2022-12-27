import java.awt.Graphics2D;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class BattleManager {
    //** A simple Comparator class to help in determining turn order. A higher speed is always beter.**/
    private class CompareSpeed implements Comparator<Entity> {

        @Override
        public int compare(final Entity entity1, final Entity entity2) {
            if (entity1.getSpeed() > entity2.getSpeed()) {
                return -1;
            } else if (entity1.getSpeed() == entity2.getSpeed()) {
                return 0;
            } else
                return 1;

        }

    }

    private final Entity[] friendly, enemy;
    private final Window window;
    private final CompareSpeed comparator = new CompareSpeed();
    public BattleManager(final Window window, final Entity[] friendly, final Entity[] enemy) {
        this.window = window;
        this.friendly = friendly;
        this.enemy = enemy;
    }

    public void draw(final Graphics2D g2) {

        g2.dispose();
    }

    public void startBattle() {
        // create a queue for each team based on the speed of their entities

        final Entity[] currentEntity = determineTurnOrder();
        System.out.println(currentEntity[0] + " is going first!");
        takeTurn(currentEntity);
    }

    public void update() {

    }

    /**
     * Performs the actions that a turn consists of. Attacking / using an item, or
     * attempting to flee. Utilizes a priority queue sorted by speed of the entities.
     * 
     * @param currentEntity - the entity whose turn it is
     */
    void takeTurn(final Entity[] currentEntity) {
        final Queue<Entity> turnOrder = new PriorityQueue<Entity>(comparator);
        for (final Entity entity : currentEntity) {
            turnOrder.add(entity);
        }
        while(!turnOrder.isEmpty()){
            System.out.println(turnOrder.remove().getSpeed());
        }

    }

    /**
     * Using the speed stat average of each team, determine which team will act
     * first.
     * 
     * @return Friendly: It is the player's team's turn
     *         <li>Enemy: It is the enemy's team's turn.</li>
     *         <li>The method will return either team at random in the event of a
     *         speed tie.</li>
     */
    private Entity[] determineTurnOrder() {
        int speedAvgEnemy = 0, speedAvgPlayer = 0;
        final Entity[][] choice = { friendly, enemy };
        for (final Entity entity : enemy)
            speedAvgEnemy += entity.getSpeed();
        speedAvgEnemy /= enemy.length;
        for (final Entity entity : friendly)
            speedAvgPlayer += entity.getSpeed();
        speedAvgPlayer /= friendly.length;

        if (speedAvgEnemy == speedAvgPlayer)
            return choice[(int) (System.currentTimeMillis() % choice.length)]; // In a speed tie, flip a coin
        return speedAvgPlayer > speedAvgEnemy ? friendly : enemy; // return 0 if the player's team's average speed is
                                                                  // greater than the enemy's. Return 1 otherwise.
    }
}
