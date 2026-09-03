import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Q05_StudentHashIndex {
    private final Map<String, Set<String>> studentCourses = new HashMap<>();
    private final Map<String, Set<String>> courseStudents = new HashMap<>();
    private int enrollments = 0;

    private String normalize(String input) {
        if (input == null || input.trim().isEmpty()) return null;
        return input.trim().toUpperCase();
    }

    public boolean enroll(String studentId, String courseId) {
        String sId = normalize(studentId);
        String cId = normalize(courseId);
        if (sId == null || cId == null) return false;

        studentCourses.putIfAbsent(sId, new HashSet<>());
        if (studentCourses.get(sId).contains(cId)) {
            return false;
        }
        
        studentCourses.get(sId).add(cId);
        courseStudents.putIfAbsent(cId, new HashSet<>());
        courseStudents.get(cId).add(sId);
        enrollments++;
        return true;
    }

    public boolean drop(String studentId, String courseId) {
        String sId = normalize(studentId);
        String cId = normalize(courseId);
        if (sId == null || cId == null) return false;

        if (!studentCourses.containsKey(sId) || !studentCourses.get(sId).contains(cId)) {
            return false;
        }

        studentCourses.get(sId).remove(cId);
        if (studentCourses.get(sId).isEmpty()) {
            studentCourses.remove(sId);
        }

        courseStudents.get(cId).remove(sId);
        if (courseStudents.get(cId).isEmpty()) {
            courseStudents.remove(cId);
        }
        
        enrollments--;
        return true;
    }

    public Set<String> coursesOf(String studentId) {
        String sId = normalize(studentId);
        if (sId == null || !studentCourses.containsKey(sId)) {
            return Collections.emptySet();
        }
        return Set.copyOf(studentCourses.get(sId));
    }

    public Set<String> studentsIn(String courseId) {
        String cId = normalize(courseId);
        if (cId == null || !courseStudents.containsKey(cId)) {
            return Collections.emptySet();
        }
        return Set.copyOf(courseStudents.get(cId));
    }

    public int enrollmentCount() {
        return enrollments;
    }
}