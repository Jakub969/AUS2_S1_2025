package Data;

import java.util.Date;

public class PCR_Test {
    private Date datumACasTestu;
    private String UUIDOsoby;
    private int idPCR;
    private int UUIDPracoviska;
    private int kodOkresu;
    private int kodKraja;
    private boolean vysledokTestu; // true = pozitivny, false = negativny
    private double hodnotaTestu;
    private String poznamka;

    public PCR_Test(Date datumACasTestu, String UUIDOsoby, int idPCR, int UUIDPracoviska,
                    int kodOkresu, int kodKraja, boolean vysledokTestu,
                    double hodnotaTestu, String poznamka) {
        this.datumACasTestu = datumACasTestu;
        this.UUIDOsoby = UUIDOsoby;
        this.idPCR = idPCR;
        this.UUIDPracoviska = UUIDPracoviska;
        this.kodOkresu = kodOkresu;
        this.kodKraja = kodKraja;
        this.vysledokTestu = vysledokTestu;
        this.hodnotaTestu = hodnotaTestu;
        this.poznamka = poznamka;
    }

    public Date getDatumACasTestu() {
        return this.datumACasTestu;
    }

    public String getUUIDOsoby() {
        return this.UUIDOsoby;
    }

    public int getIdPCR() {
        return this.idPCR;
    }

    public int getUUIDPracoviska() {
        return this.UUIDPracoviska;
    }

    public int getKodOkresu() {
        return this.kodOkresu;
    }

    public int getKodKraja() {
        return this.kodKraja;
    }

    public boolean isVysledokTestu() {
        return this.vysledokTestu;
    }

    public double getHodnotaTestu() {
        return this.hodnotaTestu;
    }

    public String getPoznamka() {
        return this.poznamka;
    }
}
