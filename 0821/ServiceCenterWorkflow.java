import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class ServiceTicket {
    private final String id;
    private final String clientName;

    public ServiceTicket(String id, String clientName) {
        this.id = id;
        this.clientName = clientName;
    }

    public String getId() { return id; }
    public String getClientName() { return clientName; }

    @Override
    public String toString() {
        return "Ticket[ID=" + id + ", Client=" + clientName + "]";
    }
}

public class ServiceCenterWorkflow {
    private final Map<String, ServiceTicket> ticketMap = new HashMap<>();
    private final Deque<ServiceTicket> waitingQueue = new ArrayDeque<>();
    private final Deque<ServiceTicket> completedStack = new ArrayDeque<>();
    private final Set<String> existingIds = new HashSet<>();

    // 建立工單（Prevent Duplicate ID）
    public boolean createTicket(String id, String clientName) {
        if (existingIds.contains(id)) {
            System.out.println("[建立失敗] 重複的 Ticket ID: " + id);
            return false;
        }
        ServiceTicket ticket = new ServiceTicket(id, clientName);
        existingIds.add(id);
        ticketMap.put(id, ticket);
        waitingQueue.offer(ticket);
        System.out.println("[建立成功] " + ticket);
        return true;
    }

    // 處理下一個
    public ServiceTicket processNext() {
        ServiceTicket ticket = waitingQueue.poll();
        if (ticket != null) {
            completedStack.push(ticket);
            System.out.println("[處理完成] " + ticket);
        } else {
            System.out.println("[處理失敗] 當前 Queue 為空");
        }
        return ticket;
    }

    // 取消尚未處理的 Ticket
    public boolean cancelWaiting(String id) {
        ServiceTicket ticket = ticketMap.get(id);
        if (ticket == null || !waitingQueue.contains(ticket)) {
            System.out.println("[取消失敗] 找不到等待中的 Ticket: " + id);
            return false;
        }
        waitingQueue.remove(ticket);
        System.out.println("[取消成功] 已取消等待中 Ticket: " + id);
        return true;
    }

    // Undo：將最後完成的放回 waiting queue 前端
    public boolean undoLastCompletion() {
        if (completedStack.isEmpty()) {
            System.out.println("[Undo 失敗] 無已完成記錄");
            return false;
        }
        ServiceTicket ticket = completedStack.pop();
        waitingQueue.addFirst(ticket);
        System.out.println("[Undo 成功] Ticket 已退回等待隊列前端: " + ticket);
        return true;
    }

    public ServiceTicket findById(String id) {
        return ticketMap.get(id);
    }

    public void printSummary() {
        System.out.printf("=== 系統狀態摘要 ===%n總計: %d | 等待中: %d | 已完成: %d%n",
                existingIds.size(), waitingQueue.size(), completedStack.size());
        System.out.println("Waiting Queue: " + waitingQueue);
        System.out.println("Completed Stack: " + completedStack);
    }

    public static void main(String[] args) {
        ServiceCenterWorkflow center = new ServiceCenterWorkflow();

        System.out.println("--- 1. 測試建立與重複 ID 阻擋 ---");
        center.createTicket("TK-01", "Alice");
        center.createTicket("TK-02", "Bob");
        center.createTicket("TK-01", "Charlie"); // 重複 ID 測試

        System.out.println("\n--- 2. 測試處理與取消不存的 ID ---");
        center.processNext(); // TK-01 完成
        center.cancelWaiting("TK-99"); // 取消不存在 ID
        center.cancelWaiting("TK-01"); // 試圖取消已完成的 ID（應失敗）

        System.out.println("\n--- 3. 測試連續兩次 Undo ---");
        center.createTicket("TK-03", "David");
        center.processNext(); // TK-02 完成
        center.processNext(); // TK-03 完成

        center.undoLastCompletion(); // Undo TK-03
        center.undoLastCompletion(); // Undo TK-02

        System.out.println("\n--- 4. 測試空 Queue 處理與多餘 Undo ---");
        center.undoLastCompletion(); // Undo TK-01
        center.undoLastCompletion(); // 測試空 Stack Undo

        center.printSummary();
    }
}