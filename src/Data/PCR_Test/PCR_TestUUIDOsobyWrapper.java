package Data.PCR_Test;

import Interface.IBST_Key;

public class PCR_TestUUIDOsobyWrapper implements IBST_Key<PCR_TestUUIDOsobyWrapper> {
    private final PCR_Test test;

    public PCR_TestUUIDOsobyWrapper(PCR_Test test) {
        this.test = test;
    }

    public PCR_Test getTest() {
        return this.test;
    }

    @Override
    public int compareTo(IBST_Key<PCR_TestUUIDOsobyWrapper> object) {
        if (object instanceof PCR_TestUUIDOsobyWrapper other) {
            return this.test.getUUIDOsoby().compareTo(other.getTest().getUUIDOsoby());
        } else {
            throw new IllegalArgumentException("Object is not PCR_TestUUIDOsobyWrapper type.");
        }
    }
}
