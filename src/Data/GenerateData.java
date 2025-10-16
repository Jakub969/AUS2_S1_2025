package Data;

import Interface.IBST_Key;

public class GenerateData implements IBST_Key<GenerateData>, Comparable<GenerateData> {
    private final int integer;

    public GenerateData(int integer) {
        this.integer = integer;
    }

    public int getInteger() {
        return this.integer;
    }

    @Override
    public int compareTo(IBST_Key<GenerateData> object) {
        if (object instanceof GenerateData other) {
            return Integer.compare(this.integer, other.integer);
        } else {
            throw new IllegalArgumentException("Object is not Data.GenerateData type.");
        }
    }

    @Override
    public String toString() {
        return Integer.toString(this.integer);
    }

    @Override
    public int compareTo(GenerateData o) {
        if (o instanceof GenerateData other) {
            return Integer.compare(this.integer, other.integer);
        } else {
            throw new IllegalArgumentException("Object is not Data.GenerateData type.");
        }
    }
}
