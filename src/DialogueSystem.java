import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Enhanced Dialogue System for Hags and Hexxes
 * Supports branching dialogues, choices, conditions, and rich text formatting
 */
public class DialogueSystem {
    
    // Dialogue states
    public static final int DIALOGUE_IDLE = 0;
    public static final int DIALOGUE_TYPING = 1;
    public static final int DIALOGUE_WAITING = 2;
    public static final int DIALOGUE_CHOICE = 3;
    
    // Text formatting tags
    public static final String BOLD_START = "<b>";
    public static final String BOLD_END = "</b>";
    public static final String ITALIC_START = "<i>";
    public static final String ITALIC_END = "</i>";
    public static final String COLOR_START = "<color:";
    public static final String COLOR_END = "</color>";
    public static final String PAUSE_START = "<pause:";
    public static final String PAUSE_END = ">";
    
    private Game game;
    private Entity currentSpeaker;
    private List<DialogueNode> dialogueTree;
    private DialogueNode currentNode;
    private int currentState = DIALOGUE_IDLE;
    private int typingIndex = 0;
    private int typingSpeed = 2; // characters per frame
    private int typingTimer = 0;
    private String displayText = "";
    private String fullText = "";
    private List<DialogueChoice> currentChoices;
    private int selectedChoice = 0;
    private int pauseTimer = 0;
    private int pauseDuration = 0;
    private boolean autoAdvance = false;
    private int autoAdvanceTimer = 0;
    private int autoAdvanceDelay = 120; // frames (2 seconds at 60fps)
    
    // UI elements
    private Rectangle dialogueBox;
    private Rectangle portraitBox;
    private BufferedImage speakerPortrait;
    private String speakerName = "";
    private Color textColor = Color.WHITE;
    private Color nameColor = Color.YELLOW;
    
    public DialogueSystem(Game game) {
        this.game = game;
        this.dialogueTree = new ArrayList<>();
        this.currentChoices = new ArrayList<>();
        setupDialogueBox();
    }
    
    private void setupDialogueBox() {
        // Position dialogue box at bottom of screen
        dialogueBox = new Rectangle(
            Tile.TILESIZE * 2, 
            Window.SCREEN_Y + Tile.TILESIZE * 8,
            Window.SCREEN_X + Tile.TILESIZE * 12,
            Tile.TILESIZE * 6
        );
        
        // Portrait box on the left
        portraitBox = new Rectangle(
            Tile.TILESIZE * 3,
            Window.SCREEN_Y + Tile.TILESIZE * 9,
            Tile.TILESIZE * 4,
            Tile.TILESIZE * 4
        );
    }
    
    /**
     * Start a dialogue with an entity
     */
    public void startDialogue(Entity speaker) {
        if (speaker == null || speaker.getDialogues() == null) {
            return;
        }
        
        this.currentSpeaker = speaker;
        this.speakerName = speaker.getName();
        this.currentState = DIALOGUE_TYPING;
        this.typingIndex = 0;
        this.typingTimer = 0;
        this.selectedChoice = 0;
        this.currentChoices.clear();
        
        // Load dialogue tree from speaker's dialogues
        loadDialogueTree(speaker.getDialogues());
        
        // Start with first node
        if (!dialogueTree.isEmpty()) {
            currentNode = dialogueTree.get(0);
            startTyping(currentNode.getText());
        }
        
        // Set game state
        if (game != null) {
            game.setGameState(Game.DIALOGUESTATE);
        }
    }
    
    /**
     * Load dialogue tree from string array
     */
    private void loadDialogueTree(String[] dialogues) {
        dialogueTree.clear();
        
        for (int i = 0; i < dialogues.length && dialogues[i] != null; i++) {
            String dialogue = dialogues[i];
            
            // Parse dialogue for special tags
            DialogueNode node = parseDialogueNode(dialogue, i);
            dialogueTree.add(node);
        }
    }
    
