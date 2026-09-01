import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayMinHeap {

    private int[] heap;
    private int size;

    public ArrayMinHeap(int initialCapacity) {
        this.heap = new int[Math.max(1, initialCapacity)];
        this.size = 0;
    }

    public void add(int value) {
        if (size == heap.length) {
            resize();
        }
        heap[size] = value;
        siftUp(size);
        size++;
    }

    public int peek() {
        if (isEmpty()) throw new NoSuchElementException("Heap is empty");
        return heap[0];
    }

    public int removeMin() {
        if (isEmpty()) throw new NoSuchElementException("Heap is empty");
        int minVal = heap[0];
        heap[0] = heap[size - 1];
        size--;
        siftDown(0);
        return minVal;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int[] snapshot() {
        return Arrays.copyOf(heap, size);
    }

    private void resize() {
        heap = Arrays.copyOf(heap, heap.length * 2);
    }

    private void siftUp(int idx) {
        while (idx > 0) {
            int parent = (idx - 1) / 2;
            if (heap[idx] < heap[parent]) {
                swap(idx, parent);
                idx = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int idx) {
        while (idx * 2 + 1 < size) {
            int left = idx * 2 + 1;
            int right = idx * 2 + 2;
            int smallest = left;

            if (right < size && heap[right] < heap[left]) {
                smallest = right;
            }

            if (heap[idx] > heap[smallest]) {
                swap(idx, smallest);
                idx = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public static void main(String[] args) {
        ArrayMinHeap heap = new ArrayMinHeap(4);
        
        // 測試至少 20 筆資料新增與動態擴容
        for (int i = 25; i >= 1; i--) {
            heap.add(i);
        }

        System.out.println("當前資料筆數: " + heap.size());
        System.out.println("堆快照 (Snapshot): " + Arrays.toString(heap.snapshot()));
        System.out.println("最小值 (Peek): " + heap.peek());
        
        System.out.println("依序刪除最小值:");
        while (!heap.isEmpty()) {
            System.out.print(heap.removeMin() + " ");
        }
        System.out.println();
    }
}