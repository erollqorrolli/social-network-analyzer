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
     * Stores the calculator and threshold.
     * The threshold controls how similar two users must be to get an edge.
     */
    public GraphBuilder(SimilarityCalc calculator, double threshold) {
        this.calculator = calculator;
        this.threshold = threshold;
    }

    /**
     *  Builds and returns a Graph from the given list of profiles.
     */
    public Graph buildGraph(List<UserProfile> profiles) {

        Graph graph = new Graph();
        for (UserProfile user : profiles) {
            graph.addNode(user);
        }

        int n = profiles.size();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                UserProfile u1 = profiles.get(i);
                UserProfile u2 = profiles.get(j);

                double userScore = calculator.computeScore(u1, u2);

                //only connect users w the minimum similarity requirement
                if (userScore >= threshold) {
                    graph.addEdge(u1, u2, userScore);
                }
            }
        }

        return graph;
    }

}