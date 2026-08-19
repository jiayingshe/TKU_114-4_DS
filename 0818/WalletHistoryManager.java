class Transaction {
    private int sequence;
    private String type; // "DEPOSIT", "PAYMENT", "TRANSFER_IN", "TRANSFER_OUT"
    private double amount;
    private String note;

    public Transaction(int sequence, String type, double amount, String note) {
        this.sequence = sequence;
        this.type = type;
        this.amount = amount;
        this.note = note;
    }

    public int getSequence() { return sequence; }
    public String getType() { return type; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return String.format("序號: %03d | 類型: %-12s | 金額: $%8.1f | 備註: %s", sequence, type, amount, note);
    }
}

class AdvancedWallet {
    private String id;
    private String owner;
    private double balance;
    private Transaction[] history;
    private int txCount;
    private static int globalSequence = 1;

    public AdvancedWallet(String id, String owner, double initialBalance, int maxHistory) {
        this.id = id;
        this.owner = owner;
        this.balance = Math.max(0, initialBalance);
        this.history = new Transaction[maxHistory];
        this.txCount = 0;
    }

    public boolean deposit(double amount) {
        if (amount <= 0 || txCount >= history.length) {
            System.out.println("存款失敗：金額不合法或歷史紀錄已滿");
            return false;
        }
        this.balance += amount;
        history[txCount++] = new Transaction(globalSequence++, "DEPOSIT", amount, "帳戶儲值");
        return true;
    }

    public boolean pay(double amount) {
        if (amount <= 0 || amount > balance || txCount >= history.length) {
            System.out.println("付款失敗：金額不合法、餘額不足或歷史紀錄已滿");
            return false;
        }
        this.balance -= amount;
        history[txCount++] = new Transaction(globalSequence++, "PAYMENT", amount, "消費付款");
        return true;
    }

    public static boolean transferTo(AdvancedWallet source, AdvancedWallet target, double amount) {
        if (source == null || target == null || source == target) return false;
        if (amount <= 0 || source.balance < amount) return false;
        if (source.txCount >= source.history.length || target.txCount >= target.history.length) {
            System.out.println("轉帳失敗：其中一方交易紀錄空間不足");
            return false;
        }

        source.balance -= amount;
        target.balance += amount;

        source.history[source.txCount++] = new Transaction(globalSequence++, "TRANSFER_OUT", amount, "轉出至 " + target.owner);
        target.history[target.txCount++] = new Transaction(globalSequence++, "TRANSFER_IN", amount, "來自 " + source.owner + " 的轉入");
        return true;
    }

    public Transaction findTransaction(int sequence) {
        for (int i = 0; i < txCount; i++) {
            if (history[i].getSequence() == sequence) {
                return history[i];
            }
        }
        return null;
    }

    public double totalByType(String type) {
        double total = 0;
        for (int i = 0; i < txCount; i++) {
            if (history[i].getType().equalsIgnoreCase(type)) {
                total += history[i].getAmount();
            }
        }
        return total;
    }

    public void printStatement() {
        System.out.println("==================================================");
        System.out.println("對帳單記錄 - 所有者: " + owner + " (ID: " + id + ")");
        System.out.println("目前餘額: $" + balance);
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < txCount; i++) {
            System.out.println(history[i]);
        }
        System.out.println("==================================================");
    }
}

public class WalletHistoryManager {
    public static void main(String[] args) {
        AdvancedWallet w1 = new AdvancedWallet("W1", "UserA", 1000, 5);
        AdvancedWallet w2 = new AdvancedWallet("W2", "UserB", 500, 5);

        w1.deposit(500);
        w1.pay(200);
        AdvancedWallet.transferTo(w1, w2, 300);

        w1.printStatement();
        w2.printStatement();

        System.out.println("\n=== 搜尋交易 (Sequence: 2) ===");
        Transaction tx = w1.findTransaction(2);
        System.out.println(tx != null ? tx : "未找到交易");

        System.out.println("\n=== 計算特定類型總金額 (TRANSFER_OUT) ===");
        System.out.println("總轉出金額：$" + w1.totalByType("TRANSFER_OUT"));
    }
}