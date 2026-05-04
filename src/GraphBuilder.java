import java.util.List;

/**
 * Builds a weighted compatibility graph from a list of user profiles.
 *
 * For every pair of users, compute a similarity score.
 * If the score is above a threshold, add an edge to the graph.
 */
public class GraphBuilder {

    private SimilarityCalc calculator;
    private double threshold;

    /**
     * TODO: Store the calculator and threshold.
     * The threshold controls how similar two users must be to get an edge.
     * A good starting value is 0.1 (so only completely unrelated users are excluded).
     */
    public GraphBuilder(SimilarityCalc calculator, double threshold) {
        // TODO
    }

    /**
     * TODO: Build and return a Graph from the given list of profiles.
     *
     * Steps:
     * 1. Create a new Graph
     * 2. Add every user as a node
     * 3. For every pair (i, j) where i < j:
     *    a. Compute the similarity score
     *    b. If score >= threshold, add an edge with that score as weight
     * 4. Return the graph
     *
     * Note: loop with i from 0 to n-1, j from i+1 to n-1 to avoid
     * duplicate pairs and self-comparisons.
     */
    public Graph buildGraph(List<UserProfile> profiles) {
        // TODO
        return null;
    }
}