import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CoursePlanningGraph {

    private final Map<String, List<String>> adjList = new HashMap<>();

    public void addCourse(String course) {
        adjList.putIfAbsent(course, new ArrayList<>());
    }

    public void addPrerequisite(String prereq, String course) {
        addCourse(prereq);
        addCourse(course);
        adjList.get(prereq).add(course);
    }

    public boolean isReachable(String start, String target) {
        if (!adjList.containsKey(start) || !adjList.containsKey(target)) {
            return false;
        }
        Set<String> visited = new HashSet<>();
        return dfsReachability(start, target, visited);
    }

    private boolean dfsReachability(String curr, String target, Set<String> visited) {
        if (curr.equals(target)) return true;
        visited.add(curr);

        for (String neighbor : adjList.getOrDefault(curr, Collections.emptyList())) {
            if (!visited.contains(neighbor)) {
                if (dfsReachability(neighbor, target, visited)) return true;
            }
        }
        return false;
    }

    public List<List<String>> findAllPaths(String start, String target) {
        List<List<String>> allPaths = new ArrayList<>();
        if (!adjList.containsKey(start) || !adjList.containsKey(target)) {
            return allPaths;
        }
        Set<String> visited = new HashSet<>();
        List<String> currentPath = new ArrayList<>();
        dfsPaths(start, target, visited, currentPath, allPaths);
        return allPaths;
    }

    private void dfsPaths(String curr, String target, Set<String> visited, List<String> currentPath, List<List<String>> allPaths) {
        visited.add(curr);
        currentPath.add(curr);

        if (curr.equals(target)) {
            allPaths.add(new ArrayList<>(currentPath));
        } else {
            for (String neighbor : adjList.getOrDefault(curr, Collections.emptyList())) {
                if (!visited.contains(neighbor)) {
                    dfsPaths(neighbor, target, visited, currentPath, allPaths);
                }
            }
        }

        currentPath.remove(currentPath.size() - 1);
        visited.remove(curr);
    }

    public static void main(String[] args) {
        CoursePlanningGraph graph = new CoursePlanningGraph();
        graph.addPrerequisite("CS101", "CS102");
        graph.addPrerequisite("CS102", "CS201");
        graph.addPrerequisite("CS101", "MIS201");
        graph.addPrerequisite("MIS201", "CS201");

        System.out.println("Reachable: " + graph.isReachable("CS101", "CS201"));
        System.out.println("All paths: " + graph.findAllPaths("CS101", "CS201"));
    }
}