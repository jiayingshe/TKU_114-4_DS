import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CourseDependencyGraph {

    private final Set<String> courses = new HashSet<>();
    private final Map<String, Set<String>> adjList = new HashMap<>();
    private final Map<String, Set<String>> reverseAdjList = new HashMap<>();

    public void addCourse(String course) {
        courses.add(course);
        adjList.putIfAbsent(course, new HashSet<>());
        reverseAdjList.putIfAbsent(course, new HashSet<>());
    }

    public void addPrerequisite(String prereq, String course) {
        addCourse(prereq);
        addCourse(course);
        adjList.get(prereq).add(course);
        reverseAdjList.get(course).add(prereq);
    }

    public int getInDegree(String course) {
        return reverseAdjList.getOrDefault(course, Collections.emptySet()).size();
    }

    public int getOutDegree(String course) {
        return adjList.getOrDefault(course, Collections.emptySet()).size();
    }

    public void printReport() {
        for (String c : courses) {
            Set<String> prereqs = reverseAdjList.getOrDefault(c, Collections.emptySet());
            Set<String> nextCourses = adjList.getOrDefault(c, Collections.emptySet());
            System.out.println("課程: " + c +
                    " | 先決條件: " + prereqs + " (入度=" + prereqs.size() + ")" +
                    " | 後續課程: " + nextCourses + " (出度=" + nextCourses.size() + ")");
        }
    }

    public static void main(String[] args) {
        CourseDependencyGraph cdg = new CourseDependencyGraph();
        cdg.addPrerequisite("CS101", "CS102");
        cdg.addPrerequisite("CS102", "CS201");
        cdg.addPrerequisite("MATH101", "CS201");

        cdg.printReport();
    }
}