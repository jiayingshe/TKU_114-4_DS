import java.util.ArrayList;
import java.util.List;

public class CampusMatrixGraph {

    private final boolean[][] adjMatrix;
    private final int numVertices;

    public CampusMatrixGraph(int vertices) {
        this.numVertices = vertices;
        this.adjMatrix = new boolean[vertices][vertices];
    }

    public void addEdge(int u, int v) {
        if (isValidVertex(u) && isValidVertex(v)) {
            adjMatrix[u][v] = true;
            adjMatrix[v][u] = true;
        }
    }

    public void removeEdge(int u, int v) {
        if (isValidVertex(u) && isValidVertex(v)) {
            adjMatrix[u][v] = false;
            adjMatrix[v][u] = false;
        }
    }

    public int getDegree(int u) {
        if (!isValidVertex(u)) return 0;
        int degree = 0;
        for (int v = 0; v < numVertices; v++) {
            if (adjMatrix[u][v]) degree++;
        }
        return degree;
    }

    public List<Integer> getNeighbors(int u) {
        List<Integer> neighbors = new ArrayList<>();
        if (!isValidVertex(u)) return neighbors;
        for (int v = 0; v < numVertices; v++) {
            if (adjMatrix[u][v]) neighbors.add(v);
        }
        return neighbors;
    }

    public int getEdgeCount() {
        int count = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = i; j < numVertices; j++) {
                if (adjMatrix[i][j]) count++;
            }
        }
        return count;
    }

    private boolean isValidVertex(int v) {
        return v >= 0 && v < numVertices;
    }

    public static void main(String[] args) {
        CampusMatrixGraph graph = new CampusMatrixGraph(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(0, 1);

        System.out.println("總邊數: " + graph.getEdgeCount());
        System.out.println("節點 0 的度數: " + graph.getDegree(0));
        System.out.println("節點 0 的鄰居: " + graph.getNeighbors(0));
    }
}