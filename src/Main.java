import DS.AVL.AVL;
import DS.BST.BST;
import Data.GenerateData;
import Tests.Structure_Compare;
import Tests.Structure_Tester;

public class Main {
    public static void main(String[] args) {
        /*for (int i = 0; i < 10000000; i++) {

        }*/
        long seed = 1760602704441L;//System.currentTimeMillis();
        System.out.println("Seed: " + seed);
        //Structure_Tester<GenerateData> testBST = new Structure_Tester<>(new BST<GenerateData>(), seed, 20);
        Structure_Tester<GenerateData> testAVL = new Structure_Tester<>(new AVL<GenerateData>(), seed, 20);
        //testBST.fillAndEmptyStructure();
        //testBST.randomOperationGenerator();
        testAVL.fillAndEmptyStructure();
        testAVL.randomOperationGenerator();
        /*Structure_Compare<GenerateData> compareBST = new Structure_Compare<>(new BST<GenerateData>(), seed);
        Structure_Compare<GenerateData> compareAVL = new Structure_Compare<>(new AVL<GenerateData>(), seed);
        compareBST.operationInsert(10000000);
        compareAVL.operationInsert(10000000);
        compareBST.operationDelete(2000000);
        compareAVL.operationDelete(2000000);
        compareBST.operationSearch(5000000);
        compareAVL.operationSearch(5000000);
        compareBST.operationRangeSearch(1000000);
        compareAVL.operationRangeSearch(1000000);*/
    }
}
