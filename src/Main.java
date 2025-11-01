import GUI.Controller.Controller;

import GUI.Model.Model;
import GUI.View.View;
import javafx.stage.Stage;
//extends javafx.application.Application
public class Main extends javafx.application.Application{

    @Override
    public void start(Stage stage) {
        Model model = new Model();
        View view = new View(stage);
        Controller controller = new Controller(model, view);
        view.setController(controller);
    }

    public static void main(String[] args) {
        launch();

        /*for (int i = 0; i < 10000; i++) {
            long seed = System.currentTimeMillis();
            System.out.println("seed: " + seed);
            Structure_Tester<GenerateTestData> testerBST = new Structure_Tester<>(new BST<GenerateTestData>(), seed, 200);
            testerBST.fillAndEmptyStructure();
            testerBST.randomOperationGenerator();
            Structure_Tester<GenerateTestData> testerAVL = new Structure_Tester<>(new AVL<GenerateTestData>(), seed, 200);
            testerAVL.fillAndEmptyStructure();
            testerAVL.randomOperationGenerator();
        }*/

        /*int pocetVkladanychPrvkov = 10000000;
        int pocetMazanychPrvkov = 2000000;
        int pocetHladanychPrvkov = 5000000;
        int pocetPrvkovPreIntervaloveHladanie = 1000000;

        long seed = System.currentTimeMillis();
        Structure_Compare<GenerateTestData> compareBST = new Structure_Compare<>(new BST<GenerateTestData>(), seed);
        Structure_Compare<GenerateTestData> compareAVL = new Structure_Compare<>(new AVL<GenerateTestData>(), seed);
        TreeMapWrapper<GenerateTestData> compareTreeMap = new TreeMapWrapper<>(seed);

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
