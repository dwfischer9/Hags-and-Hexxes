import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {
    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    public boolean ePressed = false;
    public boolean escapePressed;
    public boolean spacePressed = false;
    private Window window;
    public KeyHandler(Window window){
        this.window = window;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (window.gameState == window.playState) {

            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
                upPressed = true;
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
                downPressed = true;
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT)
                leftPressed = true;
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT)
                rightPressed = true;

            if (code == KeyEvent.VK_ESCAPE) {
                escapePressed = true;
                window.gameState = window.pauseState;
                System.out.println("Game is paused.");
            }
            if (code == KeyEvent.VK_SPACE) {
                this.spacePressed = true;
            }
            if (code == KeyEvent.VK_E) {
                ePressed = true;

            }
        }
        // Pause state
        if (window.gameState == window.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                escapePressed = true;
                window.gameState = window.playState;
            }
        }
        if (window.gameState == window.dialogueState) {
            if (code == KeyEvent.VK_E)
                window.gameState = window.playState;
        }

        // if (code == KeyEvent.VK_W || code == KeyEvent.VK_E || code == KeyEvent.VK_S
        // || code == KeyEvent.VK_A
        // || code == KeyEvent.VK_S || code == KeyEvent.VK_D || code == KeyEvent.VK_R ||
        // code == KeyEvent.VK_UP
        // || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_LEFT || code ==
        // KeyEvent.VK_RIGHT
        // || code == KeyEvent.VK_ESCAPE)
        // // For debugging, print the registered key.
        // System.out.println(KeyEvent.getKeyText(code) + " Pressed");

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
            upPressed = false;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
            downPressed = false;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT)
            leftPressed = false;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT)
            rightPressed = false;
        if (code == KeyEvent.VK_E)
            ePressed = false;
        if (code == KeyEvent.VK_ESCAPE)
            escapePressed = false;
        if (code == KeyEvent.VK_SPACE)
            spacePressed = false;

    }

}
