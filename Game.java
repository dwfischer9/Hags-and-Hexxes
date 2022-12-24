
import java.io.IOException;

public class Game {

    public static void main(String[] args) throws IOException {
        Window window = new Window();
        window.setupGame();
        Window.player.setDefaultValues();
        window.initialize();
    }

}
