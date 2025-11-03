package Data.PCR_Test;

import Interface.IBST_Key;

public class PCR_TestDatumOkresWrapper implements IBST_Key<PCR_TestDatumOkresWrapper> {
    private final PCR_Test test;

    public PCR_TestDatumOkresWrapper(PCR_Test test) {
        this.test = test;
    }

    public PCR_Test getTest() {
        return this.test;
    }

    @Override
    public int compareTo(IBST_Key<PCR_TestDatumOkresWrapper> object) {
        if (object instanceof PCR_TestDatumOkresWrapper other) {
            int cmp = this.test.getDatumACasTestu().compareTo(other.getTest().getDatumACasTestu());
            if (cmp == 0) {
                int result = Integer.compare(this.test.getKodOkresu(), other.getTest().getKodOkresu());
                if (result == 0) {
                    return Integer.signum(this.test.getUUIDOsoby().compareTo(other.getTest().getUUIDOsoby()));
                }
                return result;
            }
            return cmp;
        } else {
            throw new IllegalArgumentException("Object is not Data.PCR_Test.PCR_TestDatumOkresWrapper type.");
        }
    }
}
