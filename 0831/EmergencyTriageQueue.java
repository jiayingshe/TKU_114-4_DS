import java.util.PriorityQueue;

public class EmergencyTriageQueue {

    public static class Patient implements Comparable<Patient> {
        private final String medicalId;
        private final int urgency; // 數字越大越危急
        private final int arrivalOrder;

        public Patient(String medicalId, int urgency, int arrivalOrder) {
            this.medicalId = medicalId;
            this.urgency = urgency;
            this.arrivalOrder = arrivalOrder;
        }

        public String getMedicalId() { return medicalId; }
        public int getUrgency() { return urgency; }
        public int getArrivalOrder() { return arrivalOrder; }

        @Override
        public int compareTo(Patient other) {
            if (this.urgency != other.urgency) {
                return Integer.compare(other.urgency, this.urgency); // 危急程度高優先
            }
            if (this.arrivalOrder != other.arrivalOrder) {
                return Integer.compare(this.arrivalOrder, other.arrivalOrder); // 到院順序小優先
            }
            return this.medicalId.compareTo(other.medicalId); // 病歷號字典序
        }

        @Override
        public String toString() {
            return "Patient[" + medicalId + " | 危急度:" + urgency + " | 順序:" + arrivalOrder + "]";
        }
    }

    private final PriorityQueue<Patient> queue = new PriorityQueue<>();
    private int orderCounter = 0;

    public void checkIn(String medicalId, int urgency) {
        queue.add(new Patient(medicalId, urgency, ++orderCounter));
    }

    public String peekNext() {
        if (queue.isEmpty()) return "佇列為空，無下一位患者";
        return queue.peek().toString();
    }

    public String callNext() {
        if (queue.isEmpty()) return "叫號失敗：佇列為空";
        return "叫號成功: " + queue.poll().toString();
    }

    public int size() {
        return queue.size();
    }

    public static void main(String[] args) {
        EmergencyTriageQueue triage = new EmergencyTriageQueue();
        triage.checkIn("P101", 3);
        triage.checkIn("P102", 5);
        triage.checkIn("P103", 5);
        triage.checkIn("P104", 1);

        System.out.println("查看下一位: " + triage.peekNext());
        System.out.println(triage.callNext());
        System.out.println(triage.callNext());
        System.out.println(triage.callNext());
        System.out.println(triage.callNext());
        System.out.println(triage.callNext()); // 空佇列處理測試
    }
}