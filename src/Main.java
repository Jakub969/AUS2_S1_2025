import DS.AVL.AVL;
import DS.BST.BST;
import Data.GenerateData;
import Tests.Structure_Compare;

public class Main {
    public static void main(String[] args) {
        long seed = System.currentTimeMillis();
        Structure_Compare<GenerateData> compareBST = new Structure_Compare<>(new BST<GenerateData>(), seed);
        Structure_Compare<GenerateData> compareAVL = new Structure_Compare<>(new AVL<GenerateData>(), seed);
        compareBST.operationInsert(10000000);
        compareAVL.operationInsert(10000000);
        compareBST.operationDelete(2000000);
        compareAVL.operationDelete(2000000);
        compareBST.operationSearch(5000000);
        compareAVL.operationSearch(5000000);
        compareBST.operationRangeSearch(1000000);
        compareAVL.operationRangeSearch(1000000);
    }
}
