package GUI.Controller;

import GUI.Forms.OsobaForm;
import GUI.Forms.PCR_TestForm;
import GUI.Model.Model;
import GUI.View.View;
import Data.Osoba.Osoba;
import Data.PCR_Test.PCR_Test;
import DS.AVL.AVL_Node;

import javafx.application.Platform;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * MVC Controller pre JavaFX View a Model.
 * - View volá verejné metódy tohto controlleru zo svojich handlerov.
 * - Controller validuje vstupy, zavolá Model a výsledky vypíše do View.getOutputArea().
 */
public class Controller {
    private final Model model;
    private final View view;

    private static final SimpleDateFormat DT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat D = new SimpleDateFormat("yyyy-MM-dd");

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    // ====== File actions ======
    public void onGenerate(int n) {
        var out = view.getOutputArea();
        out.appendText("Generujem " + n + " záznamov...\n");
        try {
            // TODO: doplň podľa tvojej implementácie generátora dát, napr. model.vygenerujData(n);
            out.appendText("(TODO) Generátor dát ešte nie je implementovaný.\n");
        } catch (Exception e) {
            out.appendText("Chyba pri generovaní: " + e.getMessage() + "\n");
        }
    }

    public void onSaveCSV(String basePathWithoutExt) {
        var out = view.getOutputArea();
        try {
            model.ulozDoCSV(basePathWithoutExt);
            out.appendText("Dáta uložené do: " + basePathWithoutExt + "_osoby.csv a " + basePathWithoutExt + "_testy.csv\n");
        } catch (Exception e) {
            out.appendText("Chyba pri ukladaní CSV: " + e.getMessage() + "\n");
        }
    }

    public void onLoadCSV(String basePathWithoutExt) {
        var out = view.getOutputArea();
        try {
            model.nacitajZCSV(basePathWithoutExt);
            out.appendText("Dáta načítané z: " + basePathWithoutExt + "_osoby.csv a " + basePathWithoutExt + "_testy.csv\n");
        } catch (Exception e) {
            out.appendText("Chyba pri načítaní CSV: " + e.getMessage() + "\n");
        }
    }

    // ====== Hlavný vstup pre spúšťanie operácií 1..21 ======
    /**
     * View by malo zavolať túto metódu so stringovým názvom operácie (presne ako je vo View) a doplnkovými parametrami.
     * Nepoužívame JavaFX uzly z View priamo – View nám posiela už spracované hodnoty.
     */
    public void onOperation(String opLabel,
                            Date datumOd, Date datumDo,
                            Integer kodOkresu, Integer kodKraja, Integer kodPracoviska,
                            Integer dniChoroby,
                            String pacientUUID) {
        var out = view.getOutputArea();
        out.appendText("Spustená operácia: " + opLabel + "\n");

        try {
            int op = parseOpNumber(opLabel);
            switch (op) {
                case 1 -> op1_pridanieTestu();
                case 2 -> op2_vyhladajVysledokTestu(pacientUUID); // podľa tvojho Modelu môžeš upraviť parametre
                case 3 -> op3_vypisTestyPreOsobu(pacientUUID);
                case 4 -> op4_5(true, kodOkresu, datumOd, datumDo);
                case 5 -> op4_5(false, kodOkresu, datumOd, datumDo);
                case 6 -> op6_7(true, kodKraja, datumOd, datumDo);
                case 7 -> op6_7(false, kodKraja, datumOd, datumDo);
                case 8 -> op8_9(true, datumOd, datumDo);
                case 9 -> op8_9(false, datumOd, datumDo);
                case 10 -> op10(datumOd, dniChoroby, kodOkresu);
                case 11 -> op11(datumOd, dniChoroby, kodOkresu);
                case 12 -> op12(datumOd, dniChoroby, kodKraja);
                case 13 -> op13(datumOd, dniChoroby);
                case 14 -> op14(datumOd, dniChoroby);
                case 15 -> op15(datumOd, dniChoroby);
                case 16 -> op16(datumOd, dniChoroby);
                case 17 -> op17(kodPracoviska, datumOd, datumDo);
                case 18 -> op18(); // podľa potreby doplň vstup pre kód PCR
                case 19 -> op19();
                // Operácie 1,19,20,21 si typicky vyžadujú extra formuláre; nechávam TODO.
                default -> out.appendText("Táto operácia nie je v controllery spracovaná alebo vyžaduje špeciálny formulár.\n");
            }
        } catch (IllegalArgumentException iae) {
            out.appendText("Chybné alebo chýbajúce parametre: " + iae.getMessage() + "\n");
        } catch (Exception e) {
            out.appendText("Neočekávaná chyba: " + e.getMessage() + "\n");
        }
    }

