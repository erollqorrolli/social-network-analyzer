import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;


/**
 * Detects communities/clusters in the social graph.
 */
public class ClusterDetector {

    private Graph graph;

    public ClusterDetector(Graph graph) {
        this.graph = graph;
    }

    /**
     * Run label propagation and return a map from cluster label -> list of users.
     */

    public Map<String, List<UserProfile>> detectClusters(int maxIterations) {
        Map<UserProfile, String> labels = new HashMap<>();


        //give everyone labels
        for (UserProfile node:  graph.getAllNodes()) {
            labels.put(node, node.getName());
        }


        //trend loop
        for (int i = 0; i < maxIterations; i++) {
            boolean changed = false;

            for(UserProfile user : graph.getAllNodes()) {
                Map<String, Double> weights = new HashMap<>();

                //look at neighbors
                for (Edge e : graph.getEdges(user)) {
                    UserProfile friend;
                    if (e.getUser1().equals(user)) {
                        friend = e.getUser2();
                    } else {
                        friend = e.getUser1();
                    }

                    String friendLabel = labels.get(friend);


                    //add up the weights for this label
                    if (weights.containsKey(friendLabel)) {
                        double oldWeight = weights.get(friendLabel);
                        weights.put(friendLabel, oldWeight + e.getWeight());

                    } else {
                        weights.put(friendLabel, e.getWeight());
                    }
                }

                //find the label w the higherst weight
                String bestLabel = labels.get(user);
                double max = -1.0;

                for (String lbl : weights.keySet()) {
                    double w =  weights.get(lbl);
                    if (w > max) {
                        max = w;
                        bestLabel = lbl;
                    }
                }

                //swap labels if we find a better one

                if (!bestLabel.equals(labels.get(user))) {
                    labels.put(user,bestLabel);
                    changed = true;
                }
            }

            if (!changed) {
                break;
            }

        }

        //group by final labels
        Map<String, List<UserProfile>> result = new HashMap<>();

        for (UserProfile u : graph.getAllNodes()) {
            String lbl = labels.get(u);

            if (!result.containsKey(lbl)) {
                result.put(lbl, new ArrayList<>());
            }
            result.get(lbl).add(u);
        }
        return result;
    }

    /**
     * Simpler alternative — find connected components using BFS.
     *
     * useful if  graph is sparse (high threshold) and
     * naturally splits into disconnected groups.
     *
     * For each unvisited node, runs BFS and collect all reachable nodes
     * into one component. Returns the list of components.
     */
    public List<List<UserProfile>> connectedComponents() {

        List<List<UserProfile>> components = new ArrayList<>();
        Set<UserProfile> visited = new HashSet<>();

        for (UserProfile node : graph.getAllNodes()) {

            // if we haven't visited this person they start a new island
            if (!visited.contains(node)) {

                List<UserProfile> currentIsland = new ArrayList<>();
                Queue<UserProfile> q = new LinkedList<>();

                q.add(node);
                visited.add(node);

                while (q.size() > 0) {
                    UserProfile curr = q.remove();
                    currentIsland.add(curr);

                    for (UserProfile friend : graph.getNeighbors(curr)) {
                        if (!visited.contains(friend)) {
                            visited.add(friend);
                            q.add(friend);
                        }
                    }
                }

                components.add(currentIsland);
            }
        }

        return components;
    }
}
