package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

import java.util.Map;

public class DenseMatrix {
    private final Map<Integer, Map<Integer, Long>> values;
    public DenseMatrix(Map<Integer, Map<Integer, Long>> values, double size) {
        this.values = values;
    }
    public long get(int i, int j) {
        Map<Integer, Long> row = values.get(i);
        return row != null ? row.getOrDefault(j, 0L) : 0L;
    }

    public int size() {
        return values.size();
    }
}
