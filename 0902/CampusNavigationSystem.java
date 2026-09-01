import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CampusNavigationSystem {

    private final Map<String, Integer> locationToId = new HashMap<>();
    private final Map<Integer, String> idToLocation = new HashMap<>();
    private final Map<Integer, List<Integer>> adjList = new HashMap<>();
    private int idCounter = 0;

    public void addLocation(String name) {
        if (!locationToId.containsKey(name)) {
            locationToId.put(name, idCounter);
            idToLocation.put(idCounter, name);
            adjList.put(idCounter, new ArrayList<>());
            idCounter++;
        }
    }

    public void addPath(String src, String dest) {
        addLocation(src);
        addLocation(dest);
        int u = locationToId.get(src);
        int v = locationToId.get(dest);
        adjList.get(u).add(v);
        adjList.get(v).add(u);
    }

    public List<String> findShortestPath(String start, String end) {
        if (!locationToId.containsKey(start) || !locationToId.containsKey(end)) {
            return Collections.emptyList();
        }

        int startId = locationToId.get(start);
        int endId = locationToId.get(end);

        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> parentMap = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(startId);
        visited.add(startId);

        boolean found = false;
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            if (curr == endId) {
                found = true;
                break;
            }

            for (int neighbor : adjList.getOrDefault(curr, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, curr);
                    queue.add(neighbor);
                }
            }
        }

        if (!found) return Collections.emptyList();

        List<String> path = new ArrayList<>();
        Integer curr = endId;
        while (curr != null) {
            path.add(idToLocation.get(curr));
            curr = parentMap.get(curr);
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        CampusNavigationSystem nav = new CampusNavigationSystem();
        nav.addPath("Gate", "Library");
        nav.addPath("Library", "CS_Building");
        nav.addPath("Gate", "Cafeteria");
        nav.addPath("Cafeteria", "CS_Building");

        System.out.println("Shortest path: " + nav.findShortestPath("Gate", "CS_Building"));
    }
}