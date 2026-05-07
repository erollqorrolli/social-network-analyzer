/**
 * A weighted edge in the graph, which represents how similar two people are to each other
 * Edges are undirected in our case.
 */
public class Edge {

    private UserProfile user1;
    private UserProfile user2;
    private double weight;

    public Edge(UserProfile user1, UserProfile user2, double weight) {
        this.user1 = user1;
        this.user2 = user2;
        this.weight = weight;
    }

    public UserProfile getUser1() {
        return user1;
    }

    public UserProfile getUser2() {
        return user2;
    }

    public double getWeight() {
        return weight;
    }

    /**
     * given an edge and a user, returns the other user.
     * Throws illegal argument exception if the user isnt part of edge.
     */
    public UserProfile getOther(UserProfile user) {
        if (user.equals(user1)) {
            return user2;
        } else if (user.equals(user2)) {
            return user1;
        }
        throw new IllegalArgumentException("User not found in edge");
    }

    //translates our edge to a string,
    @Override
    public String toString() {
        return "Edge Weight: " + weight + " User 1: " + user1 + " User 2: " + user2;
    }
}