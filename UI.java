import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * UI
 */
public class UI {

    private final Font hel_12 = new Font("Helvetica", Font.PLAIN, 12);
    private final Font hel_20 = new Font("Helvetica", Font.PLAIN, 20);
    private final Font hel_40 = new Font("Helvetica", Font.PLAIN, 40);
    private final Font hel_30 = new Font("Helvetica", Font.PLAIN, 30);
    private final Color menuColor = new Color(157, 80, 255, 250);
    int commandNum = 0;
    public String currentDialogue = "";
    int dialogueNumber = 0;
    int menuItem = 0;
    private Graphics2D g2;
    private Window window;

    public UI(Window window) {
        this.window = window;
    }

    /**
     * @param text
     */
    private void drawString(Graphics g, String text, int x, int y) {

        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    public void draw(final Graphics2D g2) {
        this.g2 = g2;
        switch (Game.gameState) {
            case Game.STARTSTATE:
                drawStartScreen();
                break;
            case Game.PLAYSTATE:
                drawStatus();
                break;
            case Game.DIALOGUESTATE:
                drawDialogue();
                break;
            case Game.PAUSESTATE:
                drawPause();
                break;
            case Game.MENUSTATE:
                drawMenu();
                break;
            case Game.GAMEOVERSTATE:
                drawGameOver();
        }
    }

    private void naviagteMenus() {
        switch (Game.gameState) {
            case Game.STARTSTATE:
                if (Game.keyH.upPressed) {
                    Game.keyH.upPressed = false;
                    if (commandNum >= 0) {
                        commandNum--;
                    }
                } else if (Game.keyH.downPressed) {
                    Game.keyH.downPressed = false;
                    if (commandNum <= 3) {
                        commandNum++;
                    }
                }
                if (Game.keyH.spacePressed) {
                    selectOption(commandNum);
                }
                if (commandNum < 0) {
                    commandNum = 3;
                } else if (commandNum > 3) {
                    commandNum = 0;
                }
                break;
        }
    }

    private int navigateItems(int currentItem, int maxItems) {
        int itemNum = currentItem;
        if (Game.keyH.leftPressed) {
            Game.keyH.leftPressed = false;
            itemNum--;
        } else if (Game.keyH.rightPressed) {
            Game.keyH.rightPressed = false;
            itemNum++;
        } else if (Game.keyH.upPressed && itemNum > 6) {
            Game.keyH.upPressed = false;
            itemNum -= 6;
        } else if (Game.keyH.downPressed && itemNum <= 6) {
            Game.keyH.downPressed = false;
            itemNum += 6;
        }

        if (itemNum < 1)
            itemNum = 1;
        else if (itemNum > maxItems)
            itemNum = maxItems;
        return itemNum;
    }

    /**
     * Handles the drawing of the menu screen.
     */
    private void drawMenu() {

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
        g2.drawString(String.format("Health: %.0f/%.0f", Game.player.getHealth(), Game.player.getMaxHealth()), x,
                y); // draw health
        y += 25;
        g2.drawString(String.format("Defense: %d", Game.player.getDefense()), x, y);// draw defense
        y += 25;
        g2.drawString(String.format("Strength: %d", Game.player.getStrength()), x, y); // draw strength
        y += 25;
        g2.drawString(String.format("Speed: %d", Game.player.getSpeed()), x, y); // draw speed
        // Draw the dividing line between stats and inventory.
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(400, 70, 400, 480);
        // Draw the inventory window.
        x = 420;
        y = 70;
        int width = Tile.TILESIZE + 5;
        int height = Tile.TILESIZE + 5;
        Object[] arr = Game.player.inventory.keySet().toArray();
        menuItem = navigateItems(menuItem, arr.length); // navigate through the inventory menu. Only slots that have
                                                        // items in them should be selectable.
        for (int i = 1; i <= Game.player.inventorySize; i++) {
            if (menuItem == i && arr.length != 0) {
                g2.setColor(Color.BLACK);
            }
            g2.drawRoundRect(x, y, width, height, 3, 3);
            if (i <= arr.length && arr[i - 1] != null) {
                g2.drawImage(((Item) arr[i - 1]).getImage(), x, y, null); // draw the items present in the inventory.
                g2.drawString(capitalize(((Item) arr[i - 1]).getName()), x + 5, y + Tile.TILESIZE + 30); // draw the
                                                                                                         // // name
                                                                                                         // of// the
                                                                                                         // item.
            }
            g2.setColor(Color.WHITE);

            if (i % 6 == 0) { // every 6 items, break the line and start a new row.
                x = 350;
                y += Tile.TILESIZE + 40;
            }
            x += 70;
        }

    }

    private void drawPause() {
        int x = Window.SCREENWIDTH / 2 - 300;
        int y = Window.SCREENHEIGHT / 3 - 100;
        g2.setFont(hel_40);
        g2.setColor(menuColor);
        g2.fillRoundRect(x, y, 600, 450, 20, 20); // Draw the background of the menu
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, 600, 450, 20, 20); // Draw the outline of the menu
        g2.drawString("PAUSED", centerString("PAUSED"), 130);
        g2.setFont(hel_20);
        x += 20;
        y += 100;
        // draw the command options and display a cursor on the current selection.
        g2.drawString("Resume", centerString("Resume"), y);
        if (commandNum == 0) {
            g2.drawString(">", centerString("Resume") - 20, y);
        }
        y += 50;
        g2.drawString("Options", centerString("Options"), y);
        if (commandNum == 1) {
            g2.drawString(">", centerString("Options") - 20, y);
        }
        y += 50;
        g2.drawString("Return to Main Menu", centerString("Return to Main Menu"), y);
        if (commandNum == 2) {
            g2.drawString(">", centerString("Return to Main Menu") - 20, y);
        }
        y += 50;
        g2.drawString("Exit to Desktop", centerString("Exit to Desktop"), y);
        if (commandNum == 3) {
            g2.drawString(">", centerString("Exit to Desktop") - 20, y);
        }
    }

