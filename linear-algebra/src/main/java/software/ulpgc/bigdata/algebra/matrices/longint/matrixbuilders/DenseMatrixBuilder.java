package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.DenseMatrix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DenseMatrixBuilder {
    private final Map<Integer, Map<Integer, Long>> values;
    private final double size;

    public DenseMatrixBuilder(double size) {
        this.size = size;
        this.values = new HashMap<>();
    }

    public void set(double row, double col, double value) {
        values.computeIfAbsent((int) row, (Function<? super Integer, ? extends Map<Integer, Long>>) k -> new HashMap<>()).put((int) col, (long) value);
    }

    public Map<Integer, Map<Integer, Long>> getValues() {
        return values;
    }

    public static DenseMatrix convertToDenseMatrix(List<Coordinate> coordinates, double size) {
        DenseMatrixBuilder builder = new DenseMatrixBuilder(size);

        for (Coordinate coordinate : coordinates) {
            double row = coordinate.getRow() - 1; // Ajustar índices a partir de 1 a 0
            double col = coordinate.getCol() - 1;
            double value = coordinate.getValue();
            builder.set(row, col, value);
        }

        return new DenseMatrix(builder.getValues(), size);
    }
}

