import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Patient {
    private final String id;
    private final String name;

    public Patient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "Patient{id='" + id + "', name='" + name + "'}";
    }
}

public class ClinicQueueSystem {
    private final Queue<Patient> queue = new ArrayDeque<>();
    private final List<Patient> completedList = new ArrayList<>();

    public void register(Patient patient) {
        queue.offer(patient);
        System.out.println("掛號成功: " + patient);
    }

    public boolean cancelRegistration(String patientId) {
        boolean removed = queue.removeIf(p -> p.getId().equals(patientId));
        if (removed) {
            System.out.println("成功取消掛號，病歷號: " + patientId);
        } else {
            System.out.println("取消失敗，找不到病歷號: " + patientId);
        }
        return removed;
    }

    public Patient callNext() {
        Patient p = queue.poll();
        if (p != null) {
            completedList.add(p);
            System.out.println("請就診: " + p);
        } else {
            System.out.println("叫號失敗: 當前無等待病患");
        }
        return p;
    }

    public Patient peekNext() {
        Patient p = queue.peek();
        System.out.println("下一位預計看診: " + (p != null ? p : "無"));
        return p;
    }

    public void printCompletedList() {
        System.out.println("=== 當日已看診完成清單 ===");
        if (completedList.isEmpty()) {
            System.out.println("(無)");
        } else {
            completedList.forEach(System.out::println);
        }
    }

    public static void main(String[] args) {
        ClinicQueueSystem clinic = new ClinicQueueSystem();

        clinic.register(new Patient("P001", "張三"));
        clinic.register(new Patient("P002", "李四"));
        clinic.register(new Patient("P003", "王五"));

        clinic.peekNext();

        clinic.cancelRegistration("P002");

        clinic.callNext();
        clinic.callNext();
        clinic.callNext();

        clinic.printCompletedList();
    }
}