import java.util.NoSuchElementException;

public class Queue<T> {
    private Node<T> first;
    private Node<T> last;

    public boolean isEmpty() { return first == null; }

    public void add(T item) {
        Node<T> n = new Node<>(item);
        if (isEmpty()) {
            first = last = n;
        } else {
            last.next = n;
            last = n;
        }
    }

    public T remove() {
        if (isEmpty()) throw new NoSuchElementException();
        T item = first.data;
        first = first.next;
        if (isEmpty()) last = null;
        return item;
    }

    public T peek() {
        if (isEmpty()) throw new NoSuchElementException();
        return first.data;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder();
        for (Node<T> n = first; n != null; n = n.next)
            sb.append(n.data.toString() + ", ");
        return "[" + sb.substring(0, sb.length()-2) + "]";
    }
}
