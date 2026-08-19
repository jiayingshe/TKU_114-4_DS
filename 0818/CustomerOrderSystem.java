class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
}

class OrderItem {
    private String itemName;
    private double unitPrice;
    private int quantity;

    public OrderItem(String itemName, double unitPrice, int quantity) {
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return unitPrice * quantity;
    }

    public String getItemName() { return itemName; }
    public double getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
}

class Order {
    private String orderId;
    private Customer customer;
    private OrderItem[] items;
    private int itemCount;

    public Order(String orderId, Customer customer, int maxItems) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new OrderItem[maxItems];
        this.itemCount = 0;
    }

    public boolean addItem(OrderItem item) {
        if (itemCount >= items.length) {
            System.out.println("無法新增品項：訂單品項已達容量上限");
            return false;
        }
        items[itemCount++] = item;
        return true;
    }

    public double calculateTotal() {
        double total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += items[i].getTotalPrice();
        }
        return total;
    }

    public void printOrderSummary() {
        System.out.println("====================================");
        System.out.println("訂單編號：" + orderId);
        System.out.println("顧客名稱：" + customer.getName() + " (ID: " + customer.getCustomerId() + ")");
        System.out.println("------------------------------------");
        System.out.println("品項明細：");
        for (int i = 0; i < itemCount; i++) {
            OrderItem item = items[i];
            System.out.printf("- %s | 單價: $%.1f | 數量: %d | 小計: $%.1f\n",
                    item.getItemName(), item.getUnitPrice(), item.getQuantity(), item.getTotalPrice());
        }
        System.out.println("------------------------------------");
        System.out.printf("訂單總金額：$%.1f\n", calculateTotal());
        System.out.println("====================================");
    }
}

public class CustomerOrderSystem {
    public static void main(String[] args) {
        Customer customer = new Customer("C001", "張小明");
        Order order = new Order("ORD-20260819", customer, 5);

        order.addItem(new OrderItem("無線滑鼠", 650.0, 2));
        order.addItem(new OrderItem("機械鍵盤", 2200.0, 1));
        order.addItem(new OrderItem("螢幕架", 450.0, 1));

        order.printOrderSummary();
    }
}