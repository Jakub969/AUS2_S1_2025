package Tests;

import DS.AVL.AVL_Node;
import DS.BST.BST;
import DS.BST.BST_Node;
import Data.GenerateData;
import Interface.IBST_Key;

import java.util.ArrayList;
import java.util.Random;

public class Structure_Compare<T extends IBST_Key<T>> {
    private final BST<T> structure;
    private final ArrayList<BST_Node<T>> helper; // pomocná štruktúra na kontrolu počtu
    private final Random random;
    private long numberOfOperations;

    public Structure_Compare(BST<T> structure, long seed) {
        this.structure = structure;
        this.helper = new ArrayList<>();
        this.random = new Random(seed);
        this.numberOfOperations = 0;
    }

    public void operationInsert(int num_elements) {
        for (int i = 0; i < num_elements; i++) {
            T key = (T) new GenerateData(this.random.nextInt(num_elements * 10));
            AVL_Node<T> node = new AVL_Node<>(key, key);
            this.helper.add(node);
        }
        System.out.println("\n--- Test INSERT (" + num_elements + " prvkov) ---");
        long start = System.currentTimeMillis();
        for (BST_Node<T> tbstNode : this.helper) {
            this.structure.insert(tbstNode);
            this.numberOfOperations++;
            checkNumberOfOperations();
        }
        long end = System.currentTimeMillis();
        System.out.println("Čas vkladania: " + (end - start) + " ms");
        int countBST = this.structure.inOrder().size();
        System.out.println("Počet prvkov " + countBST + ", Pomocná štruktúra: " + this.helper.size());
    }

    public void operationDelete(int num_elements) {
        ArrayList<BST_Node<T>> nodes = new ArrayList<>();
        System.out.println("Pripravujem operaciu DELETE...");
        for (int i = 0; i < num_elements && !this.helper.isEmpty(); i++) {
            int index = this.random.nextInt(this.helper.size());
            BST_Node<T> removed = this.helper.get(index);
            int lastIndex = this.helper.size() - 1;
            this.helper.set(index, this.helper.get(lastIndex));
            this.helper.remove(lastIndex);
            nodes.add(removed);
        }
        System.out.println("\n--- Test DELETE (" + num_elements + " prvkov) ---");
        long start = System.currentTimeMillis();
        for (BST_Node<T> nodeToDelete : nodes) {
            if (nodeToDelete != null) {
                this.structure.delete(nodeToDelete);
            }
            this.numberOfOperations++;
            checkNumberOfOperations();
        }
        long end = System.currentTimeMillis();
        System.out.println("Čas mazania: " + (end - start) + " ms");

        int countBST = this.structure.inOrder().size();
        System.out.println("Počet prvkov " + countBST + ", Pomocná štruktúra: " + this.helper.size());
    }

    public void operationSearch(int num_elements) {
        ArrayList<BST_Node<T>> nodes = new ArrayList<>();

        for (int i = 0; i < num_elements; i++) {
            int index = this.random.nextInt(this.helper.size());
            nodes.add(this.helper.get(index));
        }
        System.out.println("\n--- Test SEARCH (" + num_elements + " prvkov) ---");
        long start = System.currentTimeMillis();
        for (BST_Node<T> nodeToSearch : nodes) {
            this.structure.search(nodeToSearch.getKey());
            this.numberOfOperations++;
            checkNumberOfOperations();
        }
        long end = System.currentTimeMillis();
        System.out.println("Čas hľadania: " + (end - start) + " ms");
    }

    public void operationRangeSearch(int num_operations) {
        IBST_Key[] ranges = new IBST_Key[num_operations*2];

        for (int i = 0; i < num_operations; i++) {
            int lowValue = this.random.nextInt(50000);
            T low = (T) new GenerateData(lowValue);
            T high = (T) new GenerateData(lowValue + 1000);
            ranges[i] = low;
            ranges[i+1] = high;
        }
        System.out.println("\n--- Test RANGE SEARCH (" + num_operations + " operácií) ---");
        long start = System.currentTimeMillis();
        for (int i = 0; i < num_operations; i++) {
            this.structure.rangeSearch(ranges[i], ranges[i+1]);
            this.numberOfOperations++;
            checkNumberOfOperations();
        }
        long end = System.currentTimeMillis();
        System.out.println("Čas intervalového hľadania: " + (end - start) + " ms");
    }

    private void checkNumberOfOperations() {
        if (this.numberOfOperations % 2000000 == 0) {
            operationFindMin();
            operationFindMax();
        }
    }

    private void operationFindMin() {
        System.out.println("\n--- Test FIND MIN ---");
        long start = System.currentTimeMillis();
        this.structure.getMin();
        long end = System.currentTimeMillis();
        System.out.println("Čas findMin: " + (end - start) + " ms");
    }

    private void operationFindMax() {
        System.out.println("\n--- Test FIND MAX ---");
        long start = System.currentTimeMillis();
        this.structure.getMax();
        long end = System.currentTimeMillis();
        System.out.println("Čas findMax: " + (end - start) + " ms");
    }
}
