import java.util.ArrayList;
import java.util.Stack;

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

    public ArrayList<BST_Node<T>> rangeSearch(IBST_Key<T> low, IBST_Key<T> high) {
        BST_Node<T> current = this.root;
        ArrayList<BST_Node<T>> result = new ArrayList<>();
        Stack<BST_Node<T>> stack = new Stack<>();
        while (current != null || !stack.isEmpty()) {
            //Hľadám najmenší prvok v rozsahu
            while (current != null) {
                if (current.getKey().compareTo(low) >= 0) {
                    stack.push(current);
                    current = current.getLeft_child();
                } else {
                    current = current.getRight_child();
                }
            }
            if (!stack.isEmpty()) {
                current = stack.pop();
                if (current.getKey().compareTo(low) >= 0 && current.getKey().compareTo(high) <= 0) {
                    result.add(current);
                }
                if (current.getKey().compareTo(high) < 0) {
                    current = current.getRight_child();
                } else {
                    current = null;
                }
            } else {
                break;
            }
        }
        return result;
    }

    public void delete(BST_Node<T> node) {
        //Vrchol je list
        if (node.getLeft_child() == null && node.getRight_child() == null) {
            replaceParentLink(node, null);
        }
        //Vrchol ma jedneho potomka
        else if (node.getLeft_child() == null) {
            replaceParentLink(node, node.getRight_child());
        } else if (node.getRight_child() == null) {
            replaceParentLink(node, node.getLeft_child());
        }
        //Vrchol ma dvoch potomkov
        else {
            BST_Node<T> successor = getMinNodeInRightSubtree(node.getRight_child());
            node.setKey(successor.getKey());
            if (successor.getLeft_child() == null && successor.getRight_child() == null) {
                replaceParentLink(successor, null);
            } else {
                replaceParentLink(successor, successor.getRight_child());
            }
        }
    }

    private BST_Node<T> getMinNodeInRightSubtree(BST_Node<T> node) {
        while (node.getLeft_child() != null) {
            node = node.getLeft_child();
        }
        return node;
    }

    private void replaceParentLink(BST_Node<T> node, BST_Node<T> newChild) {
        if (node.getParent() == null) {
            this.root = newChild;
            if (newChild != null) {
                newChild.setParent(null);
            }
        } else if (node.getParent().getLeft_child() == node) {
            node.getParent().setLeft_child(newChild);
            if (newChild != null) {
                newChild.setParent(node.getParent());
            }
        } else {
            node.getParent().setRight_child(newChild);
            if (newChild != null) {
                newChild.setParent(node.getParent());
            }
        }
    }

    public BST_Node<T> getRoot() {
        return root;
    }
}
