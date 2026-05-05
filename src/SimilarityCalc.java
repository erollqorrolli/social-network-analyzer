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
        this.interestWeight = interestWeight;
        this.habitWeight = habitWeight;
        this.goalWeight = goalWeight;
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
        if (set1.isEmpty() && set2.isEmpty()) {
            return 0.0;
        }

        //hashests for intersection and union
        Set<String> intersection = new HashSet<>(set1);
        Set<String> union = new HashSet<>(set1);


        intersection.retainAll(set2);
        union.addAll(set2);

        return (double) intersection.size()/ union.size();
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
        return (interestWeight * jaccardSimilarity(u1.getInterests(), u2.getInterests()))
                + (habitWeight * jaccardSimilarity(u1.getHabits(), u2.getHabits()))
                + (goalWeight * jaccardSimilarity(u1.getGoals(), u2.getGoals()));
    }
}