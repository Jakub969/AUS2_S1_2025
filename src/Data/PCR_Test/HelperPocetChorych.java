package Data.PCR_Test;

public class HelperPocetChorych {
    private final int kod;
    private int pocetChorych;

    public HelperPocetChorych(int kod, int pocetChorych) {
        this.kod = kod;
        this.pocetChorych = pocetChorych;
    }

    public int getKod() {
        return this.kod;
    }

    public int getPocetChorych() {
        return this.pocetChorych;
    }

    public void incrementPocetChorych() {
        this.pocetChorych++;
    }
}
