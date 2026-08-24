import java.util.ArrayList;
import java.util.List;

class FolderNode {
    String name;
    int ownSize;
    FolderNode left;
    FolderNode right;

    FolderNode(String name, int ownSize) {
        this.name = name;
        this.ownSize = ownSize;
    }
}

public class FolderSizeTree {

    public static int calculateSubtreeSize(FolderNode root) {
        if (root == null) return 0;

        int leftSize = calculateSubtreeSize(root.left);
        int rightSize = calculateSubtreeSize(root.right);

        return root.ownSize + leftSize + rightSize;
    }

    public static FolderNode findLargestSubtree(FolderNode root) {
        FolderNode[] largest = new FolderNode[1];
        int[] maxSize = new int[]{-1};
        findLargestHelper(root, largest, maxSize);
        return largest[0];
    }

    private static int findLargestHelper(FolderNode node, FolderNode[] largest, int[] maxSize) {
        if (node == null) return 0;

        int totalSize = node.ownSize + findLargestHelper(node.left, largest, maxSize)
                + findLargestHelper(node.right, largest, maxSize);

        if (totalSize > maxSize[0]) {
            maxSize[0] = totalSize;
            largest[0] = node;
        }
        return totalSize;
    }

    public static List<String> getLeafFolders(FolderNode root) {
        List<String> leaves = new ArrayList<>();
        findLeavesHelper(root, leaves);
        return leaves;
    }

    private static void findLeavesHelper(FolderNode node, List<String> leaves) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            leaves.add(node.name + " (" + node.ownSize + " KB)");
            return;
        }
        findLeavesHelper(node.left, leaves);
        findLeavesHelper(node.right, leaves);
    }

    public static void main(String[] args) {
        System.out.println("=== 課後作業四：目錄大小累加 ===");
        FolderNode root = new FolderNode("Root", 10);
        root.left = new FolderNode("Docs", 20);
        root.right = new FolderNode("Media", 50);
        root.left.left = new FolderNode("Work", 30);
        root.left.right = new FolderNode("Home", 15);

        System.out.println("Root 總大小 (含子目錄): " + calculateSubtreeSize(root) + " KB");

        FolderNode largest = findLargestSubtree(root);
        System.out.println("最大 Subtree 節點: " + (largest != null ? largest.name : "None"));

        System.out.println("Leaf Folders: " + getLeafFolders(root));
    }
}