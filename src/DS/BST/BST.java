package DS.BST;

import Interface.IBST_Key;
import java.util.ArrayList;
import java.util.Stack;

public class BST<T extends IBST_Key<T>> {
    protected BST_Node<T> root;

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
            if (compareResult == -1) {
                current = current.getLeft_child();
                left = true;
            } else if (compareResult == 1) {
                current = current.getRight_child();
                left = false;
            } else if (compareResult == 0) {
                throw new IllegalArgumentException("Duplicate keys are not allowed.");
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

    public BST_Node<T> search(IBST_Key<T> key) {
        BST_Node<T> current = this.root;
        while (current != null) {
            int compareResult = key.compareTo(current.getKey());
            if (compareResult == 0) {
                return current;
            } else if (compareResult == -1) {
                current = current.getLeft_child();
            } else if (compareResult == 1) {
                current = current.getRight_child();
            } else {
                throw new IllegalArgumentException("Key comparison failed.");
            }
        }
        return null;
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

    public BST_Node<T> delete(BST_Node<T> node) {
        //Vrchol je list
        if (node.getLeft_child() == null && node.getRight_child() == null) {
            replaceParentLink(node, null);
            return node.getParent();
        }
        //Vrchol ma jedneho potomka
        else if (node.getLeft_child() == null) {
            replaceParentLink(node, node.getRight_child());
            return node.getRight_child();
        } else if (node.getRight_child() == null) {
            replaceParentLink(node, node.getLeft_child());
            return node.getLeft_child();
        }
        //Vrchol ma dvoch potomkov
        else {
            BST_Node<T> successor = getMinNodeInRightSubtree(node.getRight_child());
            //Ak je nasledovník priamy potomok mazaneho vrcholu
            if (successor.getParent() == node) {
                replaceParentLink(node, successor);
                successor.setLeft_child(node.getLeft_child());
                if (node.getLeft_child() != null) {
                    node.getLeft_child().setParent(successor);
                }
            }
            //Ak nasledovník nie je priamy potomok mazaneho vrcholu
            else {
                replaceParentLink(successor, successor.getRight_child());
                replaceParentLink(node, successor);
                successor.setLeft_child(node.getLeft_child());
                successor.setRight_child(node.getRight_child());
                if (node.getLeft_child() != null) {
                    node.getLeft_child().setParent(successor);
                }
                if (node.getRight_child() != null) {
                    node.getRight_child().setParent(successor);
                }
            }
            return successor;
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
        return this.root;
    }

    public ArrayList<BST_Node<T>> inOrder() {
        ArrayList<BST_Node<T>> result = new ArrayList<>();
        Stack<BST_Node<T>> stack = new Stack<>();
        BST_Node<T> current = this.root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.getLeft_child();
            }
            if (!stack.isEmpty()) {
                current = stack.pop();
                result.add(current);
                current = current.getRight_child();
            }
        }
        return result;
    }

    public BST_Node<T> getMin() {
        if (this.root == null) {
            return null;
        }
        BST_Node<T> current = this.root;
        while (current.getLeft_child() != null) {
            current = current.getLeft_child();
        }
        return current;
    }

    public BST_Node<T> getMax() {
        if (this.root == null) {
            return null;
        }
        BST_Node<T> current = this.root;
        while (current.getRight_child() != null) {
            current = current.getRight_child();
        }
        return current;
    }
}
