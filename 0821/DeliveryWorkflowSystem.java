import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

class DeliveryOrder {
    private final String id;
    private final String destination;

    public DeliveryOrder(String id, String destination) {
        this.id = id;
        this.destination = destination;
    }

    public String getId() { return id; }
    public String getDestination() { return destination; }

    @Override
    public String toString() {
        return "Order[ID=" + id + ", Dest=" + destination + "]";
    }
}

public class DeliveryWorkflowSystem {
    private final Map<String, DeliveryOrder> orderMap = new HashMap<>();
    private final Queue<DeliveryOrder> waitingQueue = new ArrayDeque<>();
    private final Deque<DeliveryOrder> completedStack = new ArrayDeque<>();

    // 新增訂單（重複 id 不得加入）
    public boolean addOrder(String id, String destination) {
        if (orderMap.containsKey(id)) {
            System.out.println("新增失敗：重複的配送編號 " + id);
            return false;
        }
        DeliveryOrder order = new DeliveryOrder(id, destination);
        orderMap.put(id, order);
        waitingQueue.offer(order);
        System.out.println("成功新增訂單: " + order);
        return true;
    }

    // 處理下一個配送
    public DeliveryOrder processNext() {
        DeliveryOrder order = waitingQueue.poll();
        if (order != null) {
            completedStack.push(order);
            System.out.println("完成配送: " + order);
        } else {
            System.out.println("無等待配送的訂單");
        }
        return order;
    }

    // Undo（將最後完成的訂單移回等待佇列前端）
    public boolean undoLastCompletion() {
        if (completedStack.isEmpty()) {
            System.out.println("Undo 失敗：無已完成的配送紀錄");
            return false;
        }
        DeliveryOrder order = completedStack.pop();
        // 將撤銷的訂單放回 Queue 的最前端
        ((Deque<DeliveryOrder>) waitingQueue).addFirst(order);
        System.out.println("撤銷成功，訂單已放回等待隊列: " + order);
        return true;
    }

    // 依配送編號查詢
    public DeliveryOrder findById(String id) {
        return orderMap.get(id);
    }

    // 統計資料
    public void printStats() {
        System.out.printf("統計 - 總建立數: %d | 等待中: %d | 已完成: %d%n",
                orderMap.size(), waitingQueue.size(), completedStack.size());
    }

    public static void main(String[] args) {
        DeliveryWorkflowSystem system = new DeliveryWorkflowSystem();

        system.addOrder("D101", "台北");
        system.addOrder("D102", "台中");
        system.addOrder("D101", "高雄"); // 測試重複 id

        system.processNext();
        system.printStats();

        system.undoLastCompletion();
        system.printStats();

        System.out.println("查詢 D102: " + system.findById("D102"));
    }
}