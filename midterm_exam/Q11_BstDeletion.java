import java.util.ArrayList;
import java.util.List;

public class Q11_BstDeletion {

    private static class Node {
        int value;
        Node left, right;
        Node(int value) { this.value = value; }
    }

    private Node root;

    public boolean add(int value) {
        if (contains(value)) return false;
        root = insertRec(root, value);
        return true;
    }

    private Node insertRec(Node node, int value) {
        if (node == null) return new Node(value);
        if (value < node.value) node.left = insertRec(node.left, value);
        else if (value > node.value) node.right = insertRec(node.right, value);
        return node;
    }

    public boolean contains(int value) {
        Node curr = root;
        while (curr != null) {
            if (curr.value == value) return true;
            curr = (value < curr.value) ? curr.left : curr.right;
        }
        return false;
    }

    public boolean remove(int value) {
        if (!contains(value)) return false;
        root = deleteRec(root, value);
        return true;
    }

    private Node deleteRec(Node node, int value) {
        if (node == null) return null;
        if (value < node.value) {
            node.left = deleteRec(node.left, value);
        } else if (value > node.value) {
            node.right = deleteRec(node.right, value);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node minNode = getMin(node.right);
            node.value = minNode.value;
            node.right = deleteRec(node.right, minNode.value);
        }
        return node;
    }

    private Node getMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public int size() {
        return sizeRec(root);
    }

    private int sizeRec(Node node) {
        if (node == null) return 0;
        return 1 + sizeRec(node.left) + sizeRec(node.right);
    }

    public List<Integer> inorder() {
        List<Integer> res = new ArrayList<>();
        inorderRec(root, res);
        return res;
    }

    private void inorderRec(Node node, List<Integer> res) {
        if (node == null) return;
        inorderRec(node.left, res);
        res.add(node.value);
        inorderRec(node.right, res);
    }

    public boolean isValid() {
        return isValidRec(root, null, null);
    }

    private boolean isValidRec(Node node, Integer min, Integer max) {
        if (node == null) return true;
        if ((min != null && node.value <= min) || (max != null && node.value >= max)) {
            return false;
        }
        return isValidRec(node.left, min, node.value) && isValidRec(node.right, node.value, max);
    }

    public static void main(String[] args) {
        Q11_BstDeletion tree = new Q11_BstDeletion();
        for (int value : new int[]{50, 30, 70, 20, 40, 60, 80}) tree.add(value);
        System.out.println(tree.remove(20));
        System.out.println(tree.remove(30));
        System.out.println(tree.remove(50));
        System.out.println(tree.remove(999));
        System.out.println(tree.inorder());
        System.out.println(tree.size());
        System.out.println(tree.isValid());
    }
}