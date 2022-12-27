import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import java.awt.Graphics2D;

/**
 * UI
 */
public class UI {

    int commandNum = 1;
    public static String currentDialogue;
    Window window;
    Graphics2D g2;
    KeyHandler keyH = new KeyHandler();

    public void startMenu(Window window) {

    }

    public void draw(final Graphics2D g2) {
        this.g2 = g2;

        // PlayState
        if (Window.gameState == Window.startState) {
            drawStartScreen();

        }
        if (Window.gameState == Window.playState) {
            drawStatus();
        }
        // dialogue state
        if (Window.gameState == Window.dialogueState) {
            drawDialogueScreen();
        }
    }

    private void drawStartScreen() {
        System.out.println("hit");
        int x = 0;
        int y = 0;
        g2.setColor(new Color(157, 0, 255));
        g2.fillRect(x, y, Window.screenWidth, Window.screenHeight);
        x = Window.screenWidth / 3;
        y = Window.screenHeight / 3;
        g2.setFont(new Font("Helvetica", Font.PLAIN, 40));
        g2.setColor(Color.white);
        g2.drawString("Hags & Hexxes", x, y);
        y = Window.screenHeight / 3 + 100;
        g2.setFont(new Font("Helvetica", Font.PLAIN, 30));
        g2.drawString("New Game", x, y);
        System.out.println(commandNum);
        if (commandNum == 0)
            g2.drawString(">", x - 25, y);
        y = Window.screenHeight / 3 + 150;
        g2.drawString("Load Game", x, y);
        if (commandNum == 1)
            g2.drawString(">", x - 25, y);
        y = Window.screenHeight / 3 + 200;
        if (commandNum == 2)
            g2.drawString(">", x - 25, y);
        g2.drawString("Options", x, y);
        y = Window.screenHeight / 3 + 250;
        if (commandNum == 3)
            g2.drawString(">", x - 25, y);

        g2.drawString("Exit to Desktop", x, y);
        g2.dispose();
    }

    public void drawStatus() {
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
        g2.dispose();
    }

    /**
     * Handles the drawing of the health bar and the accompanying text. A helper
     * method for {@link}
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
        g2.drawString(String.format("%.0f/%.0f", Window.player.getHealth(), Window.player.getMaxHealth()), x, y - 5);
        g2.dispose();
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
        g2.dispose();
    }

    public void drawSubWindow(final int x, final int y, final int width, final int height) {
        g2.setColor(new Color(143, 12, 232, 200));
        g2.fillRoundRect(x, y, width, height, 40, 40);
        g2.setColor(new Color(26, 26, 26, 255));
        g2.setStroke(new BasicStroke(5));

        g2.drawRoundRect(x, y, width, height, 25, 25);

    }

    public static void selectOption(int commandNum) {
        if(Window.gameState == Window.startState){
            if(commandNum == 0)
                Window.gameState = Window.playState;
            else if(commandNum == 3){
                Window.frame.dispose();
                
            }
        }
    }

}
