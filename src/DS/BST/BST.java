package DS.BST;

import Interface.IBST_Key;
import java.util.ArrayList;
import java.util.Stack;
/** Trieda reprezentujúca binárny vyhľadávací strom (BST)
 * @param <T> Typ dát implementujúci rozhranie IBST_Key
 */
public class BST<T extends IBST_Key<T>> {
    protected BST_Node<T> root;
    protected Stack<BST_Node<T>> stack;

    /** Konstruktor pre prázdny binárny vyhľadávací strom */
    public BST() {
        this.root = null;
        this.stack = new Stack<>();
    }

    /** Vloženie vrchola do binárneho vyhľadávacieho stromu
     * @param node Vrchol na vloženie
     * @throws IllegalArgumentException Ak sa pokúsite vložiť duplicitný kľúč alebo ak porovnanie kľúčov zlyhá
     */
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
    /** Vyhľadanie vrchola v binárnom vyhľadávacom strome podľa kľúča
     * @param key Kľúč vrchola na vyhľadanie
     * @return Vrchol s daným kľúčom alebo null, ak vrchol neexistuje
     * @throws IllegalArgumentException Ak porovnanie kľúčov zlyhá
     */
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
    /** Vyhľadanie všetkých vrcholov v zadanom rozsahu kľúčov
     * @param low Dolná hranica rozsahu (vrátane)
     * @param high Horná hranica rozsahu (vrátane)
     * @return Zoznam vrcholov, ktorých kľúče sú v zadanom rozsahu
     */
    public ArrayList<BST_Node<T>> rangeSearch(IBST_Key<T> low, IBST_Key<T> high) {
        ArrayList<BST_Node<T>> result = new ArrayList<>();
        if (this.root == null) return result;

        BST_Node<T> current = this.root;
        BST_Node<T> candidate = null;

        //najdem najmenší prvok z rozsahu
        while (current != null) {
            if (current.getKey().compareTo(low) >= 0) {
                candidate = current;  // potenciálny prvok v rozsahu
                current = current.getLeft_child(); // možno existuje menší
            } else {
                current = current.getRight_child();
            }
        }

        //teraz candidate je najmenší uzol s kľúčom >= low (môže byť null)
        if (candidate == null) return result;

        //prechádzam inorder nasledovníkov, kým sme v rozsahu
        current = candidate;
        while (current != null && current.getKey().compareTo(high) <= 0) {
            if (current.getKey().compareTo(low) >= 0) {
                result.add(current);
            }
            current = getInorderSuccessor(current);
        }
        return result;
    }

    private BST_Node<T> getInorderSuccessor(BST_Node<T> node) {
        if (node == null) return null;

        //ak má pravé dieťa, nasledovník je najľavejší v pravom podstrome
        if (node.getRight_child() != null) {
            BST_Node<T> succ = node.getRight_child();
            while (succ.getLeft_child() != null)
                succ = succ.getLeft_child();
            return succ;
        }

        //inak idem hore, kým neprídem zľava
        BST_Node<T> parent = node.getParent();
        BST_Node<T> child = node;
        while (parent != null && parent.getRight_child() == child) {
            child = parent;
            parent = parent.getParent();
        }

        return parent;
    }

    /** Odstránenie vrchola z binárneho vyhľadávacieho stromu
     * @param node Vrchol na odstránenie
     */
    public void delete(BST_Node<T> node) {
        BST_Node<T> current = this.root;
        while (current != null) {
            this.stack.push(current);
            if (current.getKey().compareTo(node.getKey()) == 0) {
                break;
            } else if (node.getKey().compareTo(current.getKey()) == 1) {
                current = current.getRight_child();
            } else if (node.getKey().compareTo(current.getKey()) == -1) {
                current = current.getLeft_child();
            }
        }
        //Vrchol je list
        if (node.getLeft_child() == null && node.getRight_child() == null) {
            replaceParentLink(node, null);
        }
        //Vrchol ma jedneho potomka
        else if (node.getLeft_child() == null) {
            replaceParentLink(node, node.getRight_child());
            this.stack.push(node.getRight_child());
        } else if (node.getRight_child() == null) {
            replaceParentLink(node, node.getLeft_child());
            this.stack.push(node.getLeft_child());
        }
        //Vrchol ma dvoch potomkov
        else {
            BST_Node<T> successor = getMinNodeInRightSubtree(node.getRight_child());
            BST_Node<T> parentOfSuccessor = successor.getParent();
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
        }
    }

    /** Nájdenie vrchola s minimálnym kľúčom v pravom podstrome zadaného vrchola
     * @param node Vrchol, ktorého pravý podstrom sa má prehľadať
     * @return Vrchol s minimálnym kľúčom v pravom podstrome
     */
    private BST_Node<T> getMinNodeInRightSubtree(BST_Node<T> node) {
        this.stack.push(node);
        while (node.getLeft_child() != null) {
            node = node.getLeft_child();
            this.stack.push(node);
        }
        return node;
    }

    /** Nahradenie odkazu rodiča vrchola na daný vrchol novým potomkom
     * @param node Vrchol, ktorého odkaz rodiča sa má nahradiť
     * @param newChild Nový potomok, ktorý nahradí daný vrchol
     */
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
    /** Getter pre koreň stromu
     * @return Koreň stromu
     */
    public BST_Node<T> getRoot() {
        return this.root;
    }
    /** In-order prechod stromom
     * @return Zoznam vrcholov v in-order poradí
     */
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
    /** Nájdenie vrchola s minimálnym kľúčom v celom strome
     * @return Vrchol s minimálnym kľúčom alebo null, ak je strom prázdny
     */
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
    /** Nájdenie vrchola s maximálnym kľúčom v celom strome
     * @return Vrchol s maximálnym kľúčom alebo null, ak je strom prázdny
     */
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
