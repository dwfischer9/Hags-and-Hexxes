import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Enhanced UI System for Hags and Hexxes
 * Features modern design, animations, and responsive layout
 */
public class UI {
    
    // UI Constants
    private static final int ANIMATION_SPEED = 8;
    private static final int FADE_DURATION = 30;
    private static final int PULSE_DURATION = 60;
    
    // Colors
    private static final Color PRIMARY_COLOR = new Color(157, 80, 255, 250);
    private static final Color SECONDARY_COLOR = new Color(121, 5, 232, 200);
    private static final Color ACCENT_COLOR = new Color(255, 215, 0, 255);
    private static final Color DANGER_COLOR = new Color(220, 53, 69, 255);
    private static final Color SUCCESS_COLOR = new Color(40, 167, 69, 255);
    private static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 180);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(255, 255, 255, 100);
    
    // Fonts
    private final Font titleFont = new Font("Arial", Font.BOLD, 48);
    private final Font subtitleFont = new Font("Arial", Font.BOLD, 24);
    private final Font bodyFont = new Font("Arial", Font.PLAIN, 16);
    private final Font smallFont = new Font("Arial", Font.PLAIN, 12);
    private final Font menuFont = new Font("Arial", Font.BOLD, 18);
    
    // UI State
    private int windowWidth;
    private int windowHeight;
    private int animationFrame = 0;
    private int fadeAlpha = 0;
    private boolean fadeIn = true;
    private List<UINotification> notifications = new ArrayList<>();
    private String debugString = "";
    private int commandNum = 0;
    private int menuItem = 0;
    private Game game;
    
    // Animation states
    private float menuSlideOffset = 0;
    private float healthBarPulse = 0;
    private int cursorBlinkTimer = 0;
    
    public UI() {
        initializeUI();
    }
    
    public UI(Game game) {
        this.game = game;
        initializeUI();
    }
    
    private void initializeUI() {
        // Use the actual game window size instead of full screen
        this.windowWidth = Window.SCREENWIDTH;
        this.windowHeight = Window.SCREENHEIGHT;
        this.animationFrame = 0;
    }
    
    public void updateWindowSize(int width, int height) {
        // Use the actual game window size instead of full screen
        this.windowWidth = Window.SCREENWIDTH;
        this.windowHeight = Window.SCREENHEIGHT;
    }
    
    public void update() {
        animationFrame++;
        
        // Update animations
        if (fadeIn && fadeAlpha < 255) {
            fadeAlpha += ANIMATION_SPEED;
        } else if (!fadeIn && fadeAlpha > 0) {
            fadeAlpha -= ANIMATION_SPEED;
        }
        
        // Update menu slide animation
        if (game != null && game.getGameState() == Game.MENUSTATE) {
            menuSlideOffset = Math.min(1.0f, menuSlideOffset + 0.1f);
        } else {
            menuSlideOffset = Math.max(0.0f, menuSlideOffset - 0.1f);
        }
        
        // Update health bar pulse
        healthBarPulse = (float) Math.sin(animationFrame * 0.1) * 0.3f + 0.7f;
        
        // Update cursor blink
        cursorBlinkTimer = (cursorBlinkTimer + 1) % 30;
        
        // Update notifications
        notifications.removeIf(UINotification::isExpired);
        notifications.forEach(UINotification::update);
    }
    
    public void draw(final Graphics2D g2) {
        if (game == null) return;
        
        // Enable anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        switch (game.getGameState()) {
            case Game.STARTSTATE -> drawStartScreen(g2);
            case Game.PLAYSTATE -> drawGameUI(g2);
            case Game.DIALOGUESTATE -> {
                if (game.getDialogueSystem() != null) {
                    game.getDialogueSystem().draw(g2);
                }
            }
            case Game.PAUSESTATE -> drawPauseScreen(g2);
            case Game.MENUSTATE -> drawInventoryMenu(g2);
            case Game.GAMEOVERSTATE -> drawGameOverScreen(g2);
        }
        
        // Draw notifications on top
        drawNotifications(g2);
    }
    
    private void drawStartScreen(Graphics2D g2) {
        // Background with gradient
        drawGradientBackground(g2);
        
        // Title with glow effect
        drawGlowingText(g2, "Hags & Hexxes", windowWidth / 2, windowHeight / 3, titleFont, ACCENT_COLOR, 20);
        
        // Menu options
        String[] options = {"New Game", "Load Game", "Options", "Exit"};
        int startY = windowHeight / 2;
        
        for (int i = 0; i < options.length; i++) {
            int y = startY + i * 60;
            Color textColor = (commandNum == i) ? ACCENT_COLOR : TEXT_COLOR;
            
            if (commandNum == i) {
                // Draw selection indicator
                drawSelectionIndicator(g2, windowWidth / 2 - 150, y - 15);
            }
            
            drawCenteredText(g2, options[i], windowWidth / 2, y, menuFont, textColor);
        }
        
        // Handle navigation
        handleMenuNavigation();
    }
    
    private void drawGameUI(Graphics2D g2) {
        // Health bar
        drawHealthBar(g2);
        
        // Mini-map or compass
        drawCompass(g2);
        
        // Quick stats
        drawQuickStats(g2);
        
        // Interaction prompt
        drawInteractionPrompt(g2);
    }
    
    private void drawHealthBar(Graphics2D g2) {
        if (game == null || game.getPlayer() == null) return;
        
        Player player = game.getPlayer();
        int barWidth = 200;
        int barHeight = 20;
        int x = 20;
        int y = 20;
        
        // Background
        drawRoundedRect(g2, x, y, barWidth, barHeight, 10, BACKGROUND_COLOR);
        drawRoundedRect(g2, x, y, barWidth, barHeight, 10, BORDER_COLOR, false);
        
        // Health fill
        float healthPercent = player.getHealth() / player.getMaxHealth();
        int fillWidth = (int) (barWidth * healthPercent);
        
        Color healthColor = healthPercent > 0.5f ? SUCCESS_COLOR : 
                           healthPercent > 0.25f ? ACCENT_COLOR : DANGER_COLOR;
        
        // Add pulse effect when health is low
        if (healthPercent < 0.25f) {
            healthColor = new Color(healthColor.getRed(), healthColor.getGreen(), 
                                  healthColor.getBlue(), (int)(255 * healthBarPulse));
        }
        
        drawRoundedRect(g2, x + 2, y + 2, fillWidth - 4, barHeight - 4, 8, healthColor);
        
        // Health text
        String healthText = String.format("%.0f/%.0f", player.getHealth(), player.getMaxHealth());
        drawCenteredText(g2, healthText, x + barWidth / 2, y + barHeight / 2 + 5, bodyFont, TEXT_COLOR);
    }
    
    private void drawCompass(Graphics2D g2) {
        int size = 80;
        int x = windowWidth - size - 20;
        int y = 20;
        
        // Compass background
        drawRoundedRect(g2, x, y, size, size, size / 2, BACKGROUND_COLOR);
        drawRoundedRect(g2, x, y, size, size, size / 2, BORDER_COLOR, false);
        
        // Compass needle
        g2.setColor(ACCENT_COLOR);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(x + size / 2, y + 10, x + size / 2, y + size - 10);
        g2.drawLine(x + 10, y + size / 2, x + size - 10, y + size / 2);
        
        // North indicator
        drawCenteredText(g2, "N", x + size / 2, y + 15, smallFont, ACCENT_COLOR);
    }
    
    private void drawQuickStats(Graphics2D g2) {
        if (game == null || game.getPlayer() == null) return;
        
        Player player = game.getPlayer();
        int x = 20;
        int y = 60;
        
        String[] stats = {
            "Level: " + player.getLevel(),
            "Strength: " + player.getStrength(),
            "Defense: " + player.getDefense(),
            "Speed: " + player.getSpeed()
        };
        
        for (int i = 0; i < stats.length; i++) {
            drawText(g2, stats[i], x, y + i * 20, smallFont, TEXT_COLOR);
        }
    }
    
    private void drawInteractionPrompt(Graphics2D g2) {
        // Show interaction prompt when near NPCs
        if (game != null && game.getPlayer() != null) {
            // This would check if player is near an interactable object
            // For now, just show a general prompt
            String prompt = "Press E to interact";
            drawCenteredText(g2, prompt, windowWidth / 2, windowHeight - 100, bodyFont, ACCENT_COLOR);
        }
    }
    
    private void drawInventoryMenu(Graphics2D g2) {
        if (game == null || game.getPlayer() == null) return;
        
        Player player = game.getPlayer();
        
        // Semi-transparent overlay
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, windowWidth, windowHeight);
        
        // Menu container with slide animation
        int menuWidth = (int) (600 * menuSlideOffset);
        int menuHeight = (int) (400 * menuSlideOffset);
        int x = (windowWidth - menuWidth) / 2;
        int y = (windowHeight - menuHeight) / 2;
        
        // Menu background
        drawRoundedRect(g2, x, y, menuWidth, menuHeight, 20, PRIMARY_COLOR);
        drawRoundedRect(g2, x, y, menuWidth, menuHeight, 20, BORDER_COLOR, false);
        
        // Title
        drawCenteredText(g2, "Inventory", x + menuWidth / 2, y + 40, subtitleFont, TEXT_COLOR);
        
        // Inventory grid
        drawInventoryGrid(g2, player, x + 20, y + 60, menuWidth - 40, menuHeight - 80);
        
        // Handle inventory navigation
        handleInventoryNavigation(player);
    }
    
    private void drawInventoryGrid(Graphics2D g2, Player player, int x, int y, int width, int height) {
        int itemsPerRow = 6;
        int itemSize = 50;
        int spacing = 10;
        
        Object[] inventoryItems = player.inventory.keySet().toArray();
        
        for (int i = 0; i < player.inventorySize; i++) {
            int itemX = x + (i % itemsPerRow) * (itemSize + spacing);
            int itemY = y + (i / itemsPerRow) * (itemSize + spacing);
            
            // Item slot background
            Color slotColor = (menuItem == i + 1) ? ACCENT_COLOR : BACKGROUND_COLOR;
            drawRoundedRect(g2, itemX, itemY, itemSize, itemSize, 8, slotColor);
            drawRoundedRect(g2, itemX, itemY, itemSize, itemSize, 8, BORDER_COLOR, false);
            
            // Item image and name
            if (i < inventoryItems.length && inventoryItems[i] != null) {
                Item item = (Item) inventoryItems[i];
                g2.drawImage(item.getImage(), itemX + 5, itemY + 5, itemSize - 10, itemSize - 10, null);
                drawCenteredText(g2, item.getName(), itemX + itemSize / 2, itemY + itemSize + 15, smallFont, TEXT_COLOR);
            }
        }
    }
    
    private void drawPauseScreen(Graphics2D g2) {
        // Semi-transparent overlay
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, windowWidth, windowHeight);
        
        // Pause menu
        int menuWidth = 400;
        int menuHeight = 300;
        int x = (windowWidth - menuWidth) / 2;
        int y = (windowHeight - menuHeight) / 2;
        
        drawRoundedRect(g2, x, y, menuWidth, menuHeight, 20, PRIMARY_COLOR);
        drawRoundedRect(g2, x, y, menuWidth, menuHeight, 20, BORDER_COLOR, false);
        
        // Title
        drawCenteredText(g2, "PAUSED", x + menuWidth / 2, y + 50, subtitleFont, TEXT_COLOR);
        
        // Menu options
        String[] options = {"Resume", "Options", "Return to Main Menu", "Exit"};
        for (int i = 0; i < options.length; i++) {
            int optionY = y + 100 + i * 40;
            Color textColor = (commandNum == i) ? ACCENT_COLOR : TEXT_COLOR;
            
            if (commandNum == i) {
                drawSelectionIndicator(g2, x + 20, optionY - 10);
            }
            
            drawText(g2, options[i], x + 50, optionY, menuFont, textColor);
        }
        
        handleMenuNavigation();
    }
    
    private void drawGameOverScreen(Graphics2D g2) {
        // Dark overlay
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, 0, windowWidth, windowHeight);
        
        // Game over text with effect
        drawGlowingText(g2, "GAME OVER", windowWidth / 2, windowHeight / 3, titleFont, DANGER_COLOR, 30);
        
        // Menu options
        String[] options = {"Load Game", "Options", "Exit to Desktop"};
        int startY = windowHeight / 2;
        
        for (int i = 0; i < options.length; i++) {
            int y = startY + i * 60;
            Color textColor = (commandNum == i) ? ACCENT_COLOR : TEXT_COLOR;
            
            if (commandNum == i) {
                drawSelectionIndicator(g2, windowWidth / 2 - 150, y - 15);
            }
            
            drawCenteredText(g2, options[i], windowWidth / 2, y, menuFont, textColor);
        }
        
        handleMenuNavigation();
    }
    
    private void drawNotifications(Graphics2D g2) {
        int y = 100;
        for (UINotification notification : notifications) {
            if (notification.isVisible()) {
                drawNotification(g2, notification, windowWidth - 300, y);
                y += 60;
            }
        }
    }
    
    private void drawNotification(Graphics2D g2, UINotification notification, int x, int y) {
        int width = 280;
        int height = 50;
        
        // Background
        Color bgColor = notification.getType().getColor();
        drawRoundedRect(g2, x, y, width, height, 10, bgColor);
        drawRoundedRect(g2, x, y, width, height, 10, BORDER_COLOR, false);
        
        // Text
        drawText(g2, notification.getMessage(), x + 10, y + 30, bodyFont, TEXT_COLOR);
    }
    
    // Utility drawing methods
    private void drawGradientBackground(Graphics2D g2) {
        GradientPaint gradient = new GradientPaint(
            0, 0, PRIMARY_COLOR,
            windowWidth, windowHeight, SECONDARY_COLOR
        );
        g2.setPaint(gradient);
        g2.fillRect(0, 0, windowWidth, windowHeight);
    }
    
    private void drawRoundedRect(Graphics2D g2, int x, int y, int width, int height, int radius, Color color) {
        drawRoundedRect(g2, x, y, width, height, radius, color, true);
    }
    
    private void drawRoundedRect(Graphics2D g2, int x, int y, int width, int height, int radius, Color color, boolean fill) {
        g2.setColor(color);
        RoundRectangle2D rect = new RoundRectangle2D.Float(x, y, width, height, radius, radius);
        
        if (fill) {
            g2.fill(rect);
        } else {
            g2.setStroke(new BasicStroke(2));
            g2.draw(rect);
        }
    }
    
    private void drawGlowingText(Graphics2D g2, String text, int x, int y, Font font, Color color, int glowSize) {
        g2.setFont(font);
        
        // Draw glow effect
        for (int i = glowSize; i > 0; i--) {
            Color glowColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 50 / i);
            g2.setColor(glowColor);
            g2.drawString(text, x - i, y - i);
            g2.drawString(text, x + i, y + i);
        }
        
        // Draw main text
        g2.setColor(color);
        g2.drawString(text, x, y);
    }
    
    private void drawCenteredText(Graphics2D g2, String text, int x, int y, Font font, Color color) {
        g2.setFont(font);
        g2.setColor(color);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g2.drawString(text, x - textWidth / 2, y);
    }
    
    private void drawText(Graphics2D g2, String text, int x, int y, Font font, Color color) {
        g2.setFont(font);
        g2.setColor(color);
        g2.drawString(text, x, y);
    }
    
    private void drawSelectionIndicator(Graphics2D g2, int x, int y) {
        // Animated selection indicator
        int alpha = cursorBlinkTimer < 15 ? 255 : 100;
        g2.setColor(new Color(ACCENT_COLOR.getRed(), ACCENT_COLOR.getGreen(), ACCENT_COLOR.getBlue(), alpha));
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(x, y, x + 20, y);
    }
    
    // Input handling
    private void handleMenuNavigation() {
        if (game == null) return;
        
        KeyHandler keyH = game.getKeyHandler();
        if (keyH == null) return;
        
        if (keyH.isActionPressed("UP")) {
            keyH.resetKey("UP");
            keyH.resetKey("UP_ALT");
            commandNum = Math.max(0, commandNum - 1);
        } else if (keyH.isActionPressed("DOWN")) {
            keyH.resetKey("DOWN");
            keyH.resetKey("DOWN_ALT");
            commandNum = Math.min(3, commandNum + 1);
        }
        
        if (keyH.isPressed("ATTACK") || keyH.isPressed("INTERACT")) {
            keyH.resetKey("ATTACK");
            keyH.resetKey("INTERACT");
            selectOption(commandNum);
        }
    }
    
    private void handleInventoryNavigation(Player player) {
        if (game == null) return;
        
        KeyHandler keyH = game.getKeyHandler();
        if (keyH == null) return;
        
        int itemsPerRow = 6;
        
        if (keyH.isActionPressed("LEFT")) {
            keyH.resetKey("LEFT");
            keyH.resetKey("LEFT_ALT");
            menuItem = Math.max(1, menuItem - 1);
        } else if (keyH.isActionPressed("RIGHT")) {
            keyH.resetKey("RIGHT");
            keyH.resetKey("RIGHT_ALT");
            menuItem = Math.min(player.inventorySize, menuItem + 1);
        } else if (keyH.isActionPressed("UP")) {
            keyH.resetKey("UP");
            keyH.resetKey("UP_ALT");
            menuItem = Math.max(1, menuItem - itemsPerRow);
        } else if (keyH.isActionPressed("DOWN")) {
            keyH.resetKey("DOWN");
            keyH.resetKey("DOWN_ALT");
            menuItem = Math.min(player.inventorySize, menuItem + itemsPerRow);
        }
    }
    
    private void selectOption(int commandNum) {
        if (game == null) return;
        
        switch (game.getGameState()) {
            case Game.STARTSTATE -> {
                switch (commandNum) {
                    case 0 -> game.setGameState(Game.PLAYSTATE);
                    case 3 -> System.exit(0);
                }
            }
            case Game.PAUSESTATE -> {
                switch (commandNum) {
                    case 0 -> game.setGameState(Game.PLAYSTATE);
                    case 3 -> System.exit(0);
                }
            }
            case Game.GAMEOVERSTATE -> {
                switch (commandNum) {
                    case 0 -> game.setGameState(Game.PLAYSTATE);
                    case 2 -> System.exit(0);
                }
            }
        }
    }
    
    // Public methods for notifications
    public void addNotification(String message, UINotification.NotificationType type) {
        notifications.add(new UINotification(message, type));
    }
    
    public void printDebug(String s) {
        debugString += "\n" + s;
    }
    
    public void drawDebugString(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.RED);
        g2d.setFont(smallFont);
        drawText(g2d, debugString, 50, 50, smallFont, Color.RED);
        g2d.dispose();
    }
    
    // Inner classes
    public static class UINotification {
        public enum NotificationType {
            INFO(new Color(0, 123, 255, 200)),
            SUCCESS(new Color(40, 167, 69, 200)),
            WARNING(new Color(255, 193, 7, 200)),
            ERROR(new Color(220, 53, 69, 200));
            
            private final Color color;
            
            NotificationType(Color color) {
                this.color = color;
            }
            
            public Color getColor() {
                return color;
            }
        }
        
        private String message;
        private NotificationType type;
        private int duration;
        private int fadeTimer;
        private boolean visible = true;
        
        public UINotification(String message, NotificationType type) {
            this.message = message;
            this.type = type;
            this.duration = 180; // 3 seconds at 60fps
            this.fadeTimer = 0;
        }
        
        public void update() {
            fadeTimer++;
            if (fadeTimer >= duration) {
                visible = false;
            }
        }
        
        public boolean isExpired() {
            return fadeTimer >= duration + FADE_DURATION;
        }
        
        public boolean isVisible() {
            return visible;
        }
        
        public String getMessage() {
            return message;
        }
        
        public NotificationType getType() {
            return type;
        }
    }
}
