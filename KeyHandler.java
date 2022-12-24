import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {
    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    public boolean ePressed = false;
    public boolean escapePressed;

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (Window.gameState == Window.playState) {

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
                Window.gameState = Window.pauseState;
                System.out.println("Game is paused.");
            } 
            if (code == KeyEvent.VK_E) {
                ePressed = true;
                
            }
        }

        if (Window.gameState == Window.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                escapePressed = true;
                Window.gameState = Window.playState;
            }
        }
        else if (Window.gameState == Window.dialogueState) {
                if (code == KeyEvent.VK_E) {
                    ePressed = true;
                    Window.gameState = Window.playState;
                }
            }

        
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_E || code == KeyEvent.VK_S || code == KeyEvent.VK_A
                || code == KeyEvent.VK_S || code == KeyEvent.VK_D || code == KeyEvent.VK_R || code == KeyEvent.VK_UP
                || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT
                || code == KeyEvent.VK_ESCAPE)
            // For debugging, print the registered key.
            System.out.println(KeyEvent.getKeyText(code) + " Pressed");

    
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

    }

}
