public class ProductInventoryBst {

    static class Product {
        String productId;
        String name;
        int stock;

        public Product(String productId, String name, int stock) {
            this.productId = productId;
            this.name = name;
            this.stock = stock;
        }

        @Override
        public String toString() {
            return String.format("Product[ID=%s, Name=%s, Stock=%d]", productId, name, stock);
        }
    }

    private static class Node {
        Product product;
        Node left, right;

        Node(Product product) {
            this.product = product;
        }
    }

    private Node root;

    public boolean addProduct(String id, String name, int stock) {
        if (findProduct(id) != null) return false;
        root = insertRec(root, new Product(id, name, stock));
        return true;
    }

    private Node insertRec(Node node, Product p) {
        if (node == null) return new Node(p);
        int cmp = p.productId.compareTo(node.product.productId);
        if (cmp < 0) node.left = insertRec(node.left, p);
        else if (cmp > 0) node.right = insertRec(node.right, p);
        return node;
    }

    public Product findProduct(String id) {
        Node curr = root;
        while (curr != null) {
            int cmp = id.compareTo(curr.product.productId);
            if (cmp == 0) return curr.product;
            curr = (cmp < 0) ? curr.left : curr.right;
        }
        return null;
    }

    public boolean restock(String id, int amount) {
        Product p = findProduct(id);
        if (p == null) return false;
        p.stock += amount;
        return true;
    }

    public boolean reduceStock(String id, int amount) {
        Product p = findProduct(id);
        if (p == null || p.stock < amount) return false;
        p.stock -= amount;
        return true;
    }

    public boolean removeProduct(String id) {
        if (findProduct(id) == null) return false;
        root = deleteRec(root, id);
        return true;
    }

    private Node deleteRec(Node node, String id) {
        if (node == null) return null;
        int cmp = id.compareTo(node.product.productId);
        if (cmp < 0) node.left = deleteRec(node.left, id);
        else if (cmp > 0) node.right = deleteRec(node.right, id);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node minNode = node.right;
            while (minNode.left != null) minNode = minNode.left;
            node.product = minNode.product;
            node.right = deleteRec(node.right, minNode.product.productId);
        }
        return node;
    }

    public void printInOrderReport() {
        System.out.println("=== 課後作業二:商品庫存中序報表 ===");
        inOrderRec(root);
    }

    private void inOrderRec(Node node) {
        if (node != null) {
            inOrderRec(node.left);
            System.out.println(node.product);
            inOrderRec(node.right);
        }
    }

    public static void main(String[] args) {
        ProductInventoryBst inventory = new ProductInventoryBst();
        inventory.addProduct("P003", "Mouse", 10);
        inventory.addProduct("P001", "Keyboard", 5);
        inventory.addProduct("P002", "Monitor", 2);

        inventory.restock("P001", 10);
        inventory.reduceStock("P003", 3);

        inventory.printInOrderReport();
    }
}