import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Q07_RequestPipeline {

    public static boolean isBalanced(String text) {
        if (text == null) return false;
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : text.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if ((c == ')' && top != '(') ||
                    (c == ']' && top != '[') ||
                    (c == '}' && top != '{')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static List<String> process(String[] commands) {
        if (commands == null) return new ArrayList<>();
        Deque<String> normalQueue = new ArrayDeque<>();
        Deque<String> urgentQueue = new ArrayDeque<>();
        List<String> result = new ArrayList<>();

        for (String cmd : commands) {
            if (cmd == null || cmd.isBlank()) continue;
            String[] parts = cmd.trim().split("\\s+");
            if (parts[0].equals("NORMAL") && parts.length == 2) {
                normalQueue.addLast(parts[1]);
            } else if (parts[0].equals("URGENT") && parts.length == 2) {
                urgentQueue.addLast(parts[1]);
            } else if (parts[0].equals("PROCESS") && parts.length == 1) {
                if (!urgentQueue.isEmpty()) {
                    result.add(urgentQueue.removeFirst());
                } else if (!normalQueue.isEmpty()) {
                    result.add(normalQueue.removeFirst());
                } else {
                    result.add("EMPTY");
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String[] commands = {
            "NORMAL N1", "URGENT U1", "NORMAL N2", "PROCESS", "PROCESS", "PROCESS"
        };
        System.out.println(Q07_RequestPipeline.isBalanced("a{b[c] (d)}"));
        System.out.println(Q07_RequestPipeline.isBalanced("( )]"));
        System.out.println(Q07_RequestPipeline.process(commands));
    }
}