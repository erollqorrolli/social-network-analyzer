import javax.swing.*;
import java.awt.*;

// panel to show the text output from our algorithms
public class ResultsPanel extends JPanel {

    public JTextArea textArea;

    public ResultsPanel() {
        setLayout(new BorderLayout());

        textArea = new JTextArea(20, 40);
        textArea.setEditable(false); // don't let the user type in the results box

        // add a scrollbar just in case the text gets too long
        JScrollPane scroll = new JScrollPane(textArea);
        add(scroll, BorderLayout.CENTER);
    }

    // helper method to easily print lines to the box
    public void print(String text) {
        textArea.append(text + "\n");
    }
}