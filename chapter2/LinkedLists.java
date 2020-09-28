import java.util.Collection;
import java.util.NoSuchElementException;

class List<T extends Comparable<T>>  {
    private Node<T> head;

    static final class Node<T> {
        Node<T> next;
        T data;
    
        public Node(T d) {
            data = d;
        }
    }

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

    /* 2-5 */
    public static List<Integer> addFromHead(List<Integer> op1, List<Integer> op2) {
        List<Integer> ans = new List<>();
        boolean carry = false;

        Node<Integer> opPtr1 = op1.head, opPtr2 = op2.head;
        for (int tmp = 0; opPtr1 != null || opPtr2 != null; tmp = 0) {
            if (opPtr1 != null) {
                tmp += opPtr1.data;
                opPtr1 = opPtr1.next;
            }
            if (opPtr2 != null) {
                tmp += opPtr2.data;
                opPtr2 = opPtr2.next;
            }

            if (carry) tmp += 1;
            if (tmp > 9) {
                carry = true;
                ans.append(tmp % 10);
            } else {
                carry = false;
                ans.append(tmp);
            }
        }

        if (carry) ans.append(1);
        return ans;
    }

    public void insert(T d) {
        Node<T> n = new Node<>(d);
        if (head != null) n.next = head;
        head = n;
    }

    public static <T extends Comparable<T>> int length(List<T> l) {
        int len = 0;
        for (Node<T> n = l.head; n != null; n = n.next) len++;
        return len;
    }

    private static void padZero(List<Integer> l, int n) {
        for (int i = 0; i < n; i++) l.insert(0);
    }

    public static boolean addFromTailHelper(List<Integer> ans, Node<Integer> op1, Node<Integer> op2) {
        if (op1 == null && op2 == null) return false;
        boolean carry = addFromTailHelper(ans, op1.next, op2.next);

        int tmp = carry ? 1 : 0;
        tmp += op1.data + op2.data;
        ans.insert(tmp % 10);

        return tmp > 9;
    }

    public static List<Integer> addFromTail(List<Integer> op1, List<Integer> op2) {
        int len1 = length(op1), len2 = length(op2);
        if (len1 < len2) padZero(op1, len2 - len1);
        else padZero(op2, len1 - len2);
        
        List<Integer> ans = new List<>();
        boolean carry = addFromTailHelper(ans, op1.head, op2.head);
        if (carry) ans.insert(1);
        return ans;
    }

    /* 2-6 */
    public boolean isPalindrome() {
        java.util.Stack<T> stack = new java.util.Stack<>(); // Can also use the insert method from 2-5 to reverse list

        Node<T> n1 = head, n2 = head;
        for (; n2 != null && n2.next != null; n1 = n1.next, n2 = n2.next.next) stack.push(n1.data);
        if (n2 != null) n1 = n1.next; // Skip middle on odd number of element

        for (Node<T> n = n1; n != null; n = n.next)
            if (!stack.pop().equals(n.data)) return false;
        return true;
    }

    @SuppressWarnings("unchecked")
    private Node<Boolean> isPalindromeRecursiveHelper(Node<T> head, int length) {
        Node<Boolean> n = new Node<>(true);
        if (head == null || length <= 0) {
            n.next = (Node<Boolean>) head;
            return n;
        } else if (length == 1) {
            n.next = (Node<Boolean>) head.next;
            return n;
        }

        n = isPalindromeRecursiveHelper(head.next, length-2);
        if (Boolean.FALSE.equals(n.data) || n.next == null) return n;

        n.data = head.data.equals(n.next.data);
        n.next = n.next.next;
        return n;
    }

