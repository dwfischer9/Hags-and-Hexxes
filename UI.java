import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

/**
 * UI
 */
public class UI {
    public static String currentDialogue;
    Window window;
    Graphics2D g2;

    public UI(Window window) {
        this.window = window;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetica", Font.PLAIN, 40));
        // PlayState
        if (Window.gameState == Window.playState) {

        }
        // dialogue state
        if (Window.gameState == Window.dialogueState) {
            drawDialogueScreen();
        }
    }

    public void drawDialogueScreen() {
        int x = Window.tileSize * 2;
        int y = Window.tileSize / 2;
        int width = Window.screenWidth - (Window.tileSize * 4);
        int height = Window.screenHeight - (Window.tileSize * 9);
        drawSubWindow(x, y, width, height);
        x+= Window.tileSize;
        y+= Window.tileSize;
        g2.drawString(currentDialogue,x,y);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        g2.setColor(new Color(143,12,232,200));
        g2.fillRoundRect(x, y, width, height, 40, 40);
        g2.setColor(new Color(26, 26, 26, 255));
        g2.setStroke(new BasicStroke(5));

        g2.drawRoundRect(x, y, width, height, 25, 25);
        
    }

}
