package Data.PCR_Test;

import Interface.IBST_Key;

public class PCR_TestDatumKrajWrapper implements IBST_Key<PCR_TestDatumKrajWrapper> {
    private final PCR_Test test;

    public PCR_TestDatumKrajWrapper(PCR_Test test) {
        this.test = test;
    }

    public PCR_Test getTest() {
        return this.test;
    }


    @Override
    public int compareTo(IBST_Key<PCR_TestDatumKrajWrapper> object) {
        if (object instanceof PCR_TestDatumKrajWrapper other) {
            int cmp = this.test.getDatumACasTestu().compareTo(other.getTest().getDatumACasTestu());
            if (cmp == 0) {
                return Integer.compare(this.test.getKodKraja(), other.getTest().getKodKraja());
            }
            return cmp;
        } else {
            throw new IllegalArgumentException("Object is not Data.PCR_Test.PCR_TestDatumKrajWrapper type.");
        }
    }
}
