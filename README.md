# Social Network Analyzer

A Java app that models friendships based on shared traits. It builds a weighted graph of users, recommends friends, and detects communities.

## Features

- **Profiles**: create users by picking interests, habits, and goals
- **Graph building**: connects users whose compatibility passes a threshold
- **Recommendations**
  - Direct: your closest matches by shared traits
  - Friends of friends: people you share mutual connections with (triadic closure)
- **Community detection**: groups the network into clusters with label propagation
- **Test data**: one click loads 10 sample profiles

## Running it

1. Run `Mainframe.java` to launch the Swing UI.
2. Click **Load Test Data**, or add users manually.
3. Click **Build Graph**.
4. Pick a user from the **Target** dropdown.
5. Click **Get Recs** or **Find Clusters**.

## How it works

**Graph.** An adjacency list where each `UserProfile` is a node and each `Edge` stores a compatibility weight.

**Compatibility.** Jaccard similarity over each pair's combined traits:

```
shared traits / total unique traits
```

Pairs scoring at least 0.1 get an edge.

**Direct recommendations.** Sort the user's edges by weight and return the top matches.

**Friends of friends.** For each second-degree connection, count mutual friends with the user and rank by that count.

**Communities.** Label propagation:
1. Every user starts with their own label.
2. Each round, a user adopts the label with the highest total edge weight among their friends.
3. This repeats until no labels change (capped at 10 rounds).

Users sharing a label form a cluster. A BFS fallback finds fully disconnected groups.

## Assumptions

- User names are unique (`equals`/`hashCode` use the name).
- Interests, habits, and goals are weighted equally.
- Users sharing nothing stay disconnected.

## Tech

Java, Swing
