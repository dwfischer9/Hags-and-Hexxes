public class Quest {
    private int reward;
    private String name;
    public Window window;
    public Player player = window.player;
    private String description;
    public static Entity justKilled;
    private boolean active;
    private boolean completed;
    private int quantityHeld;

    public Quest(Window window, int reward, String name, String description, Item itemNeeded, int quantityNeeded) {
        this.reward = reward;
        this.completed = false;
        this.window = window;
        this.name = name;
        this.description = description;
        if (player.inventory.get(itemNeeded) == null) {
            quantityHeld = 0;
        } else
            quantityHeld = player.inventory.get(itemNeeded);
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
