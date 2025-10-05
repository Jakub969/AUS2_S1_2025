import java.util.*;

public class Structure_Tester<T extends IBST_Key<T>> {
    private BST<T> structure;
    private ArrayList<BST_Node<T>> helper; // pomocná štruktúra na kontrolu počtu
    private Random random;

    public Structure_Tester(BST<T> structure, long seed) {
        this.structure = structure;
        this.helper = new ArrayList<>();
        this.random = new Random(seed);
    }

    public void operationInsert(int num_elements) {
        System.out.println("\n--- Test INSERT (" + num_elements + " prvkov) ---");
        long start = System.currentTimeMillis();

        for (int i = 0; i < num_elements; i++) {
            T key = (T) new GenerateData(random.nextInt(num_elements * 10));
            AVL_Node<T> node = new AVL_Node<>(key, key);
            helper.add(node);
            structure.insert(node);
        }
        long end = System.currentTimeMillis();
        System.out.println("Čas vkladania: " + (end - start) + " ms");
        int countBST = countNodes();
        System.out.println("Počet prvkov - BST: " + countBST + ", Pomocná štruktúra: " + helper.size());
    }

    public void operationDelete(int num_elements) {
        System.out.println("\n--- Test DELETE (" + num_elements + " prvkov) ---");
        long start = System.currentTimeMillis();

        for (int i = 0; i < num_elements && !helper.isEmpty(); i++) {
            int index = random.nextInt(helper.size());
            BST_Node<T> nodeToDelete = helper.remove(index);
            if (nodeToDelete != null) {
                structure.delete(nodeToDelete);
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("Čas mazania: " + (end - start) + " ms");

        int countBST = countNodes();
        System.out.println("Počet prvkov - BST: " + countBST + ", Pomocná štruktúra: " + helper.size());
    }

    public void operationSearch(int num_elements) {
        System.out.println("\n--- Test SEARCH (" + num_elements + " prvkov) ---");
        long start = System.currentTimeMillis();

        for (int i = 0; i < num_elements; i++) {
            int index = random.nextInt(helper.size());
            BST_Node<T> node = helper.get(index);
            this.structure.rangeSearch(node.getKey(), node.getKey());
        }

        long end = System.currentTimeMillis();
        System.out.println("Čas hľadania: " + (end - start) + " ms");
    }

    public void operationRangeSearch(int num_operations) {
        System.out.println("\n--- Test RANGE SEARCH (" + num_operations + " operácií) ---");
        long start = System.currentTimeMillis();

        for (int i = 0; i < num_operations; i++) {
            int lowValue = random.nextInt(50000);
            T low = (T) new GenerateData(lowValue);
            T high = (T) new GenerateData(lowValue + 1000); // malé intervaly
            structure.rangeSearch(low, high);
        }

        long end = System.currentTimeMillis();
        System.out.println("Čas intervalového hľadania: " + (end - start) + " ms");
    }

    public void operationFindMin(int num_ops) {
        System.out.println("\n--- Test FIND MIN (" + num_ops + " operácií) ---");
        long start = System.currentTimeMillis();
        for (int i = 0; i < num_ops; i++) {
            getMin(structure.getRoot());
        }
        long end = System.currentTimeMillis();
        System.out.println("Čas findMin: " + (end - start) + " ms");
    }

    public void operationFindMax(int num_ops) {
        System.out.println("\n--- Test FIND MAX (" + num_ops + " operácií) ---");
        long start = System.currentTimeMillis();
        for (int i = 0; i < num_ops; i++) {
            getMax(structure.getRoot());
        }
        long end = System.currentTimeMillis();
        System.out.println("Čas findMax: " + (end - start) + " ms");
    }

    private int countNodes() {
        return this.structure.inOrder().size();
    }

    private BST_Node<T> getMin(BST_Node<T> node) {
        if (node == null) return null;
        while (node.getLeft_child() != null)
            node = node.getLeft_child();
        return node;
    }

    private BST_Node<T> getMax(BST_Node<T> node) {
        if (node == null) return null;
        while (node.getRight_child() != null)
            node = node.getRight_child();
        return node;
    }
}
