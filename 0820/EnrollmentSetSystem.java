import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Enrollment {
    private final String studentId;
    private final String courseCode;

    public Enrollment(String studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;
    }

    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(studentId, that.studentId) && Objects.equals(courseCode, that.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }

    @Override
    public String toString() {
        return "Enrollment{" + studentId + " -> " + courseCode + '}';
    }
}

public class EnrollmentSetSystem {

    private static final Set<Enrollment> enrollments = new HashSet<>();

    public static boolean enroll(String studentId, String courseCode) {
        Enrollment e = new Enrollment(studentId, courseCode);
        boolean result = enrollments.add(e);
        System.out.printf("報名 [%s -> %s] : %b\n", studentId, courseCode, result);
        return result;
    }

    public static boolean cancel(String studentId, String courseCode) {
        Enrollment e = new Enrollment(studentId, courseCode);
        boolean result = enrollments.remove(e);
        System.out.printf("取消報名 [%s -> %s] : %b\n", studentId, courseCode, result);
        return result;
    }

    public static void main(String[] args) {
        System.out.println("=== 1. 新增報名測試 ===");
        enroll("S001", "CS101");
        enroll("S001", "CS102");
        enroll("S001", "CS101");

        System.out.println("\n=== 2. 以同身分新物件測試 contains() ===");
        Enrollment query = new Enrollment("S001", "CS101");
        System.out.println("contains(S001 -> CS101): " + enrollments.contains(query));

        System.out.println("\n=== 3. 取消報名測試 ===");
        cancel("S001", "CS101"); // true
        System.out.println("取消後 contains(S001 -> CS101): " + enrollments.contains(query));
    }
}