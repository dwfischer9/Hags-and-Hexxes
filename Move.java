import java.util.concurrent.ThreadLocalRandom;

public class Move {
    public static Move tackle = new Move("Tackle",20,Type.normal);
    public static Move slap = new Move("Slap", 15, Type.normal);
    /**
     *The power of the move.
     */
    private float power;
    /**
     * The type of the move.
     */
    private Type type;
    public static <T> T getRandomElement(T[] arr){
        return arr[ThreadLocalRandom.current().nextInt(arr.length)];
    }
    /**
     *The name of the move.
     */
    private String moveName;
    public Move(String moveName,float power, Type type) {
        this.moveName = moveName;
        this.power = power;
        this.type = type;
    }
    public String getMoveName(){
        return this.moveName;
    }
    /**
     * @return the name of the move after setting.
     * @param newMoveName the new move name.
     */
    public String setMoveName(String newMoveName){
        if(newMoveName.length() >= 2 && newMoveName.length() <=20)
            this.moveName = newMoveName;
        return this.moveName;
    }
    public float getPower() {
        return power;
    }
    /**
     * @param newPower the new power.
     */
    public float setPower(float newPower) {
        if(newPower >= 0 && newPower <= 999)
            this.power = newPower;
        else
            System.err.println("The power is either negative or greater than 999.");
        return this.power;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return moveName+": [power=" + power + ", type=" + type + "]";
    }
}
