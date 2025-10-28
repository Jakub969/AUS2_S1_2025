package DS.AVL;

import Interface.IBST_Key;
import DS.BST.*;

import java.util.Stack;

/** Trieda reprezentujúca AVL strom, ktorý rozširuje binárny vyhľadávací strom (BST)
 * @param <T> Typ dát implementujúci rozhranie IBST_Key
 */
public class AVL<T extends IBST_Key<T>> extends BST<T> {
    /** Konstruktor pre AVL strom
     */
    @Override
    public void insert(BST_Node<T> node) {
        super.insert(node);
        rebalanceInsert((AVL_Node<T>) node);
    }

    /** Odstránenie vrchola zo stromu
     * @param node Vrchol na odstránenie
     */
    @Override
    public void delete(BST_Node<T> node) {
        Stack<PathItem<T>> stack = getPathToDeletedNode((AVL_Node<T>) node);
        super.delete(node);
        rebalanceDelete((AVL_Node<T>) node, stack);
    }
    private void rebalanceInsert(AVL_Node<T> node) {
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
                rotateRight(node);
                break;
            } else if (balance < -1) {
                //RL
                if (getBalance((AVL_Node<T>) node.getRight_child()) > 0) {
                    node.setRight_child(rotateRight((AVL_Node<T>) node.getRight_child()));
                }
                //LL
                rotateLeft(node);
                break;
            } else if (balance == 0 && node.getHeight() > 1) {
                break;
            }

            if (node.getParent() == null) {
                // node je nový root
                super.root = node;
            }

            node = (AVL_Node<T>) node.getParent();
        }
    }

    /** Rebalancovanie AVL stromu po vložení alebo odstranení vrchola
     * @param node Vrchol, od ktorého sa začína rebalancovanie
     */
    private void rebalanceDelete(AVL_Node<T> node, Stack<PathItem<T>> stack) {
        while (!stack.empty()) {
            PathItem<T> item = stack.pop();
            AVL_Node<T> pathNode = item.node;
            int oldBalance = item.oldBalance;

            if (pathNode == node) {
                continue;
            }
            updateHeight(pathNode);
            int newBalance = getBalance(pathNode);

            if (oldBalance == 0 && Math.abs(newBalance) == 1) {
                break;
            }

            if (newBalance > 1) {
                if (getBalance((AVL_Node<T>) pathNode.getLeft_child()) < 0) {
                    pathNode.setLeft_child(rotateLeft((AVL_Node<T>) pathNode.getLeft_child()));
                }
                pathNode = (AVL_Node<T>) rotateRight(pathNode);
            } else if (newBalance < -1) {
                if (getBalance((AVL_Node<T>) pathNode.getRight_child()) > 0) {
                    pathNode.setRight_child(rotateRight((AVL_Node<T>) pathNode.getRight_child()));
                }
                pathNode = (AVL_Node<T>) rotateLeft(pathNode);
            }

            if (pathNode.getParent() == null) {
                super.root = pathNode;
            }
        }
    }

    private Stack<PathItem<T>> getPathToDeletedNode(AVL_Node<T> node) {
        Stack<PathItem<T>> pathStack = new Stack<>();
        AVL_Node<T> current = (AVL_Node<T>) super.root;

        while (current != null) {
            pathStack.push(new PathItem<>(current, getBalance(current)));

            int compareResult = node.getKey().compareTo(current.getKey());
            if (compareResult < 0) {
                current = (AVL_Node<T>) current.getLeft_child();
            } else if (compareResult > 0) {
                current = (AVL_Node<T>) current.getRight_child();
            } else {
                break;
            }
        }

        if (current == null) {
            return pathStack;
        }

        if (current.getLeft_child() == null) {
            AVL_Node<T> right = (AVL_Node<T>) current.getRight_child();
            if (right != null) {
                pathStack.push(new PathItem<>(right, getBalance(right)));
            }
        } else if (current.getRight_child() == null) {
            AVL_Node<T> left = (AVL_Node<T>) current.getLeft_child();
            if (left != null) {
                pathStack.push(new PathItem<>(left, getBalance(left)));
            }
        } else {
            AVL_Node<T> successor = (AVL_Node<T>) current.getRight_child();
            while (successor.getLeft_child() != null) {
                pathStack.push(new PathItem<>(successor, getBalance(successor)));
                successor = (AVL_Node<T>) successor.getLeft_child();
            }
            pathStack.push(new PathItem<>(successor, getBalance(successor)));
        }

        return pathStack;
    }

    /** Aktualizácia výšky vrchola
     * @param node Vrchol, ktorého výška sa má aktualizovať
     */
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
    /** Získanie vyváženia vrchola
     * @param node Vrchol, ktorého vyváženie sa má získať
     * @return Hodnota vyváženia vrchola
     */
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
    /** Pravá rotácia okolo vrchola y
     * @param y Vrchol, okolo ktorého sa vykonáva pravá rotácia
     * @return Nový koreň podstromu po rotácii
     */
    private BST_Node<T> rotateRight(AVL_Node<T> y) {
        AVL_Node<T> x = (AVL_Node<T>) y.getLeft_child();
        AVL_Node<T> T2 = (AVL_Node<T>) x.getRight_child();

        x.setRight_child(y);
        y.setLeft_child(T2);

        return fixRotationLinks(y, x, T2);
    }
    /** Oprava odkazov po rotácii
     * @param y Pôvodný koreň podstromu pred rotáciou
     * @param x Nový koreň podstromu po rotácii
     * @param t2 Podstrom T2, ktorý sa musí aktualizovať
     * @return Nový koreň podstromu po oprave odkazov
     */
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
    /** Ľavá rotácia okolo vrchola x
     * @param x Vrchol, okolo ktorého sa vykonáva ľavá rotácia
     * @return Nový koreň podstromu po rotácii
     */
    private BST_Node<T> rotateLeft(AVL_Node<T> x) {
        AVL_Node<T> y = (AVL_Node<T>) x.getRight_child();
        AVL_Node<T> T2 = (AVL_Node<T>) y.getLeft_child();

        y.setLeft_child(x);
        x.setRight_child(T2);

        return fixRotationLinks(x, y, T2);
    }
    /** Overenie, či strom spĺňa AVL vlastnosti
     * @param node Koreň stromu alebo podstromu na overenie
     * @return True, ak strom spĺňa AVL vlastnosti, inak false
     */
    public boolean compareLeftRightSubtreeHeights(AVL_Node<T> node) {
        if (node == null) {
            return true;
        } else {
            int leftH = 0;
            int rightH = 0;
            if (node.getLeft_child() != null) {
                leftH = ((AVL_Node<T>) node.getLeft_child()).getHeight();
            }
            if (node.getRight_child() != null) {
                rightH = ((AVL_Node<T>) node.getRight_child()).getHeight();
            }
            if (Math.abs(leftH - rightH) > 1) {
                return false;
            }
        }
        return compareLeftRightSubtreeHeights((AVL_Node<T>) node.getLeft_child()) && compareLeftRightSubtreeHeights((AVL_Node<T>) node.getRight_child());
    }
}

class PathItem<T extends IBST_Key<T>> {
    AVL_Node<T> node;
    int oldBalance;

    PathItem(AVL_Node<T> node, int oldBalance) {
        this.node = node;
        this.oldBalance = oldBalance;
    }
}
