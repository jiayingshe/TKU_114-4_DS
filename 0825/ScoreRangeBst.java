import java.util.ArrayList;
import java.util.List;

class StudentScore implements Comparable<StudentScore> {
    int score;
    String studentId;

    public StudentScore(int score, String studentId) {
        this.score = score;
        this.studentId = studentId;
    }

    @Override
    public int compareTo(StudentScore other) {
        if (this.score != other.score) {
            return Integer.compare(this.score, other.score);
        }
        return this.studentId.compareTo(other.studentId);
    }

    @Override
    public String toString() {
        return "[" + studentId + ": " + score + "分]";
    }
}

public class ScoreRangeBst {

    private static class Node {
        StudentScore data;
        Node left, right;

        Node(StudentScore data) {
            this.data = data;
        }
    }

    private Node root;

    public void insert(int score, String studentId) {
        root = insertRec(root, new StudentScore(score, studentId));
    }

    private Node insertRec(Node node, StudentScore item) {
        if (node == null) return new Node(item);
        int cmp = item.compareTo(node.data);
        if (cmp < 0) node.left = insertRec(node.left, item);
        else if (cmp > 0) node.right = insertRec(node.right, item);
        return node;
    }

    public List<StudentScore> rangeQuery(int minScore, int maxScore) {
        List<StudentScore> result = new ArrayList<>();
        rangeQueryRec(root, minScore, maxScore, result);
        return result;
    }

    private void rangeQueryRec(Node node, int minScore, int maxScore, List<StudentScore> result) {
        if (node == null) return;

        if (node.data.score > minScore) {
            rangeQueryRec(node.left, minScore, maxScore, result);
        }
        if (node.data.score >= minScore && node.data.score <= maxScore) {
            result.add(node.data);
        }
        if (node.data.score < maxScore) {
            rangeQueryRec(node.right, minScore, maxScore, result);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 課後作業三：排名範圍查詢 ===");
        ScoreRangeBst bst = new ScoreRangeBst();

        bst.insert(85, "S001");
        bst.insert(90, "S002");
        bst.insert(85, "S003");
        bst.insert(70, "S004");
        bst.insert(95, "S005");

        System.out.println("範圍查詢 [80分 到 92分]:");
        List<StudentScore> list = bst.rangeQuery(80, 92);
        System.out.println(list);
    }
}