package GUI.View;

import GUI.Controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;

public class View {
    private final TextArea outputArea;
    private Controller controller;

    // Vstupné polia
    private final TextField tfPocetDat;
    private final TextField tfPacientID;
    private final TextField tfOkres;
    private final TextField tfKraj;
    private final TextField tfPracovisko;
    private final TextField tfPocetDni;
    private final DatePicker dpDatumOd;
    private final DatePicker dpDatumDo;
    private final TextField tfIdTestu;

    public View(Stage stage) {
        this.outputArea = new TextArea();
        this.outputArea.setEditable(false);
        this.outputArea.setWrapText(true);
        this.outputArea.setPrefHeight(400);

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label title = new Label("Systém evidencie PCR testov (AVL Tree)");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        title.setAlignment(Pos.CENTER);

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(8);

        this.dpDatumOd = new DatePicker();
        this.dpDatumDo = new DatePicker();
        this.tfOkres = new TextField();
        this.tfKraj = new TextField();
        this.tfPracovisko = new TextField();
        this.tfPocetDni = new TextField();
        this.tfPacientID = new TextField();
        this.tfPocetDat = new TextField();
        this.tfIdTestu = new TextField();

        inputGrid.add(new Label("Dátum od:"), 0, 0);
        inputGrid.add(this.dpDatumOd, 1, 0);
        inputGrid.add(new Label("Dátum do:"), 2, 0);
        inputGrid.add(this.dpDatumDo, 3, 0);

        inputGrid.add(new Label("Okres (kód):"), 0, 1);
        inputGrid.add(this.tfOkres, 1, 1);
        inputGrid.add(new Label("Kraj (kód):"), 2, 1);
        inputGrid.add(this.tfKraj, 3, 1);

        inputGrid.add(new Label("Pracovisko (kód):"), 0, 2);
        inputGrid.add(this.tfPracovisko, 1, 2);
        inputGrid.add(new Label("Počet dní choroby:"), 2, 2);
        inputGrid.add(this.tfPocetDni, 3, 2);

        inputGrid.add(new Label("Pacient ID:"), 0, 3);
        inputGrid.add(this.tfPacientID, 1, 3);
        inputGrid.add(new Label("Počet dát (pre generátor):"), 2, 3);
        inputGrid.add(this.tfPocetDat, 3, 3);

        inputGrid.add(new Label("ID testu:"), 0, 4);
        inputGrid.add(this.tfIdTestu, 1, 4);

        HBox fileButtons = new HBox(10);
        Button btnSaveCSV = new Button("Ulož do CSV");
        Button btnLoadCSV = new Button("Načítaj z CSV");
        Button btnGenerate = new Button("Vygeneruj dáta");

        fileButtons.getChildren().addAll(btnSaveCSV, btnLoadCSV, btnGenerate);
        fileButtons.setAlignment(Pos.CENTER_LEFT);

        btnSaveCSV.setOnAction(e -> handleSaveCSV());
        btnLoadCSV.setOnAction(e -> handleLoadCSV());
        btnGenerate.setOnAction(e -> handleGenerate());

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

        VBox outputBox = new VBox(5);
        outputBox.getChildren().addAll(new Label("Výstup:"), this.outputArea);

        root.getChildren().addAll(title, inputGrid, fileButtons, new Separator(), operationsGrid, new Separator(), outputBox);

        Scene scene = new Scene(root, 1300, 850);
        stage.setTitle("PCR Evidenčný systém (AVL)");
        stage.setScene(scene);
        stage.show();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    //handlery
    private void handleGenerate() {
        this.controller.onGenerate(Integer.parseInt(this.tfPocetDat.getText().trim()));
    }

    private void handleSaveCSV() {
        this.outputArea.clear();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ulož CSV súbor");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV súbory (*.csv)", "*.csv"));

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            String path = file.getAbsolutePath();

            if (path.endsWith(".csv")) {
                path = path.substring(0, path.length() - ".csv".length());
            }
            this.controller.onSaveCSV(path);
        } else {
            this.outputArea.appendText("Ukladanie zrušené používateľom.\n");
        }
    }

    private void handleLoadCSV() {
        this.outputArea.clear();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Vyber súbor _osoby.csv alebo _testy.csv");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV súbory (*.csv)", "*.csv"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String path = file.getAbsolutePath();

            if (path.endsWith(".csv_osoby")) {
                path = path.substring(0, path.length() - ".csv_osoby".length());
            } else{
                path = path.substring(0, path.length() - ".csv_osoby".length());
            }

            this.controller.onLoadCSV(path);
        } else {
            this.outputArea.appendText("Načítanie zrušené používateľom.\n");
        }
    }

    private void handleOperation(String op) {
        this.outputArea.clear();
        this.controller.onOperation(
                op,
                this.dpDatumOd.getValue() == null ? null : java.sql.Date.valueOf(this.dpDatumOd.getValue()),
                this.dpDatumDo.getValue() == null ? null : java.sql.Date.valueOf(this.dpDatumDo.getValue()),
                this.tfOkres.getText().isEmpty() ? null : Integer.parseInt(this.tfOkres.getText()),
                this.tfKraj.getText().isEmpty() ? null : Integer.parseInt(this.tfKraj.getText()),
                this.tfPracovisko.getText().isEmpty() ? null : Integer.parseInt(this.tfPracovisko.getText()),
                this.tfPocetDni.getText().isEmpty() ? null : Integer.parseInt(this.tfPocetDni.getText()),
                this.tfPacientID.getText().trim().isEmpty() ? null : this.tfPacientID.getText().trim(),
                this.tfIdTestu.getText().isEmpty() ? null : Integer.parseInt(this.tfIdTestu.getText())
        );
    }

    public TextArea getOutputArea() {
        return this.outputArea;
    }
}
