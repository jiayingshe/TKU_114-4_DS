public class Q08_RecursiveAudit {

    public static int sumValid(int[] data, int index) {
        int idx = Math.max(0, index);
        if (data == null || idx >= data.length) return 0;
        int current = (data[idx] >= 0 && data[idx] <= 100) ? data[idx] : 0;
        return current + sumValid(data, idx + 1);
    }

    public static int countOccurrences(int[] data, int index, int target) {
        int idx = Math.max(0, index);
        if (data == null || idx >= data.length) return 0;
        int match = (data[idx] == target) ? 1 : 0;
        return match + countOccurrences(data, idx + 1, target);
    }

    public static boolean isPalindrome(String text, int left, int right) {
        if (text == null) return false;
        if (left >= right) return true;
        char lChar = Character.toLowerCase(text.charAt(left));
        char rChar = Character.toLowerCase(text.charAt(right));
        if (lChar != rChar) return false;
        return isPalindrome(text, left + 1, right - 1);
    }

    public static void main(String[] args) {
        int[] data = {10, -1, 20, 101, 20};
        System.out.println(Q08_RecursiveAudit.sumValid(data, 0));
        System.out.println(Q08_RecursiveAudit.countOccurrences(data, 0, 20));
        System.out.println(Q08_RecursiveAudit.isPalindrome("Level", 0, 4));
    }
}
