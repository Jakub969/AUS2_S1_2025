package GUI.Forms;

import Data.Osoba.Osoba;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class OsobaForm {
    private final Stage stage = new Stage();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public OsobaForm(java.util.function.Consumer<Osoba> onSave) {
        stage.setTitle("Pridanie osoby");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField tfMeno = new TextField();
        TextField tfPriezvisko = new TextField();
        DatePicker dpDatumNarodenia = new DatePicker();
        TextField tfUUID = new TextField(UUID.randomUUID().toString());

        grid.addRow(0, new Label("Meno:"), tfMeno);
        grid.addRow(1, new Label("Priezvisko:"), tfPriezvisko);
        grid.addRow(2, new Label("Dátum narodenia:"), dpDatumNarodenia);
        grid.addRow(3, new Label("UUID:"), tfUUID);

        Button btnUloz = new Button("Uložiť");
        Button btnZrus = new Button("Zrušiť");
        grid.addRow(4, btnUloz, btnZrus);

        btnUloz.setOnAction(e -> {
            try {
                String meno = tfMeno.getText().trim();
                String priezvisko = tfPriezvisko.getText().trim();
                Date datum = java.sql.Date.valueOf(dpDatumNarodenia.getValue());
                String uuid = tfUUID.getText().trim();

                if (meno.isEmpty() || priezvisko.isEmpty())
                    throw new IllegalArgumentException("Meno a priezvisko musia byť vyplnené.");

                Osoba osoba = new Osoba(meno, priezvisko, datum, uuid);
                onSave.accept(osoba);
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
