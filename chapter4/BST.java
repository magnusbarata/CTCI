public class BST<T extends Comparable<T>> extends BinaryTree<T> {
    public BST(T data) {
        super(data);
    }

    /* 4-2 */
    public static <T extends Comparable<T>> boolean checkBST(BinaryTree<T> root) {
        if (root.left != null && (root.data.compareTo(root.left.data) < 0 || !checkBST(root.left)))
            return false;

        if (root.right != null && (root.data.compareTo(root.right.data) >= 0 || !checkBST(root.right)))
            return false;
        return true;
    }

    public static <T extends Comparable<T>> BST<T> createMinHeightBST(T[] arr, int start, int stop) {
        if (stop < start) return null;
        int mid = (start + stop) / 2;
        BST<T> n = new BST<>(arr[mid]);
        n.setLeft(createMinHeightBST(arr, start, mid - 1));
        n.setRight(createMinHeightBST(arr, mid + 1, stop)); 
        return n;
    }

    public static <T extends Comparable<T>> BST<T> createMinHeightBST(T[] arr) {
        return createMinHeightBST(arr, 0, arr.length - 1);
    }
}
