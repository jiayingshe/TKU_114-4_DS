import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class EventSimulationQueue {

    public static class Event implements Comparable<Event> {
        private final int id;
        private final long time;
        private final String type;
        private final int sequence;

        public Event(int id, long time, String type, int sequence) {
            this.id = id;
            this.time = time;
            this.type = type;
            this.sequence = sequence;
        }

        public int getId() { return id; }

        @Override
        public int compareTo(Event other) {
            if (this.time != other.time) {
                return Long.compare(this.time, other.time); // 時間先後
            }
            return Integer.compare(this.sequence, other.sequence); // 時間相同依序列
        }

        @Override
        public String toString() {
            return "Event{id=" + id + ", time=" + time + ", type='" + type + "', seq=" + sequence + "}";
        }
    }

    private final PriorityQueue<Event> queue = new PriorityQueue<>();
    private final List<String> executionLogs = new ArrayList<>();

    public void addEvent(Event event) {
        queue.add(event);
    }

    public boolean cancelEvent(int eventId) {
        return queue.removeIf(e -> e.getId() == eventId);
    }

    public void runSimulation() {
        while (!queue.isEmpty()) {
            Event current = queue.poll();
            String log = "Executed: " + current.toString();
            executionLogs.add(log);
            System.out.println(log);
        }
    }

    public static void main(String[] args) {
        EventSimulationQueue sim = new EventSimulationQueue();
        sim.addEvent(new Event(1, 100, "LOGIN", 1));
        sim.addEvent(new Event(2, 50, "CLICK", 2));
        sim.addEvent(new Event(3, 100, "PURCHASE", 2));
        sim.addEvent(new Event(4, 100, "LOGOUT", 1));

        System.out.println("取消事件 ID 2: " + sim.cancelEvent(2));
        System.out.println("--- 開始執行模擬 ---");
        sim.runSimulation();
    }
}