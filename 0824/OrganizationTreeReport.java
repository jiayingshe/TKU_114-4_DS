import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class OrgNode {
    String name;
    OrgNode left;
    OrgNode right;

    OrgNode(String name) {
        this.name = name;
    }
}

public class OrganizationTreeReport {

    public static OrgNode findParent(OrgNode root, String targetName) {
        if (root == null || root.name.equals(targetName)) return null;

        if ((root.left != null && root.left.name.equals(targetName)) ||
            (root.right != null && root.right.name.equals(targetName))) {
            return root;
        }

        OrgNode leftResult = findParent(root.left, targetName);
        if (leftResult != null) return leftResult;

        return findParent(root.right, targetName);
    }

    public static int findDepth(OrgNode root, String targetName) {
        return findDepthHelper(root, targetName, 0);
    }

    private static int findDepthHelper(OrgNode node, String targetName, int currentDepth) {
        if (node == null) return -1;
        if (node.name.equals(targetName)) return currentDepth;

        int leftDepth = findDepthHelper(node.left, targetName, currentDepth + 1);
        if (leftDepth != -1) return leftDepth;

        return findDepthHelper(node.right, targetName, currentDepth + 1);
    }

    public static List<String> pathFromRoot(OrgNode root, String targetName) {
        List<String> path = new ArrayList<>();
        findPathHelper(root, targetName, path);
        return path;
    }

    private static boolean findPathHelper(OrgNode node, String targetName, List<String> path) {
        if (node == null) return false;

        path.add(node.name);
        if (node.name.equals(targetName)) return true;

        if (findPathHelper(node.left, targetName, path) || findPathHelper(node.right, targetName, path)) {
            return true;
        }

        path.remove(path.size() - 1); // Backtrack
        return false;
    }

    public static void printByLevel(OrgNode root) {
        if (root == null) {
            System.out.println("組織樹為空");
            return;
        }

        Queue<OrgNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.print("Level " + level + ": ");

            for (int i = 0; i < levelSize; i++) {
                OrgNode curr = queue.poll();
                System.out.print(curr.name + " ");
                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
            System.out.println();
            level++;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 課後作業五：組織架構報表 ===");

        OrgNode root = new OrgNode("CEO");
        root.left = new OrgNode("VP1");
        root.right = new OrgNode("VP2");
        root.left.left = new OrgNode("DevMgr");

        printByLevel(root);

        OrgNode parent = findParent(root, "DevMgr");
        System.out.println("DevMgr 的父單位: " + (parent != null ? parent.name : "無"));

        System.out.println("VP2 的深度: " + findDepth(root, "VP2"));

        System.out.println("到 DevMgr 的路徑: " + pathFromRoot(root, "DevMgr"));

        System.out.println("不存在單位的 Parent: " + findParent(root, "Unknown"));
        System.out.println("不存在單位的 Depth: " + findDepth(root, "Unknown"));
        System.out.println("不存在單位的 Path: " + pathFromRoot(root, "Unknown"));
    }
}