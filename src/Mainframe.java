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

    // the dropdown to select who gets recommendations
    private JComboBox<String> userDropdown;

    // flag to stop test data from loading multiple times
    private boolean testDataLoaded = false;

    public Mainframe() {
        setTitle("social network analyzer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        allUsers = new ArrayList<>();
        currentGraph = new Graph();

        profilePanel = new ProfilePanel();
        resultsPanel = new ResultsPanel();
        userDropdown = new JComboBox<>();

        // top panel for the action buttons
        JPanel topPanel = new JPanel();
        JButton helpBtn = new JButton("how to use");
        JButton dummyDataBtn = new JButton("load test data");
        JButton buildGraphBtn = new JButton("build graph");
        JButton recsBtn = new JButton("get recs");
        JButton clustersBtn = new JButton("find clusters");

        topPanel.add(helpBtn);
        topPanel.add(dummyDataBtn);
        topPanel.add(buildGraphBtn);
        topPanel.add(new JLabel(" target:"));
        topPanel.add(userDropdown);
        topPanel.add(recsBtn);
        topPanel.add(clustersBtn);

        // put the panels in the window
        add(topPanel, BorderLayout.NORTH);
        add(profilePanel, BorderLayout.WEST);
        add(resultsPanel, BorderLayout.CENTER);

        // --- button click listeners ---

        helpBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showHelp();
            }
        });

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

    // shows a simple popup with instructions
    private void showHelp() {
        String msg = "how to use this app:\n\n" +
                "1. click 'load test data' (or add your own users on the left).\n" +
                "2. click 'build graph' to connect everyone together.\n" +
                "3. pick a person from the 'target' dropdown menu.\n" +
                "4. click 'get recs' to see who they should be friends with.\n" +
                "5. click 'find clusters' to see the overall friend groups.";
        JOptionPane.showMessageDialog(this, msg, "instructions", JOptionPane.INFORMATION_MESSAGE);
    }

    // reads the list selections and makes a new user
    private void addUserFromForm() {
        String name = profilePanel.nameField.getText();
        if (name.isEmpty()) return;

        // grab the highlighted items from the ui lists and put them in sets
        Set<String> interests = new HashSet<>(profilePanel.interestsList.getSelectedValuesList());
        Set<String> habits = new HashSet<>(profilePanel.habitsList.getSelectedValuesList());
        Set<String> goals = new HashSet<>(profilePanel.goalsList.getSelectedValuesList());

        UserProfile newUser = new UserProfile(name, interests, habits, goals);
        allUsers.add(newUser);
        currentGraph.addNode(newUser);
        userDropdown.addItem(name);

        resultsPanel.print("added user: " + name);

        // clear the inputs for the next person
        profilePanel.nameField.setText("");
        profilePanel.interestsList.clearSelection();
        profilePanel.habitsList.clearSelection();
        profilePanel.goalsList.clearSelection();
    }

    // creates a larger dataset of 10 users to test clustering
    private void loadDummyData() {
        if (testDataLoaded) {
            resultsPanel.print("test data already loaded!");
            return;
        }

        // tech/product focused group
        UserProfile u1 = new UserProfile("eroll",
                new HashSet<>(Arrays.asList("coding", "espresso", "fishing")),
                new HashSet<>(Arrays.asList("night owl", "gym")),
                new HashSet<>(Arrays.asList("product management", "internship")));

        UserProfile u2 = new UserProfile("alex",
                new HashSet<>(Arrays.asList("coding", "gaming")),
                new HashSet<>(Arrays.asList("night owl")),
                new HashSet<>(Arrays.asList("internship", "startup")));

        UserProfile u3 = new UserProfile("jessica",
                new HashSet<>(Arrays.asList("design", "espresso", "coding")),
                new HashSet<>(Arrays.asList("night owl", "reading")),
                new HashSet<>(Arrays.asList("product management", "startup")));

        UserProfile u4 = new UserProfile("chris",
                new HashSet<>(Arrays.asList("gaming", "coding")),
                new HashSet<>(Arrays.asList("night owl", "gym")),
                new HashSet<>(Arrays.asList("internship")));

        // creative/morning group
        UserProfile u5 = new UserProfile("sarah",
                new HashSet<>(Arrays.asList("art", "design")),
                new HashSet<>(Arrays.asList("morning person", "reading")),
                new HashSet<>(Arrays.asList("graduate", "travel")));

        UserProfile u6 = new UserProfile("emily",
                new HashSet<>(Arrays.asList("art", "music")),
                new HashSet<>(Arrays.asList("morning person", "reading")),
                new HashSet<>(Arrays.asList("travel")));

        UserProfile u7 = new UserProfile("anna",
                new HashSet<>(Arrays.asList("espresso", "music", "art")),
                new HashSet<>(Arrays.asList("morning person", "cooking")),
                new HashSet<>(Arrays.asList("graduate", "travel")));

        // mixed outdoors/casual group
        UserProfile u8 = new UserProfile("mike",
                new HashSet<>(Arrays.asList("fishing", "gaming")),
                new HashSet<>(Arrays.asList("morning person", "gym")),
                new HashSet<>(Arrays.asList("graduate")));

        UserProfile u9 = new UserProfile("david",
                new HashSet<>(Arrays.asList("fishing", "music")),
                new HashSet<>(Arrays.asList("night owl", "cooking")),
                new HashSet<>(Arrays.asList("startup")));

        UserProfile u10 = new UserProfile("tom",
                new HashSet<>(Arrays.asList("fishing", "art")),
                new HashSet<>(Arrays.asList("morning person", "reading")),
                new HashSet<>(Arrays.asList("graduate")));

        // add them all to the system
        allUsers.addAll(Arrays.asList(u1, u2, u3, u4, u5, u6, u7, u8, u9, u10));

        for (int i = 0; i < allUsers.size(); i++) {
            currentGraph.addNode(allUsers.get(i));
            userDropdown.addItem(allUsers.get(i).getName());
        }

        testDataLoaded = true; // lock it so it can't load again
        resultsPanel.print("loaded 10 test profiles.");
    }

    // connects everyone together
    private void buildTheGraph() {
        if (allUsers.size() < 2) {
            resultsPanel.print("need more users to build a graph!");
            return;
        }

        // create the math calculator with weights for interests, habits, and goals
        SimilarityCalc calc = new SimilarityCalc(1.0, 1.0, 1.0);

        // create the builder with a threshold
        GraphBuilder builder = new GraphBuilder(calc, 0.1);

        // build the graph using all users
        currentGraph = builder.buildGraph(allUsers);

        resultsPanel.print("network graph built! edges created.");
    }

    // helper method to find what two people have in common
    private String getSharedTraits(UserProfile u1, UserProfile u2) {
        List<String> shared = new ArrayList<>();

        for (String item : u1.getInterests()) {
            if (u2.getInterests().contains(item)) shared.add(item);
        }
        for (String item : u1.getHabits()) {
            if (u2.getHabits().contains(item)) shared.add(item);
        }
        for (String item : u1.getGoals()) {
            if (u2.getGoals().contains(item)) shared.add(item);
        }

        // if they have nothing directly in common, they are triadic mutual friends
        if (shared.isEmpty()) return "mutual friends";

        // join the list into a clean comma-separated string
        String result = "";
        for (int i = 0; i < shared.size(); i++) {
            result += shared.get(i);
            if (i < shared.size() - 1) {
                result += ", ";
            }
        }
        return result;
    }

    // runs our recommender logic on the selected user from the dropdown
    private void runRecs() {
        if (allUsers.isEmpty()) return;

        int selectedIndex = userDropdown.getSelectedIndex();
        if (selectedIndex == -1) {
            resultsPanel.print("please select a user first!");
            return;
        }

        UserProfile target = allUsers.get(selectedIndex);
        Recommender rec = new Recommender(currentGraph);

        resultsPanel.print("\n--- recommendations for " + target.getName() + " ---");

        List<UserProfile> direct = rec.directRecommendations(target, 2);
        resultsPanel.print("top direct matches:");
        for (int i = 0; i < direct.size(); i++) {
            UserProfile match = direct.get(i);
            String reason = getSharedTraits(target, match);
            resultsPanel.print(" - " + match.getName() + " (shared: " + reason + ")");
        }

        List<UserProfile> triadic = rec.triadicRecommendations(target, 2);
        resultsPanel.print("friends of friends:");
        for (int i = 0; i < triadic.size(); i++) {
            UserProfile match = triadic.get(i);
            String reason = getSharedTraits(target, match);
            resultsPanel.print(" - " + match.getName() + " (shared: " + reason + ")");
        }
    }

    // runs the cluster logic
    private void runClusters() {
        ClusterDetector cd = new ClusterDetector(currentGraph);
        Map<String, List<UserProfile>> clusters = cd.detectClusters(10);

        resultsPanel.print("\n--- friend groups (clusters) ---");
        for (String label : clusters.keySet()) {
            resultsPanel.print("group " + label + ":");
            List<UserProfile> groupMembers = clusters.get(label);
            for (int i = 0; i < groupMembers.size(); i++) {
                resultsPanel.print(" - " + groupMembers.get(i).getName());
            }
        }
    }

    // java needs this to actually launch the window
    public static void main(String[] args) {
        Mainframe frame = new Mainframe();
        frame.setVisible(true);
    }
}