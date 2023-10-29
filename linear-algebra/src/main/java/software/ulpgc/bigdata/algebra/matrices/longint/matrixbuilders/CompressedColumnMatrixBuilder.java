package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CompressedColumnMatrix;

import java.util.List;

public class CompressedColumnMatrixBuilder {

    public CompressedColumnMatrix convertToCCS(List<Coordinate> coordinates, double rows, double cols) {
        int[] values = new int[coordinates.size()];
        int[] colPointer = new int[(int) (cols + 1)];
        int[] rowIndex = new int[coordinates.size()];

        for (int i = 0; i <= cols; i++) {
            colPointer[i] = 0;
        }

        for (Coordinate coord : coordinates) {
            colPointer[(int) coord.getCol()]++;
        }

        for (int i = 1; i <= cols; i++) {
            colPointer[i] = colPointer[i - 1];
        }

        for (Coordinate coord : coordinates) {
            double col = coord.getCol();
            int position = colPointer[(int) col];

            values[position] = Math.toIntExact((long) coord.getValue());
            rowIndex[position] = (int) coord.getRow();

            colPointer[(int) col]++;
        }

        return new CompressedColumnMatrix(cols, values, colPointer, rowIndex);
    }
}
