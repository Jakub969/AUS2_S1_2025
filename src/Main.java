import DS.AVL.AVL;
import DS.BST.BST;
import Data.GenerateData;
import Tests.Structure_Compare;
import Tests.Structure_Tester;
import Tests.TreeMapWrapper;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            long seed = System.currentTimeMillis();
            System.out.println("seed: " + seed);
            Structure_Tester<GenerateData> testerBST = new Structure_Tester<>(new BST<GenerateData>(), seed, 20000);
            testerBST.fillAndEmptyStructure();
            testerBST.randomOperationGenerator();
            Structure_Tester<GenerateData> testerAVL = new Structure_Tester<>(new AVL<GenerateData>(), seed, 20000);
            testerAVL.fillAndEmptyStructure();
            testerAVL.randomOperationGenerator();
        }
        /*
        int pocetVkladanychPrvkov = 10000000;
        int pocetMazanychPrvkov = 2000000;
        int pocetHladanychPrvkov = 5000000;
        int pocetPrvkovPreIntervaloveHladanie = 1000000;

        long seed = System.currentTimeMillis();
        Structure_Compare<GenerateData> compareBST = new Structure_Compare<>(new BST<GenerateData>(), seed);
        Structure_Compare<GenerateData> compareAVL = new Structure_Compare<>(new AVL<GenerateData>(), seed);
        TreeMapWrapper<GenerateData> compareTreeMap = new TreeMapWrapper<>(seed);

        compareBST.operationInsert(pocetVkladanychPrvkov);
        compareAVL.operationInsert(pocetVkladanychPrvkov);
        compareTreeMap.operationInsert(pocetVkladanychPrvkov);

        compareBST.operationDelete(pocetMazanychPrvkov);
        compareAVL.operationDelete(pocetMazanychPrvkov);
        compareTreeMap.operationDelete(pocetMazanychPrvkov);

        compareBST.operationSearch(pocetHladanychPrvkov);
        compareAVL.operationSearch(pocetHladanychPrvkov);
        compareTreeMap.operationSearch(pocetHladanychPrvkov);

        compareBST.operationRangeSearch(pocetPrvkovPreIntervaloveHladanie);
        compareAVL.operationRangeSearch(pocetPrvkovPreIntervaloveHladanie);
        compareTreeMap.operationRangeSearch(pocetPrvkovPreIntervaloveHladanie);*/
    }
}
