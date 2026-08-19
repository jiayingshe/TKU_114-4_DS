abstract class Employee {
    private String id;
    private String name;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract double calculatePay();

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return String.format("ID: %s | 姓名: %-4s | 應付薪資: $%.2f", id, name, calculatePay());
    }
}

class SalariedEmployee extends Employee {
    private double monthlySalary;

    public SalariedEmployee(String id, String name, double monthlySalary) {
        super(id, name);
        this.monthlySalary = Math.max(0, monthlySalary);
    }

    @Override
    public double calculatePay() {
        return monthlySalary;
    }
}

class HourlyEmployee extends Employee {
    private double hourlyRate;
    private double hoursWorked;

    public HourlyEmployee(String id, String name, double hourlyRate, double hoursWorked) {
        super(id, name);
        this.hourlyRate = Math.max(0, hourlyRate);
        this.hoursWorked = Math.max(0, hoursWorked);
    }

    @Override
    public double calculatePay() {
        if (hoursWorked <= 40) {
            return hourlyRate * hoursWorked;
        } else {
            return (40 * hourlyRate) + ((hoursWorked - 40) * hourlyRate * 1.5);
        }
    }
}

class CommissionEmployee extends Employee {
    private double baseSalary;
    private double salesAmount;
    private double commissionRate;

    public CommissionEmployee(String id, String name, double baseSalary, double salesAmount, double commissionRate) {
        super(id, name);
        this.baseSalary = Math.max(0, baseSalary);
        this.salesAmount = Math.max(0, salesAmount);
        this.commissionRate = Math.max(0, commissionRate);
    }

    @Override
    public double calculatePay() {
        return baseSalary + (salesAmount * commissionRate);
    }
}

public class PayrollPolymorphismSystem {
    public static void main(String[] args) {
        Employee[] employees = new Employee[]{
            new SalariedEmployee("E001", "張經理", 60000),
            new HourlyEmployee("E002", "李專員", 200, 45),
            new CommissionEmployee("E003", "王業務", 25000, 300000, 0.10)
        };

        double totalPayroll = 0;
        Employee highestPaid = employees[0];

        System.out.println("=== 員工薪資結算清單 ===");
        for (Employee emp : employees) {
            System.out.println(emp);
            double pay = emp.calculatePay();
            totalPayroll += pay;
            if (pay > highestPaid.calculatePay()) {
                highestPaid = emp;
            }
        }

        System.out.println("------------------------------------");
        System.out.printf("總薪資支出：$%.2f\n", totalPayroll);
        System.out.printf("最高薪資員工：%s (%s) - $%.2f\n", 
                highestPaid.getName(), highestPaid.getId(), highestPaid.calculatePay());
    }
}