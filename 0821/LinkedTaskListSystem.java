class Task {
    private final String id;
    private final String title;

    public Task(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }

    @Override
    public String toString() {
        return "Task[" + id + ": " + title + "]";
    }
}

class TaskNode {
    Task task;
    TaskNode next;

    public TaskNode(Task task) {
        this.task = task;
        this.next = null;
    }
}

class TaskLinkedList {
    private TaskNode head;
    private int size = 0;

    public int size() { return size; }

    public boolean addFirst(Task task) {
        if (findById(task.getId()) != null) return false;
        TaskNode newNode = new TaskNode(task);
        newNode.next = head;
        head = newNode;
        size++;
        return true;
    }

    public boolean addLast(Task task) {
        if (findById(task.getId()) != null) return false;
        TaskNode newNode = new TaskNode(task);
        if (head == null) {
            head = newNode;
        } else {
            TaskNode curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = newNode;
        }
        size++;
        return true;
    }

    public Task findById(String id) {
        TaskNode curr = head;
        while (curr != null) {
            if (curr.task.getId().equals(id)) return curr.task;
            curr = curr.next;
        }
        return null;
    }

    public boolean removeById(String id) {
        if (head == null) return false;

        // 刪除 Head
        if (head.task.getId().equals(id)) {
            head = head.next;
            size--;
            return true;
        }

        // 刪除 Middle 或 Tail
        TaskNode curr = head;
        while (curr.next != null && !curr.next.task.getId().equals(id)) {
            curr = curr.next;
        }

        if (curr.next != null) {
            curr.next = curr.next.next;
            size--;
            return true;
        }
        return false;
    }

    public boolean insertAfter(String existingId, Task task) {
        if (findById(task.getId()) != null) return false; // 重複 id 不得加入

        TaskNode curr = head;
        while (curr != null) {
            if (curr.task.getId().equals(existingId)) {
                TaskNode newNode = new TaskNode(task);
                newNode.next = curr.next;
                curr.next = newNode;
                size++;
                return true;
            }
            curr = curr.next;
        }
        return false; // 找不到 existingId
    }

    public void printAll() {
        System.out.print("List (Size " + size + "): ");
        TaskNode curr = head;
        while (curr != null) {
            System.out.print(curr.task + " -> ");
            curr = curr.next;
        }
        System.out.println("null");
    }
}

public class LinkedTaskListSystem {
    public static void main(String[] args) {
        TaskLinkedList list = new TaskLinkedList();

        System.out.println("--- 測試空 List 刪除/查詢 ---");
        System.out.println("刪除不存在: " + list.removeById("T1"));
        list.printAll();

        System.out.println("\n--- 測試新增與重複 ID 阻擋 ---");
        list.addLast(new Task("T1", "Fix Bug"));
        list.addLast(new Task("T2", "Write Unit Tests"));
        list.addFirst(new Task("T0", "Code Review"));
        System.out.println("加入重複 ID (T1): " + list.addLast(new Task("T1", "Duplicate")));
        list.printAll();

        System.out.println("\n--- 測試 insertAfter ---");
        list.insertAfter("T1", new Task("T1.5", "Deploy to Staging"));
        list.printAll();

        System.out.println("\n--- 測試刪除 Head (T0) ---");
        list.removeById("T0");
        list.printAll();

        System.out.println("\n--- 測試刪除 Middle (T1.5) ---");
        list.removeById("T1.5");
        list.printAll();

        System.out.println("\n--- 測試刪除 Tail (T2) ---");
        list.removeById("T2");
        list.printAll();

        System.out.println("\n--- 測試刪除找不到的 ID ---");
        System.out.println("刪除 T999: " + list.removeById("T999"));
    }
}