import java.util.NoSuchElementException;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class BinaryTreeStatistics {

    public static int size(TreeNode root) {
        if (root == null) return 0;
        return 1 + size(root.left) + size(root.right);
    }

    public static int sum(TreeNode root) {
        if (root == null) return 0;
        return root.val + sum(root.left) + sum(root.right);
    }

    public static int maximum(TreeNode root) {
        if (root == null) {
            throw new NoSuchElementException("Cannot find maximum of an empty tree.");
        }
        int max = root.val;
        if (root.left != null) {
            max = Math.max(max, maximum(root.left));
        }
        if (root.right != null) {
            max = Math.max(max, maximum(root.right));
        }
        return max;
    }

    public static int leafCount(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        return leafCount(root.left) + leafCount(root.right);
    }

    public static int height(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(height(root.left), height(root.right));
    }

    public static boolean contains(TreeNode root, int target) {
        if (root == null) return false;
        if (root.val == target) return true;
        return contains(root.left, target) || contains(root.right, target);
    }

    public static void main(String[] args) {
        System.out.println("=== 課後作業二：Binary Tree 統計系統 ===");

        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(7);

        System.out.println("Size: " + size(root));
        System.out.println("Sum: " + sum(root));
        System.out.println("Maximum: " + maximum(root));
        System.out.println("Leaf Count: " + leafCount(root));
        System.out.println("Height: " + height(root));
        System.out.println("Contains 7: " + contains(root, 7));
        System.out.println("Contains 99: " + contains(root, 99));

        try {
            maximum(null);
        } catch (NoSuchElementException e) {
            System.out.println("空樹 Maximum 測試成功捕捉例外: " + e.getMessage());
        }
    }
}