package GUI.View;

import GUI.Model.Model;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class View {

    private final Model model;
    private final TextArea outputArea;

    public View(Stage stage, Model model) {
        this.model = model;
        this.outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefHeight(400);

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label title = new Label("Systém evidencie PCR testov (AVL Tree Demo)");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // --- Sekcia tlačidiel
        GridPane buttonsGrid = new GridPane();
        buttonsGrid.setHgap(10);
        buttonsGrid.setVgap(8);

        String[] operations = {
                "1. Vloženie výsledku PCR testu",
                "2. Vyhľadanie výsledku testu",
                "3. Výpis testov pre osobu",
                "4. Pozitívne testy (dátum + okres)",
                "5. Všetky testy (dátum + okres)",
                "6. Pozitívne testy (dátum + kraj)",
                "7. Všetky testy (dátum + kraj)",
                "8. Pozitívne testy (dátum)",
                "9. Všetky testy (dátum)",
                "10. Choré osoby (okres, X dní)",
                "11. Choré osoby (okres, X dní, podľa hodnoty)",
                "12. Choré osoby (kraj, X dní)",
                "13. Choré osoby (celkovo, X dní)",
                "14. Najvyššia hodnota testu v okrese",
                "15. Okresy podľa počtu chorých",
                "16. Kraje podľa počtu chorých",
                "17. Testy (dátum + pracovisko)",
                "18. Vyhľadanie PCR testu",
                "19. Vloženie osoby",
                "20. Vymazanie PCR testu",
                "21. Vymazanie osoby"
        };

        int col = 0, row = 0;
        for (String label : operations) {
            Button btn = new Button(label);
            btn.setPrefWidth(380);
            btn.setOnAction(e -> handleOperation(label));
            buttonsGrid.add(btn, col, row);
            row++;
            if (row % 7 == 0) { col++; row = 0; }
        }

        root.getChildren().addAll(title, buttonsGrid, new Label("Výstup:"), outputArea);
        Scene scene = new Scene(root, 1200, 700);

        stage.setTitle("PCR Evidenčný systém (AVL)");
        stage.setScene(scene);
        stage.show();
    }

    private void handleOperation(String op) {
        outputArea.clear();
        switch (op) {
            case "1. Vloženie výsledku PCR testu" -> outputArea.appendText("Volaná operácia 1 - vloženie PCR testu\n");
            case "2. Vyhľadanie výsledku testu" -> outputArea.appendText("Volaná operácia 2 - vyhľadanie výsledku testu\n");
            case "3. Výpis testov pre osobu" -> outputArea.appendText("Volaná operácia 3 - výpis testov pre osobu\n");
            case "4. Pozitívne testy (dátum + okres)" -> outputArea.appendText("Volaná operácia 4 - pozitívne testy (dátum + okres)\n");
            case "5. Všetky testy (dátum + okres)" -> outputArea.appendText("Volaná operácia 5 - všetky testy (dátum + okres)\n");
            case "6. Pozitívne testy (dátum + kraj)" -> outputArea.appendText("Volaná operácia 6 - pozitívne testy (dátum + kraj)\n");
            case "7. Všetky testy (dátum + kraj)" -> outputArea.appendText("Volaná operácia 7 - všetky testy (dátum + kraj)\n");
            case "8. Pozitívne testy (dátum)" -> outputArea.appendText("Volaná operácia 8 - pozitívne testy (dátum)\n");
            case "9. Všetky testy (dátum)" -> outputArea.appendText("Volaná operácia 9 - všetky testy (dátum)\n");
            case "10. Choré osoby (okres, X dní)" -> outputArea.appendText("Volaná operácia 10 - choré osoby (okres, X dní)\n");
            case "11. Choré osoby (okres, X dní, podľa hodnoty)" -> outputArea.appendText("Volaná operácia 11 - choré osoby (okres, X dní, podľa hodnoty)\n");
            case "12. Choré osoby (kraj, X dní)" -> outputArea.appendText("Volaná operácia 12 - choré osoby (kraj, X dní)\n");
            case "13. Choré osoby (celkovo, X dní)" -> outputArea.appendText("Volaná operácia 13 - choré osoby (celkovo, X dní)\n");
            case "14. Najvyššia hodnota testu v okrese" -> outputArea.appendText("Volaná operácia 14 - najvyššia hodnota testu v okrese\n");
            case "15. Okresy podľa počtu chorých" -> outputArea.appendText("Volaná operácia 15 - okresy podľa počtu chorých\n");
            case "16. Kraje podľa počtu chorých" -> outputArea.appendText("Volaná operácia 16 - kraje podľa počtu chorých\n");
            case "17. Testy (dátum + pracovisko)" -> outputArea.appendText("Volaná operácia 17 - testy (dátum + pracovisko)\n");
            case "18. Vyhľadanie PCR testu" -> outputArea.appendText("Volaná operácia 18 - vyhľadanie PCR testu\n");
            case "19. Vloženie osoby" -> outputArea.appendText("Volaná operácia 19 - vloženie osoby\n");
            case "20. Vymazanie PCR testu" -> outputArea.appendText("Volaná operácia 20 - vymazanie PCR testu\n");
            case "21. Vymazanie osoby" -> outputArea.appendText("Volaná operácia 21 - vymazanie osoby\n");
            default -> outputArea.appendText("Neznáma operácia: " + op + "\n");
        }
    }

    public TextArea getOutputArea() {
        return outputArea;
    }
}