    /**
     * Parse a dialogue string into a DialogueNode
     */
    private DialogueNode parseDialogueNode(String dialogue, int index) {
        DialogueNode node = new DialogueNode();
        node.setId(index);
        
        // Check for conditions
        if (dialogue.contains("[IF:")) {
            String condition = extractCondition(dialogue);
            node.setCondition(condition);
            dialogue = removeCondition(dialogue);
        }
        
        // Check for choices
        if (dialogue.contains("[CHOICE:")) {
            List<DialogueChoice> choices = extractChoices(dialogue);
            node.setChoices(choices);
            dialogue = removeChoices(dialogue);
        }
        
        // Check for actions
        if (dialogue.contains("[ACTION:")) {
            String action = extractAction(dialogue);
            node.setAction(action);
            dialogue = removeAction(dialogue);
        }
        
        // Check for branching
        if (dialogue.contains("[GOTO:")) {
            int targetId = extractGoto(dialogue);
            node.setNextNode(targetId);
            dialogue = removeGoto(dialogue);
        }
        
        // Clean up the text
        dialogue = dialogue.trim();
        node.setText(dialogue);
        
        return node;
    }
    
    private String extractCondition(String dialogue) {
        int start = dialogue.indexOf("[IF:") + 4;
        int end = dialogue.indexOf("]", start);
        return dialogue.substring(start, end);
    }
    
    private String removeCondition(String dialogue) {
        int start = dialogue.indexOf("[IF:");
        int end = dialogue.indexOf("]", start) + 1;
        return dialogue.substring(0, start) + dialogue.substring(end);
    }
    
    private List<DialogueChoice> extractChoices(String dialogue) {
        List<DialogueChoice> choices = new ArrayList<>();
        int start = 0;
        
        while ((start = dialogue.indexOf("[CHOICE:", start)) != -1) {
            int end = dialogue.indexOf("]", start);
            String choiceText = dialogue.substring(start + 8, end);
            
            DialogueChoice choice = new DialogueChoice();
            choice.setText(choiceText);
            
            // Check for choice actions
            if (choiceText.contains("->")) {
                String[] parts = choiceText.split("->");
                choice.setText(parts[0].trim());
                choice.setAction(parts[1].trim());
            }
            
            choices.add(choice);
            start = end + 1;
        }
        
        return choices;
    }
    
    private String removeChoices(String dialogue) {
        int start = dialogue.indexOf("[CHOICE:");
        if (start == -1) return dialogue;
        
        int end = dialogue.lastIndexOf("]") + 1;
        return dialogue.substring(0, start) + dialogue.substring(end);
    }
    
    private String extractAction(String dialogue) {
        int start = dialogue.indexOf("[ACTION:") + 8;
        int end = dialogue.indexOf("]", start);
        return dialogue.substring(start, end);
    }
    
    private String removeAction(String dialogue) {
        int start = dialogue.indexOf("[ACTION:");
        int end = dialogue.indexOf("]", start) + 1;
        return dialogue.substring(0, start) + dialogue.substring(end);
    }
    
    private int extractGoto(String dialogue) {
        int start = dialogue.indexOf("[GOTO:") + 6;
        int end = dialogue.indexOf("]", start);
        return Integer.parseInt(dialogue.substring(start, end));
    }
    
    private String removeGoto(String dialogue) {
        int start = dialogue.indexOf("[GOTO:");
        int end = dialogue.indexOf("]", start) + 1;
        return dialogue.substring(0, start) + dialogue.substring(end);
    }
    
    /**
     * Start typing effect for text
     */
    private void startTyping(String text) {
        this.fullText = text;
        this.displayText = "";
        this.typingIndex = 0;
        this.typingTimer = 0;
        this.currentState = DIALOGUE_TYPING;
        this.pauseTimer = 0;
        this.pauseDuration = 0;
    }
    
