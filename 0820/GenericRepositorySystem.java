import java.util.ArrayList;
import java.util.List;

class Repository<T> {
    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        if (item != null) {
            items.add(item);
        }
    }

    public T get(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.get(index);
    }

    public boolean remove(T item) {
        return items.remove(item);
    }

    public int size() {
        return items.size();
    }

    public void printAll() {
        System.out.println("--- 儲存庫內容 (Size: " + size() + ") ---");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("[%d] %s\n", i, items.get(i));
        }
    }
}

class Product {
    private final String id;
    private final String name;

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{id='" + id + "', name='" + name + "'}";
    }
}

public class GenericRepositorySystem {
    public static void main(String[] args) {
        System.out.println("=== 1. Repository<String> 測試 ===");
        Repository<String> strRepo = new Repository<>();
        strRepo.add("Java");
        strRepo.add("Python");
        strRepo.add("C++");
        strRepo.printAll();

        System.out.println("取得 index 1: " + strRepo.get(1));
        strRepo.remove("Python");
        strRepo.printAll();

        System.out.println("\n=== 2. Repository<Product> 測試 ===");
        Repository<Product> productRepo = new Repository<>();
        Product p1 = new Product("P01", "Laptop");
        Product p2 = new Product("P02", "Phone");
        productRepo.add(p1);
        productRepo.add(p2);
        productRepo.printAll();
    }
}