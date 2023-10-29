package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CompressedRowMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;

import java.util.List;

public class CompressedRowMatrixBuilder {

    public CompressedRowMatrix convertToCRS(List<Coordinate> coordinates, double rows, double cols) {
        int[] values = new int[coordinates.size()];
        int[] rowPointer = new int[(int) (rows + 1)];
        int[] columnIndex = new int[coordinates.size()];

        // Inicializa rowPointer con 0s
        for (int i = 0; i <= rows; i++) {
            rowPointer[i] = 0;
        }

        // Cuenta la cantidad de elementos por fila
        for (Coordinate coord : coordinates) {
            rowPointer[(int) (coord.getRow() + 1)]++;
        }

        // Actualiza rowPointer con los índices iniciales
        for (int i = 1; i <= rows; i++) {
            rowPointer[i] += rowPointer[i - 1];
        }

        // Copia los valores y columnas a sus posiciones correspondientes
        int[] rowCurrent = new int[(int) rows];
        for (Coordinate coord : coordinates) {
            double row = coord.getRow();
            int position = rowPointer[(int) row] + rowCurrent[(int) row];

            values[position] = Math.toIntExact((long) coord.getValue());
            columnIndex[position] = (int) coord.getCol();

            rowCurrent[(int) row]++;
        }

        return new CompressedRowMatrix(rows, values, rowPointer, columnIndex);
    }
}