    private int parseOpNumber(String label) {
        // očakáva formát "X. ..."
        int dot = label.indexOf('.');
        if (dot <= 0) throw new IllegalArgumentException("Neplatný názov operácie: " + label);
        return Integer.parseInt(label.substring(0, dot).trim());
    }

    // ====== Implementácie vybraných operácií s výstupom ======

    private void op1_pridanieTestu() {
        new PCR_TestForm(test -> {
            AVL_Node<PCR_Test> node = new AVL_Node<>(test, test);
            model.vlozPCRTest(node);
            view.getOutputArea().appendText("PCR test pridaný: kód " + test.getKodPCR() + "\n");
        }).show();

    }

    private void op3_vypisTestyPreOsobu(String uuid) {
        require(uuid != null && !uuid.isEmpty(), "Zadaj UUID pacienta.");
        Osoba dummy = new Osoba("", "", new Date(0), uuid);
        ArrayList<PCR_Test> tests = model.vyhladajVsetkyTestyPreOsobu(new AVL_Node<>(dummy, dummy));
        printTests(tests);
    }

    private void op2_vyhladajVysledokTestu(String uuid) {
        // Poznámka: podľa tvojho Modelu je op2 definovaná cez AVL_Node<PCR_Test> s kľúčom kodPCR.
        view.getOutputArea().appendText("Op2: doplň vo View zadanie kódu PCR a tu vytvor dummy PCR_Test s tým kódom.\n");
    }

    private void op4_5(boolean pozitivne, Integer kodOkresu, Date od, Date to) {
        require(kodOkresu != null, "Zadaj kód okresu.");
        require(od != null && to != null, "Zadaj dátum od/do.");
        ArrayList<PCR_Test> res = pozitivne
                ? model.vyhladajPozitivneTestyPreDatumAOkres(kodOkresu, od, to)
                : model.vyhladajTestyPreDatumAOkres(kodOkresu, od, to);
        printTests(res);
    }

    private void op6_7(boolean pozitivne, Integer kodKraja, Date od, Date to) {
        require(kodKraja != null, "Zadaj kód kraja.");
        require(od != null && to != null, "Zadaj dátum od/do.");
        ArrayList<PCR_Test> res = pozitivne
                ? model.vyhladajPozitivneTestyPreDatumAKraj(kodKraja, od, to)
                : model.vyhladajTestyPreDatumAKraj(kodKraja, od, to);
        printTests(res);
    }

    private void op8_9(boolean pozitivne, Date od, Date to) {
        require(od != null && to != null, "Zadaj dátum od/do.");
        ArrayList<PCR_Test> res = pozitivne
                ? model.vyhladajPozitivneTestyPreDatum(od, to)
                : model.vyhladajTestyPreDatum(od, to);
        printTests(res);
    }

    private void op10(Date datum, Integer dni, Integer okres) {
        require(datum != null && dni != null && okres != null, "Zadaj dátum, dni a okres.");
        ArrayList<Osoba> res = model.vypisChorychOsobPreDatumAOkres(okres, datum, dni);
        printOsoby(res);
    }

    private void op11(Date datum, Integer dni, Integer okres) {
        require(datum != null && dni != null && okres != null, "Zadaj dátum, dni a okres.");
        ArrayList<Osoba> res = model.vypisChorychOsobPreDatumAOkresUsporiadaneHodnotouTestu(okres, datum, dni);
        printOsoby(res);
    }

