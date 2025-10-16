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
            this.map.containsKey(key);
            this.numberOfOperations++;
            checkNumberOfOperations();
        }
        long end = System.currentTimeMillis();
        System.out.println("Čas hľadania: " + (end - start) + " ms");
    }

    public void operationRangeSearch(int num_operations) {
        System.out.println("\n--- Test RANGE SEARCH (" + num_operations + " operations) ---");
        ArrayList<T> sortedHelper = new ArrayList<>(this.helper);
        Collections.sort(sortedHelper);
        int[][] ranges = new int[num_operations][2];
        for (int i = 0; i < num_operations; i++) {
            int startIndex = this.random.nextInt(sortedHelper.size() - 500);
            int endIndex = startIndex + 500 + this.random.nextInt(sortedHelper.size() - startIndex - 500);
            ranges[i][0] = startIndex;
            ranges[i][1] = endIndex;
        }
        long totalTime = 0;
        int validQueries = 0;
        for (int i = 0; i < num_operations; i++) {
            T fromKey = sortedHelper.get(ranges[i][0]);
            T toKey = sortedHelper.get(ranges[i][1]);

            long start = System.nanoTime();
            this.map.subMap(fromKey, toKey).keySet();
            long end = System.nanoTime();

            totalTime += (end - start);
            validQueries++;
        }

        double avgTimeMs = (totalTime / (double) validQueries) / 1_000_000.0;
        System.out.printf("Počet intervalových hľadaní: %d%n", validQueries);
        System.out.printf("Priemerný čas intervalového hľadania: %.6f ms%n", avgTimeMs);
    }

    private void operationFindMin() {
        System.out.println("\n--- Test FIND MIN ---");
        long start = System.currentTimeMillis();
        this.map.firstKey();
        long end = System.currentTimeMillis();
        System.out.println("Čas findMin: " + (end - start) + " ms");
    }

    private void operationFindMax() {
        System.out.println("\n--- Test FIND MAX ---");
        long start = System.currentTimeMillis();
        this.map.lastKey();
        long end = System.currentTimeMillis();
        System.out.println("Čas findMax: " + (end - start) + " ms");
    }

    private void checkNumberOfOperations() {
        if (this.numberOfOperations % 2000000 == 0) {
            operationFindMin();
            operationFindMax();
        }
    }
}
