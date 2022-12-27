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
    
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        if (Window.gameState == Window.playState) {

            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                upPressed = true;
                Window.player.direction = "up";
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                downPressed = true;
                Window.player.direction = "down";
                }
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
                leftPressed = true;
                Window.player.direction = "left";    
            }
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
                rightPressed = true;
                Window.player.direction = "right";
            }
            if (code == KeyEvent.VK_ESCAPE) {
                escapePressed = true;
                Window.gameState = Window.pauseState;
                System.out.println("Game is paused.");
            }
            if (code == KeyEvent.VK_SPACE) {
                this.spacePressed = true;
            }
            if (code == KeyEvent.VK_E) {
                this.ePressed = true;

            }
        }
        //Battle state 
        if (Window.gameState == Window.battleState) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                upPressed = true;
        
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
                downPressed = true; 
            if(code == KeyEvent.VK_SPACE)
            
            spacePressed = true;
        }
    }
        if (Window.gameState == Window.startState) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                upPressed = true;
                if(Window.ui.commandNum >= 0)
                    Window.ui.commandNum--;
                }
                if(Window.ui.commandNum<0){
                    Window.ui.commandNum = 3;
                }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                downPressed = true; 
                if(Window.ui.commandNum <= 3)
                    Window.ui.commandNum++;
                }if(Window.ui.commandNum >3){
                    Window.ui.commandNum = 0;
                }
            if(code == KeyEvent.VK_SPACE){
                spacePressed = true;
                UI.selectOption(Window.ui.commandNum);
            }
            }

        
        // Pause state
        if (Window.gameState == Window.pauseState) {
            if (code == KeyEvent.VK_ESCAPE) {
                escapePressed = true;
                Window.gameState = Window.playState;
            }
        }
        if (Window.gameState == Window.dialogueState) {
            if (code == KeyEvent.VK_E)
                Window.gameState = Window.playState;
        }
    

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_E || code == KeyEvent.VK_S
        || code == KeyEvent.VK_A
        || code == KeyEvent.VK_S || code == KeyEvent.VK_D || code == KeyEvent.VK_R ||
        code == KeyEvent.VK_UP
        || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_LEFT || code ==
        KeyEvent.VK_RIGHT
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
            this.ePressed = false;
        if (code == KeyEvent.VK_ESCAPE)
            escapePressed = false;
        if (code == KeyEvent.VK_SPACE)
            spacePressed = false;

    }

}
