
import java.io.IOException;

public class Game {
    public Window window;
    public Game(){
    };
    public static void main(String[] args) throws IOException {
        Window window = new Window();
        window.setupGame();
    }

}
