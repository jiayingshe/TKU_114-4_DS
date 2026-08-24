public class RecursiveTextTools {

    public static String reverse(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        return reverse(str.substring(1)) + str.charAt(0);
    }

    public static boolean isPalindrome(String str) {
        if (str == null) return false;
        String cleaned = str.replaceAll("\\s+", "").toLowerCase();
        return isPalindromeHelper(cleaned, 0, cleaned.length() - 1);
    }

    private static boolean isPalindromeHelper(String s, int left, int right) {
        if (left >= right) return true;
        if (s.charAt(left) != s.charAt(right)) return false;
        return isPalindromeHelper(s, left + 1, right - 1);
    }

    public static int countCharacter(String str, char ch) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        int count = (str.charAt(0) == ch) ? 1 : 0;
        return count + countCharacter(str.substring(1), ch);
    }

    public static void main(String[] args) {
        System.out.println("=== 1. 反轉字串 ===");
        System.out.println("reverse(\"Java\") -> " + reverse("Java"));

        System.out.println("\n=== 2. 回文測試 ===");
        String[] testCases = {"", "a", "Level", "A nut for a jar of tuna", "Hello"};
        for (String tc : testCases) {
            System.out.printf("isPalindrome(\"%s\") -> %b\n", tc, isPalindrome(tc));
        }

        System.out.println("\n=== 3. 字元計數 ===");
        System.out.println("countCharacter(\"banana\", 'a') -> " + countCharacter("banana", 'a'));
    }
}