    private void op12(Date datum, Integer dni, Integer kraj) {
        require(datum != null && dni != null && kraj != null, "Zadaj dátum, dni a kraj.");
        ArrayList<Osoba> res = model.vypisChorychOsobPreDatumAKraj(kraj, datum, dni);
        printOsoby(res);
    }

    private void op13(Date datum, Integer dni) {
        require(datum != null && dni != null, "Zadaj dátum a dni.");
        ArrayList<Osoba> res = model.vypisChorychOsobPreDatum(datum, dni);
        printOsoby(res);
    }

    private void op14(Date datum, Integer dni) {
        require(datum != null && dni != null, "Zadaj dátum a dni.");
        ArrayList<Osoba> res = model.vypisOsobyPreDatumKazdyOkresSNajvyssouHodnotouTestu(datum, dni);
        printOsoby(res);
    }

    private void op15(Date datum, Integer dni) {
        require(datum != null && dni != null, "Zadaj dátum a dni.");
        var res = model.vypisOkresovUsporiadanychPoctomChorychPreDatum(datum, dni);
        var out = view.getOutputArea();
        res.forEach(h -> out.appendText("Okres=" + h.getKod() + ", chorí=" + h.getPocetChorych() + "\n"));
    }

    private void op16(Date datum, Integer dni) {
        require(datum != null && dni != null, "Zadaj dátum a dni.");
        var res = model.vypisKrajovUsporiadanychPoctomChorychPreDatum(datum, dni);
        var out = view.getOutputArea();
        res.forEach(h -> out.appendText("Kraj=" + h.getKod() + ", chorí=" + h.getPocetChorych() + "\n"));
    }

    private void op17(Integer prac, Date od, Date to) {
        require(prac != null && od != null && to != null, "Zadaj kód pracoviska a dátum od/do.");
        ArrayList<PCR_Test> res = model.vypisTestovPreDatumAPracovisko(prac, od, to);
        printTests(res);
    }

    private void op18() {
        view.getOutputArea().appendText("Op18: doplň vo View vstup na kód PCR a zavolaj model.vyhladaniePCRTestu(...).\n");
    }

    private void op19() {
        new OsobaForm(osoba -> {
            AVL_Node<Osoba> node = new AVL_Node<>(osoba, osoba);
            model.vlozOsobu(node);
            view.getOutputArea().appendText("Osoba pridaná: " + osoba.getMeno() + " " + osoba.getPriezvisko() + "\n");
        }).show();
    }

    // ====== Pomocné výstupy ======
    private void printTests(ArrayList<PCR_Test> tests) {
        var out = view.getOutputArea();
        out.appendText("Počet záznamov: " + (tests == null ? 0 : tests.size()) + "\n");
        if (tests == null) return;
        for (PCR_Test t : tests) {
            out.appendText(DT.format(t.getDatumACasTestu()) + ", UUID=" + t.getUUIDOsoby() +
                    ", kodPCR=" + t.getKodPCR() + ", okres=" + t.getKodOkresu() + ", kraj=" + t.getKodKraja() +
                    ", vysl=" + t.isVysledokTestu() + ", hodnota=" + t.getHodnotaTestu() +
                    (t.getPoznamka() == null || t.getPoznamka().isEmpty() ? "" : ", pozn.: " + t.getPoznamka()) +
                    "\n");
        }
    }

    private void printOsoby(ArrayList<Osoba> osoby) {
        var out = view.getOutputArea();
        out.appendText("Počet osôb: " + (osoby == null ? 0 : osoby.size()) + "\n");
        if (osoby == null) return;
        for (Osoba o : osoby) {
            out.appendText(o.getUUID() + ": " + o.getMeno() + " " + o.getPriezvisko() +
                    ", nar.: " + (o.getDatumNarodenia() == null ? "?" : D.format(o.getDatumNarodenia())) + "\n");
        }
    }

    private static void require(boolean cond, String msg) {
        if (!cond) throw new IllegalArgumentException(msg);
    }
}
