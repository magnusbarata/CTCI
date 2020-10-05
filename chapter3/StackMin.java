/* 3-2 */
import java.util.EmptyStackException;

public class StackMin<T extends Comparable<T>> extends Stack<T> {
    Stack<T> mins = new Stack<>();

    @Override
    public void push(T item) {
        if (isEmpty() || item.compareTo(min()) < 0) mins.push(item);
        super.push(item);
    }

    @Override
    public T pop() {
        T item = super.pop();
        if (item.equals(min())) mins.pop();
        return item;
    }

    public T min() {
        if (isEmpty()) throw new EmptyStackException();
        return mins.peek();
    }
}