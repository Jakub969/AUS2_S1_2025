package Data.PCR_Test;

import Interface.IBST_Key;

import java.util.Date;

public class PCR_TestDatumWrapper implements IBST_Key<PCR_TestDatumWrapper> {
    private final PCR_Test test;

    public PCR_TestDatumWrapper(PCR_Test test) {
        this.test = test;
    }

    public PCR_Test getTest() {
        return this.test;
    }


    @Override
    public int compareTo(IBST_Key<PCR_TestDatumWrapper> object) {
        if (object instanceof PCR_TestDatumWrapper other) {
            int result = this.test.getDatumACasTestu().compareTo(other.getTest().getDatumACasTestu());
            if (result == 0) {
                return Integer.signum(this.test.getUUIDOsoby().compareTo(other.getTest().getUUIDOsoby()));
            }
            return result;
        } else {
            throw new IllegalArgumentException("Object is not Data.PCR_Test.PCR_TestDatumWrapper type.");
        }
    }
}
