package Data.PCR_Test;

import Interface.IBST_Key;

public class PCR_TestUUIDOsobyKodTestuWrapper implements IBST_Key<PCR_TestUUIDOsobyKodTestuWrapper> {
    private final PCR_Test test;

    public PCR_TestUUIDOsobyKodTestuWrapper(PCR_Test test) {
        this.test = test;
    }

    public PCR_Test getTest() {
        return this.test;
    }

    @Override
    public int compareTo(IBST_Key<PCR_TestUUIDOsobyKodTestuWrapper> object) {
        if (object instanceof PCR_TestUUIDOsobyKodTestuWrapper other) {
            int cmp = Integer.signum(this.test.getUUIDOsoby().compareTo(other.getTest().getUUIDOsoby()));
            if (cmp == 0) {
                return Integer.compare(this.test.getKodPCR(), other.getTest().getKodPCR());
            }
            return cmp;
        } else {
            throw new IllegalArgumentException("Object is not PCR_TestUUIDOsobyKodTestuWrapper type.");
        }
    }
}
