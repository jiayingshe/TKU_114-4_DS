
public class IntegratedStructureAudit {

    public enum DataStructureType {
        LIST, QUEUE, BST, HEAP, HASH_TABLE, GRAPH
    }

    public static class AuditResult {
        public boolean isAppropriate;
        public String diagnosis;
        public DataStructureType recommendedType;

        public AuditResult(boolean isAppropriate, String diagnosis, DataStructureType recommendedType) {
            this.isAppropriate = isAppropriate;
            this.diagnosis = diagnosis;
            this.recommendedType = recommendedType;
        }

        @Override
        public String toString() {
            return "AuditResult{" +
                    "isAppropriate=" + isAppropriate +
                    ", diagnosis='" + diagnosis + '\'' +
                    ", recommendedType=" + recommendedType +
                    '}';
        }
    }

    public static AuditResult audit(DataStructureType current, String useCase) {
        String lowerCase = useCase.toLowerCase();

        if (lowerCase.contains("key-value") || lowerCase.contains("fast lookup")) {
            if (current == DataStructureType.HASH_TABLE) {
                return new AuditResult(true, "Correct usage for O(1) average lookup.", current);
            }
            return new AuditResult(false, "Inefficient lookup. Use Hash Table for key-value pair access.", DataStructureType.HASH_TABLE);
        }

        if (lowerCase.contains("priority") || lowerCase.contains("min/max")) {
            if (current == DataStructureType.HEAP) {
                return new AuditResult(true, "Correct usage for min/max or priority management.", current);
            }
            return new AuditResult(false, "Suboptimal for priority operations. Heap is recommended.", DataStructureType.HEAP);
        }

        if (lowerCase.contains("fifo") || lowerCase.contains("first-in-first-out")) {
            if (current == DataStructureType.QUEUE) {
                return new AuditResult(true, "Correct usage for sequential processing.", current);
            }
            return new AuditResult(false, "Queue is better suited for FIFO semantics.", DataStructureType.QUEUE);
        }

        if (lowerCase.contains("relationship") || lowerCase.contains("network")) {
            if (current == DataStructureType.GRAPH) {
                return new AuditResult(true, "Correct usage for complex relational data.", current);
            }
            return new AuditResult(false, "Graph is recommended for modeling networks/relationships.", DataStructureType.GRAPH);
        }

        if (lowerCase.contains("sorted") || lowerCase.contains("range search")) {
            if (current == DataStructureType.BST) {
                return new AuditResult(true, "Correct usage for dynamic ordered traversal.", current);
            }
            return new AuditResult(false, "BST is recommended for ordered data and range operations.", DataStructureType.BST);
        }

        if (current == DataStructureType.LIST) {
            return new AuditResult(true, "Acceptable standard dynamic array structure.", current);
        }

        return new AuditResult(false, "Structure may not fit the workload requirement.", DataStructureType.LIST);
    }

    public static void main(String[] args) {
        System.out.println(audit(DataStructureType.LIST, "Fast lookup by key-value"));
        System.out.println(audit(DataStructureType.HEAP, "Find Min/Max element with priority"));
        System.out.println(audit(DataStructureType.QUEUE, "Process requests in FIFO order"));
    }
}