import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * computes network-level statistics about the social graph.
 * these are the metrics from your social networks course:
 * degree distribution, clustering coefficient, and bridge detection.
 */
public class NetworkStats {

    private Graph graph;

    // used globally for the bridge finding dfs algorithm
    private int timer = 0;

    public NetworkStats(Graph graph) {
        this.graph = graph;
    }

    /**
     * return a map from each user to their degree (number of edges).
     */
    public Map<UserProfile, Integer> degreeDistribution() {
        Map<UserProfile, Integer> dist = new HashMap<>();

        // wrapped in ArrayList to fix the Set vs List error!
        List<UserProfile> allUsers = new ArrayList<>(graph.getAllNodes());

        for (int i = 0; i < allUsers.size(); i++) {
            UserProfile u = allUsers.get(i);
            int degree = graph.getEdges(u).size();
            dist.put(u, degree);
        }

        return dist;
    }

    /**
     * compute the average degree of the graph.
     * average degree = (2 * number of edges) / number of nodes
     */
    public double averageDegree() {
        List<UserProfile> allUsers = new ArrayList<>(graph.getAllNodes());
        if (allUsers.isEmpty()) {
            return 0.0;
        }

        // sum of all degrees is exactly 2 * number of edges
        int sumOfDegrees = 0;
        for (int i = 0; i < allUsers.size(); i++) {
            UserProfile u = allUsers.get(i);
            sumOfDegrees += graph.getEdges(u).size();
        }

        return (double) sumOfDegrees / allUsers.size();
    }

    /**
     * compute the local clustering coefficient for a given user.
     */
    public double localClusteringCoefficient(UserProfile user) {
        List<UserProfile> neighbors = graph.getNeighbors(user);
        int degree = neighbors.size();

        // if they have 1 or 0 friends, they can't form a triangle
        if (degree <= 1) {
            return 0.0;
        }

        // put neighbors in a set so we can quickly check if they know each other
        Set<UserProfile> neighborSet = new HashSet<>();
        for (int i = 0; i < neighbors.size(); i++) {
            neighborSet.add(neighbors.get(i));
        }

        int actualEdges = 0;

        // check every pair of friends to see if they are also friends
        for (int i = 0; i < neighbors.size(); i++) {
            UserProfile friend1 = neighbors.get(i);

            for (int j = i + 1; j < neighbors.size(); j++) {
                UserProfile friend2 = neighbors.get(j);

                // if friend1's friend list contains friend2, they share an edge
                if (graph.getNeighbors(friend1).contains(friend2)) {
                    actualEdges++;
                }
            }
        }

        // math formula for max possible edges between k nodes: k * (k - 1) / 2
        double maxPossibleEdges = (degree * (degree - 1.0)) / 2.0;

        return actualEdges / maxPossibleEdges;
    }

    /**
     * compute the average clustering coefficient across all nodes.
     */
    public double averageClusteringCoefficient() {
        List<UserProfile> allUsers = new ArrayList<>(graph.getAllNodes());
        if (allUsers.isEmpty()) {
            return 0.0;
        }

        double totalClustering = 0.0;
        for (int i = 0; i < allUsers.size(); i++) {
            totalClustering += localClusteringCoefficient(allUsers.get(i));
        }

        return totalClustering / allUsers.size();
    }

    /**
     * find all bridge edges in the graph using classic tarjan dfs.
     */
    public List<Edge> findBridges() {
        List<Edge> bridges = new ArrayList<>();
        Map<UserProfile, Integer> disc = new HashMap<>();
        Map<UserProfile, Integer> low = new HashMap<>();
        Set<UserProfile> visited = new HashSet<>();

        timer = 0; // reset the timer just in case it runs twice

        List<UserProfile> allUsers = new ArrayList<>(graph.getAllNodes());

        for (int i = 0; i < allUsers.size(); i++) {
            UserProfile node = allUsers.get(i);
            if (!visited.contains(node)) {
                bridgeDfs(node, null, visited, disc, low, bridges);
            }
        }

        return bridges;
    }

    // recursive helper function for finding bridges
    private void bridgeDfs(UserProfile node, UserProfile parent, Set<UserProfile> visited,
                           Map<UserProfile, Integer> disc, Map<UserProfile, Integer> low, List<Edge> bridges) {

        visited.add(node);
        disc.put(node, timer);
        low.put(node, timer);
        timer++;

        List<Edge> edges = graph.getEdges(node);

        for (int i = 0; i < edges.size(); i++) {
            Edge edge = edges.get(i);

            // find out who is on the other side of this edge
            UserProfile neighbor;
            if (edge.getUser1().equals(node)) {
                neighbor = edge.getUser2();
            } else {
                neighbor = edge.getUser1();
            }

            // don't go backwards to the person who just called us
            if (neighbor.equals(parent)) {
                continue;
            }

            if (visited.contains(neighbor)) {
                // this is a back edge, update the low value
                int minLow = Math.min(low.get(node), disc.get(neighbor));
                low.put(node, minLow);
            } else {
                // go deeper into the graph
                bridgeDfs(neighbor, node, visited, disc, low, bridges);

                // when we come back up, update the low value
                int minLow = Math.min(low.get(node), low.get(neighbor));
                low.put(node, minLow);

                // if the lowest node reachable from the neighbor is strictly below our discovery time,
                // it means cutting this edge traps the neighbor on an island!
                if (low.get(neighbor) > disc.get(node)) {
                    bridges.add(edge);
                }
            }
        }
    }

    /**
     * compute the graph density.
     */
    public double graphDensity() {
        List<UserProfile> allUsers = new ArrayList<>(graph.getAllNodes());
        int n = allUsers.size();

        if (n < 2) {
            return 0.0; // you can't have density without at least 2 people
        }

        // sum of all degrees is exactly 2 * number of edges
        int sumOfDegrees = 0;
        for (int i = 0; i < n; i++) {
            UserProfile u = allUsers.get(i);
            sumOfDegrees += graph.getEdges(u).size();
        }

        int edgeCount = sumOfDegrees / 2;

        // formula: (2 * edges) / (nodes * (nodes - 1))
        return (2.0 * edgeCount) / (n * (n - 1.0));
    }
}