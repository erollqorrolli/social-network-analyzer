import javax.swing.*;
import java.awt.*;

// panel for adding new users to the system
public class ProfilePanel extends JPanel {

    public JTextField nameField;
    public JList<String> interestsList;
    public JList<String> habitsList;
    public JList<String> goalsList;
    public JButton addButton;

    public ProfilePanel() {
        // border layout to hold the form and the button nicely
        setLayout(new BorderLayout(10, 10));

        // grid layout for the inputs
        JPanel grid = new JPanel(new GridLayout(4, 2, 5, 5));

        nameField = new JTextField();

        // predefined options for people to click
        String[] interestOptions = {"coding", "espresso", "fishing", "design", "gaming", "art", "music"};
        String[] habitOptions = {"morning person", "night owl", "gym", "reading", "cooking"};
        String[] goalOptions = {"internship", "product management", "graduate", "travel", "startup"};

        interestsList = new JList<>(interestOptions);
        habitsList = new JList<>(habitOptions);
        goalsList = new JList<>(goalOptions);

        // add scroll bars to the lists so they fit nicely
        grid.add(new JLabel(" name:"));
        grid.add(nameField);

        grid.add(new JLabel(" interests (ctrl/cmd to pick many):"));
        grid.add(new JScrollPane(interestsList));

        grid.add(new JLabel(" habits (ctrl/cmd to pick many):"));
        grid.add(new JScrollPane(habitsList));

        grid.add(new JLabel(" goals (ctrl/cmd to pick many):"));
        grid.add(new JScrollPane(goalsList));

        add(grid, BorderLayout.CENTER);

        addButton = new JButton("add user");
        add(addButton, BorderLayout.SOUTH);
    }
}