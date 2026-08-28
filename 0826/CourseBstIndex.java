import java.util.ArrayList;
import java.util.List;

public class CourseBstIndex {

    public static class Course {
        private final String courseCode;
        private final String name;
        private int credits;

        public Course(String courseCode, String name, int credits) {
            if (courseCode == null || courseCode.isBlank() || name == null || name.isBlank()) {
                throw new IllegalArgumentException("Invalid course code or name");
            }
            this.courseCode = courseCode.trim();
            this.name = name.trim();
            this.credits = Math.min(6, Math.max(1, credits));
        }

        public String getCourseCode() { return courseCode; }
        public String getName() { return name; }
        public int getCredits() { return credits; }
        public void setCredits(int credits) {
            this.credits = Math.min(6, Math.max(1, credits));
        }

        @Override
        public String toString() {
            return String.format("[%s] %s (%d credits)", courseCode, name, credits);
        }
    }

    private static class Node {
        Course course;
        Node left, right;
        Node(Course course) { this.course = course; }
    }

    private Node root;

    public boolean add(Course course) {
        if (course == null || find(course.getCourseCode()) != null) {
            return false;
        }
        root = insertRec(root, course);
        return true;
    }

    private Node insertRec(Node node, Course course) {
        if (node == null) return new Node(course);
        int cmp = course.getCourseCode().compareTo(node.course.getCourseCode());
        if (cmp < 0) node.left = insertRec(node.left, course);
        else if (cmp > 0) node.right = insertRec(node.right, course);
        return node;
    }

    public Course find(String courseCode) {
        if (courseCode == null) return null;
        Node curr = root;
        while (curr != null) {
            int cmp = courseCode.compareTo(curr.course.getCourseCode());
            if (cmp == 0) return curr.course;
            curr = (cmp < 0) ? curr.left : curr.right;
        }
        return null;
    }

    public boolean updateCredits(String courseCode, int credits) {
        Course c = find(courseCode);
        if (c == null) return false;
        c.setCredits(credits);
        return true;
    }

    public boolean remove(String courseCode) {
        if (find(courseCode) == null) return false;
        root = deleteRec(root, courseCode);
        return true;
    }

    private Node deleteRec(Node node, String courseCode) {
        if (node == null) return null;
        int cmp = courseCode.compareTo(node.course.getCourseCode());
        if (cmp < 0) {
            node.left = deleteRec(node.left, courseCode);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, courseCode);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node minNode = getMin(node.right);
            node.course = minNode.course;
            node.right = deleteRec(node.right, minNode.course.getCourseCode());
        }
        return node;
    }

    private Node getMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public List<Course> rangeQuery(String startCode, String endCode) {
        List<Course> result = new ArrayList<>();
        if (startCode == null || endCode == null || startCode.compareTo(endCode) > 0) {
            return result;
        }
        rangeRec(root, startCode, endCode, result);
        return result;
    }

    private void rangeRec(Node node, String start, String end, List<Course> result) {
        if (node == null) return;
        int cmpStart = node.course.getCourseCode().compareTo(start);
        int cmpEnd = node.course.getCourseCode().compareTo(end);

        if (cmpStart > 0) rangeRec(node.left, start, end, result);
        if (cmpStart >= 0 && cmpEnd <= 0) result.add(node.course);
        if (cmpEnd < 0) rangeRec(node.right, start, end, result);
    }

    public List<Course> getSortedReport() {
        List<Course> result = new ArrayList<>();
        inorderRec(root, result);
        return result;
    }

    private void inorderRec(Node node, List<Course> result) {
        if (node == null) return;
        inorderRec(node.left, result);
        result.add(node.course);
        inorderRec(node.right, result);
    }

    public static void main(String[] args) {
        CourseBstIndex index = new CourseBstIndex();
        index.add(new Course("CS101", "Intro to CS", 3));
        index.add(new Course("CS201", "Data Structures", 4));
        index.add(new Course("MATH101", "Calculus", 4));
        index.add(new Course("ENG101", "English", 2));

        index.updateCredits("CS101", 10);
        System.out.println("Range Query (CS100 - CS300): " + index.rangeQuery("CS100", "CS300"));
        index.remove("CS101");
        System.out.println("Sorted Report: " + index.getSortedReport());
    }
}