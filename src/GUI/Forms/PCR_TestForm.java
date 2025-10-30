package GUI.Forms;

import Data.PCR_Test.PCR_Test;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class PCR_TestForm {
    private final Stage stage = new Stage();

    public PCR_TestForm(java.util.function.Consumer<PCR_Test> onSave) {
        stage.setTitle("Pridanie PCR testu");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        DatePicker dpDatum = new DatePicker(LocalDate.now());
        TextField tfCas = new TextField(LocalTime.now().toString());
        TextField tfUUIDOsoby = new TextField();
        TextField tfKodPCR = new TextField();
        TextField tfUUIDPracoviska = new TextField();
        TextField tfKodOkresu = new TextField();
        TextField tfKodKraja = new TextField();
        CheckBox cbPozitivny = new CheckBox("Pozitívny výsledok");
        TextField tfHodnota = new TextField();
        TextField tfPoznamka = new TextField();

        int row = 0;
        grid.addRow(row++, new Label("Dátum testu:"), dpDatum);
        grid.addRow(row++, new Label("Čas testu (HH:mm:ss):"), tfCas);
        grid.addRow(row++, new Label("UUID osoby:"), tfUUIDOsoby);
        grid.addRow(row++, new Label("Kód PCR:"), tfKodPCR);
        grid.addRow(row++, new Label("UUID pracoviska:"), tfUUIDPracoviska);
        grid.addRow(row++, new Label("Kód okresu:"), tfKodOkresu);
        grid.addRow(row++, new Label("Kód kraja:"), tfKodKraja);
        grid.addRow(row++, cbPozitivny);
        grid.addRow(row++, new Label("Hodnota testu:"), tfHodnota);
        grid.addRow(row++, new Label("Poznámka:"), tfPoznamka);

        Button btnUloz = new Button("Uložiť");
        Button btnZrus = new Button("Zrušiť");
        grid.addRow(row, btnUloz, btnZrus);

        btnUloz.setOnAction(e -> {
            try {
                LocalDate date = dpDatum.getValue();
                LocalTime time = LocalTime.parse(tfCas.getText().trim());
                Date datetime = Timestamp.valueOf(LocalDateTime.of(date, time));

                String uuidOsoby = tfUUIDOsoby.getText().trim();
                int kodPCR = Integer.parseInt(tfKodPCR.getText().trim());
                int uuidPracoviska = Integer.parseInt(tfUUIDPracoviska.getText().trim());
                int kodOkresu = Integer.parseInt(tfKodOkresu.getText().trim());
                int kodKraja = Integer.parseInt(tfKodKraja.getText().trim());
                boolean vysledok = cbPozitivny.isSelected();
                double hodnota = Double.parseDouble(tfHodnota.getText().trim());
                String poznamka = tfPoznamka.getText().trim();

                PCR_Test test = new PCR_Test(datetime, uuidOsoby, kodPCR, uuidPracoviska,
                        kodOkresu, kodKraja, vysledok, hodnota, poznamka);
                onSave.accept(test);
                stage.close();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Chyba: " + ex.getMessage()).showAndWait();
            }
        });

        btnZrus.setOnAction(e -> stage.close());

        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.setResizable(false);
    }

    public void show() {
        stage.show();
    }
}