    /**
     * Update dialogue system
     */
    public void update() {
        switch (currentState) {
            case DIALOGUE_TYPING -> updateTyping();
            case DIALOGUE_WAITING -> updateWaiting();
            case DIALOGUE_CHOICE -> updateChoice();
        }
    }
    
    private void updateTyping() {
        typingTimer++;
        
        if (typingTimer >= typingSpeed) {
            typingTimer = 0;
            
            if (typingIndex < fullText.length()) {
                char nextChar = fullText.charAt(typingIndex);
                
                // Handle special formatting
                if (nextChar == '<') {
                    // Skip formatting tags
                    int endTag = fullText.indexOf('>', typingIndex);
                    if (endTag != -1) {
                        typingIndex = endTag + 1;
                        return;
                    }
                }
                
                displayText += nextChar;
                typingIndex++;
                
                // Check for pause tags
                if (displayText.contains(PAUSE_START)) {
                    int pauseStart = displayText.indexOf(PAUSE_START);
                    int pauseEnd = displayText.indexOf(PAUSE_END, pauseStart);
                    if (pauseEnd != -1) {
                        String pauseStr = displayText.substring(pauseStart + 7, pauseEnd);
                        pauseDuration = Integer.parseInt(pauseStr);
                        displayText = displayText.replace(PAUSE_START + pauseStr + PAUSE_END, "");
                        currentState = DIALOGUE_WAITING;
                        pauseTimer = 0;
                        return;
                    }
                }
            } else {
                // Finished typing
                currentState = DIALOGUE_WAITING;
                autoAdvanceTimer = 0;
            }
        }
    }
    
    private void updateWaiting() {
        if (pauseDuration > 0) {
            pauseTimer++;
            if (pauseTimer >= pauseDuration) {
                pauseDuration = 0;
                pauseTimer = 0;
                currentState = DIALOGUE_TYPING;
            }
        } else {
            autoAdvanceTimer++;
            if (autoAdvance && autoAdvanceTimer >= autoAdvanceDelay) {
                advanceDialogue();
            }
        }
    }
    
    private void updateChoice() {
        // Choice navigation is handled by key input
    }
    
    /**
     * Handle key input for dialogue
     */
    public void handleKeyInput(KeyHandler keyH) {
        if (keyH == null) return;
        
        switch (currentState) {
            case DIALOGUE_TYPING -> {
                // Skip typing on key press
                if (keyH.isPressed("INTERACT") || keyH.isPressed("ATTACK")) {
                    keyH.resetKey("INTERACT");
                    keyH.resetKey("ATTACK");
                    displayText = fullText;
                    currentState = DIALOGUE_WAITING;
                    autoAdvanceTimer = 0;
                }
            }
            case DIALOGUE_WAITING -> {
                if (keyH.isPressed("INTERACT") || keyH.isPressed("ATTACK")) {
                    keyH.resetKey("INTERACT");
                    keyH.resetKey("ATTACK");
                    advanceDialogue();
                }
            }
            case DIALOGUE_CHOICE -> {
                if (keyH.isActionPressed("UP")) {
                    keyH.resetKey("UP");
                    keyH.resetKey("UP_ALT");
                    selectedChoice = Math.max(0, selectedChoice - 1);
                } else if (keyH.isActionPressed("DOWN")) {
                    keyH.resetKey("DOWN");
                    keyH.resetKey("DOWN_ALT");
                    selectedChoice = Math.min(currentChoices.size() - 1, selectedChoice + 1);
                } else if (keyH.isPressed("INTERACT") || keyH.isPressed("ATTACK")) {
                    keyH.resetKey("INTERACT");
                    keyH.resetKey("ATTACK");
                    selectChoice();
                }
            }
        }
    }
    
