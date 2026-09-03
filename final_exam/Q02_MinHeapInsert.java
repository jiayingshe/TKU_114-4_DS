import java.util.ArrayList;
import java.util.List;

public class Q02_MinHeapInsert {
    private final List<Integer> heap = new ArrayList<>();

    public void add(int value) {
        heap.add(value);
        int current = heap.size() - 1;
        while (current > 0) {
            int parent = (current - 1) / 2;
            if (heap.get(current) < heap.get(parent)) {
                int temp = heap.get(current);
                heap.set(current, heap.get(parent));
                heap.set(parent, temp);
                current = parent;
            } else {
                break;
            }
        }
    }

    public Integer peek() {
        if (heap.isEmpty()) return null;
        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public List<Integer> snapshot() {
        return new ArrayList<>(heap);
    }

    public boolean isValidMinHeap() {
        for (int i = 0; i < heap.size(); i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < heap.size() && heap.get(left) < heap.get(i)) return false;
            if (right < heap.size() && heap.get(right) < heap.get(i)) return false;
        }
        return true;
    }
}