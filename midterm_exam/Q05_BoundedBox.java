import java.util.ArrayList;
import java.util.List;

public class Q05_BoundedBox<T extends Comparable<T>> {

    private final int capacity;
    private final List<T> items = new ArrayList<>();

    public Q05_BoundedBox(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1");
        }
        this.capacity = capacity;
    }

    public boolean add(T value) {
        if (value == null || isFull()) {
            return false;
        }
        items.add(value);
        return true;
    }

    public int size() {
        return items.size();
    }

    public boolean isFull() {
        return items.size() >= capacity;
    }

    public T minimum() {
        if (items.isEmpty()) return null;
        T min = items.get(0);
        for (int i = 1; i < items.size(); i++) {
            if (items.get(i).compareTo(min) < 0) {
                min = items.get(i);
            }
        }
        return min;
    }

    public T maximum() {
        if (items.isEmpty()) return null;
        T max = items.get(0);
        for (int i = 1; i < items.size(); i++) {
            if (items.get(i).compareTo(max) > 0) {
                max = items.get(i);
            }
        }
        return max;
    }

    public int countGreaterThan(T threshold) {
        if (threshold == null) return 0;
        int count = 0;
        for (T item : items) {
            if (item.compareTo(threshold) > 0) {
                count++;
            }
        }
        return count;
    }

    public List<T> snapshot() {
        return new ArrayList<>(items);
    }

    public static void main(String[] args) {
        Q05_BoundedBox<Integer> box = new Q05_BoundedBox<>(3);
        System.out.println(box.add(40));
        System.out.println(box.add(10));
        System.out.println(box.add(30));
        System.out.println(box.add(20));
        System.out.println(box.minimum());
        System.out.println(box.maximum());
        System.out.println(box.countGreaterThan(25));
        System.out.println(box.snapshot());
    }
}