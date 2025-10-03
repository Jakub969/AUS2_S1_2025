public class BST<T extends Comparable<T>> {
    private BST_Node<T> root;

    public BST() {
        this.root = null;
    }

    public void insert(BST_Node<T> node) {
        if (this.root == null) {
            this.root = node;
            return;
        }
        BST_Node<T> current = this.root;
        BST_Node<T> parent = null;
        boolean left = true;
        while (current != null) {
            parent = current;
            int compareResult = node.getKey().compareTo(current.getKey());
            if (compareResult == 0 || compareResult == -1) {
                current = current.getLeft_child();
                left = true;
            } else if (compareResult == 1) {
                current = current.getRight_child();
                left = false;
            } else {
                throw new IllegalArgumentException("Key comparison failed.");
            }
        }
        node.setParent(parent);
        if (left) {
            parent.setLeft_child(node);
        } else {
            parent.setRight_child(node);
        }
    }



    public BST_Node<T> getRoot() {
        return root;
    }
}
