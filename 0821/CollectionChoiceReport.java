import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CollectionChoiceReport {

    public static void main(String[] args) {
        System.out.println("=== 課後作業四：集合選擇報告與實作 ===");
        System.out.println("\n[需求 1: 搜尋紀錄 (允許重複, 保留順序)]");
        System.out.println("選擇：Interface: List | Implementation: ArrayList");
        List<String> searchHistory = new ArrayList<>();
        searchHistory.add("Java Queue");
        searchHistory.add("Data Structures");
        searchHistory.add("Java Queue");
        System.out.println("操作結果: " + searchHistory);
        System.out.println("\n[需求 2: 不重複會員編號]");
        System.out.println("選擇：Interface: Set | Implementation: HashSet");
        Set<String> memberIds = new HashSet<>();
        memberIds.add("M001");
        memberIds.add("M002");
        memberIds.add("M001");
        System.out.println("操作結果: " + memberIds);
        System.out.println("\n[需求 3: 學號查詢成績]");
        System.out.println("選擇：Interface: Map | Implementation: HashMap");
        Map<String, Integer> studentScores = new HashMap<>();
        studentScores.put("S111001", 85);
        studentScores.put("S111002", 92);
        System.out.println("查詢 S111002 成績: " + studentScores.get("S111002"));
        System.out.println("\n[需求 4: 到達順序處理列印]");
        System.out.println("選擇：Interface: Queue | Implementation: ArrayDeque");
        Queue<String> printJobs = new ArrayDeque<>();
        printJobs.offer("Doc1.pdf");
        printJobs.offer("Report.docx");
        System.out.println("列印處理: " + printJobs.poll());
        System.out.println("剩餘列印工作: " + printJobs);
        System.out.println("\n[需求 5: 復原最近操作 (Undo)]");
        System.out.println("選擇：Interface: Deque | Implementation: ArrayDeque");
        Deque<String> actionHistory = new ArrayDeque<>();
        actionHistory.push("Draw Line");
        actionHistory.push("Fill Color");
        System.out.println("執行 Undo 復原: " + actionHistory.pop());
        System.out.println("當前 Stack 頂端: " + actionHistory.peek());
    }
}