package software.ulpgc.bigdata.algebra.matrices.longint.matrixbuilders;

import software.ulpgc.bigdata.algebra.matrices.longint.matrix.Coordinate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoordinateMatrixBuilder {

    public List<Coordinate> buildMatrix(String filePath) {
        List<Coordinate> coordinates = new ArrayList<>();
        boolean isMatrixData = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("%")) {
                    continue; // Salta los comentarios
                }
                if (!isMatrixData) {
                    if (line.matches("\\d+\\.?\\d* \\d+\\.?\\d* \\d+\\.?\\d*")) {
                        isMatrixData = true;
                    }
                } else {
                    String[] parts = line.split(" ");
                    if (parts.length == 3) {
                        double row = Double.parseDouble(parts[0]);
                        double col = Double.parseDouble(parts[1]);
                        double value = Double.parseDouble(parts[2]);
                        coordinates.add(new Coordinate(row, col, value));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return coordinates;
    }


}
