import java.util.ArrayList;
import java.util.List;

public class CompleteBstTestSuite {

    static class SimpleBst {
        static class Node {
            int val;
            Node left, right;
            Node(int val) { this.val = val; }
        }
        Node root;

        public boolean insert(int val) {
            if (contains(val)) return false;
            root = insertRec(root, val);
            return true;
        }

        private Node insertRec(Node node, int val) {
            if (node == null) return new Node(val);
            if (val < node.val) node.left = insertRec(node.left, val);
            else if (val > node.val) node.right = insertRec(node.right, val);
            return node;
        }

        public boolean contains(int val) {
            Node curr = root;
            while (curr != null) {
                if (curr.val == val) return true;
                curr = (val < curr.val) ? curr.left : curr.right;
            }
            return false;
        }

        public boolean delete(int val) {
            if (!contains(val)) return false;
            root = deleteRec(root, val);
            return true;
        }

        private Node deleteRec(Node node, int val) {
            if (node == null) return null;
            if (val < node.val) node.left = deleteRec(node.left, val);
            else if (val > node.val) node.right = deleteRec(node.right, val);
            else {
                if (node.left == null) return node.right;
                if (node.right == null) return node.left;
                Node min = node.right;
                while (min.left != null) min = min.left;
                node.val = min.val;
                node.right = deleteRec(node.right, min.val);
            }
            return node;
        }

        public List<Integer> rangeSearch(int min, int max) {
            List<Integer> res = new ArrayList<>();
            rangeRec(root, min, max, res);
            return res;
        }

        private void rangeRec(Node node, int min, int max, List<Integer> res) {
            if (node == null) return;
            if (node.val > min) rangeRec(node.left, min, max, res);
            if (node.val >= min && node.val <= max) res.add(node.val);
            if (node.val < max) rangeRec(node.right, min, max, res);
        }

        public boolean isInvariantValid() {
            return isValidRec(root, null, null);
        }

        private boolean isValidRec(Node node, Integer min, Integer max) {
            if (node == null) return true;
            if ((min != null && node.val <= min) || (max != null && node.val >= max)) return false;
            return isValidRec(node.left, min, node.val) && isValidRec(node.right, node.val, max);
        }
    }

    private static int passCount = 0;
    private static int failCount = 0;

    public static void check(String description, boolean condition) {
        if (condition) {
            System.out.println("[PASS] " + description);
            passCount++;
        } else {
            System.out.println("[FAIL] " + description);
            failCount++;
        }
    }

    public static void main(String[] args) {
        SimpleBst bst = new SimpleBst();

        check("1. Empty tree does not contain element", !bst.contains(10));
        check("2. Delete on empty tree returns false", !bst.delete(10));
        check("3. Empty tree invariant is valid", bst.isInvariantValid());

        check("4. Insert root successfully", bst.insert(50));
        check("5. Insert duplicate key fails", !bst.insert(50));
        check("6. Contains root key", bst.contains(50));

        bst.insert(30); bst.insert(70);
        bst.insert(20); bst.insert(40);
        bst.insert(60); bst.insert(80);

        check("7. Tree contains leaf node", bst.contains(20));
        check("8. Tree contains internal node", bst.contains(30));
        check("9. Missing key returns false", !bst.contains(999));

        check("10. Range search normal bounds", bst.rangeSearch(25, 65).equals(List.of(30, 40, 50, 60)));
        check("11. Range search out of bounds", bst.rangeSearch(100, 200).isEmpty());
        check("12. Range search invalid range (min > max)", bst.rangeSearch(50, 20).isEmpty());

        check("13. BST Invariant is valid before deletion", bst.isInvariantValid());

        check("14. Delete leaf node (20)", bst.delete(20) && !bst.contains(20));

        bst.insert(25);
        bst.delete(40);
        check("15. Delete one-child node (30)", bst.delete(30) && !bst.contains(30) && bst.contains(25));

        check("16. Delete two-child node (70)", bst.delete(70) && !bst.contains(70) && bst.contains(60) && bst.contains(80));

        check("17. Delete root node (50)", bst.delete(50) && !bst.contains(50));

        check("18. Delete non-existent key returns false", !bst.delete(999));
        check("19. BST Invariant remains valid after all deletions", bst.isInvariantValid());
        check("20. Remaining node count range verify", bst.rangeSearch(0, 100).size() == 3);

        System.out.println("\n--- Test Summary ---");
        System.out.println("TOTAL PASSED: " + passCount + " | TOTAL FAILED: " + failCount);
    }
}
