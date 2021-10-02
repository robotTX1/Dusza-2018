package com.dusza;

import java.util.ArrayList;
import java.util.List;

public class Magic {
    private int[][] matrix;
    private ObjectType[][] ProcessedMatrix;
    private final int HEIGHT;
    private final int WIDTH;

    public Magic (int[][] matrix){
        this.matrix = matrix;
        HEIGHT = matrix.length;
        WIDTH = matrix[0].length;
        ProcessedMatrix = new ObjectType[HEIGHT][WIDTH];

    }

    private List<int[]> recursiveSearch(int wall, int x, int y) {
        List<int[]> out = new ArrayList<>();

        if (matrix[x][y] != wall) {

            if (x < WIDTH) out = recursiveSearch(wall, x+1, y);
            if ( x > 0) out = recursiveSearch(wall, x-1, y);
            if (y < HEIGHT) out = recursiveSearch(wall, x, y+1);
            if ( y > 0) out = recursiveSearch(wall, x, y-1);

            int[] coords = {x,y};
            out.add(coords);
        }
        return out;
    }

    private void findWalls() {
        List<int[]> coords = recursiveSearch(0,0,0);

        for (int[] c : coords) {
            ProcessedMatrix[c[0]][c[1]] = ObjectType.WALL;
            matrix[c[0]][c[1]] = 0;
        }

    }

    private void findRooms() {

    }
}
