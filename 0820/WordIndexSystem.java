import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class WordIndexSystem {
    public static void main(String[] args) {
        String[] sentences = {
            "Java is strong, Java is robust.",
            "Python is simple and Python is powerful.",
            "Object-oriented programming in Java and Python."
        };

        Map<String, Integer> wordCountMap = new LinkedHashMap<>();
        Set<String> uniqueWords = new LinkedHashSet<>();

        for (String sentence : sentences) {
            String cleaned = sentence.replaceAll("[,.]", "").toLowerCase();
            String[] words = cleaned.split("\\s+");

            for (String word : words) {
                if (word.isEmpty()) continue;
                uniqueWords.add(word);
                wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
            }
        }

        System.out.println("=== 所有不重複單字 (Set) ===");
        System.out.println(uniqueWords);

        System.out.println("\n=== 單字出現次數統計 (Map) ===");
        wordCountMap.forEach((word, count) -> System.out.printf("%-15s : %d\n", word, count));

        System.out.println("\n=== 出現至少兩次的單字 ===");
        wordCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() >= 2)
                .forEach(entry -> System.out.printf("- %-12s (%d 次)\n", entry.getKey(), entry.getValue()));
    }
}