import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SocialNetworkGraph {

    private final Map<String, Set<String>> adjList = new HashMap<>();

    public void addUser(String user) {
        adjList.putIfAbsent(user, new HashSet<>());
    }

    public void addFriendship(String u1, String u2) {
        addUser(u1);
        addUser(u2);
        adjList.get(u1).add(u2);
        adjList.get(u2).add(u1);
    }

    public void removeFriendship(String u1, String u2) {
        if (adjList.containsKey(u1)) adjList.get(u1).remove(u2);
        if (adjList.containsKey(u2)) adjList.get(u2).remove(u1);
    }

    public Set<String> getCommonFriends(String u1, String u2) {
        if (!adjList.containsKey(u1) || !adjList.containsKey(u2)) {
            return Collections.emptySet();
        }
        Set<String> common = new HashSet<>(adjList.get(u1));
        common.retainAll(adjList.get(u2));
        return common;
    }

    public List<String> getIsolatedUsers() {
        List<String> isolated = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : adjList.entrySet()) {
            if (entry.getValue().isEmpty()) {
                isolated.add(entry.getKey());
            }
        }
        return isolated;
    }

    public static void main(String[] args) {
        SocialNetworkGraph sn = new SocialNetworkGraph();
        sn.addFriendship("Alice", "Bob");
        sn.addFriendship("Alice", "Charlie");
        sn.addFriendship("Bob", "Charlie");
        sn.addUser("David");

        System.out.println("Alice 與 Bob 的共同好友: " + sn.getCommonFriends("Alice", "Bob"));
        System.out.println("孤立用戶: " + sn.getIsolatedUsers());
    }
}