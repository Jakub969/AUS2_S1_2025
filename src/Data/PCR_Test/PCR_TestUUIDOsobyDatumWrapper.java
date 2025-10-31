package Data.PCR_Test;

import Interface.IBST_Key;

public class PCR_TestUUIDOsobyDatumWrapper implements IBST_Key<PCR_TestUUIDOsobyDatumWrapper> {
    private PCR_Test test;

    public PCR_TestUUIDOsobyDatumWrapper(PCR_Test test) {
        this.test = test;
    }

    public PCR_Test getTest() {
        return this.test;
    }

    @Override
    public int compareTo(IBST_Key<PCR_TestUUIDOsobyDatumWrapper> object) {
        if (object instanceof PCR_TestUUIDOsobyDatumWrapper other) {
            int cmp = this.test.getUUIDOsoby().compareTo(other.getTest().getUUIDOsoby());
            if (cmp == 0) {
                return this.test.getDatumACasTestu().compareTo(other.getTest().getDatumACasTestu());
            }
            return cmp;
        } else {
            throw new IllegalArgumentException("Object is not PCR_TestUUIDOsobyDatumWrapper type.");
        }
    }
}
