package DS.BST;
import Interface.IBST_Key;

public class BST_Node<T extends IBST_Key<T>> {
    private BST_Node left_child;
    private BST_Node right_child;
    private BST_Node parent;
    private final T data;
    private IBST_Key<T> key;

    public BST_Node(IBST_Key<T> key, T data) {
        this.data = data;
        this.key = key;
        this.left_child = null;
        this.right_child = null;
        this.parent = null;
    }

    public BST_Node getLeft_child() {
        return this.left_child;
    }

    public void setLeft_child(BST_Node left_child) {
        this.left_child = left_child;
    }

    public BST_Node getRight_child() {
        return this.right_child;
    }

    public void setRight_child(BST_Node right_child) {
        this.right_child = right_child;
    }

    public BST_Node getParent() {
        return this.parent;
    }

    public void setParent(BST_Node parent) {
        this.parent = parent;
    }

    public T getData() {
        return this.data;
    }
    public IBST_Key<T> getKey() {
        return this.key;
    }

    public void setKey(IBST_Key<T> key) {
        this.key = key;
    }
}
