package Tests;

import DS.AVL.AVL_Node;
import DS.BST.BST;
import DS.BST.BST_Node;
import Data.GenerateTestData;
import Interface.IBST_Key;

import java.util.*;

public class Structure_Compare<T extends IBST_Key<T>> {
    private final BST<T> structure;
    private final ArrayList<BST_Node<T>> helper; // pomocná štruktúra na kontrolu počtu
    private final Random random;
    private int numberOfOperations;
    Set<Integer> usedKeys;

    public Structure_Compare(BST<T> structure, long seed) {
        this.structure = structure;
        this.helper = new ArrayList<>();
        this.random = new Random(seed);
        this.numberOfOperations = 0;
        this.usedKeys = new HashSet<>();
    }

    public void operationInsert(int num_elements) {
        for (int i = 0; i < num_elements; i++) {
            int keyValue;
            do {
                keyValue = this.random.nextInt(num_elements);
            } while (this.usedKeys.contains(keyValue));
            this.usedKeys.add(keyValue);
            T key = (T) new GenerateTestData(keyValue);
            AVL_Node<T> node = new AVL_Node<>(key, key);
            this.helper.add(node);
        }
        System.out.println("\n--- Test INSERT (" + num_elements + " prvkov) ---");
        long start = System.currentTimeMillis();
        for (BST_Node<T> node : this.helper) {
            this.structure.insert(node);
            this.numberOfOperations++;
            checkNumberOfOperations();
        }
        long end = System.currentTimeMillis();
        System.out.println("Čas vkladania: " + (end - start) + " ms");
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
            this.structure.delete(nodeToDelete);
            this.numberOfOperations++;
            checkNumberOfOperations();
        }
        long end = System.currentTimeMillis();
        System.out.println("Čas mazania: " + (end - start) + " ms");
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
        System.out.println("\n--- Test RANGE SEARCH (" + num_operations + " prvkov) ---");

        ArrayList<BST_Node<T>> sortedHelper = new ArrayList<>(this.helper);
        Collections.sort(sortedHelper, (n1, n2) -> n1.getKey().compareTo(n2.getKey()));

        BST_Node[][] ranges = new BST_Node[num_operations][2];
        for (int i = 0; i < num_operations; i++) {
            int startIndex = this.random.nextInt(sortedHelper.size() - 500);
            int endIndex = startIndex + 500 + this.random.nextInt(sortedHelper.size() - startIndex - 500);
            ranges[i][0] = sortedHelper.get(startIndex);
            ranges[i][1] = sortedHelper.get(endIndex);
        }

        long totalTime = 0;

        for (int i = 0; i < num_operations; i++) {

            long start = System.currentTimeMillis();
            this.structure.rangeSearch(ranges[i][0].getKey(), ranges[i][0].getKey());
            long end = System.currentTimeMillis();
            this.numberOfOperations++;
            checkNumberOfOperations();
            totalTime += (end - start);
        }
        System.out.println("Celkový čas range search: " + totalTime + " ms");
    }

    private void checkNumberOfOperations() {
        if (this.numberOfOperations % 2000000 == 0) {
            operationFindMin();
            operationFindMax();
        }
    }

    private void operationFindMin() {
        System.out.println("\n--- Test FIND MIN ---");
        long start = System.nanoTime();
        this.structure.getMin();
        long end = System.nanoTime();
        System.out.println("Čas findMin: " + (end - start) + " ns");
    }

    private void operationFindMax() {
        System.out.println("\n--- Test FIND MAX ---");
        long start = System.nanoTime();
        this.structure.getMax();
        long end = System.nanoTime();
        System.out.println("Čas findMax: " + (end - start) + " ns");
    }
}
