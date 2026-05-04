import java.util.Set;
import java.util.HashSet;

/**
 * Object that represents a user in our profile. Stores all relevant information such as their name and
 * attributes that will go towards their scoring
 */
public class UserProfile {

    private String name;
    private Set<String> interests;
    private Set<String> habits;
    private Set<String> goals;

    /**
     * uses empty hashsets if there is no input
     */
    public UserProfile(String name, Set<String> interests, Set<String> habits, Set<String> goals) {
        this.name = name;
        this.interests = interests;
        this.habits = habits;
        this.goals = goals;
    }

    // --- Getters ---

    public String getName() {
        return name;
    }

    public Set<String> getInterests() {
        return interests;
    }

    public Set<String> getHabits() {
        return habits;
    }

    public Set<String> getGoals() {
        return goals;
    }

    /**
     * Funciton to return info of user, includes everythign
     */
    @Override
    public String toString() {
        return "Name: " + name + " " + "Interests: " + interests + " " + "Goals: " + goals;
    }

    /**
     * Checks if two users are the same *ASSUMES ALL USERS HAVE UNIQUE NAME*
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserProfile other = (UserProfile) o;
        return name.equals(other.name);
    }

    /**
     *
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}