    /**
     * Advance to next dialogue node
     */
    private void advanceDialogue() {
        if (currentNode == null) {
            endDialogue();
            return;
        }
        
        // Execute action if present
        if (currentNode.getAction() != null) {
            executeAction(currentNode.getAction());
        }
        
        // Handle choices
        if (currentNode.hasChoices()) {
            currentChoices = currentNode.getChoices();
            selectedChoice = 0;
            currentState = DIALOGUE_CHOICE;
            return;
        }
        
        // Move to next node
        int nextId = currentNode.getNextNode();
        if (nextId >= 0 && nextId < dialogueTree.size()) {
            currentNode = dialogueTree.get(nextId);
            
            // Check conditions
            if (currentNode.getCondition() != null && !checkCondition(currentNode.getCondition())) {
                // Skip this node if condition not met
                advanceDialogue();
                return;
            }
            
            startTyping(currentNode.getText());
        } else {
            endDialogue();
        }
    }
    
    /**
     * Select current choice
     */
    private void selectChoice() {
        if (selectedChoice >= 0 && selectedChoice < currentChoices.size()) {
            DialogueChoice choice = currentChoices.get(selectedChoice);
            
            // Execute choice action
            if (choice.getAction() != null) {
                executeAction(choice.getAction());
            }
            
            // Move to next node
            advanceDialogue();
        }
    }
    
    /**
     * Execute dialogue action
     */
    private void executeAction(String action) {
        if (action == null) return;
        
        String[] parts = action.split(":");
        if (parts.length < 2) return;
        
        String actionType = parts[0];
        String actionData = parts[1];
        
        switch (actionType.toLowerCase()) {
            case "give_item" -> {
                // Give item to player
                if (game != null && game.getPlayer() != null) {
                    // Implementation for giving items
                    System.out.println("Giving item: " + actionData);
                }
            }
            case "set_flag" -> {
                // Set a game flag
                System.out.println("Setting flag: " + actionData);
            }
            case "teleport" -> {
                // Teleport player
                String[] coords = actionData.split(",");
                if (coords.length == 2 && game != null && game.getPlayer() != null) {
                    int x = Integer.parseInt(coords[0]);
                    int y = Integer.parseInt(coords[1]);
                    game.getPlayer().setWorldX(x * Tile.TILESIZE);
                    game.getPlayer().setWorldY(y * Tile.TILESIZE);
                }
            }
            case "heal" -> {
                // Heal player
                if (game != null && game.getPlayer() != null) {
                    game.getPlayer().setHealth(game.getPlayer().getMaxHealth());
                }
            }
        }
    }
    
    /**
     * Check dialogue condition
     */
    private boolean checkCondition(String condition) {
        if (condition == null) return true;
        
        String[] parts = condition.split(":");
        if (parts.length < 2) return true;
        
        String conditionType = parts[0];
        String conditionData = parts[1];
        
        if (game == null || game.getPlayer() == null) return false;
        
        switch (conditionType.toLowerCase()) {
            case "has_item" -> {
                // Check if player has item
                // For now, just check if any item with similar name exists
                for (Item item : game.getPlayer().inventory.keySet()) {
                    if (item.getName().equalsIgnoreCase(conditionData)) {
                        return true;
                    }
                }
                return false;
            }
            case "health_above" -> {
                // Check if player health is above threshold
                int threshold = Integer.parseInt(conditionData);
                return game.getPlayer().getHealth() > threshold;
            }
            case "flag_set" -> {
                // Check if flag is set
                return false; // Implement flag system
            }
            default -> {
                return true;
            }
        }
    }
    
    /**
     * End dialogue
     */
    public void endDialogue() {
        currentState = DIALOGUE_IDLE;
        currentSpeaker = null;
        currentNode = null;
        currentChoices.clear();
        
        if (game != null) {
            game.setGameState(Game.PLAYSTATE);
        }
    }
    
