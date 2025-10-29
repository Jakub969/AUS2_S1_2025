package Data;

import Interface.IBST_Key;

import java.util.Date;

public class PCR_Test implements IBST_Key<PCR_Test> {
    private Date datumACasTestu;
    private String UUIDOsoby;
    private int kodPCR;
    private int UUIDPracoviska;
    private int kodOkresu;
    private int kodKraja;
    private boolean vysledokTestu; // true = pozitivny, false = negativny
    private double hodnotaTestu;
    private String poznamka;

    public PCR_Test(Date datumACasTestu, String UUIDOsoby, int kodPCR, int UUIDPracoviska,
                    int kodOkresu, int kodKraja, boolean vysledokTestu,
                    double hodnotaTestu, String poznamka) {
        this.datumACasTestu = datumACasTestu;
        this.UUIDOsoby = UUIDOsoby;
        this.kodPCR = kodPCR;
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

    public int getKodPCR() {
        return this.kodPCR;
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

    @Override
    public int compareTo(IBST_Key<PCR_Test> object) {
        if (this.kodPCR == ((PCR_Test) object).getKodPCR()) {
            return 0;
        }
        return 0;
    }
}
