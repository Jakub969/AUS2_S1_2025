public class AVL<T extends IBST_Key<T>> extends BST<T> {
    @Override
    public void insert(BST_Node<T> node) {
        super.insert(node);
        rebalance((AVL_Node<T>) node);
    }

    @Override
    public void delete(BST_Node<T> node) {
        BST_Node<T> parent = node.getParent();
        super.delete(node);
        rebalance((AVL_Node<T>) parent);
    }

    private void rebalance(AVL_Node<T> node) {
        while (node != null) {
            updateHeight(node);
            int balance = getBalance(node);

            // 4 prípady
            if (balance > 1) {
                if (getBalance((AVL_Node<T>) node.getLeft_child()) < 0) {
                    node.setLeft_child(rotateLeft((AVL_Node<T>) node.getLeft_child()));
                }
                node = (AVL_Node<T>) rotateRight(node);
            } else if (balance < -1) {
                if (getBalance((AVL_Node<T>) node.getRight_child()) > 0) {
                    node.setRight_child(rotateRight((AVL_Node<T>) node.getRight_child()));
                }
                node = (AVL_Node<T>) rotateLeft(node);
            }

            if (node.getParent() == null) {
                // node je nový root
                super.root = node;
            }

            node = (AVL_Node<T>) node.getParent();
        }
    }

    private void updateHeight(AVL_Node<T> node) {
        int leftH = (node.getLeft_child() == null) ? 0 : ((AVL_Node<T>) node.getLeft_child()).getHeight();
        int rightH = (node.getRight_child() == null) ? 0 : ((AVL_Node<T>) node.getRight_child()).getHeight();
        node.setHeight(Math.max(leftH, rightH) + 1);
    }

    private int getBalance(AVL_Node<T> node) {
        int leftH = (node.getLeft_child() == null) ? 0 : ((AVL_Node<T>) node.getLeft_child()).getHeight();
        int rightH = (node.getRight_child() == null) ? 0 : ((AVL_Node<T>) node.getRight_child()).getHeight();
        return leftH - rightH;
    }

    private BST_Node<T> rotateRight(AVL_Node<T> y) {
        AVL_Node<T> x = (AVL_Node<T>) y.getLeft_child();
        AVL_Node<T> T2 = (AVL_Node<T>) x.getRight_child();

        x.setRight_child(y);
        y.setLeft_child(T2);

        if (T2 != null) T2.setParent(y);

        x.setParent(y.getParent());
        if (y.getParent() == null) {
            super.root = x;
        } else if (y.getParent().getLeft_child() == y) {
            y.getParent().setLeft_child(x);
        } else {
            y.getParent().setRight_child(x);
        }
        y.setParent(x);

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    private BST_Node<T> rotateLeft(AVL_Node<T> x) {
        AVL_Node<T> y = (AVL_Node<T>) x.getRight_child();
        AVL_Node<T> T2 = (AVL_Node<T>) y.getLeft_child();

        y.setLeft_child(x);
        x.setRight_child(T2);

        if (T2 != null) T2.setParent(x);

        y.setParent(x.getParent());
        if (x.getParent() == null) {
            super.root = y;
        } else if (x.getParent().getLeft_child() == x) {
            x.getParent().setLeft_child(y);
        } else {
            x.getParent().setRight_child(y);
        }
        x.setParent(y);

        updateHeight(x);
        updateHeight(y);

        return y;
    }
}