    /**
     * Draw dialogue UI
     */
    public void draw(Graphics2D g2) {
        if (currentState == DIALOGUE_IDLE) return;
        
        // Draw dialogue box background
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(dialogueBox.x, dialogueBox.y, dialogueBox.width, dialogueBox.height, 20, 20);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(dialogueBox.x, dialogueBox.y, dialogueBox.width, dialogueBox.height, 20, 20);
        
        // Draw speaker name
        if (speakerName != null && !speakerName.isEmpty()) {
            g2.setColor(nameColor);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.drawString(speakerName, dialogueBox.x + 20, dialogueBox.y + 25);
        }
        
        // Draw dialogue text
        g2.setColor(textColor);
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        drawFormattedText(g2, displayText, dialogueBox.x + 20, dialogueBox.y + 50, dialogueBox.width - 40);
        
        // Draw choices if in choice state
        if (currentState == DIALOGUE_CHOICE) {
            drawChoices(g2);
        }
        
        // Draw continue indicator
        if (currentState == DIALOGUE_WAITING && !currentNode.hasChoices()) {
            drawContinueIndicator(g2);
        }
    }
    
    /**
     * Draw formatted text with color and style tags
     */
    private void drawFormattedText(Graphics2D g2, String text, int x, int y, int maxWidth) {
        String[] lines = text.split("\n");
        int lineHeight = 20;
        int currentY = y;
        
        for (String line : lines) {
            // Handle formatting tags
            line = processFormattingTags(line);
            
            // Word wrap
            String[] words = line.split(" ");
            StringBuilder currentLine = new StringBuilder();
            int currentX = x;
            
            for (String word : words) {
                String testLine = currentLine + (currentLine.length() > 0 ? " " : "") + word;
                int textWidth = g2.getFontMetrics().stringWidth(testLine);
                
                if (textWidth > maxWidth && currentLine.length() > 0) {
                    // Draw current line and start new one
                    g2.drawString(currentLine.toString(), currentX, currentY);
                    currentLine = new StringBuilder(word);
                    currentY += lineHeight;
                } else {
                    currentLine = new StringBuilder(testLine);
                }
            }
            
            // Draw remaining text
            if (currentLine.length() > 0) {
                g2.drawString(currentLine.toString(), currentX, currentY);
                currentY += lineHeight;
            }
        }
    }
    
    private String processFormattingTags(String text) {
        // Remove formatting tags for now - implement proper formatting later
        text = text.replace(BOLD_START, "").replace(BOLD_END, "");
        text = text.replace(ITALIC_START, "").replace(ITALIC_END, "");
        text = text.replaceAll(COLOR_START + ".*?" + COLOR_END, "");
        return text;
    }
    
    private void drawChoices(Graphics2D g2) {
        int choiceY = dialogueBox.y + dialogueBox.height - 30 - (currentChoices.size() * 25);
        
        for (int i = 0; i < currentChoices.size(); i++) {
            DialogueChoice choice = currentChoices.get(i);
            
            if (i == selectedChoice) {
                g2.setColor(Color.YELLOW);
                g2.drawString("> " + choice.getText(), dialogueBox.x + 40, choiceY + (i * 25));
            } else {
                g2.setColor(Color.WHITE);
                g2.drawString("  " + choice.getText(), dialogueBox.x + 40, choiceY + (i * 25));
            }
        }
    }
    
    private void drawContinueIndicator(Graphics2D g2) {
        int indicatorX = dialogueBox.x + dialogueBox.width - 30;
        int indicatorY = dialogueBox.y + dialogueBox.height - 20;
        
        // Animated indicator
        int alpha = (int)(128 + 127 * Math.sin(System.currentTimeMillis() / 200.0));
        g2.setColor(new Color(255, 255, 255, alpha));
        g2.drawString("▼", indicatorX, indicatorY);
    }
    
    // Getters and setters
    public boolean isActive() {
        return currentState != DIALOGUE_IDLE;
    }
    
    public void setAutoAdvance(boolean autoAdvance) {
        this.autoAdvance = autoAdvance;
    }
    
    public void setTypingSpeed(int speed) {
        this.typingSpeed = Math.max(1, speed);
    }
} 