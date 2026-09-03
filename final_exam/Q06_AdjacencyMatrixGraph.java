import java.util.ArrayList;
import java.util.List;

public class Q06_AdjacencyMatrixGraph {
    private final List<String> vertices;
    private final boolean[][] matrix;
    private final int size;

    public Q06_AdjacencyMatrixGraph(List<String> vertices) {
        this.vertices = new ArrayList<>();
        if (vertices != null) {
            this.vertices.addAll(vertices);
        }
        this.size = this.vertices.size();
        this.matrix = new boolean[size][size];
    }

    private int indexOf(String v) {
        return vertices.indexOf(v);
    }

    public boolean addEdge(String first, String second) {
        int i1 = indexOf(first);
        int i2 = indexOf(second);
        if (i1 == -1 || i2 == -1 || i1 == i2) return false;
        if (matrix[i1][i2]) return false;
        
        matrix[i1][i2] = true;
        matrix[i2][i1] = true;
        return true;
    }

    public boolean removeEdge(String first, String second) {
        int i1 = indexOf(first);
        int i2 = indexOf(second);
        if (i1 == -1 || i2 == -1) return false;
        if (!matrix[i1][i2]) return false;
        
        matrix[i1][i2] = false;
        matrix[i2][i1] = false;
        return true;
    }

    public boolean hasEdge(String first, String second) {
        int i1 = indexOf(first);
        int i2 = indexOf(second);
        if (i1 == -1 || i2 == -1) return false;
        return matrix[i1][i2];
    }

    public int degree(String vertex) {
        int idx = indexOf(vertex);
        if (idx == -1) return 0;
        int d = 0;
        for (int i = 0; i < size; i++) {
            if (matrix[idx][i]) d++;
        }
        return d;
    }

    public List<String> neighbors(String vertex) {
        List<String> res = new ArrayList<>();
        int idx = indexOf(vertex);
        if (idx == -1) return res;
        for (int i = 0; i < size; i++) {
            if (matrix[idx][i]) {
                res.add(vertices.get(i));
            }
        }
        return res;
    }
}