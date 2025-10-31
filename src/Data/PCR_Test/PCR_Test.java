package Data.PCR_Test;

import Interface.IBST_Key;

import java.text.ParseException;
import java.util.Date;

public class PCR_Test implements IBST_Key<PCR_Test> {
    private final Date datumACasTestu;
    private final String UUIDOsoby;
    private final int kodPCR;
    private final int UUIDPracoviska;
    private final int kodOkresu;
    private final int kodKraja;
    private final boolean vysledokTestu; // true = pozitivny, false = negativny
    private final double hodnotaTestu;
    private final String poznamka;

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
        if (object instanceof PCR_Test other) {
            return Integer.compare(this.kodPCR, other.kodPCR);
        } else {
            throw new IllegalArgumentException("Object is not Data.PCR_Test.PCR_Test type.");
        }
    }

    @Override
    public String toString() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(this.datumACasTestu) + "," +
                this.UUIDOsoby + "," +
                this.kodPCR + "," +
                this.UUIDPracoviska + "," +
                this.kodOkresu + "," +
                this.kodKraja + "," +
                this.vysledokTestu + "," +
                this.hodnotaTestu + "," +
                (this.poznamka == null ? "" : this.poznamka.replace(",", ";"));
    }

}
