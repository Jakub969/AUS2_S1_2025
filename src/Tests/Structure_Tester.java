package Tests;

import DS.AVL.AVL;
import DS.AVL.AVL_Node;
import DS.BST.BST;
import DS.BST.BST_Node;
import Data.GenerateData;
import Interface.IBST_Key;

import java.util.*;

public class Structure_Tester<T extends IBST_Key<T>> {
    private final BST<T> structure;
    private final ArrayList<BST_Node<T>> helper; // pomocná štruktúra na kontrolu počtu
    private final Random random;
    private final int numberOfOperations;
    Set<Integer> usedKeys;

    public Structure_Tester(BST<T> structure, long seed, int numberOfOperations) {
        this.structure = structure;
        this.helper = new ArrayList<>();
        this.random = new Random(seed);
        this.numberOfOperations = numberOfOperations;
        this.usedKeys = new HashSet<>();
    }

    public void randomOperationGenerator() {
        System.out.println("\n--- Generujem náhodné operácie (" + this.numberOfOperations + " operácií) ---");
        for (int i = 0; i < this.numberOfOperations; i++) {
            double rand = this.random.nextDouble();
            if (rand < 0.75) {
                if (!operationInsert()) {
                    System.out.println("Test zlyhal pri operácii INSERT.");
                    return;
                }
            } else if (rand < 0.8) {
                if (!operationDelete()) {
                    System.out.println("Test zlyhal pri operácii DELETE.");
                    return;
                }
            } else if (rand < 0.85) {
                if (!operationSearch()) {
                    System.out.println("Test zlyhal pri operácii SEARCH.");
                    return;
                }
            } else {
                if (!operationRangeSearch()) {
                    System.out.println("Test zlyhal pri operácii RANGE SEARCH.");
                    return;
                }
            }
        }
    }

    public void fillAndEmptyStructure() {
        int halfOperations = 0;
        if (this.numberOfOperations % 2 != 0) {
            halfOperations = (this.numberOfOperations - 1) / 2;
        } else {
            halfOperations = this.numberOfOperations / 2;
        }
        System.out.println("\n--- Vkladám (" + halfOperations + " prvkov) ---");
        Set<Integer> uK = new HashSet<>();

        for (int i = 0; i < halfOperations; i++) {
            int randomValue;

            do {
                randomValue = this.random.nextInt(this.numberOfOperations);
            } while (uK.contains(randomValue));

            uK.add(randomValue);
            T key = (T) new GenerateData(randomValue);
            AVL_Node<T> node = new AVL_Node<>(key, key);
            this.helper.add(node);
            this.structure.insert(node);
            int countBST = this.structure.inOrder().size();
            if (countBST != this.helper.size()) {
                System.out.println("Chyba insert: Počet prvkov sa nezhoduje s pomocnou štruktúrou!");
                break;
            }
            if (this.structure instanceof AVL<T>) {
                if (!this.isAVL((AVL_Node<T>) this.structure.getRoot())) {
                    throw new IllegalArgumentException("Chyba insert: Strom nesplna AVL vlastnosti!");
                }
            }
        }
        System.out.println("\n--- Odstraňujem (" + halfOperations + " prvkov) ---");
        for (int i = 0; i < halfOperations; i++) {
            operationDelete();
        }
        System.out.println("Počet prvkov v strome: " + this.structure.inOrder().size() + ", Pomocná štruktúra: " + this.helper.size());
    }

    private boolean operationInsert() {
        int randomValue;
        do {
            randomValue = this.random.nextInt(this.numberOfOperations);
        } while (this.usedKeys.contains(randomValue));
        this.usedKeys.add(randomValue);
        T key = (T) new GenerateData(randomValue);

        AVL_Node<T> node = new AVL_Node<>(key, key);
        this.helper.add(node);
        this.structure.insert(node);
        int countBST = this.structure.inOrder().size();
        if (countBST != this.helper.size()) {
            System.out.println("Chyba insert: Počet prvkov sa nezhoduje s pomocnou štruktúrou!");
            return false;
        }
        if (this.structure instanceof AVL<T>) {
            if (!this.isAVL((AVL_Node<T>) this.structure.getRoot())) {
                throw new IllegalArgumentException("Chyba insert: Strom nesplna AVL vlastnosti!");
            }
        }
        return true;
    }

    private boolean operationDelete() {
        if (!this.helper.isEmpty()) {
            int index = this.random.nextInt(this.helper.size());
            BST_Node<T> nodeToDelete = this.helper.get(index);
            int lastIndex = this.helper.size() - 1;
            this.helper.set(index, this.helper.get(lastIndex));
            this.helper.remove(lastIndex);
            this.structure.delete(nodeToDelete);
        }
        int countBST = this.structure.inOrder().size();
        if (countBST != this.helper.size()) {
            System.out.println("Chyba delete: Počet prvkov sa nezhoduje s pomocnou štruktúrou!");
            return false;
        }
        if (this.structure instanceof AVL<T>) {
            if (!this.isAVL((AVL_Node<T>) this.structure.getRoot())) {
                throw new IllegalArgumentException("Chyba delete: Strom nesplna AVL vlastnosti!");
            }
        }
        return true;
    }

    private boolean operationSearch() {
        if (this.helper.isEmpty()) {
            return true;
        }
        int index = this.random.nextInt(this.helper.size());
        BST_Node<T> node = this.helper.get(index);
        BST_Node<T> treeNode = this.structure.search(node.getKey());
        if (treeNode != node) {
            System.out.println("Chyba search: Nájdený uzol sa nezhoduje s očakávaným!");
            return false;
        }
        return true;
    }

    private boolean operationRangeSearch() {
        if (this.helper.size() < 2) {
            return true;
        }
        ArrayList<BST_Node<T>> foundNodes = new ArrayList<>();
        ArrayList<BST_Node<T>> sortedHelper = new ArrayList<>(this.helper);
        Collections.sort(sortedHelper, (n1, n2) -> n1.getKey().compareTo(n2.getKey()));
        int startIndex = this.random.nextInt(Math.max(1, sortedHelper.size() - 1));
        int endIndex = startIndex + this.random.nextInt(sortedHelper.size() - startIndex);

        for (int i = startIndex; i <= endIndex; i++) {
            foundNodes.add(sortedHelper.get(i));
        }
        ArrayList<BST_Node<T>> treeNodes = this.structure.rangeSearch(
                sortedHelper.get(startIndex).getKey(),
                sortedHelper.get(endIndex).getKey()
        );
        if (treeNodes.size() != foundNodes.size()) {
            System.out.println("Chyba range search: Počet nájdených uzlov sa nezhoduje s očakávaným!");
            return false;
        }
        for (int i = 0; i < treeNodes.size(); i++) {
            if (treeNodes.get(i) != foundNodes.get(i)) {
                System.out.println("Chyba range search: Nájdený uzol sa nezhoduje s očakávaným!");
                return false;
            }
        }
        return true;
    }

    private boolean isAVL(AVL_Node<T> node) {
        if (this.structure instanceof AVL<T> avlTree) {
            boolean result = avlTree.compareLeftRightSubtreeHeights(node);
            return result;
        }
        return false;
    }
}
