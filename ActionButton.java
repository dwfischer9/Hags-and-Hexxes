
import java.awt.Color;
import java.util.EventListener;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class ActionButton extends JButton implements EventListener {

    public ActionButton(String text) {
        super(text);
        this.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    /**
     * Displays the moves known by the player character and puts them on the screen.
     * These buttons trigger an attack and deal with the damage and rereshing the
     * stats of the foe.
     */

}
