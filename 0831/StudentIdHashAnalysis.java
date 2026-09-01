import java.util.List;

public class StudentIdHashAnalysis {

    public static class AnalysisReport {
        public int totalItems;
        public int totalCollisions;
        public int maxChain;
        public double avgChain;

        @Override
        public String toString() {
            return String.format("總筆數: %d | 總碰撞: %d | 最大鏈長: %d | 平均鏈長: %.2f",
                    totalItems, totalCollisions, maxChain, avgChain);
        }
    }

    public static AnalysisReport analyze(List<String> studentIds, int numBuckets) {
        AnalysisReport report = new AnalysisReport();
        if (numBuckets <= 0 || studentIds == null || studentIds.isEmpty()) return report;

        int[] bucketCounts = new int[numBuckets];

        for (String id : studentIds) {
            if (id == null) continue;
            int hash = Math.abs(id.hashCode());
            int index = hash % numBuckets;
            bucketCounts[index]++;
            report.totalItems++;
        }

        int activeBuckets = 0;
        for (int count : bucketCounts) {
            if (count > 0) {
                activeBuckets++;
                report.totalCollisions += (count - 1);
                report.maxChain = Math.max(report.maxChain, count);
            }
        }

        report.avgChain = activeBuckets == 0 ? 0 : (double) report.totalItems / activeBuckets;
        return report;
    }

    public static void main(String[] args) {
        List<String> ids = List.of(
            "S101", "S102", "S103", "S104", "S105", 
            "S106", "S107", "S108", "S109", "S110"
        );

        System.out.println("5 個桶分析結果  : " + analyze(ids, 5));
        System.out.println("10 個桶分析結果 : " + analyze(ids, 10));
    }
}
