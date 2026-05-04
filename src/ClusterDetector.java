import java.util.List;
import java.util.Map;

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
        // TODO
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
     *   return result
     */
    public Map<String, List<UserProfile>> detectClusters(int maxIterations) {
        // TODO
        return null;
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
        // TODO
        return null;
    }
}