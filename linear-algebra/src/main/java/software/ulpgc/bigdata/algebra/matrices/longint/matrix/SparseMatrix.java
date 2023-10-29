package software.ulpgc.bigdata.algebra.matrices.longint.matrix;
import java.util.ArrayList;
import java.util.List;

public class SparseMatrix{
    protected final List<Coordinate> coordinates;

    public SparseMatrix() {
        this.coordinates = new ArrayList<>();
    }

    public void set(double i, double j, double value) {
        coordinates.add(new Coordinate(i, j, value));
    }
}
