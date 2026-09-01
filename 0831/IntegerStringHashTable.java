import java.util.ArrayList;
import java.util.List;

public class IntegerStringHashTable {

    private static class Node {
        int key;
        String value;
        Node next;

        Node(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private final Node[] table;
    private int size;

    public IntegerStringHashTable(int capacity) {
        table = new Node[Math.max(1, capacity)];
        size = 0;
    }

    private int getBucketIndex(int key) {
        return Math.floorMod(key, table.length);
    }

    public void put(int key, String value) {
        int index = getBucketIndex(key);
        Node curr = table[index];

        while (curr != null) {
            if (curr.key == key) {
                curr.value = value; // 相同 key 更新 value
                return;
            }
            curr = curr.next;
        }

        Node newNode = new Node(key, value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
    }

    public String get(int key) {
        int index = getBucketIndex(key);
        Node curr = table[index];
        while (curr != null) {
            if (curr.key == key) return curr.value;
            curr = curr.next;
        }
        return null;
    }

    public boolean containsKey(int key) {
        return get(key) != null;
    }

    public boolean remove(int key) {
        int index = getBucketIndex(key);
        Node curr = table[index];
        Node prev = null;

        while (curr != null) {
            if (curr.key == key) {
                if (prev == null) {
                    table[index] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void bucketReport() {
        for (int i = 0; i < table.length; i++) {
            List<String> items = new ArrayList<>();
            Node curr = table[i];
            while (curr != null) {
                items.add(curr.key + "=" + curr.value);
                curr = curr.next;
            }
            System.out.println("Bucket " + i + ": " + items);
        }
    }

    public static void main(String[] args) {
        IntegerStringHashTable ht = new IntegerStringHashTable(5);
        ht.put(1, "A");
        ht.put(6, "B");
        ht.put(1, "A_Updated"); // 測試相同 Key 更新，Size 不增加
        
        System.out.println("Size: " + ht.size());
        System.out.println("Get 1: " + ht.get(1));
        System.out.println("Contains 6: " + ht.containsKey(6));
        
        ht.remove(6);
        System.out.println("Size after remove: " + ht.size());
        ht.bucketReport();
    }
}