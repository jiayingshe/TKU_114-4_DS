import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Q02_ServiceOrder {

    public static class LineItem {
        private final String name;
        private final int unitPrice;
        private final int quantity;

        public LineItem(String name, int unitPrice, int quantity) {
            this.name = name;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public int getUnitPrice() {
            return unitPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        public int subtotal() {
            return unitPrice * quantity;
        }
    }

    private final String orderId;
    private final List<LineItem> items = new ArrayList<>();

    public Q02_ServiceOrder(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("orderId cannot be null or blank");
        }
        this.orderId = orderId;
    }

    public boolean addItem(String name, int unitPrice, int quantity) {
        if (name == null || name.isBlank() || unitPrice < 0 || quantity <= 0) {
            return false;
        }
        items.add(new LineItem(name, unitPrice, quantity));
        return true;
    }

    public int itemCount() {
        return items.size();
    }

    public int totalAmount() {
        int sum = 0;
        for (LineItem item : items) {
            sum += item.subtotal();
        }
        return sum;
    }

    public String largestItemName() {
        if (items.isEmpty()) {
            return "";
        }
        LineItem maxItem = items.get(0);
        for (int i = 1; i < items.size(); i++) {
            if (items.get(i).subtotal() > maxItem.subtotal()) {
                maxItem = items.get(i);
            }
        }
        return maxItem.getName();
    }

    public List<String> itemSummaries() {
        List<String> summaries = new ArrayList<>();
        for (LineItem item : items) {
            summaries.add(item.getName() + ": " + item.subtotal());
        }
        return Collections.unmodifiableList(summaries);
    }

    public static void main(String[] args) {
        Q02_ServiceOrder order = new Q02_ServiceOrder("R-01");
        order.addItem("Inspection", 300, 1);
        order.addItem("Cable", 80, 4);
        order.addItem("Cleaning", 200, 1);
        System.out.println(order.itemCount());
        System.out.println(order.totalAmount());
        System.out.println(order.largestItemName());
        System.out.println(order.itemSummaries());
    }
}