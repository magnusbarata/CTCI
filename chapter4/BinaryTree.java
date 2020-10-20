public class BinaryTree<T extends Comparable<T>> {
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

    private void setLeft(BinaryTree<T> left) {
        this.left = left;
        if (left != null) left.parent = this;
    }

    private void setRight(BinaryTree<T> right) {
        this.right = right;
        if (right != null) right.parent = this;
    }

    /* 4-2 */
    public boolean isBST() {
        if (left != null && (data.compareTo(left.data) < 0 || !left.isBST()))
            return false;

        if (right != null && (data.compareTo(right.data) >= 0 || !right.isBST()))
            return false;
        return true;
    }

    public static <T extends Comparable<T>> BinaryTree<T> createMinBST(T[] arr, int start, int stop) {
        if (stop < start) return null;
        int mid = (start + stop) / 2;
        BinaryTree<T> n = new BinaryTree<>(arr[mid]);
        n.setLeft(createMinBST(arr, start, mid - 1));
        n.setRight(createMinBST(arr, mid + 1, stop)); 
        return n;
    }

    public static <T extends Comparable<T>> BinaryTree<T> createMinBST(T[] arr) {
        return createMinBST(arr, 0, arr.length - 1);
    }
    
}
