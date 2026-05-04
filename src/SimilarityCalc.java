import java.util.Set;
import java.util.HashSet;

/**
 * Computes compatibility scores between two UserProfiles.
 * Uses Jaccard similarity: |A ∩ B| / |A ∪ B|
 *
 * The overall score is a weighted average of Jaccard similarity
 * across interests, habits, and goals.
 */
public class SimilarityCalc {

    // Weights for each category (should sum to 1.0)
    private double interestWeight;
    private double habitWeight;
    private double goalWeight;

    /**
     * TODO: Store the weights. Default suggestion: 0.4, 0.3, 0.3
     */
    public SimilarityCalc(double interestWeight, double habitWeight, double goalWeight) {
        // TODO
    }

    /**
     * TODO: Compute Jaccard similarity between two sets.
     *
     * Jaccard(A, B) = |A ∩ B| / |A ∪ B|
     *
     * Edge case: if both sets are empty, return 0.0 (not NaN).
     *
     * Hint: create new HashSets for intersection and union so you
     * don't modify the originals. Use retainAll() for intersection
     * and addAll() for union.
     */
    public double jaccardSimilarity(Set<String> set1, Set<String> set2) {
        // TODO
        return 0.0;
    }

    /**
     * TODO: Compute the overall compatibility score between two users.
     *
     * Score = (interestWeight * jaccard(interests1, interests2))
     *       + (habitWeight   * jaccard(habits1, habits2))
     *       + (goalWeight    * jaccard(goals1, goals2))
     *
     * Return a value between 0.0 and 1.0.
     */
    public double computeScore(UserProfile u1, UserProfile u2) {
        // TODO
        return 0.0;
    }
}