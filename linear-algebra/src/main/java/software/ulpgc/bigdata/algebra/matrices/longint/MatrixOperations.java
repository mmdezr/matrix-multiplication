package software.ulpgc.bigdata.algebra.matrices.longint;

import org.openjdk.jmh.annotations.*;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.*;
import software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 3, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Fork(value = 1)
public class MatrixOperations {
    String filePath = "src/main/resources/mc2depi.mtx";
    CoordinateMatrixBuilder builder = new CoordinateMatrixBuilder();
    List<Coordinate> coordinates = builder.buildMatrix(filePath);
    double size = calculateMatrixSize(coordinates); // Calcula el tamaño de la matriz

    public static SparseMatrix multiply(CompressedRowMatrix a, CompressedColumnMatrix b) {
        SparseMatrixBuilder builder = new SparseMatrixBuilder(a.size);
        for (int i = 0; i < a.size; i++) {
            for (int j = 0; j < b.size; j++) {
                int ii = a.rowPointer[i];
                int iEnd = a.rowPointer[i + 1];
                int jj = b.colPointer[j];
                int jEnd = b.colPointer[j + 1];
                long s = 0;
                while (ii < iEnd & jj < jEnd) {
                    int aa = a.columns[ii];
                    int bb = b.rows[jj];
                    if (aa == bb) {
                        s += (long) a.values[ii] * b.values[jj];
                        ii++;
                        jj++;
                    } else if (aa < bb) {
                        ii++;
                    } else {
                        jj++;
                    }
                }
                if (s != 0) {
                    builder.set(i, j, s);
                }
            }
        }
        return builder.toMatrix();
    }

    public static SparseMatrix multiplyDense(DenseMatrix a) {
        int size = a.size();
        SparseMatrixBuilder builder = new SparseMatrixBuilder(size);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                long sum = 0;
                for (int k = 0; k < size; k++) {
                    sum += a.get(i, k) * a.get(k, j);
                }
                if (sum != 0) {
                    builder.set(i, j, sum);
                }
            }
        }

        return builder.toMatrix();
    }

    @Benchmark
    public SparseMatrix benchmarkMultiply() {
        double rows = calculateRowsFromCoordinates(coordinates);
        double cols = calculateColsFromCoordinates(coordinates);
        CompressedRowMatrixBuilder crsBuilder = new CompressedRowMatrixBuilder();
        CompressedColumnMatrixBuilder ccsBuilder = new CompressedColumnMatrixBuilder();
        CompressedRowMatrix crsMatrix = crsBuilder.convertToCRS(coordinates, rows, cols);
        CompressedColumnMatrix ccsMatrix = ccsBuilder.convertToCCS(coordinates, rows, cols);
        System.out.println(multiply(crsMatrix, ccsMatrix));
        return multiply(crsMatrix, ccsMatrix);
    }

    @Benchmark
    public SparseMatrix benchmarkMultiplyDense() {
        DenseMatrix denseMatrix = DenseMatrixBuilder.convertToDenseMatrix(coordinates, size);
        System.out.println(multiplyDense(denseMatrix));
        return multiplyDense(denseMatrix);
    }

    private static double calculateRowsFromCoordinates(List<Coordinate> coordinates) {
        double maxRow = 0;
        for (Coordinate coord : coordinates) {
            maxRow = Math.max(maxRow, coord.getRow());
        }
        return maxRow + 1; // Se suma 1 para obtener el número total de filas
    }

    private static double calculateColsFromCoordinates(List<Coordinate> coordinates) {
        double maxCol = 0;
        for (Coordinate coord : coordinates) {
            maxCol = Math.max(maxCol, coord.getCol());
        }
        return maxCol + 1; // Se suma 1 para obtener el número total de columnas
    }

    private static double calculateMatrixSize(List<Coordinate> coordinates) {
        double maxSize = 0;

        for (Coordinate coord : coordinates) {
            maxSize = Math.max(maxSize, Math.max(coord.getRow(), coord.getCol()) + 1); // Ajusta el valor al máximo índice de fila o columna
        }

        return maxSize;
    }
}
