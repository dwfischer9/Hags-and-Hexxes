import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * UI
 */
public class UI {
    private final Font hel_40 = new Font("Helvetica", Font.PLAIN, 40);
    private final Font hel_20 = new Font("Helvetica", Font.PLAIN, 20);
    private final Color menuColor = new Color(157, 0, 255, 250);
    int commandNum = 0;
    public String currentDialogue = "";
    int dialogueNumber = 0;
    private Graphics2D g2;
    private Window window;
    private KeyHandler keyH;

    public UI(Window window) {
        this.window = window;
        keyH = window.keyH;
    }

    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    public void draw(final Graphics2D g2) {
        this.g2 = g2;
        switch (window.gameState) {
            case Window.STARTSTATE:
                drawStartScreen();
                break;
            case Window.PLAYSTATE:
                drawStatus();
                break;
            case Window.DIALOGUESTATE:
                drawDialogueScreen();
                break;
            case Window.PAUSESTATE:
                drawPauseScreen();
                break;
            case Window.MENUSTATE:
                drawMenuScreen();
                break;
        }
    }

    private void naviagteMenus() {
        if (window.gameState == Window.STARTSTATE) {
            if (keyH.upPressed) {
                if (commandNum >= 0) {
                    commandNum--;
                }
            } else if (keyH.downPressed) {
                if (commandNum <= 3) {
                    commandNum++;
                }
            }
            if (keyH.spacePressed) {
                selectOption(commandNum);
            }
            if (commandNum < 0) {
                commandNum = 3;
            } else if (commandNum > 3) {
                commandNum = 0;
            }
        }

    }

    /**
     * Handles the drawing of the menu screen.
     */
    private void drawMenuScreen() {

        // Draw the menu window.
        int x = 50;
        int y = 50;
        // Draw the background of the menu
        g2.setColor(menuColor);
        g2.fillRoundRect(x, y, 800, 450, 20, 20);
        // Draw the outline of the menu
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, 800, 450, 20, 20);
        g2.setFont(hel_40);

