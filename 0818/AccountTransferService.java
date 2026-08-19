class Account {
    private String accountNumber;
    private String owner;
    private double balance;

    public Account(String accountNumber, String owner, double initialBalance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = Math.max(0, initialBalance);
    }

    public void withdraw(double amount) { this.balance -= amount; }
    public void deposit(double amount) { this.balance += amount; }

    public double getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public String getOwner() { return owner; }
}

class TransferService {
    public static boolean transfer(Account source, Account target, double amount) {
        if (source == null || target == null) {
            System.out.println("轉帳失敗：來源或目標帳戶不可為 null");
            return false;
        }

        if (source == target) {
            System.out.println("轉帳失敗：來源與目標不能為同一帳戶");
            return false;
        }

        if (amount <= 0) {
            System.out.println("轉帳失敗：轉帳金額必須大於 0");
            return false;
        }

        if (source.getBalance() < amount) {
            System.out.println("轉帳失敗：來源帳戶餘額不足（目前餘額：$" + source.getBalance() + "，欲轉金額：$" + amount + "）");
            return false;
        }

        // 交易執行（雙重交易完整性）
        source.withdraw(amount);
        target.deposit(amount);
        System.out.println("轉帳成功：已從 " + source.getOwner() + " 轉出 $" + amount + " 至 " + target.getOwner());
        return true;
    }
}

public class AccountTransferService {
    public static void main(String[] args) {
        Account accA = new Account("ACT-001", "Alice", 1000.0);
        Account accB = new Account("ACT-002", "Bob", 500.0);

        System.out.println("=== 測試 1：成功轉帳 ===");
        TransferService.transfer(accA, accB, 300.0);
        System.out.println("Alice 餘額：$" + accA.getBalance() + " | Bob 餘額：$" + accB.getBalance());

        System.out.println("\n=== 測試 2：餘額不足轉帳 ===");
        TransferService.transfer(accA, accB, 2000.0);
        System.out.println("Alice 餘額：$" + accA.getBalance() + " | Bob 餘額：$" + accB.getBalance());

        System.out.println("\n=== 測試 3：同帳戶轉帳 ===");
        TransferService.transfer(accA, accA, 100.0);

        System.out.println("\n=== 測試 4：Target 為 null ===");
        TransferService.transfer(accA, null, 100.0);
    }
}