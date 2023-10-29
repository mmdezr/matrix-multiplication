package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

public class CompressedColumnMatrix extends SparseMatrix {
    public double size;
    public int[] values;
    public int[] colPointer;
    public int[] rows; // Se agrega rows
    private final int[] rowIndex;

    public CompressedColumnMatrix(double size, int[] values, int[] colPointer, int[] rowIndex) {
        this.size = size;
        this.values = values;
        this.colPointer = colPointer;
        this.rowIndex = rowIndex;
        this.rows = calculateRows(colPointer, rowIndex);
    }
    private int[] calculateRows(int[] colPointer, int[] rowIndex) {
        int[] rows = new int[rowIndex.length];
        int rowIdx = 0;
        for (int i = 0; i < colPointer.length - 1; i++) {
            int colStart = colPointer[i];
            int colEnd = colPointer[i + 1];
            for (int j = colStart; j < colEnd; j++) {
                rows[rowIdx] = rowIndex[j];
                rowIdx++;
            }
        }
        return rows;
    }
}
