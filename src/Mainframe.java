import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

// main window that connects the ui to the backend logic
public class Mainframe extends JFrame {

    private ProfilePanel profilePanel;
    private ResultsPanel resultsPanel;

    // our backend data
    private List<UserProfile> allUsers;
    private Graph currentGraph;

    public Mainframe() {
        setTitle("social network analyzer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        allUsers = new ArrayList<>();
        currentGraph = new Graph();

        profilePanel = new ProfilePanel();
        resultsPanel = new ResultsPanel();

        // top panel for the action buttons
        JPanel topPanel = new JPanel();
        JButton dummyDataBtn = new JButton("load test data");
        JButton buildGraphBtn = new JButton("build graph");
        JButton recsBtn = new JButton("get recs");
        JButton clustersBtn = new JButton("find clusters");

        topPanel.add(dummyDataBtn);
        topPanel.add(buildGraphBtn);
        topPanel.add(recsBtn);
        topPanel.add(clustersBtn);

        // put the panels in the window
        add(topPanel, BorderLayout.NORTH);
        add(profilePanel, BorderLayout.WEST);
        add(resultsPanel, BorderLayout.CENTER);

        // --- button click listeners ---

        profilePanel.addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addUserFromForm();
            }
        });

        dummyDataBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadDummyData();
            }
        });

        buildGraphBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buildTheGraph();
            }
        });

        recsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runRecs();
            }
        });

        clustersBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runClusters();
            }
        });

        pack();
        setLocationRelativeTo(null); // center the window on screen
    }

    // reads the text boxes and makes a new user
    private void addUserFromForm() {
        String name = profilePanel.nameField.getText();
        if (name.isEmpty()) return;

        // split the text by comma to make the sets
        Set<String> interests = new HashSet<>(Arrays.asList(profilePanel.interestsField.getText().split(",")));
        Set<String> habits = new HashSet<>(Arrays.asList(profilePanel.habitsField.getText().split(",")));
        Set<String> goals = new HashSet<>(Arrays.asList(profilePanel.goalsField.getText().split(",")));

        UserProfile newUser = new UserProfile(name, interests, habits, goals);
        allUsers.add(newUser);
        currentGraph.addNode(newUser);

        resultsPanel.print("added user: " + name);

        // clear the boxes for the next person
        profilePanel.nameField.setText("");
        profilePanel.interestsField.setText("");
        profilePanel.habitsField.setText("");
        profilePanel.goalsField.setText("");
    }

    // makes fake users so we dont have to type them all out manually
    private void loadDummyData() {
        UserProfile u1 = new UserProfile("eroll",
                new HashSet<>(Arrays.asList("coding", "coffee", "design")),
                new HashSet<>(Arrays.asList("morning", "gym")),
                new HashSet<>(Arrays.asList("graduate", "internship")));

        UserProfile u2 = new UserProfile("alex",
                new HashSet<>(Arrays.asList("coding", "gaming")),
                new HashSet<>(Arrays.asList("night", "gym")),
                new HashSet<>(Arrays.asList("graduate")));

        UserProfile u3 = new UserProfile("sarah",
                new HashSet<>(Arrays.asList("art", "coffee")),
                new HashSet<>(Arrays.asList("morning", "reading")),
                new HashSet<>(Arrays.asList("travel", "internship")));

        allUsers.add(u1);
        allUsers.add(u2);
        allUsers.add(u3);

        currentGraph.addNode(u1);
        currentGraph.addNode(u2);
        currentGraph.addNode(u3);

        resultsPanel.print("loaded test data.");
    }

    // connects everyone together
    private void buildTheGraph() {
        if (allUsers.size() < 2) {
            resultsPanel.print("need more users to build a graph!");
            return;
        }

        // create the math calculator with weights for interests, habits, and goals (all equal here)
        SimilarityCalc calc = new SimilarityCalc(1.0, 1.0, 1.0);

        // create the builder with a threshold (0.1 means they need at least 10% similarity)
        GraphBuilder builder = new GraphBuilder(calc, 0.1);

        // build the graph using all users
        currentGraph = builder.buildGraph(allUsers);

        resultsPanel.print("network graph built! edges created.");
    }

    // runs our recommender logic on the first user
    private void runRecs() {
        if (allUsers.isEmpty()) return;

        UserProfile target = allUsers.get(0); // testing it on the first user
        Recommender rec = new Recommender(currentGraph);

        resultsPanel.print("\n--- recommendations for " + target.getName() + " ---");

        List<UserProfile> direct = rec.directRecommendations(target, 2);
        resultsPanel.print("top direct matches:");
        for (UserProfile u : direct) {
            resultsPanel.print(" - " + u.getName());
        }

        List<UserProfile> triadic = rec.triadicRecommendations(target, 2);
        resultsPanel.print("friends of friends:");
        for (UserProfile u : triadic) {
            resultsPanel.print(" - " + u.getName());
        }
    }

    // runs the cluster logic
    private void runClusters() {
        ClusterDetector cd = new ClusterDetector(currentGraph);
        Map<String, List<UserProfile>> clusters = cd.detectClusters(10);

        resultsPanel.print("\n--- friend groups (clusters) ---");
        for (String label : clusters.keySet()) {
            resultsPanel.print("group " + label + ":");
            for (UserProfile u : clusters.get(label)) {
                resultsPanel.print(" - " + u.getName());
            }
        }
    }

    public static void main(String[] args) {
        Mainframe frame = new Mainframe();
        frame.setVisible(true);
    }
}