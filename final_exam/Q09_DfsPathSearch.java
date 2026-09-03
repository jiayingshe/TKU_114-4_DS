import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Q09_DfsPathSearch {
    public static List<String> dfs(Map<String, List<String>> graph, String start) {
        List<String> result = new ArrayList<>();
        if (graph == null || start == null || !graph.containsKey(start)) return result;
        
        Set<String> visited = new HashSet<>();
        dfsHelper(graph, start, visited, result);
        return result;
    }

    private static void dfsHelper(Map<String, List<String>> graph, String current, Set<String> visited, List<String> result) {
        visited.add(current);
        result.add(current);
        
        List<String> neighbors = graph.get(current);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (graph.containsKey(neighbor) && !visited.contains(neighbor)) {
                    dfsHelper(graph, neighbor, visited, result);
                }
            }
        }
    }

    public static boolean reachable(Map<String, List<String>> graph, String start, String target) {
        if (graph == null || start == null || target == null) return false;
        if (!graph.containsKey(start) || !graph.containsKey(target)) return false;
        if (start.equals(target)) return true;

        Set<String> visited = new HashSet<>();
        return reachHelper(graph, start, target, visited);
    }

    private static boolean reachHelper(Map<String, List<String>> graph, String current, String target, Set<String> visited) {
        if (current.equals(target)) return true;
        visited.add(current);
        
        List<String> neighbors = graph.get(current);
        if (neighbors != null) {
            for (String neighbor : neighbors) {
                if (graph.containsKey(neighbor) && !visited.contains(neighbor)) {
                    if (reachHelper(graph, neighbor, target, visited)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}