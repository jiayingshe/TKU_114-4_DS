import java.util.List;

public class TraversalTestReport {

    public static void runReport(String testName, Node root,
                                 List<String> expPre, List<String> expIn,
                                 List<String> expPost, List<String> expLevel) {
        System.out.println("========================================");
        System.out.println("測試案例: " + testName);
        System.out.println("========================================");

        List<String> actPre = TraversalResultCollector.preorder(root);
        List<String> actIn = TraversalResultCollector.inorder(root);
        List<String> actPost = TraversalResultCollector.postorder(root);
        List<String> actLevel = TraversalResultCollector.levelOrder(root);

        printComparison("Preorder  ", expPre, actPre);
        printComparison("Inorder   ", expIn, actIn);
        printComparison("Postorder ", expPost, actPost);
        printComparison("LevelOrder", expLevel, actLevel);
        System.out.println();
    }

    private static void printComparison(String type, List<String> expected, List<String> actual) {
        boolean matches = expected.equals(actual);
        System.out.printf("%s -> 預期: %-15s | 實際: %-15s | 結果: %s%n",
                type, expected, actual, matches ? "PASS" : "FAIL");
    }

    public static void main(String[] args) {
        System.out.println("=== 課後作業六：Traversal 測試報告 ===");

        runReport("1. Empty Tree", null,
                List.of(), List.of(), List.of(), List.of());

        Node single = new Node("A");
        runReport("2. Single Node", single,
                List.of("A"), List.of("A"), List.of("A"), List.of("A"));

        Node onlyLeft = new Node("A");
        onlyLeft.left = new Node("B");
        runReport("3. Only Left Tree", onlyLeft,
                List.of("A", "B"), List.of("B", "A"), List.of("B", "A"), List.of("A", "B"));

        Node onlyRight = new Node("A");
        onlyRight.right = new Node("C");
        runReport("4. Only Right Tree", onlyRight,
                List.of("A", "C"), List.of("A", "C"), List.of("C", "A"), List.of("A", "C"));

        Node complete = new Node("A");
        complete.left = new Node("B");
        complete.right = new Node("C");
        runReport("5. Complete Tree", complete,
                List.of("A", "B", "C"), List.of("B", "A", "C"), List.of("B", "C", "A"), List.of("A", "B", "C"));

        
        Node irregular = new Node("A");
        irregular.left = new Node("B");
        irregular.left.right = new Node("C");
        runReport("6. Irregular Tree", irregular,
                List.of("A", "B", "C"), List.of("B", "C", "A"), List.of("C", "B", "A"), List.of("A", "B", "C"));
    }
}