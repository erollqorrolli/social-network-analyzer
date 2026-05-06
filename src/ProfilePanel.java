import javax.swing.*;
import java.awt.*;

// panel for adding new users to the system
public class ProfilePanel extends JPanel {

    public JTextField nameField;
    public JTextField interestsField;
    public JTextField habitsField;
    public JTextField goalsField;
    public JButton addButton;

    public ProfilePanel() {
        // simple grid layout: 5 rows, 2 columns
        setLayout(new GridLayout(5, 2, 5, 5));

        // initialize the text boxes
        nameField = new JTextField();
        interestsField = new JTextField();
        habitsField = new JTextField();
        goalsField = new JTextField();
        addButton = new JButton("add user");

        // add everything to the panel
        add(new JLabel(" name:"));
        add(nameField);
        add(new JLabel(" interests (comma separated):"));
        add(interestsField);
        add(new JLabel(" habits (comma separated):"));
        add(habitsField);
        add(new JLabel(" goals (comma separated):"));
        add(goalsField);
        add(new JLabel("")); // empty space to push button to the right
        add(addButton);
    }
}