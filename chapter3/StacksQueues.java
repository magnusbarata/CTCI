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

    public static void main(String[] args) {
    }
}
