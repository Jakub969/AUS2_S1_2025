public class GenerateData implements IBST_Key<GenerateData>{
    private final int integer;

    public GenerateData(int integer) {
        this.integer = integer;
    }

    public int getInteger() {
        return integer;
    }

    @Override
    public int compareTo(IBST_Key object) {
        if (object instanceof GenerateData) {
            GenerateData other = (GenerateData) object;
            return Integer.compare(this.integer, other.integer);
        } else {
            throw new IllegalArgumentException("Object is not of type GenerateData");
        }
    }
}
