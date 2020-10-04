/* 3-1 */
import java.util.EmptyStackException;

class FullStackException extends Exception {
    private static final long serialVersionUID = 1L;
    public FullStackException() { super("Arrays used for stacks are full"); }
}

public class MultiStack {
    private class Info {
        int start, size, cap;
        
        public Info(int start, int cap) {
            this.start = start;
            this.cap = cap;
        }

        public boolean isWithinCapacity(int idx) {
            if (idx < 0 || idx >= values.length) return false;
            int wrappedIdx = idx < start ? idx + values.length : idx;
            return start <= wrappedIdx && wrappedIdx < start + cap;
        }

        public int lastCapIdx() { return adjustIndex(start + cap - 1); }
        public int lastIdx() { return adjustIndex(start + size - 1); }
        public boolean isFull() { return size == cap; }
        public boolean isEmpty() { return size == 0; }
    }

    private Info[] info;
    private int[] values;

    public MultiStack(int n, int size) {
        info = new Info[n];
        for (int i = 0; i < n; i++) info[i] = new Info(i * size, size);
        values = new int[n * size];
    }

    public void push(int n, int v) throws FullStackException {
        if (allStacksAreFull()) throw new FullStackException();
        if (info[n].isFull()) expand(n);

        info[n].size++;
        values[info[n].lastIdx()] = v;
    }

    public int pop(int n) {
        if (info[n].isEmpty()) throw new EmptyStackException();
        int val = values[info[n].lastIdx()];
        values[info[n].lastIdx()] = 0;
        info[n].size--;
        return val;
    }

    public int peek(int n) { return values[info[n].lastIdx()]; }

    private void shift(int n) {
        if (info[n].size >= info[n].cap) {
            shift((n + 1) % info.length);
            info[n].cap++;
        }

        for (int i = info[n].lastCapIdx(); info[n].isWithinCapacity(i); i = prevIdx(i))
            values[i] = values[prevIdx(i)];
        
        values[info[n].start] = 0;
        info[n].start = nextIdx(info[n].start);
        info[n].cap--;
    }

    private void expand(int n) {
        shift((n + 1) % info.length);
        info[n].cap++;
    }

    public int totalElements() {
        int size = 0;
        for (Info i: info) size += i.size;
        return size;
    }

    public boolean allStacksAreFull() { return totalElements() == values.length; }
    private int nextIdx(int i) { return adjustIndex(i + 1); }
    private int prevIdx(int i) { return adjustIndex(i - 1); }

    private int adjustIndex(int idx) {
        int len = values.length;
        return ((idx % len) + len) % len;
    }

    public String toString(int n) {
        if (info[n].size == 0) return "[]";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < info[n].size; i++)
            sb.append(values[adjustIndex(info[n].start + i)] + ", ");
        return "[" + sb.substring(0, sb.length()-2) + "]";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < info.length; i++)
            sb.append(toString(i) + ", ");
        return sb.substring(0, sb.length()-2);
    }
}