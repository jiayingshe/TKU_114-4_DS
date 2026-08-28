import java.util.ArrayList;
import java.util.List;

public class LibraryBookBst {

    public static class Book {
        private final String isbn;
        private final String title;
        private final String author;
        private boolean available;

        public Book(String isbn, String title, String author) {
            if (isbn == null || isbn.isBlank() || title == null || title.isBlank()) {
                throw new IllegalArgumentException("ISBN and Title cannot be blank");
            }
            this.isbn = isbn.trim();
            this.title = title.trim();
            this.author = author != null ? author.trim() : "Unknown";
            this.available = true;
        }

        public String getIsbn() { return isbn; }
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public boolean isAvailable() { return available; }

        @Override
        public String toString() {
            return String.format("ISBN: %s | Title: %s | Author: %s | Status: %s",
                    isbn, title, author, available ? "Available" : "Borrowed");
        }
    }

    private static class Node {
        Book book;
        Node left, right;
        Node(Book book) { this.book = book; }
    }

    private Node root;

    public boolean add(Book book) {
        if (book == null || find(book.getIsbn()) != null) return false;
        root = insertRec(root, book);
        return true;
    }

    private Node insertRec(Node node, Book book) {
        if (node == null) return new Node(book);
        int cmp = book.getIsbn().compareTo(node.book.getIsbn());
        if (cmp < 0) node.left = insertRec(node.left, book);
        else if (cmp > 0) node.right = insertRec(node.right, book);
        return node;
    }

    public Book find(String isbn) {
        if (isbn == null) return null;
        Node curr = root;
        while (curr != null) {
            int cmp = isbn.compareTo(curr.book.getIsbn());
            if (cmp == 0) return curr.book;
            curr = (cmp < 0) ? curr.left : curr.right;
        }
        return null;
    }

    public boolean borrowBook(String isbn) {
        Book book = find(isbn);
        if (book == null || !book.isAvailable()) return false;
        book.available = false;
        return true;
    }

    public boolean returnBook(String isbn) {
        Book book = find(isbn);
        if (book == null || book.isAvailable()) return false;
        book.available = true;
        return true;
    }

    public boolean remove(String isbn) {
        Book book = find(isbn);
        if (book == null || !book.isAvailable()) {
            return false;
        }
        root = deleteRec(root, isbn);
        return true;
    }

    private Node deleteRec(Node node, String isbn) {
        if (node == null) return null;
        int cmp = isbn.compareTo(node.book.getIsbn());
        if (cmp < 0) {
            node.left = deleteRec(node.left, isbn);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, isbn);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node minNode = getMin(node.right);
            node.book = minNode.book;
            node.right = deleteRec(node.right, minNode.book.getIsbn());
        }
        return node;
    }

    private Node getMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public List<Book> rangeQuery(String startIsbn, String endIsbn) {
        List<Book> result = new ArrayList<>();
        if (startIsbn == null || endIsbn == null || startIsbn.compareTo(endIsbn) > 0) {
            return result;
        }
        rangeRec(root, startIsbn, endIsbn, result);
        return result;
    }

    private void rangeRec(Node node, String start, String end, List<Book> result) {
        if (node == null) return;
        int cmpStart = node.book.getIsbn().compareTo(start);
        int cmpEnd = node.book.getIsbn().compareTo(end);

        if (cmpStart > 0) rangeRec(node.left, start, end, result);
        if (cmpStart >= 0 && cmpEnd <= 0) result.add(node.book);
        if (cmpEnd < 0) rangeRec(node.right, start, end, result);
    }

    public List<Book> getOrderedReport() {
        List<Book> result = new ArrayList<>();
        inorderRec(root, result);
        return result;
    }

    private void inorderRec(Node node, List<Book> result) {
        if (node == null) return;
        inorderRec(node.left, result);
        result.add(node.book);
        inorderRec(node.right, result);
    }

    public static void main(String[] args) {
        LibraryBookBst library = new LibraryBookBst();
        library.add(new Book("978-0134685991", "Effective Java", "Joshua Bloch"));
        library.add(new Book("978-0132350884", "Clean Code", "Robert C. Martin"));

        library.borrowBook("978-0132350884");
        System.out.println("Delete borrowed book: " + library.remove("978-0132350884"));
        library.returnBook("978-0132350884");
        System.out.println("Delete returned book: " + library.remove("978-0132350884"));
        System.out.println("Library Report: " + library.getOrderedReport());
    }
}