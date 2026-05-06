import java.util.*;

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

        this.graph = graph;
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

        List<Edge> edges = graph.getEdges(user);

        //sort by edge weights
        Collections.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                return Double.compare(e2.getWeight(), e1.getWeight());
            }
        });

        List<UserProfile> recs = new ArrayList<>();

        //loop through the sorted edges
        for (int i = 0; i < edges.size(); i++) {

            if (recs.size() == n) {
                break;
            }

            Edge edge = edges.get(i);

            //find which side of the edge is the friend
            UserProfile friend;
            if (edge.getUser1().equals(user)) {
                friend = edge.getUser2();
            } else {
                friend = edge.getUser1();
            }

            recs.add(friend);
        }
        return recs;
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
        Set<UserProfile> directFriends = new HashSet<>(graph.getNeighbors(user));

        Map<UserProfile, Integer> mutualsCount = new HashMap<>();

        for(UserProfile friend : directFriends) {
            //look at their neighbors
            for (UserProfile fof: graph.getNeighbors(friend)) {
                if(!fof.equals(user) && !directFriends.contains(fof)){
                    int currentCount = mutualsCount.getOrDefault(fof, 0);
                    mutualsCount.put(fof, currentCount + 1);
                }
            }
        }


        List<Map.Entry<UserProfile, Integer>> entryList = new ArrayList<>(mutualsCount.entrySet());

        Collections.sort(entryList, new Comparator<Map.Entry<UserProfile, Integer>>() {
            @Override
            public int compare(Map.Entry<UserProfile, Integer> e1, Map.Entry<UserProfile, Integer> e2) {
                return Integer.compare(e2.getValue(), e1.getValue());
            }
        });

        List<UserProfile> finalRecs = new ArrayList<>();
        for (int i = 0; i < entryList.size(); i++) {
            if (finalRecs.size() >= n) {
                break;
            }
            finalRecs.add(entryList.get(i).getKey());
        }

        return finalRecs;

    }
}