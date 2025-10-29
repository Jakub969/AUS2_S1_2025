package Data.PCR_Test;

import Interface.IBST_Key;

import java.util.Date;

public class PCR_TestDatumWrapper implements IBST_Key<PCR_TestDatumWrapper> {
    private PCR_Test test;

    public PCR_TestDatumWrapper(PCR_Test test) {
        this.test = test;
    }

    public PCR_Test getTest() {
        return this.test;
    }


    @Override
    public int compareTo(IBST_Key<PCR_TestDatumWrapper> object) {
        if (object instanceof PCR_TestDatumWrapper other) {
            return this.test.getDatumACasTestu().compareTo(other.getTest().getDatumACasTestu());
        } else {
            throw new IllegalArgumentException("Object is not Data.PCR_Test.PCR_TestDatumWrapper type.");
        }
    }
}
