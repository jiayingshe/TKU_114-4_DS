import java.util.Arrays;

public class BstShapeExperiment {

    private static class Node {
        int val;
        Node left, right;
        Node(int val) { this.val = val; }
    }

    private Node root;
    private int compareCount = 0;

    public void insert(int val) {
        root = insertRec(root, val);
    }

    private Node insertRec(Node node, int val) {
        if (node == null) return new Node(val);
        if (val < node.val) node.left = insertRec(node.left, val);
        else node.right = insertRec(node.right, val);
        return node;
    }

    public int getHeight() {
        return getHeightRec(root);
    }

    private int getHeightRec(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeightRec(node.left), getHeightRec(node.right));
    }

    public void search(int val) {
        Node curr = root;
        while (curr != null) {
            compareCount++;
            if (curr.val == val) return;
            curr = (val < curr.val) ? curr.left : curr.right;
        }
    }

    public int getCompareCount() { return compareCount; }

    public static void main(String[] args) {
        System.out.println("=== 課後作業五：樹狀實驗 ===");
        int[] data = {8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15};

        BstShapeExperiment bst1 = new BstShapeExperiment();
        for (int v : data) bst1.insert(v);
        for (int v : data) bst1.search(v);

        int[] sortedData = Arrays.copyOf(data, data.length);
        Arrays.sort(sortedData);
        BstShapeExperiment bst2 = new BstShapeExperiment();
        for (int v : sortedData) bst2.insert(v);
        for (int v : sortedData) bst2.search(v);

        System.out.printf("[平衡 BST] 樹高: %d | 搜尋 15 個元素總比較次數: %d%n",
                bst1.getHeight(), bst1.getCompareCount());
        System.out.printf("[傾斜 BST] 樹高: %d | 搜尋 15 個元素總比較次數: %d%n",
                bst2.getHeight(), bst2.getCompareCount());
    }
}