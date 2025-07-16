import java.util.List;

/**
 * Represents a single node in a dialogue tree
 */
public class DialogueNode {
    private int id;
    private String text;
    private String condition;
    private String action;
    private int nextNode = -1; // -1 means end of dialogue
    private List<DialogueChoice> choices;
    
    public DialogueNode() {
        this.id = -1;
        this.text = "";
        this.condition = null;
        this.action = null;
        this.nextNode = -1;
        this.choices = null;
    }
    
    public DialogueNode(int id, String text) {
        this.id = id;
        this.text = text;
        this.condition = null;
        this.action = null;
        this.nextNode = -1;
        this.choices = null;
    }
    
    // Getters and setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getCondition() {
        return condition;
    }
    
    public void setCondition(String condition) {
        this.condition = condition;
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
    
    public List<DialogueChoice> getChoices() {
        return choices;
    }
    
    public void setChoices(List<DialogueChoice> choices) {
        this.choices = choices;
    }
    
    public boolean hasChoices() {
        return choices != null && !choices.isEmpty();
    }
    
    @Override
    public String toString() {
        return "DialogueNode{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", condition='" + condition + '\'' +
                ", action='" + action + '\'' +
                ", nextNode=" + nextNode +
                ", hasChoices=" + hasChoices() +
                '}';
    }
} 