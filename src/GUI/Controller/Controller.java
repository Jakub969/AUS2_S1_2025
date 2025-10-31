package GUI.Controller;

import GUI.Forms.OsobaForm;
import GUI.Forms.PCR_TestForm;
import GUI.Model.Model;
import GUI.View.View;
import Data.Osoba.Osoba;
import Data.PCR_Test.PCR_Test;

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

    public void onGenerate(int n) {
        var out = this.view.getOutputArea();
        out.appendText("Generujem " + n + " záznamov...\n");
        try {
            this.model.generujData(n);
            out.appendText("Vygenerovaných " + n + " záznamov \n");
        } catch (Exception e) {
            out.appendText("Chyba pri generovaní: " + e.getMessage() + "\n");
        }
    }

    public void onSaveCSV(String basePathWithoutExt) {
        var out = this.view.getOutputArea();
        try {
            this.model.ulozDoCSV(basePathWithoutExt);
            out.appendText("Dáta uložené do: " + basePathWithoutExt + "_osoby.csv a " + basePathWithoutExt + "_testy.csv\n");
        } catch (Exception e) {
            out.appendText("Chyba pri ukladaní CSV: " + e.getMessage() + "\n");
        }
    }

    public void onLoadCSV(String basePathWithoutExt) {
        var out = this.view.getOutputArea();
        try {
            this.model.nacitajZCSV(basePathWithoutExt);
            out.appendText("Dáta načítané z: " + basePathWithoutExt + "_osoby a " + basePathWithoutExt + "_testy\n");
        } catch (Exception e) {
            out.appendText("Chyba pri načítaní CSV: " + e.getMessage() + "\n");
        }
    }

    /**
     * View by malo zavolať túto metódu so stringovým názvom operácie (presne ako je vo View) a doplnkovými parametrami.
     * Nepoužívajú sa JavaFX uzly z View priamo – View nám posiela už spracované hodnoty.
     */
    public void onOperation(String opLabel,
                            Date datumOd, Date datumDo,
                            Integer kodOkresu, Integer kodKraja, Integer kodPracoviska,
                            Integer dniChoroby,
                            String pacientUUID, Integer kodTestu) {
        var out = this.view.getOutputArea();
        out.appendText("Spustená operácia: " + opLabel + "\n");

        try {
            int op = parseOpNumber(opLabel);
            switch (op) {
                case 1 -> op1_pridanieTestu();
                case 2 -> op2_vyhladajVysledokTestu(kodTestu,pacientUUID);
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
                case 18 -> op18(kodTestu);
                case 19 -> op19();
                case 20 -> op20(kodTestu);
                case 21 -> op21(pacientUUID);
                default -> out.appendText("Táto operácia nie je v controllery spracovaná alebo vyžaduje špeciálny formulár.\n");
            }
        } catch (IllegalArgumentException iae) {
            out.appendText("Chybné alebo chýbajúce parametre: " + iae.getMessage() + "\n");
        } catch (Exception e) {
            out.appendText("Neočekávaná chyba: " + e.getMessage() + "\n");
        }
    }

    private int parseOpNumber(String label) {
        int dot = label.indexOf('.');
        if (dot <= 0) throw new IllegalArgumentException("Neplatný názov operácie: " + label);
        return Integer.parseInt(label.substring(0, dot).trim());
    }

    //Implementácie vybraných operácií s výstupom
    private void op1_pridanieTestu() {
        new PCR_TestForm(test -> {
            this.model.vlozPCRTest(test.getDatumACasTestu(), test.getUUIDOsoby(), test.getKodPCR(),
                    test.getUUIDPracoviska(), test.getKodOkresu(), test.getKodKraja(),
                    test.isVysledokTestu(), test.getHodnotaTestu(), test.getPoznamka());
            this.view.getOutputArea().appendText("PCR test pridaný: kód " + test.getKodPCR() + "\n");
        }).show();

    }

    private void op2_vyhladajVysledokTestu(Integer kodTestu, String uuid) {
        require(kodTestu != null && uuid != null, "Zadaj kod testu a UUID pacienta.");
        PCR_Test test = this.model.vyhladajVysledokPCRTestu(kodTestu , uuid);
        if (test != null) {
            var out = this.view.getOutputArea();
            out.appendText("Nájdený test: " + test + "\n");
        } else {
            this.view.getOutputArea().appendText("Test nebol nájdený.\n");
        }
    }

    private void op3_vypisTestyPreOsobu(String uuid) {
        require(uuid != null && !uuid.isEmpty(), "Zadaj UUID pacienta.");
        ArrayList<PCR_Test> tests = this.model.vyhladajVsetkyTestyPreOsobu(uuid);
        printTests(tests);
    }

    private void op4_5(boolean pozitivne, Integer kodOkresu, Date od, Date to) {
        require(kodOkresu != null, "Zadaj kód okresu.");
        require(od != null && to != null, "Zadaj dátum od/do.");
        ArrayList<PCR_Test> res = pozitivne
                ? this.model.vyhladajPozitivneTestyPreDatumAOkres(kodOkresu, od, to)
                : this.model.vyhladajTestyPreDatumAOkres(kodOkresu, od, to);
        printTests(res);
    }

    private void op6_7(boolean pozitivne, Integer kodKraja, Date od, Date to) {
        require(kodKraja != null, "Zadaj kód kraja.");
        require(od != null && to != null, "Zadaj dátum od/do.");
        ArrayList<PCR_Test> res = pozitivne
                ? this.model.vyhladajPozitivneTestyPreDatumAKraj(kodKraja, od, to)
                : this.model.vyhladajTestyPreDatumAKraj(kodKraja, od, to);
        printTests(res);
    }

    private void op8_9(boolean pozitivne, Date od, Date to) {
        require(od != null && to != null, "Zadaj dátum od/do.");
        ArrayList<PCR_Test> res = pozitivne
                ? this.model.vyhladajPozitivneTestyPreDatum(od, to)
                : this.model.vyhladajTestyPreDatum(od, to);
        printTests(res);
    }

    private void op10(Date datum, Integer dni, Integer okres) {
        require(datum != null && dni != null && okres != null, "Zadaj dátum, dni a okres.");
        ArrayList<Osoba> res = this.model.vypisChorychOsobPreDatumAOkres(okres, datum, dni);
        printOsoby(res);
    }

    private void op11(Date datum, Integer dni, Integer okres) {
        require(datum != null && dni != null && okres != null, "Zadaj dátum, dni a okres.");
        ArrayList<Osoba> res = this.model.vypisChorychOsobPreDatumAOkresUsporiadaneHodnotouTestu(okres, datum, dni);
        printOsoby(res);
    }

    private void op12(Date datum, Integer dni, Integer kraj) {
        require(datum != null && dni != null && kraj != null, "Zadaj dátum, dni a kraj.");
        ArrayList<Osoba> res = this.model.vypisChorychOsobPreDatumAKraj(kraj, datum, dni);
        printOsoby(res);
    }

    private void op13(Date datum, Integer dni) {
        require(datum != null && dni != null, "Zadaj dátum a dni.");
        ArrayList<Osoba> res = this.model.vypisChorychOsobPreDatum(datum, dni);
        printOsoby(res);
    }

    private void op14(Date datum, Integer dni) {
        require(datum != null && dni != null, "Zadaj dátum a dni.");
        ArrayList<Osoba> res = this.model.vypisOsobyPreDatumKazdyOkresSNajvyssouHodnotouTestu(datum, dni);
        printOsoby(res);
    }

    private void op15(Date datum, Integer dni) {
        require(datum != null && dni != null, "Zadaj dátum a dni.");
        var res = this.model.vypisOkresovUsporiadanychPoctomChorychPreDatum(datum, dni);
        var out = this.view.getOutputArea();
        res.forEach(h -> out.appendText("Okres=" + h.getKod() + ", chorí=" + h.getPocetChorych() + "\n"));
    }

    private void op16(Date datum, Integer dni) {
        require(datum != null && dni != null, "Zadaj dátum a dni.");
        var res = this.model.vypisKrajovUsporiadanychPoctomChorychPreDatum(datum, dni);
        var out = this.view.getOutputArea();
        res.forEach(h -> out.appendText("Kraj=" + h.getKod() + ", chorí=" + h.getPocetChorych() + "\n"));
    }

    private void op17(Integer prac, Date od, Date to) {
        require(prac != null && od != null && to != null, "Zadaj kód pracoviska a dátum od/do.");
        ArrayList<PCR_Test> res = this.model.vypisTestovPreDatumAPracovisko(prac, od, to);
        printTests(res);
    }

    private void op18(Integer kodTestu) {
        require(kodTestu != null, "Zadaj kód PCR testu.");
        PCR_Test test = this.model.vyhladaniePCRTestu(kodTestu);
        var out = this.view.getOutputArea();
        if (test != null) {
            out.appendText("Nájdený test: " + test + "\n");
        } else {
            out.appendText("Test nebol nájdený.\n");
        }
    }

    private void op19() {
        new OsobaForm(osoba -> {
            this.model.vlozOsobu(osoba.getMeno(), osoba.getPriezvisko(), osoba.getDatumNarodenia(), osoba.getUUID());
            this.view.getOutputArea().appendText("Osoba pridaná: " + osoba.getMeno() + " " + osoba.getPriezvisko() + "\n");
        }).show();
    }

    private void op20(int kodPCR) {
        this.model.vymazPCRTest(kodPCR);
        this.view.getOutputArea().appendText("PCR test s kódom " + kodPCR + " bol vymazaný.\n");
    }

    private void op21(String uuid) {
        require(uuid != null && !uuid.isEmpty(), "Zadaj UUID osoby.");
        this.model.vymazOsobu(uuid);
        this.view.getOutputArea().appendText("Osoba s UUID " + uuid + " bola vymazaná.\n");
    }
    //Pomocné výstupy
    private void printTests(ArrayList<PCR_Test> tests) {
        var out = this.view.getOutputArea();
        out.appendText("Počet záznamov: " + (tests == null ? 0 : tests.size()) + "\n");
        if (tests == null) return;
        for (PCR_Test t : tests) {
            out.appendText(DT.format(t.getDatumACasTestu()) + ", UUID=" + t.getUUIDOsoby() +
                    ", kodPCR=" + t.getKodPCR() + ", okres=" + t.getKodOkresu() + ", kraj=" + t.getKodKraja() +
                    ", pozitívny=" + t.isVysledokTestu() + ", hodnota=" + t.getHodnotaTestu() +
                    (t.getPoznamka() == null || t.getPoznamka().isEmpty() ? "" : ", pozn.: " + t.getPoznamka()) +
                    "\n");
        }
    }

    private void printOsoby(ArrayList<Osoba> osoby) {
        var out = this.view.getOutputArea();
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
