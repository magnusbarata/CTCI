/* 3-3 */
import java.util.ArrayList;
import java.util.EmptyStackException;

class CustomStack<T> extends Stack<T> {
    public T removeBottom() {
        if (isEmpty()) throw new EmptyStackException();
        Node<T> n = top;
        for (; n.next != null && n.next.next != null; n = n.next);
        if (n.next == null) return pop();
        T data = n.next.data;
        n.next = null;
        return data;
    }
}

public class SetOfStacks<T> {
    private ArrayList<CustomStack<T>> stacks;
    private int capacity, size;

    public SetOfStacks(int capacity) {
        stacks = new ArrayList<>();
        this.capacity = capacity;
        size = capacity;
    }

    public boolean isEmpty() { return stacks.isEmpty(); }

    public void push(T item) {
        size++;
        if (size > capacity) {
            stacks.add(new CustomStack<>());
            size = 1;
        }
        stacks.get(stacks.size() - 1).push(item);
    }

    public T pop() {
        return popAt(stacks.size() - 1);
    }

    public T popAt(int idx) {
        if (stacks.isEmpty()) throw new EmptyStackException();
        size--;
        return shiftStack(idx, true);
    }

    private T shiftStack(int idx, boolean rmTop) {
        CustomStack<T> stack = stacks.get(idx);
        T item = rmTop ? stack.pop() : stack.removeBottom();
        if (stack.isEmpty()) {
            stacks.remove(idx);
            size = capacity;
        } else if (idx < stacks.size() - 1) {
            stack.push(shiftStack(idx + 1, false));
        }
        return item;
    }

    @Override
    public String toString() {
        if (stacks.isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder();
        for (CustomStack<T> s: stacks)
            sb.append(s.toString() + ", ");
        return "[" + sb.substring(0, sb.length()-2) + "]";
    }
}
