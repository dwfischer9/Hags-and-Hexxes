public class BattleManager {
    
    public static Player player = Window.player;
    public static Entity entity = Window.testEntity;
    public KeyHandler keyH = Window.keyH;
    public boolean isPlayerTurn;
    public boolean waitingForAttack;

    public BattleManager() {
        isPlayerTurn = true;
        waitingForAttack = true;
    }
    public  void playerTurn(){
        System.out.println("Player's turn");
        this.isPlayerTurn = true;
        this.waitingForAttack = true;
        attack(player, entity);
    }
    

    public void attack(Entity user, Entity target){
        if(isPlayerTurn){
            while(this.waitingForAttack == true){
                try{System.out.println("Waiting for attack...");
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
            System.out.println("attack happened");

        }
    }



}
