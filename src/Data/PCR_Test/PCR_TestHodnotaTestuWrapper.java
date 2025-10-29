package Data.PCR_Test;

import Interface.IBST_Key;

public class PCR_TestHodnotaTestuWrapper implements IBST_Key<PCR_TestHodnotaTestuWrapper> {
    private PCR_Test test;

    public PCR_TestHodnotaTestuWrapper(PCR_Test test) {
        this.test = test;
    }

    public PCR_Test getTest() {
        return this.test;
    }

    @Override
    public int compareTo(IBST_Key<PCR_TestHodnotaTestuWrapper> object) {
        double tolerance = 0.0001;
        if (object instanceof PCR_TestHodnotaTestuWrapper other) {
            double diff = this.test.getHodnotaTestu() - other.test.getHodnotaTestu();
            if (Math.abs(diff) < tolerance) {
                return 0;
            } else if (diff < 0) {
                return -1;
            } else {
                return 1;
            }
        } else {
            throw new IllegalArgumentException("Object is not Data.PCR_Test.PCR_TestHodnotaTestuWrapper type.");
        }
    }
}
