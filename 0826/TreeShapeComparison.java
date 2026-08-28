import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeShapeComparison {

    static class MetricsBst {
        static class Node {
            int key;
            Node left, right;
            Node(int key) { this.key = key; }
        }
        Node root;

        public void insert(int key) {
            root = insertRec(root, key);
        }

        private Node insertRec(Node node, int key) {
            if (node == null) return new Node(key);
            if (key < node.key) node.left = insertRec(node.left, key);
            else if (key > node.key) node.right = insertRec(node.right, key);
            return node;
        }

        public int getHeight() {
            return getHeightRec(root);
        }

        private int getHeightRec(Node node) {
            if (node == null) return -1;
            return 1 + Math.max(getHeightRec(node.left), getHeightRec(node.right));
        }

        public int searchComparisons(int target) {
            int count = 0;
            Node curr = root;
            while (curr != null) {
                count++;
                if (curr.key == target) break;
                curr = (target < curr.key) ? curr.left : curr.right;
            }
            return count;
        }
    }

    public static void main(String[] args) {
        int[] keys = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150};
        int[] missingKeys = {5, 25, 45, 65, 85, 105, 125, 145, 165, 200};

        int[] ascendingKeys = Arrays.copyOf(keys, keys.length);

        int[] descendingKeys = new int[keys.length];
        for (int i = 0; i < keys.length; i++) {
            descendingKeys[i] = keys[keys.length - 1 - i];
        }

        List<Integer> balancedOrder = new ArrayList<>();
        buildBalancedOrder(keys, 0, keys.length - 1, balancedOrder);

        MetricsBst treeAsc = buildTree(ascendingKeys);
        MetricsBst treeDesc = buildTree(descendingKeys);
        MetricsBst treeBal = buildTree(balancedOrder.stream().mapToInt(i -> i).toArray());

        System.out.printf("%-18s | %-6s | %-22s | %-25s%n", "Insert Order", "Height", "Total Existing Search Comps", "Total Missing Search Comps");
        System.out.println("-------------------------------------------------------------------------------------");

        printMetrics("Ascending", treeAsc, keys, missingKeys);
        printMetrics("Descending", treeDesc, keys, missingKeys);
        printMetrics("Near-Balanced", treeBal, keys, missingKeys);
    }

    private static MetricsBst buildTree(int[] arr) {
        MetricsBst tree = new MetricsBst();
        for (int k : arr) tree.insert(k);
        return tree;
    }

    private static void buildBalancedOrder(int[] arr, int start, int end, List<Integer> result) {
        if (start > end) return;
        int mid = start + (end - start) / 2;
        result.add(arr[mid]);
        buildBalancedOrder(arr, start, mid - 1, result);
        buildBalancedOrder(arr, mid + 1, end, result);
    }

    private static void printMetrics(String name, MetricsBst tree, int[] existingKeys, int[] missingKeys) {
        int existComps = 0;
        for (int k : existingKeys) existComps += tree.searchComparisons(k);

        int missingComps = 0;
        for (int k : missingKeys) missingComps += tree.searchComparisons(k);

        System.out.printf("%-18s | %-6d | %-27d | %-25d%n", name, tree.getHeight(), existComps, missingComps);
    }
}