import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class Q07_AdjacencyListGraph {
    private final Map<String, LinkedHashSet<String>> adj = new HashMap<>();

    public boolean addVertex(String vertex) {
        if (vertex == null || adj.containsKey(vertex)) return false;
        adj.put(vertex, new LinkedHashSet<>());
        return true;
    }

    public boolean addEdge(String from, String to) {
        if (from == null || to == null || from.equals(to)) return false;
        if (!adj.containsKey(from) || !adj.containsKey(to)) return false;
        
        LinkedHashSet<String> edges = adj.get(from);
        if (edges.contains(to)) return false;
        
        edges.add(to);
        return true;
    }

    public boolean removeEdge(String from, String to) {
        if (!adj.containsKey(from) || !adj.containsKey(to)) return false;
        return adj.get(from).remove(to);
    }

    public List<String> outgoing(String vertex) {
        if (!adj.containsKey(vertex)) return new ArrayList<>();
        return new ArrayList<>(adj.get(vertex));
    }

    public int inDegree(String vertex) {
        if (!adj.containsKey(vertex)) return 0;
        int count = 0;
        for (LinkedHashSet<String> edges : adj.values()) {
            if (edges.contains(vertex)) count++;
        }
        return count;
    }

    public int edgeCount() {
        int count = 0;
        for (LinkedHashSet<String> edges : adj.values()) {
            count += edges.size();
        }
        return count;
    }
}