interface PricingPolicy {
    double calculatePrice(double originalPrice);
    String getPolicyName();
}

class RegularPricing implements PricingPolicy {
    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice;
    }

    @Override
    public String getPolicyName() { return "原價計費"; }
}

class VipDiscountPricing implements PricingPolicy {
    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.85;
    }

    @Override
    public String getPolicyName() { return "VIP 85 折優惠"; }
}

class ThresholdDiscountPricing implements PricingPolicy {
    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice >= 2000 ? originalPrice - 300 : originalPrice;
    }

    @Override
    public String getPolicyName() { return "滿 2000 折 300 優惠"; }
}

interface NotificationChannel {
    boolean sendNotification(String message);
    String getChannelName();
}

class EmailChannel implements NotificationChannel {
    @Override
    public boolean sendNotification(String message) {
        System.out.println("[Email 發送] " + message);
        return true;
    }

    @Override
    public String getChannelName() { return "電子郵件"; }
}

class SmsChannel implements NotificationChannel {
    @Override
    public boolean sendNotification(String message) {
        System.out.println("[簡訊 發送] " + message);
        return true;
    }

    @Override
    public String getChannelName() { return "簡訊"; }
}

class ConsoleChannel implements NotificationChannel {
    @Override
    public boolean sendNotification(String message) {
        System.out.println("[控制台 輸出] " + message);
        return true;
    }

    @Override
    public String getChannelName() { return "控制台"; }
}

class CheckoutResult {
    private String orderId;
    private double originalPrice;
    private double finalPrice;
    private boolean notificationStatus;

    public CheckoutResult(String orderId, double originalPrice, double finalPrice, boolean notificationStatus) {
        this.orderId = orderId;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.notificationStatus = notificationStatus;
    }

    @Override
    public String toString() {
        return String.format("訂單: %s | 原價: $%.1f | 結帳價: $%.1f | 通知狀態: %s",
                orderId, originalPrice, finalPrice, notificationStatus ? "成功" : "失敗");
    }
}

class CheckoutService {
    public CheckoutResult checkout(String orderId, double originalPrice, PricingPolicy pricingPolicy, NotificationChannel channel) {
        double finalPrice = pricingPolicy.calculatePrice(originalPrice);
        String msg = String.format("訂單 %s 結帳完成，最終金額為 $%.1f (採用: %s)", orderId, finalPrice, pricingPolicy.getPolicyName());
        boolean status = channel.sendNotification(msg);

        return new CheckoutResult(orderId, originalPrice, finalPrice, status);
    }
}

public class FlexibleCheckoutSystem {
    public static void main(String[] args) {
        CheckoutService service = new CheckoutService();

        PricingPolicy regular = new RegularPricing();
        PricingPolicy vip = new VipDiscountPricing();
        PricingPolicy threshold = new ThresholdDiscountPricing();

        NotificationChannel email = new EmailChannel();
        NotificationChannel sms = new SmsChannel();
        NotificationChannel console = new ConsoleChannel();

        System.out.println("=== 測試 6 種定價與通路組合 ===");
        
        CheckoutResult r1 = service.checkout("ORD-01", 1000, regular, email);
        System.out.println("結果 1: " + r1 + "\n");

        CheckoutResult r2 = service.checkout("ORD-02", 1000, vip, sms);
        System.out.println("結果 2: " + r2 + "\n");

        CheckoutResult r3 = service.checkout("ORD-03", 2500, threshold, console);
        System.out.println("結果 3: " + r3 + "\n");

        CheckoutResult r4 = service.checkout("ORD-04", 1500, threshold, email);
        System.out.println("結果 4: " + r4 + "\n");

        CheckoutResult r5 = service.checkout("ORD-05", 3000, vip, console);
        System.out.println("結果 5: " + r5 + "\n");

        CheckoutResult r6 = service.checkout("ORD-06", 500, regular, sms);
        System.out.println("結果 6: " + r6 + "\n");
    }
}