import java.util.ArrayList;

public class BinaryTree<T> {
    public BinaryTree<T> left, right, parent; 
    public T data;
    private int size = 0;

    public BinaryTree(T d) {
        data = d;
        size = 1;
    }

    public int size() { return size; }
    public int height() {
        int leftH = left == null ? 0 : left.height();
        int rightH = right == null ? 0 : right.height();
        return 1 + Math.max(leftH, rightH);
    }

    public void setLeft(BinaryTree<T> left) {
        this.left = left;
        if (left != null) left.parent = this;
    }

    public void setRight(BinaryTree<T> right) {
        this.right = right;
        if (right != null) right.parent = this;
    }

    @Override
    public String toString() { return data.toString(); }

    public static <T> BinaryTree<T> arrayToTree(T[] arr) {
        if (arr.length == 0) return null;
        BinaryTree<T> root = new BinaryTree<>(arr[0]);
        ArrayList<BinaryTree<T>> queue = new ArrayList<>();
        queue.add(root);
        for (int i = 1; i < arr.length;) {
            BinaryTree<T> t = queue.get(0);
            if (t.left == null) {
                t.left = new BinaryTree<>(arr[i]);
                i++;
                queue.add(t.left);
            } else if (t.right == null) {
                t.right = new BinaryTree<>(arr[i]);
                i++;
                queue.add(t.right);
            } else {
                queue.remove(0);
            }
        }
        return root;
    }

    /* 4-3 */
    public static <T> ArrayList<ArrayList<BinaryTree<T>>> listOfDepthsBFS(BinaryTree<T> root) {
        ArrayList<ArrayList<BinaryTree<T>>> res = new ArrayList<>();

        ArrayList<BinaryTree<T>> cur = new ArrayList<>();
        if (root != null) cur.add(root);

        while (!cur.isEmpty()) {
            res.add(cur);
            ArrayList<BinaryTree<T>> parents = cur;
            cur = new ArrayList<>();
            for (BinaryTree<T> p : parents) {
                if (p.left != null) cur.add(p.left);
                if (p.right != null) cur.add(p.right);
            }
        }
        return res;
    }
}
