/**
 * Represents a choice option in a dialogue
 */
public class DialogueChoice {
    private String text;
    private String action;
    private int nextNode = -1;
    
    public DialogueChoice() {
        this.text = "";
        this.action = null;
        this.nextNode = -1;
    }
    
    public DialogueChoice(String text) {
        this.text = text;
        this.action = null;
        this.nextNode = -1;
    }
    
    public DialogueChoice(String text, String action) {
        this.text = text;
        this.action = action;
        this.nextNode = -1;
    }
    
    // Getters and setters
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public int getNextNode() {
        return nextNode;
    }
    
    public void setNextNode(int nextNode) {
        this.nextNode = nextNode;
    }
    
    @Override
    public String toString() {
        return "DialogueChoice{" +
                "text='" + text + '\'' +
                ", action='" + action + '\'' +
                ", nextNode=" + nextNode +
                '}';
    }
} 