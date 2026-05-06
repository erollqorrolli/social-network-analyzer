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
 *
 * Uses label propagation:
 * 1. Assign each node a unique label (its own name works).
 * 2. Repeatedly: each node adopts the most common label among its neighbors
 *    (weighted by edge weight). Break ties however you want.
 * 3. Stop when no labels change (or after a max number of iterations).
 * 4. Nodes with the same label are in the same cluster.
 */
public class ClusterDetector {

    private Graph graph;

    public ClusterDetector(Graph graph) {
        this.graph = graph;
    }

    /**
     * TODO: Run label propagation and return a map from cluster label -> list of users.
     *
     * Pseudocode:
     *   labels = {} // Map<UserProfile, String>
     *   for each node: labels[node] = node.getName()
     *
     *   repeat up to maxIterations times:
     *     changed = false
     *     for each node:
     *       // Count up the total edge weight for each neighbor label
     *       weightByLabel = {} // Map<String, Double>
     *       for each edge of node:
     *         neighborLabel = labels[edge.getOther(node)]
     *         weightByLabel[neighborLabel] += edge.getWeight()
     *
     *       // Pick the label with highest total weight
     *       bestLabel = label with max value in weightByLabel
     *       if bestLabel != labels[node]:
     *         labels[node] = bestLabel
     *         changed = true
     *
     *     if !changed: break
     *
     *   // Group users by their final labels
     *   result = {} // Map<String, List<UserProfile>>
     *   for each node: add node to result[labels[node]]
     *   return result=*/
    public Map<String, List<UserProfile>> detectClusters(int maxIterations) {
        // TODO

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
     * TODO: Simpler alternative — find connected components using BFS.
     *
     * This is useful if your graph is sparse (high threshold) and
     * naturally splits into disconnected groups.
     *
     * For each unvisited node, run BFS/DFS and collect all reachable nodes
     * into one component. Return the list of components.
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
