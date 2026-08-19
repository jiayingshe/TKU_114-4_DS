interface DeliveryMethod {
    double calculateFee(double orderAmount);
    String getDescription();
}

class HomeDelivery implements DeliveryMethod {
    @Override
    public double calculateFee(double orderAmount) {
        return orderAmount >= 2000 ? 0 : 100;
    }

    @Override
    public String getDescription() {
        return "宅配到府 (滿 2000 免運)";
    }
}

class StorePickup implements DeliveryMethod {
    @Override
    public double calculateFee(double orderAmount) {
        return orderAmount >= 1000 ? 0 : 60;
    }

    @Override
    public String getDescription() {
        return "超商取貨 (滿 1000 免運)";
    }
}

class SelfPickup implements DeliveryMethod {
    @Override
    public double calculateFee(double orderAmount) {
        return 0;
    }

    @Override
    public String getDescription() {
        return "門市自取 (免運費)";
    }
}

class OrderService {
    private DeliveryMethod deliveryMethod;

    public OrderService(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void printInvoice(String orderId, double subtotal) {
        if (deliveryMethod == null) {
            System.out.println("錯誤：未指定運送方式");
            return;
        }

        double shippingFee = deliveryMethod.calculateFee(subtotal);
        double grandTotal = subtotal + shippingFee;

        System.out.println("============ 發票明細 ============");
        System.out.println("訂單編號：" + orderId);
        System.out.println("運送方式：" + deliveryMethod.getDescription());
        System.out.printf("商品小計：$%.2f\n", subtotal);
        System.out.printf("運費費用：$%.2f\n", shippingFee);
        System.out.printf("應付總額：$%.2f\n", grandTotal);
        System.out.println("==================================");
    }
}

public class DeliveryStrategySystem {
    public static void main(String[] args) {
        OrderService service = new OrderService(new HomeDelivery());
        System.out.println("=== 測試 1：宅配金額未達免運 ===");
        service.printInvoice("ORD-001", 1500);

        System.out.println("\n=== 測試 2：切換至超商取貨達到免運 ===");
        service.setDeliveryMethod(new StorePickup());
        service.printInvoice("ORD-002", 1500);

        System.out.println("\n=== 測試 3：門市自取 ===");
        service.setDeliveryMethod(new SelfPickup());
        service.printInvoice("ORD-003", 500);
    }
}