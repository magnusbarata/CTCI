import java.util.Collection;
import java.util.NoSuchElementException;

final class Node<T> {
    Node<T> next;
    T data;

    public Node(T d) {
        data = d;
    }
}

class List<T extends Comparable<T>>  {
    private Node<T> head;

    public List() {}

    public List(Collection<? extends T> ds) {
        append(ds);
    }

    public void append(Collection<? extends T> ds) {
        for (T d: ds) append(d);
    }

    public void append(T d) {
        Node<T> e = new Node<>(d);

        if (head == null) {
            head = e;
        } else {
            Node<T> n = head;
            for (; n.next != null; n = n.next);
            n.next = e;
        }
    }

    public Node<T> getNode(T d) {
        if (head == null) throw new NoSuchElementException();
        for (Node<T> n = head; n != null; n = n.next)
            if (n.data.equals(d)) return n;
        throw new NoSuchElementException();
    }

    public void remove(T d) {
        if (head == null) throw new NoSuchElementException();
        if (head.data.equals(d)) {
            head = head.next;
        } else {
            Node<T> n = head;
            for (; n.next != null; n = n.next) {
                if (n.next.data.equals(d)) {
                    n.next = n.next.next;
                    return;
                }
            }
            throw new NoSuchElementException();
        }
    }

    /* 2-1 */
    public void rmDups() {
       java.util.HashSet<T> set = new java.util.HashSet<>();
       for (Node<T> prev = null, n = head; n != null; n = n.next) {
           if (set.contains(n.data)) {
               prev.next = n.next;
           } else {
               set.add(n.data);
               prev = n;
           }
       }
    }

    public void rmDupsNoBuffer() {   
        for (Node<T> i = head; i != null; i = i.next) {
            for (Node<T> j = i; j.next != null;) {
                if (i.data.equals(j.next.data)) j.next = j.next.next;
                else j = j.next;
            }
        }
    }

    /* 2-2 */
    public Node<T> getKthFromTail(int k) {
        Node<T> cur = head, runner = head;
        for (int i = 0; i < k; runner = runner.next, i++)
            if (runner == null) throw new NoSuchElementException();

        for (; runner != null; runner = runner.next, cur = cur.next);
        return cur;
    }

    private Node<T> getKthFromTail(Node<T> h, int k, int[] i) {
        if (h == null) return null;
        Node<T> n = getKthFromTail(h.next, k, i);
        i[0] += 1;
        if (i[0] == k) return h;
        return n;
    }

    public Node<T> getKthFromTailRecursive(int k) {
        Node<T> n = getKthFromTail(head, k, new int[]{0});
        if (n == null) throw new NoSuchElementException();
        return n;
    }

    /* 2-3 */
    public void delMiddle(Node<T> n) {
        if (n == null || n.next == null) throw new NoSuchElementException();
        n.data = n.next.data;
        n.next = n.next.next;
    }

    /* 2-4 */
    public void partition(T d) {
        Node<T> start, end, next = null;

        start = end = head;
        for (Node<T> n = head; n != null; n = next) {
            next = n.next;
            if (n.data.compareTo(d) < 0) { 
                n.next = start;
                start = n;
            } else {
                end.next = n;
                end = n;
            }
        }
        
        end.next = null;
        head = start;
    }

    @SuppressWarnings("unchecked")
    public void stablePartition(T d) {
        Node<T>[] left = new Node[2];
        Node<T>[] right = new Node[2];
        Node<T> next = null;
        for (Node<T> n = head; n != null; n = next) {
            next = n.next;
            n.next = null;
            if (n.data.compareTo(d) < 0) {
                if (left[0] == null) {
                    left[0] = n;
                    left[1] = left[0];
                } else {
                    left[1].next = n;
                    left[1] = n;
                }
            } else {
                if (right[0] == null) {
                    right[0] = n;
                    right[1] = right[0];
                } else {
                    right[1].next = n;
                    right[1] = n;
                }
            }
        }

        if (left[0] == null) {
            head = right[0];
        } else {
            left[1].next = right[0];
            head = left[0];
        }
    }


    @Override
    public String toString() {
        if (head == null) return "[]";

        StringBuilder sb = new StringBuilder();
        Node<T> n = head;
        for (; n != null; n = n.next)
            sb.append(n.data.toString() + ", ");
        return "[" + sb.substring(0, sb.length()-2) + "]";
    }
}

public class LinkedLists {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {
        System.out.print("Prep: ");
        List<Integer> intList = new List<>();
        intList.append(1);
        intList.append(2);
        intList.append(3);
        intList.remove(1);
        intList.remove(3);
        assert intList.toString().equals("[2]") : intList + " List must be: [2]";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("2-1: ");
        intList.append(java.util.Arrays.asList(1, 2, 3, 2, 3));
        intList.rmDups();
        assert intList.toString().equals("[2, 1, 3]") : intList + " List must be: [2, 1, 3]";
        intList.append(java.util.Arrays.asList(1, 2, 3, 3, 2));
        intList.rmDupsNoBuffer();
        assert intList.toString().equals("[2, 1, 3]") : intList + " List must be: [2, 1, 3]";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("2-2: ");
        intList.append(java.util.Arrays.asList(4, 5, 6, 7, 8));
        assert intList.getKthFromTail(3).data == 6 : "Item 3rd from last is 6";
        assert intList.getKthFromTail(8).data == 2 : "Item 8th from last is 2";
        assert intList.getKthFromTailRecursive(3).data == 6 : "Item 3rd from last is 6";
        assert intList.getKthFromTailRecursive(8).data == 2 : "Item 8th from last is 2";
        try {
            intList.getKthFromTail(9);
        } catch (NoSuchElementException e) {
            System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);
        }

        System.out.print("2-3: ");
        List<Character> charList = new List<>(java.util.Arrays.asList('a', 'b', 'c', 'd', 'e', 'f'));
        charList.delMiddle(charList.getNode('c'));
        assert charList.toString().equals("[a, b, d, e, f]") : charList + " List must be: [a, b, d, e, f]";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("2-4: ");
        intList = new List<>(java.util.Arrays.asList(3, 5, 8, 5, 10, 2, 1));
        intList.stablePartition(10);
        assert intList.toString().equals("[3, 5, 8, 5, 2, 1, 10]") : intList + " List must be: [3, 5, 8, 5, 2, 1, 10]";
        intList.stablePartition(5);
        assert intList.toString().equals("[3, 2, 1, 5, 8, 5, 10]") : intList + " List must be: [3, 2, 1, 5, 8, 5, 10]";
        intList.stablePartition(1);
        assert intList.toString().equals("[3, 2, 1, 5, 8, 5, 10]") : intList + " List must be: [3, 2, 1, 5, 8, 5, 10]";
        intList.partition(5);
        assert intList.toString().equals("[1, 2, 3, 5, 8, 5, 10]") : intList + " List must be: [1, 2, 3, 5, 8, 5, 10]";
        intList.partition(10);
        assert intList.toString().equals("[5, 8, 5, 3, 2, 1, 10]") : intList + " List must be: [5, 8, 5, 3, 2, 1, 10]";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);
    }
}
