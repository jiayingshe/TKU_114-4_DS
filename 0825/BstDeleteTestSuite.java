public class BstDeleteTestSuite {

    private static class Node {
        int val;
        Node left, right;

        Node(int val) { this.val = val; }
    }

    private Node root;

    public void insert(int val) {
        root = insertRec(root, val);
    }

    private Node insertRec(Node node, int val) {
        if (node == null) return new Node(val);
        if (val < node.val) node.left = insertRec(node.left, val);
        else if (val > node.val) node.right = insertRec(node.right, val);
        return node;
    }

    public boolean delete(int val) {
        if (!contains(val)) return false;
        root = deleteRec(root, val);
        return true;
    }

    private boolean contains(int val) {
        Node curr = root;
        while (curr != null) {
            if (curr.val == val) return true;
            curr = (val < curr.val) ? curr.left : curr.right;
        }
        return false;
    }

    private Node deleteRec(Node node, int val) {
        if (node == null) return null;
        if (val < node.val) node.left = deleteRec(node.left, val);
        else if (val > node.val) node.right = deleteRec(node.right, val);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node minNode = node.right;
            while (minNode.left != null) minNode = minNode.left;
            node.val = minNode.val;
            node.right = deleteRec(node.right, minNode.val);
        }
        return node;
    }

    public boolean isEmpty() { return root == null; }

    public static void main(String[] args) {
        System.out.println("=== 課後作業四：完整刪除測試 ===");
        BstDeleteTestSuite bst = new BstDeleteTestSuite();

        System.out.println("1. 空樹刪除 (10): " + (!bst.delete(10) ? "PASS" : "FAIL"));

        bst.insert(50);
        System.out.println("2. 單根刪除 (50): " + (bst.delete(50) && bst.isEmpty() ? "PASS" : "FAIL"));

        bst.insert(50);
        bst.insert(30);
        System.out.println("3. 缺失節點刪除 (99): " + (!bst.delete(99) ? "PASS" : "FAIL"));

        bst.insert(20);
        System.out.println("4. 一子節點刪除 (30): " + (bst.delete(30) ? "PASS" : "FAIL"));

        bst.insert(70);
        bst.insert(60);
        bst.insert(80);
        System.out.println("5. 雙子節點刪除 (70): " + (bst.delete(70) ? "PASS" : "FAIL"));

        bst.delete(50);
        bst.delete(20);
        bst.delete(60);
        bst.delete(80);
        System.out.println("6. 連續刪除到空樹: " + (bst.isEmpty() ? "PASS" : "FAIL"));
    }
}
