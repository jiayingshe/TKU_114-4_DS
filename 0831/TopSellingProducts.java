import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopSellingProducts {

    public static class Product implements Comparable<Product> {
        private final String id;
        private final double sales;

        public Product(String id, double sales) {
            this.id = id;
            this.sales = sales;
        }

        public String getId() { return id; }
        public double getSales() { return sales; }

        @Override
        public int compareTo(Product other) {
            if (Double.compare(this.sales, other.sales) != 0) {
                return Double.compare(this.sales, other.sales); // 銷售額低的優先 (維持小頂堆)
            }
            return other.id.compareTo(this.id); // 銷售額相同時，字典序大的優先在小頂堆頂端被剔除
        }

        @Override
        public String toString() {
            return id + " ($" + sales + ")";
        }
    }

    public static List<Product> getTopKProducts(List<Product> products, int k) {
        if (k <= 0 || products == null) return new ArrayList<>();

        // 1. 合併重複商品的銷售額
        Map<String, Double> mergedSales = new HashMap<>();
        for (Product p : products) {
            if (p != null) {
                mergedSales.merge(p.getId(), p.getSales(), Double::sum);
            }
        }

        // 2. 使用 Min Heap 保留 Top-K
        PriorityQueue<Product> minHeap = new PriorityQueue<>();

        for (Map.Entry<String, Double> entry : mergedSales.entrySet()) {
            Product current = new Product(entry.getKey(), entry.getValue());
            if (minHeap.size() < k) {
                minHeap.add(current);
            } else if (current.compareTo(minHeap.peek()) > 0) {
                minHeap.poll();
                minHeap.add(current);
            }
        }

        // 3. 轉為結果列表並降序排序
        List<Product> result = new ArrayList<>(minHeap);
        result.sort((a, b) -> {
            if (Double.compare(b.getSales(), a.getSales()) != 0) {
                return Double.compare(b.getSales(), a.getSales());
            }
            return a.getId().compareTo(b.getId());
        });
        return result;
    }

    public static void main(String[] args) {
        List<Product> list = List.of(
            new Product("P10", 100), new Product("P20", 300),
            new Product("P10", 250), new Product("P30", 350),
            new Product("P40", 350)
        );
        
        System.out.println("Top 3 熱門商品: " + getTopKProducts(list, 3));
    }
}