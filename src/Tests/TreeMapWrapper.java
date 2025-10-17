package Tests;

import java.util.*;

public class TreeMapWrapper<T extends Comparable<T>> {

    private final TreeMap<T, T> map = new TreeMap<>();
    private final ArrayList<T> helper;
    private final Random random;
    private int numberOfOperations;
    private final Set<Integer> usedKeys;

    public TreeMapWrapper(long seed) {
        this.random = new Random(seed);
        this.helper = new ArrayList<>();
        this.numberOfOperations = 0;
        this.usedKeys = new HashSet<>();
    }

    public void operationInsert(int num_elements) {
        System.out.println("\n--- Test INSERT (" + num_elements + " prvkov) ---");
        for (int i = 0; i < num_elements; i++) {
            int keyValue;
            do {
                keyValue = this.random.nextInt(num_elements);
            } while (this.usedKeys.contains(keyValue));
            this.usedKeys.add(keyValue);
            T key = (T) (Comparable) keyValue;
            this.helper.add(key);
        }
        long start = System.currentTimeMillis();
        for (T key : this.helper) {
            this.map.put(key, key);
            this.numberOfOperations++;
            checkNumberOfOperations();
        }
        long end = System.currentTimeMillis();
        System.out.println("Čas vkladania: " + (end - start) + " ms");
    }

    public void operationDelete(int num_elements) {
        System.out.println("\n--- Test DELETE (" + num_elements + " prvkov) ---");
        ArrayList<T> nodesToDelete = new ArrayList<>();
        for (int i = 0; i < num_elements && !this.helper.isEmpty(); i++) {
            int index = this.random.nextInt(this.helper.size());
            T removed = this.helper.get(index);
            int lastIndex = this.helper.size() - 1;
            this.helper.set(index, this.helper.get(lastIndex));
            this.helper.remove(lastIndex);
            nodesToDelete.add(removed);
        }
        long start = System.currentTimeMillis();
        for (T key : nodesToDelete) {
            this.map.remove(key);
            this.numberOfOperations++;
            checkNumberOfOperations();
        }
        long end = System.currentTimeMillis();
        System.out.println("Čas mazania: " + (end - start) + " ms");
    }

    public void operationSearch(int num_elements) {
        if (this.helper.isEmpty()) {
            System.out.println("No elements to search!");
            return;
        }
        ArrayList<T> searchKeys = new ArrayList<>();
        for (int i = 0; i < num_elements; i++) {
            int index = this.random.nextInt(this.helper.size());
            searchKeys.add(this.helper.get(index));
        }

        System.out.println("\n--- Test SEARCH (" + num_elements + " prvkov) ---");
        long start = System.currentTimeMillis();
        for (T key : searchKeys) {
            this.map.get(key);
            this.numberOfOperations++;
            checkNumberOfOperations();
        }
        long end = System.currentTimeMillis();
        System.out.println("Čas hľadania: " + (end - start) + " ms");
    }

    public void operationRangeSearch(int num_operations) {
        System.out.println("\n--- Test RANGE SEARCH (" + num_operations + " prvkov) ---");
        ArrayList<T> sortedHelper = new ArrayList<>(this.helper);
        Collections.sort(sortedHelper);
        ArrayList<ArrayList<T>> ranges = new ArrayList<>();
        for (int i = 0; i < num_operations; i++) {
            int startIndex = this.random.nextInt(sortedHelper.size() - 500);
            int endIndex = startIndex + 500 + this.random.nextInt(sortedHelper.size() - startIndex - 500);
            ranges.add(new ArrayList<>(Arrays.asList(sortedHelper.get(startIndex), sortedHelper.get(endIndex))));
        }
        long totalTime = 0;
        for (int i = 0; i < num_operations; i++) {
            long start = System.currentTimeMillis();
            this.map.subMap(ranges.get(i).get(0), ranges.get(i).get(1));
            long end = System.currentTimeMillis();
            this.numberOfOperations++;
            checkNumberOfOperations();
            totalTime += (end - start);
        }
        System.out.println("Celkový čas range search: " + totalTime + " ms");
    }

    private void operationFindMin() {
        System.out.println("\n--- Test FIND MIN ---");
        long start = System.nanoTime();
        this.map.firstKey();
        long end = System.nanoTime();
        System.out.println("Čas findMin: " + (end - start) + " ns");
    }

    private void operationFindMax() {
        System.out.println("\n--- Test FIND MAX ---");
        long start = System.nanoTime();
        this.map.lastKey();
        long end = System.nanoTime();
        System.out.println("Čas findMax: " + (end - start) + " ns");
    }

    private void checkNumberOfOperations() {
        if (this.numberOfOperations % 2000000 == 0) {
            operationFindMin();
            operationFindMax();
        }
    }
}