        // Draw the character's stats and any current effects they are under.
        y = 100;
        x = 65;
        g2.drawString("Stats & Attributes", x, y);// draw title
        g2.setFont(hel_20);
        y += 50;
        g2.drawString(String.format("Health: %.0f/%.0f", Window.player.getHealth(), Window.player.getMaxHealth()), x,
                y); // draw health
        y += 25;
        g2.drawString(String.format("Defense: %d", Window.player.getDefense()), x, y);// draw defense
        y += 25;
        g2.drawString(String.format("Strength: %d", Window.player.getStrength()), x, y); // draw strength
        y += 25;
        g2.drawString(String.format("Speed: %d", Window.player.getSpeed()), x, y); // draw speed
        // Draw the dividing line between stats and inventory.
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(400, 70, 400, 480);
        // Draw the inventory window.
        x = 420;
        y = 70;
        int width = Window.TILESIZE + 5;
        int height = Window.TILESIZE + 5;
        Object[] arr = Window.player.inventory.keySet().toArray();
        for (int i = 1; i <= Window.player.inventorySize; i++) {
            g2.drawRoundRect(x, y, width, height, 3, 3);
            if (i <= arr.length && arr[i - 1] != null) {
                g2.drawImage(((Item) arr[i - 1]).getImage(), x, y, null); // draw the items present in the inventory.
                g2.drawString(capitalize(((Item) arr[i - 1]).getName()), x + 5, y + Window.TILESIZE + 30); // draw the
                                                                                                           // name of
                                                                                                           // the item.
            }
            if (i % 6 == 0) { // every 6 items, break the line and start a new row.
                x = 350;
                y += Window.TILESIZE + 40;
            }
            x += 70;
        }

    }

    private void drawPauseScreen() {
        int x = Window.SCREENWIDTH / 2 - 300;
        int y = Window.SCREENHEIGHT / 3 - 100;
        g2.setFont(hel_40);
        g2.setColor(menuColor);
        g2.fillRoundRect(x, y, 600, 450, 20, 20); // Draw the background of the menu
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, 600, 450, 20, 20); // Draw the outline of the menu
        g2.drawString("PAUSED", getXForCenteredString("PAUSED"), 130);
        g2.setFont(hel_20);
        x += 20;
        y += 100;
        // draw the command options and display a cursor on the current selection.
        g2.drawString("Resume", getXForCenteredString("Resume"), y);
        if (commandNum == 0) {
            g2.drawString(">", getXForCenteredString("Resume") - 20, y);
        }
        y += 50;
        g2.drawString("Options", getXForCenteredString("Options"), y);
        if (commandNum == 1) {
            g2.drawString(">", getXForCenteredString("Options") - 20, y);
        }
        y += 50;
        g2.drawString("Return to Main Menu", getXForCenteredString("Return to Main Menu"), y);
        if (commandNum == 2) {
            g2.drawString(">", getXForCenteredString("Return to Main Menu") - 20, y);
        }
        y += 50;
        g2.drawString("Exit to Desktop", getXForCenteredString("Exit to Desktop"), y);
        if (commandNum == 3) {
            g2.drawString(">", getXForCenteredString("Exit to Desktop") - 20, y);
        }
    }

    private void drawStartScreen() {

        int x = 0;
        int y = 0;
        g2.setColor(new Color(157, 0, 255));
        g2.fillRect(x, y, Window.SCREENWIDTH, Window.SCREENHEIGHT);
        x = Window.SCREENWIDTH / 3;
        y = Window.SCREENHEIGHT / 3;
        g2.setFont(new Font("Helvetica", Font.PLAIN, 40));
        g2.setColor(Color.white);
        g2.drawString("Hags & Hexxes", x, y);
        y = Window.SCREENHEIGHT / 3 + 100;
        g2.setFont(new Font("Helvetica", Font.PLAIN, 30));
        g2.drawString("New Game", x, y);
        // update keys in the menu.
        naviagteMenus();

        if (commandNum == 0)
            g2.drawString(">", x - 25, y);
        y = Window.SCREENHEIGHT / 3 + 150;
        g2.drawString("Load Game", x, y);
        if (commandNum == 1)
            g2.drawString(">", x - 25, y);
        y = Window.SCREENHEIGHT / 3 + 200;
        if (commandNum == 2)
            g2.drawString(">", x - 25, y);
        g2.drawString("Options", x, y);
        y = Window.SCREENHEIGHT / 3 + 250;
        if (commandNum == 3)
            g2.drawString(">", x - 25, y);

        g2.drawString("Exit to Desktop", x, y);
        g2.dispose();
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public void drawStatus() {
        final int width = Window.TILESIZE * 5;
        final int height = Window.TILESIZE * 1;
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
        final int x = Window.TILESIZE / 2;
        final int y = Window.TILESIZE / 2;
        final int width = Window.TILESIZE * 3;
        final int height = Window.TILESIZE / 4;
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
        int x = Window.TILESIZE * 2;
        int y = Window.TILESIZE / 2;
        final int width = Window.SCREENWIDTH - (Window.TILESIZE * 4);
        final int height = Window.SCREENHEIGHT - (Window.TILESIZE * 9);
        drawSubWindow(x, y, width, height);
        x += Window.TILESIZE;
        y += Window.TILESIZE;
        g2.setFont(new Font("Helvetica", Font.PLAIN, 20));
        drawString(g2, currentDialogue, x, y);
        g2.dispose();
    }

    public void drawSubWindow(final int x, final int y, final int width, final int height) {
        g2.setColor(new Color(143, 12, 232, 200));
        g2.fillRoundRect(x, y, width, height, 40, 40);
        g2.setColor(new Color(26, 26, 26, 255));
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, width, height, 25, 25);
    }

    public void selectOption(int commandNum) {
        if (window.gameState == Window.STARTSTATE || window.gameState == Window.PAUSESTATE) {
            if (commandNum == 0)
                window.gameState = Window.PLAYSTATE;
            else if (commandNum == 3) {
            }
        }
    }

    public int getXForCenteredString(String s) {
        int length = (int) g2.getFontMetrics().getStringBounds(s, g2).getWidth();
        int x = Window.SCREENWIDTH / 2 - length / 2;
        return x;
    }

}
