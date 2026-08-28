import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Q09_TreeTraversal {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static List<Integer> preorder(Node root) {
        List<Integer> res = new ArrayList<>();
        preorderRec(root, res);
        return res;
    }

    private static void preorderRec(Node node, List<Integer> res) {
        if (node == null) return;
        res.add(node.value);
        preorderRec(node.left, res);
        preorderRec(node.right, res);
    }

    public static List<Integer> inorder(Node root) {
        List<Integer> res = new ArrayList<>();
        inorderRec(root, res);
        return res;
    }

    private static void inorderRec(Node node, List<Integer> res) {
        if (node == null) return;
        inorderRec(node.left, res);
        res.add(node.value);
        inorderRec(node.right, res);
    }

    public static List<Integer> postorder(Node root) {
        List<Integer> res = new ArrayList<>();
        postorderRec(root, res);
        return res;
    }

    private static void postorderRec(Node node, List<Integer> res) {
        if (node == null) return;
        postorderRec(node.left, res);
        postorderRec(node.right, res);
        res.add(node.value);
    }

    public static List<Integer> levelOrder(Node root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Deque<Node> queue = new ArrayDeque<>();
        queue.addLast(root);

        while (!queue.isEmpty()) {
            Node curr = queue.removeFirst();
            res.add(curr.value);
            if (curr.left != null) queue.addLast(curr.left);
            if (curr.right != null) queue.addLast(curr.right);
        }
        return res;
    }

    public static void main(String[] args) {
        Node root = new Node(8);
        root.left = new Node(4);
        root.right = new Node(12);
        root.left.left = new Node(2);
        root.left.right = new Node(6);
        root.right.right = new Node(14);

        System.out.println("preorder: " + preorder(root));
        System.out.println("inorder: " + inorder(root));
        System.out.println("postorder: " + postorder(root));
        System.out.println("level: " + levelOrder(root));
    }
}