public class Main {
    public static void main(String[] args) {
        long seed = System.currentTimeMillis();
        /*Structure_Tester<GenerateData> testerBST = new Structure_Tester<>(new BST<GenerateData>(), seed);
        testerBST.operationInsert(10000000);
        testerBST.operationDelete(2000000);
        testerBST.operationSearch(5000000);
        testerBST.operationRangeSearch(1000000);
        testerBST.operationFindMin(2000000);
        testerBST.operationFindMax(2000000);*/
        Structure_Tester<GenerateData> testerAVL = new Structure_Tester<>(new AVL<GenerateData>(), seed);
        testerAVL.operationInsert(10000000);
        testerAVL.operationDelete(2000000);
        testerAVL.operationSearch(5000000);
        testerAVL.operationRangeSearch(1000000);
        testerAVL.operationFindMin(2000000);
        testerAVL.operationFindMax(2000000);
    }
}
