public class GenerateData implements IBST_Key<GenerateData>{
    private final int integer;

    public GenerateData(int integer) {
        this.integer = integer;
    }

    public int getInteger() {
        return integer;
    }

    @Override
    public int compareTo(IBST_Key<GenerateData> object) {
        if (object instanceof GenerateData other) {
            return Integer.compare(this.integer, other.integer);
        } else {
            throw new IllegalArgumentException("Object is not GenerateData type.");
        }
    }
}
