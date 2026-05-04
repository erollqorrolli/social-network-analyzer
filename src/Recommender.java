import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * Recommends compatible users based on the graph structure.
 *
 * Two strategies:
 * 1. Direct: recommend the highest-weight neighbors.
 * 2. Triadic closure: recommend friends-of-friends who share many neighbors
 *    (people you're not directly connected to but likely compatible with).
 */
public class Recommender {

    private Graph graph;

    public Recommender(Graph graph) {
        // TODO
    }

    /**
     * TODO: Return the top-N directly connected users for the given user,
     * sorted by edge weight (highest first).
     *
     * Hint: get the user's edges, sort them by weight descending,
     * then take the first N and extract the other endpoint.
     */
    public List<UserProfile> directRecommendations(UserProfile user, int n) {
        // TODO
        return null;
    }

    /**
     * TODO: Recommend users via triadic closure.
     *
     * Find all users who are 2 hops away (friends-of-friends) but NOT
     * directly connected to the target user. Score each candidate by
     * how many mutual neighbors they share with the target user.
     * Return the top-N candidates sorted by mutual neighbor count (descending).
     *
     * Steps:
     * 1. Get the target user's direct neighbors into a Set
     * 2. For each neighbor, look at THEIR neighbors
     * 3. If that person is not the target and not already a direct neighbor,
     *    increment their mutual-neighbor count in a Map
     * 4. Sort by count descending, return top N
     */
    public List<UserProfile> triadicRecommendations(UserProfile user, int n) {
        // TODO
        return null;
    }
}