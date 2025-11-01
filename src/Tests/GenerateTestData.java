package Tests;

import Interface.IBST_Key;

public class GenerateTestData implements IBST_Key<GenerateTestData>, Comparable<GenerateTestData> {
    private final int integer;

    public GenerateTestData(int integer) {
        this.integer = integer;
    }

    public int getInteger() {
        return this.integer;
    }

    @Override
    public int compareTo(IBST_Key<GenerateTestData> object) {
        if (object instanceof GenerateTestData other) {
            return Integer.compare(this.integer, other.integer);
        } else {
            throw new IllegalArgumentException("Object is not Tests.GenerateTestData type.");
        }
    }

    @Override
    public String toString() {
        return Integer.toString(this.integer);
    }

    @Override
    public int compareTo(GenerateTestData o) {
        if (o instanceof GenerateTestData other) {
            return Integer.compare(this.integer, other.integer);
        } else {
            throw new IllegalArgumentException("Object is not Tests.GenerateTestData type.");
        }
    }
}
