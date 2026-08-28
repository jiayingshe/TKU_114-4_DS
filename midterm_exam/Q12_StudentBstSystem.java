import java.util.ArrayList;
import java.util.List;

public class Q12_StudentBstSystem {

    public static class Student {
        private final int id;
        private final String name;
        private int score;

        public Student(int id, String name, int score) {
            if (id <= 0 || name == null || name.isBlank()) {
                throw new IllegalArgumentException("Invalid student id or name");
            }
            this.id = id;
            this.name = name;
            this.score = Math.min(100, Math.max(0, score));
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = Math.min(100, Math.max(0, score));
        }

        @Override
        public String toString() {
            return id + " " + name + " " + score;
        }
    }

    private static class Node {
        Student student;
        Node left, right;
        Node(Student student) { this.student = student; }
    }

    private Node root;

    public boolean add(Student student) {
        if (student == null || find(student.getId()) != null) {
            return false;
        }
        root = insertRec(root, student);
        return true;
    }

    private Node insertRec(Node node, Student student) {
        if (node == null) return new Node(student);
        if (student.getId() < node.student.getId()) {
            node.left = insertRec(node.left, student);
        } else if (student.getId() > node.student.getId()) {
            node.right = insertRec(node.right, student);
        }
        return node;
    }

    public Student find(int id) {
        Node curr = root;
        while (curr != null) {
            if (curr.student.getId() == id) return curr.student;
            curr = (id < curr.student.getId()) ? curr.left : curr.right;
        }
        return null;
    }

    public boolean updateScore(int id, int score) {
        Student st = find(id);
        if (st == null) return false;
        st.setScore(score);
        return true;
    }

    public boolean remove(int id) {
        if (find(id) == null) return false;
        root = deleteRec(root, id);
        return true;
    }

    private Node deleteRec(Node node, int id) {
        if (node == null) return null;
        if (id < node.student.getId()) {
            node.left = deleteRec(node.left, id);
        } else if (id > node.student.getId()) {
            node.right = deleteRec(node.right, id);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node minNode = getMin(node.right);
            node.student = minNode.student;
            node.right = deleteRec(node.right, minNode.student.getId());
        }
        return node;
    }

    private Node getMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public List<Student> studentsBetween(int lowId, int highId) {
        List<Student> res = new ArrayList<>();
        if (lowId > highId) return res;
        rangeRec(root, lowId, highId, res);
        return res;
    }

    private void rangeRec(Node node, int low, int high, List<Student> res) {
        if (node == null) return;
        if (node.student.getId() > low) rangeRec(node.left, low, high, res);
        if (node.student.getId() >= low && node.student.getId() <= high) res.add(node.student);
        if (node.student.getId() < high) rangeRec(node.right, low, high, res);
    }

    public List<Student> inorder() {
        List<Student> res = new ArrayList<>();
        inorderRec(root, res);
        return res;
    }

    private void inorderRec(Node node, List<Student> res) {
        if (node == null) return;
        inorderRec(node.left, res);
        res.add(node.student);
        inorderRec(node.right, res);
    }

    public static void main(String[] args) {
        Q12_StudentBstSystem system = new Q12_StudentBstSystem();
        system.add(new Q12_StudentBstSystem.Student(300, "Mina", 78));
        system.add(new Q12_StudentBstSystem.Student(100, "Leo", 84));
        system.add(new Q12_StudentBstSystem.Student(500, "Nora", 105));
        system.add(new Q12_StudentBstSystem.Student(200, "Ivy", 69));

        System.out.println(system.updateScore(200, 88));
        System.out.println(system.studentsBetween(150, 500));
        System.out.println(system.remove(300));
        System.out.println(system.inorder());
    }
}