import java.util.ArrayList;
import java.util.List;

public class OrderManagementBst {

    public enum Status {
        PENDING, PROCESSING, COMPLETED, CANCELED
    }

    public static class Order {
        private final int orderId;
        private final String customer;
        private final double amount;
        private Status status;

        public Order(int orderId, String customer, double amount) {
            if (orderId <= 0 || customer == null || customer.isBlank() || amount < 0) {
                throw new IllegalArgumentException("Invalid Order Parameters");
            }
            this.orderId = orderId;
            this.customer = customer.trim();
            this.amount = amount;
            this.status = Status.PENDING;
        }

        public int getOrderId() { return orderId; }
        public String getCustomer() { return customer; }
        public double getAmount() { return amount; }
        public Status getStatus() { return status; }
        public void setStatus(Status status) {
            if (status != null) this.status = status;
        }

        @Override
        public String toString() {
            return String.format("OrderID: %d | Customer: %s | Amount: $%.2f | Status: %s",
                    orderId, customer, amount, status);
        }
    }

    private static class Node {
        Order order;
        Node left, right;
        Node(Order order) { this.order = order; }
    }

    private Node root;

    public boolean add(Order order) {
        if (order == null || find(order.getOrderId()) != null) return false;
        root = insertRec(root, order);
        return true;
    }

    private Node insertRec(Node node, Order order) {
        if (node == null) return new Node(order);
        if (order.getOrderId() < node.order.getOrderId()) node.left = insertRec(node.left, order);
        else if (order.getOrderId() > node.order.getOrderId()) node.right = insertRec(node.right, order);
        return node;
    }

    public Order find(int orderId) {
        Node curr = root;
        while (curr != null) {
            if (curr.order.getOrderId() == orderId) return curr.order;
            curr = (orderId < curr.order.getOrderId()) ? curr.left : curr.right;
        }
        return null;
    }

    public boolean updateStatus(int orderId, Status status) {
        Order order = find(orderId);
        if (order == null) return false;
        order.setStatus(status);
        return true;
    }

    public boolean cancel(int orderId) {
        return updateStatus(orderId, Status.CANCELED);
    }

    public boolean remove(int orderId) {
        Order order = find(orderId);
        if (order == null || order.getStatus() != Status.CANCELED) {
            return false;
        }
        root = deleteRec(root, orderId);
        return true;
    }

    private Node deleteRec(Node node, int orderId) {
        if (node == null) return null;
        if (orderId < node.order.getOrderId()) {
            node.left = deleteRec(node.left, orderId);
        } else if (orderId > node.order.getOrderId()) {
            node.right = deleteRec(node.right, orderId);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node minNode = getMin(node.right);
            node.order = minNode.order;
            node.right = deleteRec(node.right, minNode.order.getOrderId());
        }
        return node;
    }

    private Node getMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public List<Order> idRangeReport(int lowId, int highId) {
        List<Order> result = new ArrayList<>();
        if (lowId > highId) return result;
        rangeRec(root, lowId, highId, result);
        return result;
    }

    private void rangeRec(Node node, int low, int high, List<Order> result) {
        if (node == null) return;
        if (node.order.getOrderId() > low) rangeRec(node.left, low, high, result);
        if (node.order.getOrderId() >= low && node.order.getOrderId() <= high) result.add(node.order);
        if (node.order.getOrderId() < high) rangeRec(node.right, low, high, result);
    }

    public double getTotalAmount() {
        return totalAmountRec(root);
    }

    private double totalAmountRec(Node node) {
        if (node == null) return 0.0;
        return node.order.getAmount() + totalAmountRec(node.left) + totalAmountRec(node.right);
    }

    public static void main(String[] args) {
        OrderManagementBst system = new OrderManagementBst();
        system.add(new Order(101, "Alice", 250.0));
        system.add(new Order(102, "Bob", 150.5));
        system.add(new Order(103, "Charlie", 500.0));

        System.out.println("Total Amount: $" + system.getTotalAmount());
        System.out.println("Remove PENDING Order 101: " + system.remove(101));
        system.cancel(101);
        System.out.println("Remove CANCELED Order 101: " + system.remove(101));
        System.out.println("Range Report (100-105): " + system.idRangeReport(100, 105));
    }
}