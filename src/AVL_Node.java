public class AVL_Node<T extends IBST_Key<T>> extends BST_Node<T> {
    private int height;

    public AVL_Node(IBST_Key<T> key, T data) {
        super(key, data);
        this.height = 1;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
