package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.SparseMatrix;

import java.util.ArrayList;
import java.util.List;

public class SparseMatrixBuilder{
    protected final double size;
    protected final List<Coordinate> coordinates;

    public SparseMatrixBuilder(double size) {
        this.size = size;
        this.coordinates = new ArrayList<>();
    }
    public void set(int i, int j, long value) {
        set(new Coordinate(i,j,value));
    }

    public void set(Coordinate coordinate) {
        coordinates.add(coordinate);
    }

    public SparseMatrix toMatrix() {
        SparseMatrix sparseMatrix = new SparseMatrix();
        for (Coordinate coordinate : coordinates) {
            double row = coordinate.getRow();
            double col = coordinate.getCol();
            double value = coordinate.getValue();
            sparseMatrix.set(row, col, value);
        }
        return sparseMatrix;
    }

}
