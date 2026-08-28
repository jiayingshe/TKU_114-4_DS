import java.util.ArrayList;
import java.util.List;

public class DirectoryTreeReport {

    public static class Node {
        private final String name;
        private final boolean isDirectory;
        private final long size;
        private final List<Node> children = new ArrayList<>();

        public Node(String name, long size) {
            this.name = name;
            this.isDirectory = false;
            this.size = Math.max(0, size);
        }

        public Node(String name) {
            this.name = name;
            this.isDirectory = true;
            this.size = 0;
        }

        public void addChild(Node child) {
            if (isDirectory && child != null) {
                children.add(child);
            }
        }

        public String getName() { return name; }
        public boolean isDirectory() { return isDirectory; }
        public long getSize() { return size; }
        public List<Node> getChildren() { return children; }
    }

    public static class Report {
        public int totalNodes;
        public int fileCount;
        public int directoryCount;
        public int height;
        public Node maxFile;
        public long totalSize;

        @Override
        public String toString() {
            return String.format("Total Nodes: %d | Files: %d | Directories: %d | Height: %d | Total Size: %d | Max File: %s",
                    totalNodes, fileCount, directoryCount, height, totalSize,
                    (maxFile != null ? maxFile.getName() + " (" + maxFile.getSize() + "B)" : "None"));
        }
    }

    public static Report generateReport(Node root) {
        Report report = new Report();
        if (root == null) return report;

        postOrderCalculate(root, report);
        report.height = getHeight(root);
        return report;
    }

    private static long postOrderCalculate(Node node, Report report) {
        if (node == null) return 0;

        report.totalNodes++;
        if (node.isDirectory()) {
            report.directoryCount++;
            long currentDirectoryTotal = 0;
            for (Node child : node.getChildren()) {
                currentDirectoryTotal += postOrderCalculate(child, report);
            }
            return currentDirectoryTotal;
        } else {
            report.fileCount++;
            report.totalSize += node.getSize();
            if (report.maxFile == null || node.getSize() > report.maxFile.getSize()) {
                report.maxFile = node;
            }
            return node.getSize();
        }
    }

    private static int getHeight(Node node) {
        if (node == null || !node.isDirectory() || node.getChildren().isEmpty()) {
            return 0;
        }
        int maxChildHeight = 0;
        for (Node child : node.getChildren()) {
            maxChildHeight = Math.max(maxChildHeight, getHeight(child));
        }
        return 1 + maxChildHeight;
    }

    public static void main(String[] args) {
        Node root = new Node("root");
        Node docs = new Node("docs");
        Node pic = new Node("pictures");

        docs.addChild(new Node("resume.pdf", 500));
        docs.addChild(new Node("notes.txt", 150));
        pic.addChild(new Node("avatar.png", 1200));

        root.addChild(docs);
        root.addChild(pic);
        root.addChild(new Node("config.sys", 50));

        Report report = generateReport(root);
        System.out.println(report);
    }
}