    public boolean isPalindromeRecursive() {
        return isPalindromeRecursiveHelper(head, length(this)).data;
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

        System.out.print("2-5: ");
        intList = List.addFromHead(new List<>(java.util.Arrays.asList(7, 1, 6)),
                                   new List<>(java.util.Arrays.asList(5, 9, 2)));
        assert intList.toString().equals("[2, 1, 9]") : intList + " Add from head: 617 + 295 = 912";
        intList = List.addFromHead(new List<>(java.util.Arrays.asList(9, 7, 8)),
                                   new List<>(java.util.Arrays.asList(6, 8, 5)));
        assert intList.toString().equals("[5, 6, 4, 1]") : intList + " Add from head: 879 + 586 = 1465";
        intList = List.addFromHead(new List<>(java.util.Arrays.asList(6, 1, 7)),
                                   new List<>(java.util.Arrays.asList(2, 9, 5, 1, 3)));
        assert intList.toString().equals("[8, 0, 3, 2, 3]") : intList + " Add from head: 716 + 31592 = 32308";
        intList = List.addFromTail(new List<>(java.util.Arrays.asList(6, 1, 7)),
                                   new List<>(java.util.Arrays.asList(2, 9, 5)));
        assert intList.toString().equals("[9, 1, 2]") : intList + " Add from tail: 617 + 295 = 912";
        intList = List.addFromTail(new List<>(java.util.Arrays.asList(8, 7, 9)),
                                   new List<>(java.util.Arrays.asList(5, 8, 6)));
        assert intList.toString().equals("[1, 4, 6, 5]") : intList + " Add from tail: 879 + 586 = 1465";
        intList = List.addFromTail(new List<>(java.util.Arrays.asList(7, 1, 6)),
                                   new List<>(java.util.Arrays.asList(3, 1, 5, 9, 2)));
        assert intList.toString().equals("[3, 2, 3, 0, 8]") : intList + " Add from tail: 716 + 31592 = 32308";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);

        System.out.print("2-6: ");
        charList = new List<>(java.util.Arrays.asList('r', 'a', 'c', 'e', 'c', 'a', 'r'));
        assert charList.isPalindrome() : charList + " ['r', 'a', 'c', 'e', 'c', 'a', 'r'] is a palindrome";
        charList = new List<>(java.util.Arrays.asList('a', 'c', 'a'));
        assert charList.isPalindrome() : charList + " ['a', 'c', 'a'] is a palindrome";
        charList = new List<>(java.util.Arrays.asList('a', 'c', 'b'));
        assert !charList.isPalindrome() : charList + " ['a', 'c', 'b'] is not a palindrome";
        charList = new List<>(java.util.Arrays.asList('c', 'c'));
        assert charList.isPalindrome() : charList + " ['c', 'c'] is a palindrome";
        charList = new List<>(java.util.Arrays.asList('r', 'a', 'c', 'e', 'c', 'a'));
        assert !charList.isPalindrome() : charList + " ['r', 'a', 'c', 'e', 'c', 'a'] is not a palindrome";
        charList = new List<>(java.util.Arrays.asList('r', 'a', 'c', 'e', 'c', 'a', 'r'));
        assert charList.isPalindromeRecursive() : charList + " Recursive: ['r', 'a', 'c', 'e', 'c', 'a', 'r'] is a palindrome";
        charList = new List<>(java.util.Arrays.asList('a', 'c', 'a'));
        assert charList.isPalindromeRecursive() : charList + " Recursive: ['a', 'c', 'a'] is a palindrome";
        charList = new List<>(java.util.Arrays.asList('a', 'c', 'b'));
        assert !charList.isPalindromeRecursive() : charList + " Recursive: ['a', 'c', 'b'] is not a palindrome";
        charList = new List<>(java.util.Arrays.asList('c', 'c'));
        assert charList.isPalindromeRecursive() : charList + " Recursive: ['c', 'c'] is a palindrome";
        charList = new List<>(java.util.Arrays.asList('r', 'a', 'c', 'e', 'c', 'a'));
        assert !charList.isPalindromeRecursive() : charList + " Recursive: ['r', 'a', 'c', 'e', 'c', 'a'] is not a palindrome";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);
    }
}