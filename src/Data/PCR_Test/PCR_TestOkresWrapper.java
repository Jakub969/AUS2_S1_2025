package Data.PCR_Test;

import Interface.IBST_Key;

public class PCR_TestOkresWrapper implements IBST_Key<PCR_TestOkresWrapper> {
    private PCR_Test test;

    public PCR_TestOkresWrapper(PCR_Test test) {
        this.test = test;
    }

    public PCR_Test getTest() {
        return this.test;
    }


    @Override
    public int compareTo(IBST_Key<PCR_TestOkresWrapper> object) {
        if (object instanceof PCR_TestOkresWrapper other) {
            return Integer.compare(this.test.getKodOkresu(), other.test.getKodOkresu());
        } else {
            throw new IllegalArgumentException("Object is not Data.PCR_Test.PCR_TestOkresWrapper type.");
        }
    }
}
