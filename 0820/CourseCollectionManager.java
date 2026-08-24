import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

class StudentRegistration {
    private final String studentId;
    private final String name;
    private int score;
    private final String tag;

    public StudentRegistration(String studentId, String name, int score, String tag) {
        this.studentId = studentId;
        this.name = name;
        this.score = score;
        this.tag = (tag == null) ? "" : tag.trim();
    }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public String getTag() { return tag; }

    public String getGradeLevel() {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRegistration that = (StudentRegistration) o;
        return Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    @Override
    public String toString() {
        return String.format("學號: %s | 姓名: %-5s | 分數: %3d | 等第: %s | 標籤: '%s'",
                studentId, name, score, getGradeLevel(), tag);
    }
}

class CourseManager {
    private final List<StudentRegistration> list = new ArrayList<>();
    private final Set<StudentRegistration> set = new HashSet<>();
    private final Map<String, StudentRegistration> map = new HashMap<>();

    public boolean addStudent(StudentRegistration sr) {
        if (sr == null || map.containsKey(sr.getStudentId())) {
            System.out.println("新增失敗：學號已存在或資料為空 (" + (sr != null ? sr.getStudentId() : "null") + ")");
            return false;
        }
        list.add(sr);
        set.add(sr);
        map.put(sr.getStudentId(), sr);
        return true;
    }

    public void updateScore(String studentId, int score) {
        StudentRegistration sr = map.get(studentId);
        if (sr != null) {
            sr.setScore(score);
            System.out.printf("已更新 %s 的分數為: %d\n", studentId, score);
        } else {
            System.out.println("更新失敗：找不到學號 " + studentId);
        }
    }

    public List<StudentRegistration> findByTag(String tag) {
        String targetTag = (tag == null) ? "" : tag.trim();
        List<StudentRegistration> result = new ArrayList<>();
        for (StudentRegistration sr : list) {
            if (sr.getTag().equalsIgnoreCase(targetTag)) {
                result.add(sr);
            }
        }
        return result;
    }

    public Map<String, Integer> scoreDistribution() {
        Map<String, Integer> dist = new LinkedHashMap<>();
        dist.put("A", 0); dist.put("B", 0); dist.put("C", 0); dist.put("D", 0); dist.put("F", 0);
        for (StudentRegistration sr : list) {
            String level = sr.getGradeLevel();
            dist.put(level, dist.get(level) + 1);
        }
        return dist;
    }

    public List<StudentRegistration> top(int count) {
        List<StudentRegistration> sortedList = new ArrayList<>(list);
        sortedList.sort(Comparator.comparingInt(StudentRegistration::getScore).reversed());
        return sortedList.subList(0, Math.min(count, sortedList.size()));
    }

    public void removeBelow(int minimum) {
        Iterator<StudentRegistration> it = list.iterator();
        while (it.hasNext()) {
            StudentRegistration sr = it.next();
            if (sr.getScore() < minimum) {
                set.remove(sr);
                map.remove(sr.getStudentId());
                it.remove();
            }
        }
        System.out.println("已移除分數低於 " + minimum + " 分的學生");
    }

    public void printAll() {
        System.out.println("--- 學生名單 (Total: " + list.size() + ") ---");
        list.forEach(System.out::println);
    }
}

public class CourseCollectionManager {
    public static void main(String[] args) {
        CourseManager manager = new CourseManager();

        System.out.println("=== 1. 新增報名資料 ===");
        manager.addStudent(new StudentRegistration("S01", "Alice", 92, "Honor"));
        manager.addStudent(new StudentRegistration("S02", "Bob", 78, "Sports"));
        manager.addStudent(new StudentRegistration("S03", "Charlie", 55, ""));
        manager.addStudent(new StudentRegistration("S04", "David", 85, "Honor"));
        manager.addStudent(new StudentRegistration("S05", "Eve", 48, ""));
        manager.addStudent(new StudentRegistration("S06", "Frank", 85, "Arts"));
        manager.addStudent(new StudentRegistration("S01", "Duplicate", 100, ""));

        manager.printAll();

        System.out.println("\n=== 2. 更新分數 ===");
        manager.updateScore("S03", 65);

        System.out.println("\n=== 3. 依 Tag 搜尋 (Honor) ===");
        manager.findByTag("Honor").forEach(System.out::println);

        System.out.println("\n=== 4. 成績等第分佈 ===");
        System.out.println(manager.scoreDistribution());

        System.out.println("\n=== 5. 前 3 名學生 ===");
        manager.top(3).forEach(System.out::println);

        System.out.println("\n=== 6. 移除 60 分以下學生 (保持一致性) ===");
        manager.removeBelow(60);
        manager.printAll();
    }
}