package Data.PCR_Test;

import Interface.IBST_Key;

public class PCR_TestDatumPracoviskoWrapper implements IBST_Key<PCR_TestDatumPracoviskoWrapper> {
    private final PCR_Test test;

    public PCR_TestDatumPracoviskoWrapper(PCR_Test test) {
        this.test = test;
    }

    public PCR_Test getTest() {
        return this.test;
    }

    @Override
    public int compareTo(IBST_Key<PCR_TestDatumPracoviskoWrapper> object) {
        if (object instanceof PCR_TestDatumPracoviskoWrapper other) {
            int cmp = Integer.compare(this.test.getUUIDPracoviska(), other.getTest().getUUIDPracoviska());
            if (cmp == 0) {
                int result = this.test.getDatumACasTestu().compareTo(other.getTest().getDatumACasTestu());
                if (result == 0) {
                    return Integer.signum(this.test.getUUIDOsoby().compareTo(other.getTest().getUUIDOsoby()));
                }
                return result;
            }
            return cmp;
        } else {
            throw new IllegalArgumentException("Object is not Data.PCR_Test.PCR_TestDatumPracoviskoWrapper type.");
        }
    }
}
