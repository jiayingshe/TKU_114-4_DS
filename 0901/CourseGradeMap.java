import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class CourseGradeMap {

    private final Map<String, List<Integer>> gradeMap = new HashMap<>();

    public void addGrade(String courseId, int grade) {
        gradeMap.computeIfAbsent(courseId, k -> new ArrayList<>()).add(grade);
    }

    public double getAverage(String courseId) {
        List<Integer> grades = gradeMap.get(courseId);
        if (grades == null || grades.isEmpty()) return 0.0;
        int sum = 0;
        for (int g : grades) sum += g;
        return (double) sum / grades.size();
    }

    public int getMaxGrade(String courseId) {
        List<Integer> grades = gradeMap.get(courseId);
        if (grades == null || grades.isEmpty()) throw new NoSuchElementException("無成績紀錄");
        return Collections.max(grades);
    }

    public void printSortedReport() {
        Map<String, List<Integer>> sortedMap = new TreeMap<>(gradeMap);
        for (Map.Entry<String, List<Integer>> entry : sortedMap.entrySet()) {
            String course = entry.getKey();
            System.out.println("課號: " + course + 
                    " | 成績: " + entry.getValue() + 
                    " | 平均: " + String.format("%.2f", getAverage(course)) + 
                    " | 最高分: " + getMaxGrade(course));
        }
    }

    public static void main(String[] args) {
        CourseGradeMap tracker = new CourseGradeMap();
        tracker.addGrade("CS101", 85);
        tracker.addGrade("CS101", 90);
        tracker.addGrade("MIS201", 92);
        tracker.addGrade("CS101", 78);
        tracker.addGrade("MIS201", 88);

        tracker.printSortedReport();
    }
}