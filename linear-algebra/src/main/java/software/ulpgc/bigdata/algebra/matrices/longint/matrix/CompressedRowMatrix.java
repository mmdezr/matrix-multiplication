package software.ulpgc.bigdata.algebra.matrices.longint.matrix;

public class CompressedRowMatrix extends SparseMatrix {
    public final double size;
    public int[] values;
    public int[] rowPointer;
    public int[] columns; // Nueva adición: índices de columnas
    private final int[] columnIndex;

    public CompressedRowMatrix(double size, int[] values, int[] rowPointer, int[] columnIndex) {
        this.size = size;
        this.values = values;
        this.rowPointer = rowPointer;
        this.columnIndex = columnIndex;
        this.columns = calculateColumns(rowPointer, columnIndex); // Inicialización de 'columns'
    }

    // Método para calcular los índices de las columnas
    private int[] calculateColumns(int[] rowPointer, int[] columnIndex) {
        int[] columns = new int[columnIndex.length];
        int colIdx = 0;
        for (int i = 0; i < rowPointer.length - 1; i++) {
            int rowStart = rowPointer[i];
            int rowEnd = rowPointer[i + 1];
            for (int j = rowStart; j < rowEnd; j++) {
                columns[colIdx] = columnIndex[j];
                colIdx++;
            }
        }
        return columns;
    }
}

