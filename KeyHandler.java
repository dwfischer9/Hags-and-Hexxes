import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {
    private Window window;
    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    public boolean ePressed = false;
    public boolean escapePressed;
    public boolean spacePressed = false;
    public boolean tabPressed = false;

    public KeyHandler(Window window) {
        this.window = window;
    }

    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        final int code = e.getKeyCode();

        switch (code) { // Key inputs.
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                upPressed = true;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                downPressed = true;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;
            case KeyEvent.VK_ESCAPE:
                escapePressed = true;
                window.gameState = Window.PLAYSTATE;
                break;
            case KeyEvent.VK_SPACE:
                System.out.println("Space pressed");
                this.spacePressed = true;
                break;
            case KeyEvent.VK_E:
                this.ePressed = true;
                break;
            case KeyEvent.VK_TAB:
                this.tabPressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        final int code = e.getKeyCode();
        switch (code) { // Key inputs.
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                upPressed = false;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                downPressed = false;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;
            case KeyEvent.VK_ESCAPE:
                escapePressed = false;
                break;
            case KeyEvent.VK_SPACE:
                this.spacePressed = false;
                break;
            case KeyEvent.VK_E:
                this.ePressed = false;
                break;
            case KeyEvent.VK_TAB:
                this.tabPressed = false;
                break;
        }
    }

}
