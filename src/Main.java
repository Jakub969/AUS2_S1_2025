public class Main {
    public static void main(String[] args) {
        long seed = System.currentTimeMillis();
        /*Structure_Tester<GenerateData> testerBST = new Structure_Tester<>(new BST<GenerateData>(), seed);
        testerBST.operationInsert(1000);
        testerBST.operationDelete(200);
        testerBST.operationSearch(500);
        testerBST.operationRangeSearch(100);
        testerBST.operationFindMin(200);
        testerBST.operationFindMax(200);*/
        Structure_Tester<GenerateData> testerAVL = new Structure_Tester<>(new AVL<GenerateData>(), seed);
        testerAVL.operationInsert(1000);
        testerAVL.operationDelete(200);
        testerAVL.operationSearch(500);
        testerAVL.operationRangeSearch(100);
        testerAVL.operationFindMin(200);
        testerAVL.operationFindMax(200);
    }
}
