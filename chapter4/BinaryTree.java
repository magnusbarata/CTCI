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

    public void setLeft(BinaryTree<T> left) {
        this.left = left;
        if (left != null) left.parent = this;
    }

    public void setRight(BinaryTree<T> right) {
        this.right = right;
        if (right != null) right.parent = this;
    }
}
