package Tests;

import DS.AVL.AVL_Node;
import DS.BST.BST;
import DS.BST.BST_Node;
import Data.GenerateData;
import Interface.IBST_Key;

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
        for (int i = 0; i < num_elements; i++) {
            T key = (T) new GenerateData(random.nextInt(num_elements * 10));
            AVL_Node<T> node = new AVL_Node<>(key, key);
            helper.add(node);
            structure.insert(node);
        }
        int countBST = this.structure.inOrder().size();
        System.out.println("Počet prvkov " + countBST + ", Pomocná štruktúra: " + helper.size());
    }

    public boolean operationDelete(int num_elements) {
        for (int i = 0; i < num_elements && !helper.isEmpty(); i++) {
            int index = random.nextInt(helper.size());
            BST_Node<T> nodeToDelete = helper.remove(index);
            if (nodeToDelete != null) {
                structure.delete(nodeToDelete);
            }
        }
        int countBST = this.structure.inOrder().size();
        System.out.println("Počet prvkov " + countBST + ", Pomocná štruktúra: " + helper.size());
        if (countBST != helper.size()) {
            System.out.println("Chyba: Počet prvkov v DS.BST.BST nezhoduje s pomocnou štruktúrou!");
            return false;
        }
        return true;
    }

    public void operationSearch(int num_elements) {
        for (int i = 0; i < num_elements; i++) {
            int index = random.nextInt(helper.size());
            BST_Node<T> node = helper.get(index);
            this.structure.search(node.getKey());
        }
    }

    public void operationRangeSearch(int num_operations) {
        for (int i = 0; i < num_operations; i++) {
            int lowValue = random.nextInt(50000);
            T low = (T) new GenerateData(lowValue);
            T high = (T) new GenerateData(lowValue + 1000);
            structure.rangeSearch(low, high);
        }
    }
}
