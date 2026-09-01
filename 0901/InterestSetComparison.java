import java.util.HashSet;
import java.util.Set;

public class InterestSetComparison {

    public static <T> Set<T> getUnion(Set<T> s1, Set<T> s2) {
        Set<T> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }

    public static <T> Set<T> getIntersection(Set<T> s1, Set<T> s2) {
        Set<T> result = new HashSet<>(s1);
        result.retainAll(s2);
        return result;
    }

    public static <T> Set<T> getFirstOnly(Set<T> s1, Set<T> s2) {
        Set<T> result = new HashSet<>(s1);
        result.removeAll(s2);
        return result;
    }

    public static <T> Set<T> getSecondaryOnly(Set<T> s1, Set<T> s2) {
        Set<T> result = new HashSet<>(s2);
        result.removeAll(s1);
        return result;
    }

    public static void main(String[] args) {
        Set<String> userA = Set.of("Music", "Coding", "Gaming", "Reading");
        Set<String> userB = Set.of("Gaming", "Cooking", "Reading", "Travel");

        System.out.println("聯集 (Union)          : " + getUnion(userA, userB));
        System.out.println("交集 (Intersection)   : " + getIntersection(userA, userB));
        System.out.println("僅 UserA (First-Only)  : " + getFirstOnly(userA, userB));
        System.out.println("僅 UserB (Second-Only) : " + getSecondaryOnly(userA, userB));
    }
}