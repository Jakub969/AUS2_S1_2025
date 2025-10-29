package Data.Osoba;

import Interface.IBST_Key;

import java.util.Date;

public class Osoba implements IBST_Key<Osoba> {
    private String meno;
    private String priezvisko;
    private Date datumNarodenia;
    private String UUID;

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
        return this.UUID.compareTo(((Osoba) object).getUUID());
    }
}
