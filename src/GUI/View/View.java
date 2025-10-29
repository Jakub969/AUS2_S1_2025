package GUI.View;

import GUI.Model.Model;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;

public class View {

    private final Model model;
    private final TextArea outputArea;

    // Vstupné polia
    private final TextField tfPocetDat;
    private final TextField tfPacientID;
    private final TextField tfOkres;
    private final TextField tfKraj;
    private final TextField tfPracovisko;
    private final TextField tfPocetDni;
    private final DatePicker dpDatumOd;
    private final DatePicker dpDatumDo;

    public View(Stage stage, Model model) {
        this.model = model;
        this.outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefHeight(400);

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label title = new Label("Systém evidencie PCR testov (AVL Tree)");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        title.setAlignment(Pos.CENTER);

        // ===== VSTUPNÉ POLE PARAMETROV =====
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(8);

        dpDatumOd = new DatePicker();
        dpDatumDo = new DatePicker();
        tfOkres = new TextField();
        tfKraj = new TextField();
        tfPracovisko = new TextField();
        tfPocetDni = new TextField();
        tfPacientID = new TextField();
        tfPocetDat = new TextField();

        inputGrid.add(new Label("Dátum od:"), 0, 0);
        inputGrid.add(dpDatumOd, 1, 0);
        inputGrid.add(new Label("Dátum do:"), 2, 0);
        inputGrid.add(dpDatumDo, 3, 0);

        inputGrid.add(new Label("Okres (kód):"), 0, 1);
        inputGrid.add(tfOkres, 1, 1);
        inputGrid.add(new Label("Kraj (kód):"), 2, 1);
        inputGrid.add(tfKraj, 3, 1);

        inputGrid.add(new Label("Pracovisko (kód):"), 0, 2);
        inputGrid.add(tfPracovisko, 1, 2);
        inputGrid.add(new Label("Počet dní choroby:"), 2, 2);
        inputGrid.add(tfPocetDni, 3, 2);

        inputGrid.add(new Label("Pacient ID:"), 0, 3);
        inputGrid.add(tfPacientID, 1, 3);
        inputGrid.add(new Label("Počet dát (pre generátor):"), 2, 3);
        inputGrid.add(tfPocetDat, 3, 3);

        // ===== HLAVNÉ TLAČIDLÁ =====
        HBox fileButtons = new HBox(10);
        Button btnGenerate = new Button("Vygeneruj dáta");
        Button btnSaveCSV = new Button("Ulož do CSV");
        Button btnLoadCSV = new Button("Načítaj z CSV");

        fileButtons.getChildren().addAll(btnGenerate, btnSaveCSV, btnLoadCSV);
        fileButtons.setAlignment(Pos.CENTER);

        btnGenerate.setOnAction(e -> handleGenerate());
        btnSaveCSV.setOnAction(e -> handleSaveCSV());
        btnLoadCSV.setOnAction(e -> handleLoadCSV());

        // ===== OPERÁCIE (21 tlačidiel) =====
        GridPane operationsGrid = new GridPane();
        operationsGrid.setHgap(10);
        operationsGrid.setVgap(8);

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
            operationsGrid.add(btn, col, row);
            row++;
            if (row % 7 == 0) { col++; row = 0; }
        }

        // ===== VÝSTUP =====
        VBox outputBox = new VBox(5);
        outputBox.getChildren().addAll(new Label("Výstup:"), outputArea);

        // ===== CELKOVÝ LAYOUT =====
        root.getChildren().addAll(title, inputGrid, fileButtons, new Separator(), operationsGrid, new Separator(), outputBox);

        Scene scene = new Scene(root, 1300, 850);
        stage.setTitle("PCR Evidenčný systém (AVL)");
        stage.setScene(scene);
        stage.show();
    }

    // ================== HANDLERY ==================

    private void handleGenerate() {
        String pocetText = tfPocetDat.getText().trim();
        outputArea.clear();
        if (pocetText.isEmpty()) {
            outputArea.appendText("Zadaj počet dát pre generovanie.\n");
            return;
        }
        try {
            int n = Integer.parseInt(pocetText);
            outputArea.appendText("Generujem " + n + " testovacích záznamov...\n");
            // model.vygenerujData(n); // doplníš neskôr
        } catch (NumberFormatException e) {
            outputArea.appendText("Chybný formát čísla.\n");
        }
    }

    private void handleSaveCSV() {
        outputArea.clear();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ulož CSV súbor");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV súbory (*.csv)", "*.csv"));

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            outputArea.appendText("Ukladám dáta do súboru: " + file.getAbsolutePath() + "\n");
            try {
                // Tu doplníš volanie svojej metódy z modelu
                // model.saveToCSV(file.getAbsolutePath());
                outputArea.appendText("Úspešne uložené.\n");
            } catch (Exception e) {
                outputArea.appendText("Chyba pri ukladaní: " + e.getMessage() + "\n");
            }
        } else {
            outputArea.appendText("Ukladanie zrušené používateľom.\n");
        }
    }

    private void handleLoadCSV() {
        outputArea.clear();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Načítaj CSV súbor");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV súbory (*.csv)", "*.csv"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            outputArea.appendText("Načítavam dáta zo súboru: " + file.getAbsolutePath() + "\n");
            try {
                // Tu doplníš volanie svojej metódy z modelu
                // model.loadFromCSV(file.getAbsolutePath());
                outputArea.appendText("Úspešne načítané.\n");
            } catch (Exception e) {
                outputArea.appendText("Chyba pri načítaní: " + e.getMessage() + "\n");
            }
        } else {
            outputArea.appendText("Načítanie zrušené používateľom.\n");
        }
    }

    private void handleOperation(String op) {
        outputArea.clear();
        outputArea.appendText("Spustená operácia: " + op + "\n");
        // Tu budeš volať metódy z Model podľa operácie
    }

    public TextArea getOutputArea() {
        return outputArea;
    }
}
