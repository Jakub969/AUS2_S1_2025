package Data.Osoba;

import Interface.IBST_Key;

import java.text.ParseException;
import java.util.Date;

public class Osoba implements IBST_Key<Osoba> {
    private final String meno;
    private final String priezvisko;
    private final Date datumNarodenia;
    private final String UUID;

    public Osoba(String meno, String priezvisko, Date datumNarodenia, String UUID) {
        this.meno = meno;
        this.priezvisko = priezvisko;
        this.datumNarodenia = datumNarodenia;
        this.UUID = UUID;
    }

    public String getMeno() {
        return this.meno;
    }

    public String getPriezvisko() {
        return this.priezvisko;
    }

    public Date getDatumNarodenia() {
        return this.datumNarodenia;
    }

    public String getUUID() {
        return this.UUID;
    }

    @Override
    public int compareTo(IBST_Key<Osoba> object) {
        if (object instanceof Osoba other) {
            return Integer.signum(this.UUID.compareTo(other.getUUID()));
        } else {
            throw new IllegalArgumentException("Object is not Data.Osoba.Osoba type.");
        }
    }

    @Override
    public String toString() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return this.meno + "," + this.priezvisko + "," + sdf.format(this.datumNarodenia) + "," + this.UUID;
    }
}
