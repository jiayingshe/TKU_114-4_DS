class DigitalWallet {
    private String walletId;
    private String owner;
    private double balance;
    private int transactionCount;

    public DigitalWallet(String walletId, String owner, double initialBalance) {
        this.walletId = walletId;
        this.owner = owner;
        this.balance = Math.max(0, initialBalance);
        this.transactionCount = 0;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("儲值失敗：金額必須大於 0");
            return false;
        }
        this.balance += amount;
        this.transactionCount++;
        System.out.println("儲值成功 $" + amount + "，目前餘額：$" + this.balance);
        return true;
    }

    public boolean pay(double amount) {
        if (amount <= 0) {
            System.out.println("付款失敗：金額必須大於 0");
            return false;
        }
        if (amount > this.balance) {
            System.out.println("付款失敗：餘額不足（目前餘額：$" + this.balance + "，欲支付：$" + amount + "）");
            return false;
        }
        this.balance -= amount;
        this.transactionCount++;
        System.out.println("付款成功 $" + amount + "，目前餘額：$" + this.balance);
        return true;
    }

    public boolean refund(double amount) {
        if (amount <= 0) {
            System.out.println("退款失敗：金額必須大於 0");
            return false;
        }
        this.balance += amount;
        this.transactionCount++;
        System.out.println("退款成功 $" + amount + "，目前餘額：$" + this.balance);
        return true;
    }

    public double getBalance() { return balance; }
    public int getTransactionCount() { return transactionCount; }
    public String getWalletId() { return walletId; }
    public String getOwner() { return owner; }
}

public class DigitalWalletSystem {
    public static void main(String[] args) {
        DigitalWallet wallet = new DigitalWallet("W1001", "Alex", 1000.0);

        System.out.println("=== 測試 1：正常儲值 ===");
        wallet.deposit(500.0);

        System.out.println("\n=== 測試 2：正常付款 ===");
        wallet.pay(300.0);

        System.out.println("\n=== 測試 3：餘額不足付款 ===");
        wallet.pay(2000.0);

        System.out.println("\n=== 測試 4：負數或不合法金額操作 ===");
        wallet.deposit(-100.0);
        wallet.pay(0);

        System.out.println("\n=== 測試 5：正常退款 ===");
        wallet.refund(150.0);

        System.out.println("\n=== 總結 ===");
        System.out.println("最終餘額：$" + wallet.getBalance());
        System.out.println("總交易次數：" + wallet.getTransactionCount());
    }
}