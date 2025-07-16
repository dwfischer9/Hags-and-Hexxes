import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyHandler implements KeyListener {
    // Key states - using a map for better organization
    private final Map<String, Boolean> keyStates = new HashMap<>();
    
    // Key bindings - easily configurable
    private final Map<String, Integer> keyBindings = new HashMap<>();
    
    // Initialize default key bindings
    public KeyHandler() {
        // Movement keys
        keyBindings.put("UP", KeyEvent.VK_W);
        keyBindings.put("DOWN", KeyEvent.VK_S);
        keyBindings.put("LEFT", KeyEvent.VK_A);
        keyBindings.put("RIGHT", KeyEvent.VK_D);
        keyBindings.put("UP_ALT", KeyEvent.VK_UP);
        keyBindings.put("DOWN_ALT", KeyEvent.VK_DOWN);
        keyBindings.put("LEFT_ALT", KeyEvent.VK_LEFT);
        keyBindings.put("RIGHT_ALT", KeyEvent.VK_RIGHT);
        
        // Action keys
        keyBindings.put("ATTACK", KeyEvent.VK_SPACE);
        keyBindings.put("INTERACT", KeyEvent.VK_E);
        keyBindings.put("MENU", KeyEvent.VK_ESCAPE);
        keyBindings.put("INVENTORY", KeyEvent.VK_TAB);
        
        // Initialize all key states to false
        keyBindings.values().forEach(keyCode -> keyStates.put(String.valueOf(keyCode), false));
    }
    
    // Check if a specific action key is pressed
    public boolean isPressed(String action) {
        Integer keyCode = keyBindings.get(action);
        return keyCode != null && keyStates.getOrDefault(String.valueOf(keyCode), false);
    }
    
    // Check if any key for a specific action is pressed (useful for movement with multiple bindings)
    public boolean isActionPressed(String action) {
        return keyBindings.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(action))
                .anyMatch(entry -> keyStates.getOrDefault(String.valueOf(entry.getValue()), false));
    }
    
    // Get current movement direction as a string (supports diagonal movement)
    public String getMovementDirection() {
        boolean up = isActionPressed("UP");
        boolean down = isActionPressed("DOWN");
        boolean left = isActionPressed("LEFT");
        boolean right = isActionPressed("RIGHT");
        
        if (up && right) return "up-right";
        if (up && left) return "up-left";
        if (down && right) return "down-right";
        if (down && left) return "down-left";
        if (up) return "up";
        if (down) return "down";
        if (left) return "left";
        if (right) return "right";
        
        return "none";
    }
    
    // Get movement vector for diagonal movement
    public int[] getMovementVector() {
        boolean up = isActionPressed("UP");
        boolean down = isActionPressed("DOWN");
        boolean left = isActionPressed("LEFT");
        boolean right = isActionPressed("RIGHT");
        
        int dx = 0, dy = 0;
        if (up) dy--;
        if (down) dy++;
        if (left) dx--;
        if (right) dx++;
        
        return new int[]{dx, dy};
    }
    
    // Reset a specific key state (useful for one-time actions)
    public void resetKey(String action) {
        Integer keyCode = keyBindings.get(action);
        if (keyCode != null) {
            keyStates.put(String.valueOf(keyCode), false);
        }
    }
    
    // Change key binding
    public void setKeyBinding(String action, int newKeyCode) {
        keyBindings.put(action, newKeyCode);
        keyStates.put(String.valueOf(newKeyCode), false);
    }

    @Override
    public void keyTyped(final KeyEvent e) {
        // Not used in this implementation
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        keyStates.put(String.valueOf(e.getKeyCode()), true);
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        keyStates.put(String.valueOf(e.getKeyCode()), false);
    }
    
    // Legacy support methods for backward compatibility
    public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    public boolean ePressed = false;
    public boolean escapePressed = false;
    public boolean spacePressed = false;
    public boolean tabPressed = false;
    
    // Update legacy boolean fields for compatibility
    public void updateLegacyFields() {
        upPressed = isActionPressed("UP");
        downPressed = isActionPressed("DOWN");
        leftPressed = isActionPressed("LEFT");
        rightPressed = isActionPressed("RIGHT");
        ePressed = isPressed("INTERACT");
        escapePressed = isPressed("MENU");
        spacePressed = isPressed("ATTACK");
        tabPressed = isPressed("INVENTORY");
    }
}
