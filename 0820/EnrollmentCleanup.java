import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class EnrollmentCleanup {
    public static void main(String[] args) {
        List<String> rawList = new ArrayList<>(Arrays.asList(
            "Alice", "", "Bob", null, "Alice", "  ", "Charlie", "Bob", "David", null
        ));

        System.out.println("清理前資料: " + rawList);

        // 使用 Iterator 安全移除空白與 null
        Iterator<String> iterator = rawList.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (item == null || item.trim().isEmpty()) {
                iterator.remove();
            }
        }

        // 使用 Set 找出重複姓名與清理後的獨立名單
        Set<String> seen = new HashSet<>();
        Set<String> duplicates = new LinkedHashSet<>();

        for (String name : rawList) {
            if (!seen.add(name)) {
                duplicates.add(name);
            }
        }

        System.out.println("清理不合法資料後: " + rawList);
        System.out.println("重複姓名報告: " + duplicates);
        System.out.println("最終不重複名單: " + seen);
    }
}