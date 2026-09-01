import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ServiceRequestSystem {

    public static class Request implements Comparable<Request> {
        private final String id;
        private final int priority;
        private final long timestamp;

        public Request(String id, int priority, long timestamp) {
            this.id = id;
            this.priority = priority;
            this.timestamp = timestamp;
        }

        public String getId() { return id; }
        public int getPriority() { return priority; }

        @Override
        public int compareTo(Request other) {
            if (this.priority != other.priority) {
                return Integer.compare(other.priority, this.priority);
            }
            return Long.compare(this.timestamp, other.timestamp);
        }

        @Override
        public String toString() {
            return "Request{id='" + id + "', priority=" + priority + '}';
        }
    }

    private final Map<String, Request> requestMap = new HashMap<>();
    private final PriorityQueue<Request> priorityQueue = new PriorityQueue<>();

    public void addRequest(String id, int priority) {
        cancelRequest(id);
        Request req = new Request(id, priority, System.nanoTime());
        requestMap.put(id, req);
        priorityQueue.add(req);
    }

    public Request getRequest(String id) {
        return requestMap.get(id);
    }

    public Request processNextRequest() {
        if (priorityQueue.isEmpty()) return null;
        Request next = priorityQueue.poll();
        requestMap.remove(next.getId());
        return next;
    }

    public boolean cancelRequest(String id) {
        Request req = requestMap.remove(id);
        if (req != null) {
            priorityQueue.remove(req);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        ServiceRequestSystem sys = new ServiceRequestSystem();
        sys.addRequest("R101", 2);
        sys.addRequest("R102", 5);
        sys.addRequest("R103", 3);

        System.out.println("Cancel R101: " + sys.cancelRequest("R101"));
        System.out.println("Next: " + sys.processNextRequest());
        System.out.println("Next: " + sys.processNextRequest());
    }
}