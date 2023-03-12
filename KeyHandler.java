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
    public boolean tabPressed = false;

    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        final int code = e.getKeyCode();

        if (Window.gameState == Window.PLAYSTATE) {
            switch (code) { // Key inputs.
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    upPressed = true;
                    Window.player.direction = "up";
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    downPressed = true;
                    Window.player.direction = "down";
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    leftPressed = true;
                    Window.player.direction = "left";
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    rightPressed = true;
                    Window.player.direction = "right";
                    break;
                case KeyEvent.VK_ESCAPE:
                    escapePressed = true;
                    Window.gameState = Window.PLAYSTATE;
                    break;
                case KeyEvent.VK_SPACE:
                    this.spacePressed = true;
                    break;
                case KeyEvent.VK_E:
                    this.ePressed = true;
                    break;
                case KeyEvent.VK_TAB:
                    this.tabPressed = true;
                    Window.gameState = Window.MENUSTATE;
                    break;
            }
        }
        // Battle state
        else if (Window.gameState == Window.BATTLESTATE) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
                upPressed = true;

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
                downPressed = true;
            if (code == KeyEvent.VK_SPACE)
                spacePressed = true;

        } else if (Window.gameState == Window.STARTSTATE) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                upPressed = true;
                if (Window.ui.commandNum >= 0)
                    Window.ui.commandNum--;
            }
            if (Window.ui.commandNum < 0) {
                Window.ui.commandNum = 3;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                downPressed = true;
                if (Window.ui.commandNum <= 3)
                    Window.ui.commandNum++;
            }
            if (Window.ui.commandNum > 3) {
                Window.ui.commandNum = 0;
            }
            if (code == KeyEvent.VK_SPACE) {
                spacePressed = true;
                UI.selectOption(Window.ui.commandNum);
            }
        } else if (Window.gameState == Window.PAUSESTATE) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                upPressed = true;
                if (Window.ui.commandNum >= 0)
                    Window.ui.commandNum--;
            }
            if (Window.ui.commandNum < 0) {
                Window.ui.commandNum = 3;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                downPressed = true;
                if (Window.ui.commandNum <= 3)
                    Window.ui.commandNum++;
            }
            if (Window.ui.commandNum > 3) {
                Window.ui.commandNum = 0;
            }
            if (code == KeyEvent.VK_SPACE) {
                spacePressed = true;
                UI.selectOption(Window.ui.commandNum);
            }
        } else if (Window.gameState == Window.DIALOGUESTATE) {
            if (code == KeyEvent.VK_E) {
                ePressed = true;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                Window.gameState = Window.PLAYSTATE;
            }
        } else if (Window.gameState == Window.PAUSESTATE) {
            if (code == KeyEvent.VK_ESCAPE) {
                escapePressed = true;
                Window.gameState = Window.PLAYSTATE;
            }
        } else if (Window.gameState == Window.MENUSTATE) {
            if (code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_TAB) {
                Window.gameState = Window.PLAYSTATE;
            }
        }

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_E || code == KeyEvent.VK_S
                || code == KeyEvent.VK_A
                || code == KeyEvent.VK_S || code == KeyEvent.VK_D || code == KeyEvent.VK_R ||
                code == KeyEvent.VK_UP
                || code == KeyEvent.VK_DOWN || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT
                || code == KeyEvent.VK_ESCAPE)
            // For debugging, print the registered key.
            System.out.println(KeyEvent.getKeyText(code) + " Pressed");

    }

    @Override
    public void keyReleased(final KeyEvent e) {
        final int code = e.getKeyCode();
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
        if (code == KeyEvent.VK_TAB)
            tabPressed = false;

    }

}
