import java.util.EmptyStackException;

public class Stack<T> {
    private Node<T> top;
    
    public boolean isEmpty() { return top == null; }

    public void push(T item) {
        Node<T> n = new Node<>(item);
        n.next = top;
        top = n;
    }

    public T pop() {
        if (isEmpty()) throw new EmptyStackException();
        T item = top.data;
        top = top.next;
        return item;
    }

    public T peek() {
        if (isEmpty()) throw new EmptyStackException();
        return top.data;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder();
        for (Node<T> n = top; n != null; n = n.next)
            sb.append(n.data.toString() + ", ");
        return "[" + sb.substring(0, sb.length()-2) + "]";
    }
}
