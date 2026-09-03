import java.util.ArrayList;
import java.util.List;

public class Q04_ChainedHashTable {
    private static class Entry {
        int key;
        String value;
        Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private final List<List<Entry>> buckets;
    private final int bucketCount;
    private int size = 0;

    public Q04_ChainedHashTable(int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException();
        }
        this.bucketCount = bucketCount;
        this.buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }
    }

    private int getIndex(int key) {
        return Math.abs(key % bucketCount);
    }

    public void put(int key, String value) {
        List<Entry> chain = buckets.get(getIndex(key));
        for (Entry e : chain) {
            if (e.key == key) {
                e.value = value;
                return;
            }
        }
        chain.add(new Entry(key, value));
        size++;
    }

    public String get(int key) {
        List<Entry> chain = buckets.get(getIndex(key));
        for (Entry e : chain) {
            if (e.key == key) {
                return e.value;
            }
        }
        return null;
    }

    public boolean remove(int key) {
        List<Entry> chain = buckets.get(getIndex(key));
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key == key) {
                chain.remove(i);
                size--;
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public int longestChain() {
        int max = 0;
        for (List<Entry> chain : buckets) {
            if (chain.size() > max) {
                max = chain.size();
            }
        }
        return max;
    }
}