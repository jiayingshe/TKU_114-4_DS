import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Q10_UnweightedShortestPath {
    public static List<String> shortestPath(Map<String, List<String>> graph, String start, String target) {
        if (graph == null || start == null || target == null) return new ArrayList<>();
        if (!graph.containsKey(start) || !graph.containsKey(target)) return new ArrayList<>();
        
        if (start.equals(target)) {
            List<String> single = new ArrayList<>();
            single.add(start);
            return single;
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> predecessor = new HashMap<>();

        queue.offer(start);
        visited.add(start);

        boolean found = false;
        while (!queue.isEmpty() && !found) {
            String curr = queue.poll();
            
            List<String> neighbors = graph.get(curr);
            if (neighbors != null) {
                for (String neighbor : neighbors) {
                    if (graph.containsKey(neighbor) && !visited.contains(neighbor)) {
                        visited.add(neighbor);
                        predecessor.put(neighbor, curr);
                        if (neighbor.equals(target)) {
                            found = true;
                            break;
                        }
                        queue.offer(neighbor);
                    }
                }
            }
        }

        if (!found) return new ArrayList<>();

        List<String> path = new ArrayList<>();
        String step = target;
        while (step != null) {
            path.add(step);
            step = predecessor.get(step);
        }
        Collections.reverse(path);
        return path;
    }
}