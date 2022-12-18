import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
public class KeyHandler implements KeyListener{
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
        // TODO Auto-generated method stub
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = true;
            System.out.println("W Pressed");
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
            System.out.println("S Pressed");
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
            System.out.println("A Pressed");
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
            System.out.println("D Pressed");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            this.upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            this.downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            this.leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            this.rightPressed = false;
        }
        
    }
    
}
