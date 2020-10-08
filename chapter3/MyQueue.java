/* 3-4 */
import java.util.NoSuchElementException;

public class MyQueue<T> {
    private Stack<T> newest = new Stack<>(), oldest = new Stack<>();

    public boolean isEmpty() { return newest.isEmpty() && oldest.isEmpty(); }
    public void add(T item) { newest.push(item); }

    public T remove() {
        if (oldest.isEmpty()) moveToOldest();
        return oldest.pop();
    }

    public T peek() {
        if (oldest.isEmpty()) moveToOldest();
        return oldest.peek();
    }

    private void moveToOldest() {
        if (newest.isEmpty()) throw new NoSuchElementException();
        while(!newest.isEmpty()) oldest.push(newest.pop());
    }
}
