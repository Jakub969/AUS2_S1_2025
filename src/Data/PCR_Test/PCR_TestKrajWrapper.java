package Data.PCR_Test;

import Interface.IBST_Key;

public class PCR_TestKrajWrapper implements IBST_Key<PCR_TestKrajWrapper> {
    private PCR_Test test;

    public PCR_TestKrajWrapper(PCR_Test test) {
        this.test = test;
    }

    public PCR_Test getTest() {
        return this.test;
    }

    @Override
    public int compareTo(IBST_Key<PCR_TestKrajWrapper> object) {
        if (object instanceof PCR_TestKrajWrapper other) {
            return Integer.compare(this.test.getKodKraja(), other.test.getKodKraja());
        } else {
            throw new IllegalArgumentException("Object is not Data.PCR_Test.PCR_TestKrajWrapper type.");
        }
    }
}
