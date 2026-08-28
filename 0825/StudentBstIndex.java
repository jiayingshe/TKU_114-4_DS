class Student {
    String studentId;
    String name;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student[ID=" + studentId + ", Name=" + name + "]";
    }
}

public class StudentBstIndex {

    private static class Node {
        Student student;
        Node left, right;

        Node(Student student) {
            this.student = student;
        }
    }

    private Node root;

    public Student search(String studentId) {
        Node curr = root;
        while (curr != null) {
            int cmp = studentId.compareTo(curr.student.studentId);
            if (cmp == 0) return curr.student;
            else if (cmp < 0) curr = curr.left;
            else curr = curr.right;
        }
        return null;
    }

    public boolean insert(Student student) {
        if (root == null) {
            root = new Node(student);
            return true;
        }
        Node curr = root;
        Node parent = null;
        int cmp = 0;

        while (curr != null) {
            parent = curr;
            cmp = student.studentId.compareTo(curr.student.studentId);
            if (cmp == 0) {
                System.out.println("插入失敗：重複的 Student ID -> " + student.studentId);
                return false;
            } else if (cmp < 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        if (cmp < 0) parent.left = new Node(student);
        else parent.right = new Node(student);
        return true;
    }

    public boolean delete(String studentId) {
        if (search(studentId) == null) return false;
        root = deleteRec(root, studentId);
        return true;
    }

    private Node deleteRec(Node node, String studentId) {
        if (node == null) return null;

        int cmp = studentId.compareTo(node.student.studentId);
        if (cmp < 0) {
            node.left = deleteRec(node.left, studentId);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, studentId);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node minNode = getMin(node.right);
            node.student = minNode.student;
            node.right = deleteRec(node.right, minNode.student.studentId);
        }
        return node;
    }

    private Node getMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public void printInOrder() {
        printInOrderRec(root);
        System.out.println();
    }

    private void printInOrderRec(Node node) {
        if (node != null) {
            printInOrderRec(node.left);
            System.out.print(node.student + " ");
            printInOrderRec(node.right);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 課後作業一：學號索引 ===");
        StudentBstIndex bst = new StudentBstIndex();

        bst.insert(new Student("S102", "Alice"));
        bst.insert(new Student("S101", "Bob"));
        bst.insert(new Student("S103", "Charlie"));
        bst.insert(new Student("S101", "Duplicate"));

        System.out.print("中序輸出: ");
        bst.printInOrder();

        System.out.println("搜尋 S102: " + bst.search("S102"));
        System.out.println("刪除 S102: " + bst.delete("S102"));
        System.out.print("刪除後中序輸出: ");
        bst.printInOrder();
    }
}