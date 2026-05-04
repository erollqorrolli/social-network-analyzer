import java.util.*;

/**
 * This is the full graph object, which implements the edge object (as edges, obvi), and
 * userProfiles and nodes.
 */
public class Graph {

    private Map<UserProfile, List<Edge>> adjList;

    public Graph() {
        //Key as the user, value is the list of edges
        adjList = new HashMap<>();

    }

    /**
     * Checks if  a user is in the graph, if its not, it gets added to the graph. No edges to it yet.
     */
    public void addNode(UserProfile user) {
        if (adjList.containsKey(user)) {
            return;
        }
        adjList.put(user, new ArrayList<>());
    }

    /**
     * Checks if users are the hashmap, if they are, create a connection between them
     * If they are not, add them and then create the connection
     */
    public void addEdge(UserProfile user1, UserProfile user2, double weight) {
        //checks if user 1 is in the list
        if (!adjList.containsKey(user1)) {
            adjList.put(user1, new ArrayList<>());
        }
        //checks if suer 2 is in the list
        if (!adjList.containsKey(user2)) {
            adjList.put(user2, new ArrayList<>());
        }

        //create an edge between them. Done by adding to the arraylist to both
        List<Edge> neigh1 = adjList.get(user1);
        neigh1.add(new Edge(user1, user2, weight));

        List<Edge> neigh2 = adjList.get(user2);
        neigh2.add(new Edge(user2, user1, weight));

    }

    /**
     * returns the edges of the user, empty if user doesnt exist
     */
    public List<Edge> getEdges(UserProfile user) {
        //if does not exist return empty
        if (!adjList.containsKey(user)) {
            return new ArrayList<>();
        }

        //otherwise return the actual arraylist, may be empty tho but doesnt matter
        return adjList.get(user);
    }

    /**
     * Returns the neighborhood
     */
    public List<UserProfile> getNeighbors(UserProfile user) {
        List<Edge> edges = getEdges(user);

        List<UserProfile> res = new ArrayList<>();
        //iterate throguh each, get the neighbor
        for (Edge edge : edges) {
            res.add(edge.getOther(user));
        }
        return res;
    }

    /**
     * returns all the users
     */
    public Set<UserProfile> getAllNodes() {
        return adjList.keySet();
    }

    /**
     * TODO: Return ALL edges in the graph (no duplicates).
     * Since each edge is stored in two adjacency lists, be careful not to double-count.
     * Hint: use a Set<Edge> or only add edges where user1 < user2 alphabetically.
     */
    public List<Edge> getAllEdges() {
        List<Edge> edges = new ArrayList<>();
        Set<Edge> discovered = new HashSet<>();

        for (UserProfile u : adjList.keySet()) {
            for (Edge edge : adjList.get(u)) {
                if (!discovered.contains(edge)) {
                    edges.add(edge);
                    discovered.add(edge);
                }
            }
        }
        return edges;
    }

    //returns the number of nodes
    public int nodeCount() {
        return adjList.size();
    }

    //returns the number of edges
    public int edgeCount() {
        return getAllEdges().size();
    }
}