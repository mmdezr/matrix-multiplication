package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

public class Coordinate {
    private double i;
    private double j;
    private double value;

    public Coordinate(double i, double j, double value) {
        this.i = i;
        this.j = j;
        this.value = value;
    }

    public double getRow() {
        return i;
    }

    public void setRow(int i) {
        this.i = i;
    }

    public double getCol() {
        return j;
    }

    public void setCol(int j) {
        this.j = j;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
