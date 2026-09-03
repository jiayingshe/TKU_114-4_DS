import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Q12_CampusDispatchSystem {
    public record Request(String id, String location, int priority, long sequence) {}

    private final Map<String, List<String>> roads = new HashMap<>();
    private final Set<String> requestIds = new HashSet<>();
    private final PriorityQueue<Request> pendingRequests = new PriorityQueue<>(
        Comparator.comparingInt(Request::priority)
            .thenComparingLong(Request::sequence)
    );

    public boolean addLocation(String location) {
        if (location == null || roads.containsKey(location)) return false;
        roads.put(location, new ArrayList<>());
        return true;
    }

    public boolean addRoad(String first, String second) {
        if (first == null || second == null || first.equals(second)) return false;
        if (!roads.containsKey(first) || !roads.containsKey(second)) return false;
        
        List<String> list1 = roads.get(first);
        List<String> list2 = roads.get(second);
        if (list1.contains(second)) return false;
        
        list1.add(second);
        list2.add(first);
        return true;
    }

    public boolean submit(Request request) {
        if (request == null || request.id() == null || request.location() == null) return false;
        if (requestIds.contains(request.id())) return false;
        if (!roads.containsKey(request.location())) return false;

        requestIds.add(request.id());
        pendingRequests.offer(request);
        return true;
    }

    public Request nextReachable(String serviceCenter) {
        if (serviceCenter == null || !roads.containsKey(serviceCenter)) return null;

        List<Request> unreachable = new ArrayList<>();
        Request reachableReq = null;

        while (!pendingRequests.isEmpty()) {
            Request req = pendingRequests.poll();
            List<String> path = route(serviceCenter, req.location());
            if (!path.isEmpty()) {
                reachableReq = req;
                break;
            } else {
                unreachable.add(req);
            }
        }

        for (Request r : unreachable) {
            pendingRequests.offer(r);
        }

        if (reachableReq != null) {
            requestIds.remove(reachableReq.id());
        }

        return reachableReq;
    }

    public List<String> route(String start, String target) {
        if (start == null || target == null) return new ArrayList<>();
        if (!roads.containsKey(start) || !roads.containsKey(target)) return new ArrayList<>();

        if (start.equals(target)) {
            List<String> single = new ArrayList<>();
            single.add(start);
            return single;
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> pred = new HashMap<>();

        queue.offer(start);
        visited.add(start);

        boolean found = false;
        while (!queue.isEmpty() && !found) {
            String curr = queue.poll();
            for (String neighbor : roads.get(curr)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    pred.put(neighbor, curr);
                    if (neighbor.equals(target)) {
                        found = true;
                        break;
                    }
                    queue.offer(neighbor);
                }
            }
        }

        if (!found) return new ArrayList<>();

        List<String> path = new ArrayList<>();
        String step = target;
        while (step != null) {
            path.add(step);
            step = pred.get(step);
        }
        Collections.reverse(path);
        return path;
    }

    public int pendingCount() {
        return pendingRequests.size();
    }
}
