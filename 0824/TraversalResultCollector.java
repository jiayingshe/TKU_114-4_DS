import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Node {
    String data;
    Node left;
    Node right;

    Node(String data) {
        this.data = data;
    }
}

public class TraversalResultCollector {

    public static List<String> preorder(Node root) {
        List<String> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }

    private static void preorderHelper(Node node, List<String> list) {
        if (node == null) return;
        list.add(node.data);
        preorderHelper(node.left, list);
        preorderHelper(node.right, list);
    }

    public static List<String> inorder(Node root) {
        List<String> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private static void inorderHelper(Node node, List<String> list) {
        if (node == null) return;
        inorderHelper(node.left, list);
        list.add(node.data);
        inorderHelper(node.right, list);
    }

    public static List<String> postorder(Node root) {
        List<String> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }

    private static void postorderHelper(Node node, List<String> list) {
        if (node == null) return;
        postorderHelper(node.left, list);
        postorderHelper(node.right, list);
        list.add(node.data);
    }

    public static List<String> levelOrder(Node root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;

        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            result.add(curr.data);
            if (curr.left != null) queue.offer(curr.left);
            if (curr.right != null) queue.offer(curr.right);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("=== 課後作業三：Traversal 結果集合 ===");

        System.out.println("Empty Tree Preorder: " + preorder(null));

        Node single = new Node("A");
        System.out.println("Single Node Inorder: " + inorder(single));

        Node leftSkewed = new Node("A");
        leftSkewed.left = new Node("B");
        leftSkewed.left.left = new Node("C");
        System.out.println("Left-Skewed Postorder: " + postorder(leftSkewed));

        Node complete = new Node("A");
        complete.left = new Node("B");
        complete.right = new Node("C");
        complete.left.left = new Node("D");
        complete.left.right = new Node("E");
        System.out.println("Complete Tree LevelOrder: " + levelOrder(complete));
    }
}