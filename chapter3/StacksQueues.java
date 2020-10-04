import java.util.EmptyStackException;
import java.util.NoSuchElementException;

final class Node<T> {
    private Node<T> next;
    private T data;

    public Node(T d) {
        this.data = d;
    }

    public T getData() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }
    public void setData(T data) {
        this.data = data;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}

class Stack<T> {
    private Node<T> top;

    public boolean isEmpty() {
        return top == null; 
    }

    public void push(T item) {
        Node<T> n = new Node<>(item);
        n.setNext(top);
        top = n;     
    }

    public T pop() {
        if (isEmpty()) throw new EmptyStackException();
        T item = top.getData();
        top = top.getNext();
        return item;
    }

    public T peek() {
        if (isEmpty()) throw new EmptyStackException();
        return top.getData();
    }
}

class Queue<T> {
    private Node<T> first;
    private Node<T> last;

    public boolean isEmpty() {
        return first == null;
    }

    public void add(T item) {
        Node<T> n = new Node<>(item);
        if (isEmpty()) {
            first = last = n;
        } else {
            last.setNext(n);
            last = n;
        }
    }

    public T remove() {
        if (isEmpty()) throw new NoSuchElementException();
        T item = first.getData();
        first = first.getNext();
        if (first == null) last = null;
        return item;
    }

    public T peek() {
        if (isEmpty()) throw new NoSuchElementException();
        return first.getData();
    }
}

public class StacksQueues {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) throws Exception {
        System.out.print("3-1: ");
        MultiStack stacks = new MultiStack(3, 4);
        assert stacks.toString().equals("[], [], []") : "Stacks must be empty upon initialization";
        stacks.push(0, 10); stacks.push(0, 11); stacks.push(0, 12); stacks.pop(0); stacks.push(0, 13);
        stacks.push(1, 20); stacks.push(1, 21); stacks.push(1, 22);
        stacks.push(2, 30); stacks.push(2, 31); stacks.push(2, 31); stacks.push(2, 32); stacks.push(2, 33); stacks.push(2, 34);
        assert stacks.toString().equals("[10, 11, 13], [20, 21, 22], [30, 31, 31, 32, 33, 34]") :
                        "Stacks must be: [10, 11, 13], [20, 21, 22], [30, 31, 31, 32, 33, 34]";
        stacks.pop(1); stacks.push(2, 35);
        assert stacks.peek(0) == 13 : "Last element on stack 0 is 13";
        assert stacks.peek(1) == 21 : "Last element on stack 1 is 21";
        assert stacks.peek(2) == 35 : "Last element on stack 2 is 35";
        System.out.println(ANSI_GREEN + "OK!" + ANSI_RESET);
    }
}