    private void drawStartScreen() {

        int x = 0;
        int y = 0;
        g2.setColor(new Color(157, 0, 200));
        g2.fillRect(x, y, Window.SCREENWIDTH, Window.SCREENHEIGHT);
        x = Window.SCREENWIDTH / 3;
        y = Window.SCREENHEIGHT / 3;
        g2.setFont(hel_40);
        g2.setColor(Color.white);
        g2.drawString("Hags & Hexxes", x, y);
        y = Window.SCREENHEIGHT / 3 + 100;
        g2.setFont(hel_30);
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
        final int width = Tile.TILESIZE * 5;
        final int height = Tile.TILESIZE * 1;
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
        final int x = Tile.TILESIZE / 2;
        final int y = Tile.TILESIZE / 2;
        final int width = Tile.TILESIZE * 3;
        final int height = Tile.TILESIZE / 4;
        g2.setColor(new Color(30, 0, 0));
        g2.fillRoundRect(x, y, width, height, 3, 3);
        g2.setColor(Color.green);
        g2.fillRect(x, y, (int) (width * ((Game.player.getHealth() / Game.player.getMaxHealth()))), height);
        g2.setColor(Color.white);
        g2.setFont(hel_12);
        g2.drawString(String.format("%.0f/%.0f", Game.player.getHealth(), Game.player.getMaxHealth()), x, y - 5);
        g2.dispose();
    }

    public void drawDialogue() {
        int x = Tile.TILESIZE * 2;
        int y = Tile.TILESIZE / 2;
        final int width = Window.SCREENWIDTH - (Tile.TILESIZE * 4);
        final int height = Window.SCREENHEIGHT - (Tile.TILESIZE * 9);
        drawSubWindow(x, y, width, height);
        x += Tile.TILESIZE;
        y += Tile.TILESIZE;
        g2.setFont(hel_20);
        drawString(g2, currentDialogue, x, y);
        g2.dispose();
    }

    private void drawGameOver() {

        // Draw the menu window.
        int x = 50;
        int y = 50;
        // Draw the background of the menu
        g2.setColor(menuColor);
        g2.fillRoundRect(x, y, 1430, 770, 20, 20);
        // Draw the outline of the menu
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, 1430, 770, 20, 20);
        g2.setFont(hel_40);
        // Draw the dividing line between stats and inventory.
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(400, 70, 400, 480);

        g2.dispose();
    }

    public void drawSubWindow(final int x, final int y, final int width, final int height) {
        g2.setColor(new Color(143, 12, 232, 200));
        g2.fillRoundRect(x, y, width, height, 40, 40);
        g2.setColor(new Color(26, 26, 26, 255));
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, width, height, 25, 25);
    }

    public void drawHitNumbers(Entity entity, int damage) {
        int x = entity.getWorldX();
        int y = entity.getWorldY();
        drawString(g2, String.valueOf(damage), x - 2 * Tile.TILESIZE, y);
    }

    private void selectOption(int commandNum) {
        if (Game.gameState == Game.STARTSTATE || Game.gameState == Game.PAUSESTATE) {
            if (commandNum == 0)
                Game.gameState = Game.PLAYSTATE;
            else if (commandNum == 3) {
                System.exit(0); // exit with status code 0
            }
        }
    }

    private int centerString(String s) {
        int length = (int) g2.getFontMetrics().getStringBounds(s, g2).getWidth();
        int x = Window.SCREENWIDTH / 2 - length / 2;
        return x;
    }

}
