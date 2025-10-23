package DS.AVL;

import Interface.IBST_Key;
import DS.BST.*;
/** Trieda reprezentujúca AVL strom, ktorý rozširuje binárny vyhľadávací strom (BST)
 * @param <T> Typ dát implementujúci rozhranie IBST_Key
 */
public class AVL<T extends IBST_Key<T>> extends BST<T> {
    /** Konstruktor pre AVL strom
     */
    @Override
    public void insert(BST_Node<T> node) {
        super.insert(node);
        rebalance((AVL_Node<T>) node, true);
    }
    /** Odstránenie vrchola zo stromu
     * @param node Vrchol na odstránenie
     */
    @Override
    public void delete(BST_Node<T> node) {
        super.delete(node);
        rebalance((AVL_Node<T>) node, false);
    }
    /** Rebalancovanie AVL stromu po vložení alebo odstranení vrchola
     * @param node Vrchol, od ktorého sa začína rebalancovanie
     * @param isInsert True, ak sa rebalancovanie vykonáva po vložení, inak false
     */
    private void rebalance(AVL_Node<T> node, boolean isInsert) {
        while (!super.stack.empty()) {
            AVL_Node<T> pathNode = (AVL_Node<T>) super.stack.pop();
            if (pathNode == node) {
                continue;
            }
            boolean wasBalanced = pathNode.isBalanced();
            updateHeight(pathNode);
            int newBalance = getBalance(pathNode);
            if (!isInsert && wasBalanced && Math.abs(newBalance) == 1) {
                break;
            }

            // 4 prípady
            if (newBalance > 1) {
                //LR
                if (getBalance((AVL_Node<T>) pathNode.getLeft_child()) < 0) {
                    pathNode.setLeft_child(rotateLeft((AVL_Node<T>) pathNode.getLeft_child()));
                }
                //RR
                pathNode = (AVL_Node<T>) rotateRight(pathNode);
                if (isInsert) {
                    break;
                }
            } else if (newBalance < -1) {
                //RL
                if (getBalance((AVL_Node<T>) pathNode.getRight_child()) > 0) {
                    pathNode.setRight_child(rotateRight((AVL_Node<T>) pathNode.getRight_child()));
                }
                //LL
                pathNode = (AVL_Node<T>) rotateLeft(pathNode);
                if (isInsert) {
                    break;
                }
            }

            if (pathNode.getParent() == null) {
                // node je nový root
                super.root = pathNode;
            }
        }
        super.stack.clear();
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
        int result = leftH - rightH;
        node.setBalanced(result == 0);
        return result;
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
