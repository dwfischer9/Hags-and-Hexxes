import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import java.awt.Graphics2D;

/**
 * UI
 */
public class UI {
    public void startMenu() {
        final JButton startGameButton = new JButton("Start Game");
        final JButton optionsButton = new JButton("Options");
        final JButton exitButton = new JButton("Exit to Desktop");
        startGameButton.setVisible(true);
        startGameButton.setBounds(window.screenWidth / 2 - 70, window.screenHeight / 2 - 20, 150, 20);
        window.add(startGameButton);
        optionsButton.setVisible(true);
        optionsButton.setBounds(window.screenWidth / 2 - 70, window.screenHeight / 2 + 10, 150, 20);
        window.add(optionsButton);
        exitButton.setVisible(true);
        exitButton.setBounds(window.screenWidth / 2 - 70, window.screenHeight / 2 + 50, 150, 20);
        window.add(exitButton);
    }

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
        if (window.gameState == window.startState) {
            drawStartScreen();

        }
        if (window.gameState == window.playState) {
            drawStatus();
        }
        // dialogue state
        if (window.gameState == window.dialogueState) {
            drawDialogueScreen();
        }
    }

    private void drawStartScreen() {
        System.out.println("hit");
        int x = 0;
        int y = 0;
        g2.setColor(new Color(157, 0, 255));
        g2.fillRect(x, y, window.screenWidth, window.screenHeight);
        x = window.screenWidth / 3;
        y = window.screenHeight / 3;
        g2.setFont(new Font("Helvetica", Font.PLAIN, 40));
        g2.setColor(Color.white);
        g2.drawString("Hags & Hexxes", x, y);

        g2.dispose();
    }

    public void drawStatus() {
        final int width = window.tileSize * 5;
        final int height = window.tileSize * 1;
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
     * Handles the drawing of the health bar and the accompanying text. A helper
     * method for {@link}
     */
    private void drawHealthBar() {
        final int x = window.tileSize / 2;
        final int y = window.tileSize / 2;
        final int width = window.tileSize * 3;
        final int height = window.tileSize / 4;
        g2.setColor(new Color(30, 0, 0));
        g2.fillRoundRect(x, y, width, height, 3, 3);
        g2.setColor(Color.green);
        g2.fillRect(x, y, (int) (width * ((window.player.getHealth() / window.player.getMaxHealth()))), height);
        g2.setColor(Color.white);
        g2.setFont(new Font("Helvetica", Font.PLAIN, 12));
        g2.drawString(String.format("%.0f/%.0f", window.player.getHealth(), window.player.getMaxHealth()), x, y - 5);
        g2.dispose();
    }

    public void drawDialogueScreen() {
        int x = window.tileSize * 2;
        int y = window.tileSize / 2;
        final int width = window.screenWidth - (window.tileSize * 4);
        final int height = window.screenHeight - (window.tileSize * 9);
        drawSubWindow(x, y, width, height);
        x += window.tileSize;
        y += window.tileSize;
        g2.drawString(currentDialogue, x, y);
        g2.dispose();
    }

    public void drawSubWindow(final int x, final int y, final int width, final int height) {
        g2.setColor(new Color(143, 12, 232, 200));
        g2.fillRoundRect(x, y, width, height, 40, 40);
        g2.setColor(new Color(26, 26, 26, 255));
        g2.setStroke(new BasicStroke(5));

        g2.drawRoundRect(x, y, width, height, 25, 25);

    }

}
