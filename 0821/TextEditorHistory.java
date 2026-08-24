import java.util.ArrayDeque;
import java.util.Deque;

public class TextEditorHistory {
    private final Deque<String> undoStack = new ArrayDeque<>();
    private final Deque<String> redoStack = new ArrayDeque<>();
    private String currentText = "";

    public void type(String text) {
        undoStack.push(currentText);
        currentText += text;
        redoStack.clear();
        printStatus("Type \"" + text + "\"");
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("[Undo 失敗] 無法復原：Undo Stack 為空");
            return;
        }
        redoStack.push(currentText);
        currentText = undoStack.pop();
        printStatus("Undo");
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("[Redo 失敗] 無法重做：Redo Stack 為空");
            return;
        }
        undoStack.push(currentText);
        currentText = redoStack.pop();
        printStatus("Redo");
    }

    private void printStatus(String action) {
        System.out.printf("[%s] 當前內容: \"%s\" | UndoStack: %s | RedoStack: %s%n",
                action, currentText, undoStack, redoStack);
    }

    public static void main(String[] args) {
        TextEditorHistory editor = new TextEditorHistory();

        editor.type("Hello");
        editor.type(" World");
        editor.type("!");

        editor.undo();
        editor.undo();

        editor.redo();

        editor.type(" Java");

        editor.redo();
        editor.undo();
        editor.undo();
        editor.undo();
    }
}