package DS.AVL;

import Interface.IBST_Key;
import DS.BST.*;

public class AVL<T extends IBST_Key<T>> extends BST<T> {
    @Override
    public void insert(BST_Node<T> node) {
        super.insert(node);
        rebalance((AVL_Node<T>) node, true);
    }

    @Override
    public void delete(BST_Node<T> node) {
        BST_Node<T> parent = node.getParent();
        super.delete(node);
        rebalance((AVL_Node<T>) parent, false);
    }

    private void rebalance(AVL_Node<T> node, boolean isInsert) {
        while (node != null) {
            updateHeight(node);
            int balance = getBalance(node);

            // 4 prípady
            if (balance > 1) {
                //LR
                if (getBalance((AVL_Node<T>) node.getLeft_child()) < 0) {
                    node.setLeft_child(rotateLeft((AVL_Node<T>) node.getLeft_child()));
                }
                //RR
                node = (AVL_Node<T>) rotateRight(node);
                if (isInsert) {
                    break;
                }
            } else if (balance < -1) {
                //RL
                if (getBalance((AVL_Node<T>) node.getRight_child()) > 0) {
                    node.setRight_child(rotateRight((AVL_Node<T>) node.getRight_child()));
                }
                //LL
                node = (AVL_Node<T>) rotateLeft(node);
                if (isInsert) {
                    break;
                }
            } else if (isInsert && balance == 0 && node.getHeight() > 1) {
                break;
            }

            if (node.getParent() == null) {
                // node je nový root
                super.root = node;
            }

            node = (AVL_Node<T>) node.getParent();
        }
    }

    private void updateHeight(AVL_Node<T> node) {
        int leftH = 0;
        int rightH = 0;
        if (node.getLeft_child() != null) {
            leftH = ((AVL_Node<T>) node.getLeft_child()).getHeight();
        }
        if (node.getRight_child() != null) {
            rightH = ((AVL_Node<T>) node.getRight_child()).getHeight();
        }
        node.setHeight(Math.max(leftH, rightH) + 1);
    }

    private int getBalance(AVL_Node<T> node) {
        int leftH = 0;
        int rightH = 0;
        if (node.getLeft_child() != null) {
            leftH = ((AVL_Node<T>) node.getLeft_child()).getHeight();
        }
        if (node.getRight_child() != null) {
            rightH = ((AVL_Node<T>) node.getRight_child()).getHeight();
        }
        return leftH - rightH;
    }

    private BST_Node<T> rotateRight(AVL_Node<T> y) {
        AVL_Node<T> x = (AVL_Node<T>) y.getLeft_child();
        AVL_Node<T> T2 = (AVL_Node<T>) x.getRight_child();

        x.setRight_child(y);
        y.setLeft_child(T2);

        return fixRotationLinks(y, x, T2);
    }

    private BST_Node<T> fixRotationLinks(AVL_Node<T> y, AVL_Node<T> x, AVL_Node<T> t2) {
        if (t2 != null) {
            t2.setParent(y);
        }
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

        return fixRotationLinks(x, y, T2);
    }
}
