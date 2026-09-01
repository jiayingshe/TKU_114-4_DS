
public class ResizableStringMap {

    private static class Node {
        String key;
        String value;
        Node next;

        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node[] table;
    private int size;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    public ResizableStringMap() {
        this(4);
    }

    public ResizableStringMap(int initialCapacity) {
        table = new Node[Math.max(1, initialCapacity)];
        size = 0;
    }

    private int getBucketIndex(String key, int capacity) {
        if (key == null) return 0;
        return Math.floorMod(key.hashCode(), capacity);
    }

    public void put(String key, String value) {
        if ((double) (size + 1) / table.length > LOAD_FACTOR_THRESHOLD) {
            resize();
        }

        int index = getBucketIndex(key, table.length);
        Node curr = table[index];

        while (curr != null) {
            if ((curr.key == null && key == null) || (curr.key != null && curr.key.equals(key))) {
                curr.value = value;
                return;
            }
            curr = curr.next;
        }

        Node newNode = new Node(key, value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
    }

    public String get(String key) {
        int index = getBucketIndex(key, table.length);
        Node curr = table[index];
        while (curr != null) {
            if ((curr.key == null && key == null) || (curr.key != null && curr.key.equals(key))) {
                return curr.value;
            }
            curr = curr.next;
        }
        return null;
    }

    private void resize() {
        int newCapacity = table.length * 2 + 1;
        Node[] newTable = new Node[newCapacity];

        for (Node head : table) {
            Node curr = head;
            while (curr != null) {
                Node next = curr.next;
                int newIndex = getBucketIndex(curr.key, newCapacity);
                curr.next = newTable[newIndex];
                newTable[newIndex] = curr;
                curr = next;
            }
        }
        table = newTable;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return table.length;
    }

    public void printStatus() {
        System.out.println("Size: " + size + " | Capacity: " + table.length);
    }

    public static void main(String[] args) {
        ResizableStringMap map = new ResizableStringMap(2);
        map.put("A", "ValA");
        map.printStatus();
        map.put("B", "ValB");
        map.printStatus();
        map.put("C", "ValC");
        map.printStatus();
    }
}