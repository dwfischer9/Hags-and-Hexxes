import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class KeyHandler implements KeyListener {
    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
   
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = true;
            System.out.println("W Pressed");
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
            System.out.println("S Pressed");
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
            System.out.println("A Pressed");
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
            System.out.println("D Pressed");
        }
        if(code == KeyEvent.VK_ESCAPE){
            System.out.println("Escape pressed");
            if(Window.gameState == Window.pauseState)
                Window.gameState = Window.playState;
            else
                Window.gameState = Window.pauseState;
                
                
                
                ;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

    }

}
