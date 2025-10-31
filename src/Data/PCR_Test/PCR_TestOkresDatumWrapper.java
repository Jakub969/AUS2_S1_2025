package Data.PCR_Test;

import Interface.IBST_Key;

public class PCR_TestOkresDatumWrapper implements IBST_Key<PCR_TestOkresDatumWrapper> {
    private final PCR_Test test;

    public PCR_TestOkresDatumWrapper(PCR_Test test) {
        this.test = test;
    }

    public PCR_Test getTest() {
        return this.test;
    }

    @Override
    public int compareTo(IBST_Key<PCR_TestOkresDatumWrapper> object) {
        if (object instanceof PCR_TestOkresDatumWrapper other) {
            int cmp = Integer.compare(this.test.getKodOkresu(), other.getTest().getKodOkresu());
            if (cmp == 0) {
                return this.test.getDatumACasTestu().compareTo(other.getTest().getDatumACasTestu());
            }
            return cmp;
        } else {
            throw new IllegalArgumentException("Object is not PCR_TestOkresDatumWrapper type.");
        }
    }
}
