package Data.PCR_Test;

import Interface.IBST_Key;

public class PCR_TestKrajDatumWrapper implements IBST_Key<PCR_TestKrajDatumWrapper> {
    private PCR_Test test;

    public PCR_TestKrajDatumWrapper(PCR_Test test) {
        this.test = test;
    }

    public PCR_Test getTest() {
        return this.test;
    }


    @Override
    public int compareTo(IBST_Key<PCR_TestKrajDatumWrapper> object) {
        if (object instanceof PCR_TestKrajDatumWrapper other) {
            int cmp = Integer.compare(this.test.getKodKraja(), other.getTest().getKodKraja());
            if (cmp == 0) {
                return this.test.getDatumACasTestu().compareTo(other.getTest().getDatumACasTestu());
            }
            return cmp;
        } else {
            throw new IllegalArgumentException("Object is not PCR_TestOkresDatumWrapper type.");
        }
    }
}
