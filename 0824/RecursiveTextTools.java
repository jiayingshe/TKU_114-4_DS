public class RecursiveTextTools {

    public static String reverse(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        return reverse(str.substring(1)) + str.charAt(0);
    }

    public static boolean isPalindrome(String str) {
        if (str == null) return false;
        
        String cleaned = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return isPalindromeHelper(cleaned, 0, cleaned.length() - 1);
    }

    private static boolean isPalindromeHelper(String str, int left, int right) {
        if (left >= right) return true;
        if (str.charAt(left) != str.charAt(right)) return false;
        return isPalindromeHelper(str, left + 1, right - 1);
    }

    public static int countCharacter(String str, char target) {
        if (str == null || str.isEmpty()) return 0;
        int count = (str.charAt(0) == target) ? 1 : 0;
        return count + countCharacter(str.substring(1), target);
    }

    public static void main(String[] args) {
        System.out.println("=== 課後作業一：遞迴字串工具 ===");

        System.out.println("Reverse 'hello': " + reverse("hello"));

        String[] testPalindromes = {"", "a", "Level", "A man a plan a canal Panama", "hello"};
        for (String s : testPalindromes) {
            System.out.printf("Is Palindrome (\"%s\"): %b%n", s, isPalindrome(s));
        }

        System.out.println("Count 'l' in 'hello world': " + countCharacter("hello world", 'l'));
    }
}