import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * UI
 */
public class UI {
    public static String currentDialogue;
    Window window;
    Graphics2D g2;

    public UI(final Window window) {
        this.window = window;
    }

    public void draw(final Graphics2D g2) {
        this.g2 = g2;

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetica", Font.PLAIN, 40));
        // PlayState
        if (Window.gameState == Window.playState) {
            drawStatus();
        }
        // dialogue state
        if (Window.gameState == Window.dialogueState) {
            drawDialogueScreen();
        }
    }

    public void drawStatus() {
        final int x = Window.tileSize * 1;
        final int y = Window.tileSize * 1;
        final int width = Window.tileSize * 5;
        final int height = Window.tileSize * 1;
        final int[] xPts = { 1, width, width - height, 1 };
        final int[] yPts = { 1, 1, height, height };
        g2.setColor(new Color(121, 5, 232, 200));
        g2.fillPolygon(xPts, yPts, 4);
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.black);
        g2.drawPolygon(xPts, yPts, 4);
        drawHealthBar();
    }

    /**
     * Handles the drawing of the health bar and the accompanying text. A helper method for {@link}
     */
    private void drawHealthBar() {
        final int x = Window.tileSize / 2;
        final int y = Window.tileSize / 2;
        final int width = Window.tileSize * 3;
        final int height = Window.tileSize / 4;
        g2.setColor(new Color(30, 0, 0));
        g2.fillRoundRect(x, y, width, height, 3, 3);
        g2.setColor(Color.green);
        g2.fillRect(x, y, (int) (width * ((Window.player.getHealth() / Window.player.getMaxHealth()))), height);
        g2.setColor(Color.white);
        g2.setFont(new Font("Helvetica", Font.PLAIN, 12));
        g2.drawString(String.format("%.0f/%.0f", Window.player.getHealth(), Window.player.getMaxHealth()), x, y -5);
    }

    public void drawDialogueScreen() {
        int x = Window.tileSize * 2;
        int y = Window.tileSize / 2;
        final int width = Window.screenWidth - (Window.tileSize * 4);
        final int height = Window.screenHeight - (Window.tileSize * 9);
        drawSubWindow(x, y, width, height);
        x += Window.tileSize;
        y += Window.tileSize;
        g2.drawString(currentDialogue, x, y);
    }

    public void drawSubWindow(final int x, final int y, final int width, final int height) {
        g2.setColor(new Color(143, 12, 232, 200));
        g2.fillRoundRect(x, y, width, height, 40, 40);
        g2.setColor(new Color(26, 26, 26, 255));
        g2.setStroke(new BasicStroke(5));

        g2.drawRoundRect(x, y, width, height, 25, 25);

    }

}
