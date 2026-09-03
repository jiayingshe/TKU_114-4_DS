import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q11_BstHashDirectory {
    private static class Node {
        int id;
        Node left, right;
        Node(int id) { this.id = id; }
    }

    private Node root;
    private final Map<Integer, String> hashDir = new HashMap<>();

    public boolean add(int id, String name) {
        if (id <= 0 || name == null || name.trim().isEmpty()) return false;
        String trimmed = name.trim();
        if (hashDir.containsKey(id)) return false;

        root = insert(root, id);
        hashDir.put(id, trimmed);
        return true;
    }

    private Node insert(Node node, int id) {
        if (node == null) return new Node(id);
        if (id < node.id) node.left = insert(node.left, id);
        else if (id > node.id) node.right = insert(node.right, id);
        return node;
    }

    public String findName(int id) {
        return hashDir.get(id);
    }

    public boolean remove(int id) {
        if (!hashDir.containsKey(id)) return false;
        hashDir.remove(id);
        root = deleteNode(root, id);
        return true;
    }

    private Node deleteNode(Node root, int id) {
        if (root == null) return null;
        if (id < root.id) root.left = deleteNode(root.left, id);
        else if (id > root.id) root.right = deleteNode(root.right, id);
        else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            
            Node minNode = findMin(root.right);
            root.id = minNode.id;
            root.right = deleteNode(root.right, root.id);
        }
        return root;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public List<Integer> idsBetween(int low, int high) {
        List<Integer> result = new ArrayList<>();
        if (low > high) return result;
        inorderRange(root, low, high, result);
        return result;
    }

    private void inorderRange(Node node, int low, int high, List<Integer> result) {
        if (node == null) return;
        if (low < node.id) inorderRange(node.left, low, high, result);
        if (low <= node.id && node.id <= high) result.add(node.id);
        if (high > node.id) inorderRange(node.right, low, high, result);
    }

    public int size() {
        return hashDir.size();
    }
}