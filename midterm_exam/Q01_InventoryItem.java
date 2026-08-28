public class Q01_InventoryItem {
    private final String id;
    private final String name;
    private int stock;

    public Q01_InventoryItem(String id, String name, int stock) {
        if (id == null || id.strip().isEmpty() || name == null || name.strip().isEmpty()) {
            throw new IllegalArgumentException("id or name cannot be null or empty");
        }
        this.id = id.strip();
        this.name = name.strip();
        this.stock = Math.max(0, stock);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public boolean restock(int amount) {
        if (amount > 0) {
            this.stock += amount;
            return true;
        }
        return false;
    }

    public boolean sell(int amount) {
        if (amount > 0 && this.stock >= amount) {
            this.stock -= amount;
            return true;
        }
        return false;
    }

    public String status() {
        return id + " | " + name + " | " + stock;
    }

    public static void main(String[] args) {
        Q01_InventoryItem item = new Q01_InventoryItem(" P100 ", "Keyboard", 5);
        System.out.println(item.restock(3));
        System.out.println(item.sell(6));
        System.out.println(item.sell(3));
        System.out.println(item.status());
    }
}