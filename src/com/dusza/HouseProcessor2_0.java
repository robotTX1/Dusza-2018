package com.dusza;

import java.util.ArrayList;
import java.util.List;

public class HouseProcessor2_0 {
    private final int[][] matrix;
    private int[][] indexedMatrix;
    private final ObjectType[][] ProcessedMatrix;
    private final int HEIGHT;
    private final int WIDTH;

    public HouseProcessor2_0(int[][] matrix){
        this.matrix = matrix;
        HEIGHT = matrix.length;
        WIDTH = matrix[0].length;

        ProcessedMatrix = new ObjectType[HEIGHT][WIDTH];

        // remove empty places on the edges

        for (int x=0; x<WIDTH; x++) {
            roomOnTheEdgeOfMatrix(x,0);
        }
        for (int y=1; y<HEIGHT-1; y++) {
            roomOnTheEdgeOfMatrix(0,y);
            roomOnTheEdgeOfMatrix(WIDTH-1,y);
        }

        for (int x=0; x<WIDTH; x++) {
            roomOnTheEdgeOfMatrix(x, HEIGHT-1);
        }

        generateNewIndexMatrix();

    }

    private void roomOnTheEdgeOfMatrix(int x, int y){
        generateNewIndexMatrix();
        List<int[]> coords = recursiveSearch(1,x,y);
        for (int[] c : coords) {
            matrix[c[1]][c[0]] = 2;
        }

    }

    private void generateNewIndexMatrix() {
        indexedMatrix = new int[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                indexedMatrix[y][x] = matrix[y][x];
            }
        }
    }

    private List<int[]> recursiveSearch(int wall, int x, int y) {
        List<int[]> out = new ArrayList<>();

        //int test = matrix[y][x];

        if (matrix[y][x] != wall && matrix[y][x] != 2) {
            indexedMatrix[y][x] = -1;
            if (x < WIDTH-1 && (indexedMatrix[y][x+1] > -1)) out.addAll(recursiveSearch(wall, x+1, y));
            if ( x > 0 && (indexedMatrix[y][x-1] > -1)) out.addAll(recursiveSearch(wall, x-1, y));
            if (y < HEIGHT-1 && (indexedMatrix[y+1][x] > -1)) out.addAll(recursiveSearch(wall, x, y+1));
            if ( y > 0 && (indexedMatrix[y-1][x] > -1)) out.addAll(recursiveSearch(wall, x, y-1));

            int[] coords = {x,y};
            out.add(coords);
        }
        return out;
    }

    public void findWalls() {
        generateNewIndexMatrix();
        List<int[]> coords = recursiveSearch(0,0,0);

        for (int[] c : coords) {
            ProcessedMatrix[c[1]][c[0]] = ObjectType.WALL;
            matrix[c[1]][c[0]] = 0;
        }

    }

    public void findObjects() {
        for(int y = 0; y<HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (matrix[y][x] == 0 || matrix[y][x] == 2) continue;
                generateNewIndexMatrix();
                List<int[]> coords = recursiveSearch(0,x,y);
                int maxX = 0;
                int maxY = 0;
                int minX = Integer.MAX_VALUE;
                int minY = Integer.MAX_VALUE;

                for (int[] c : coords) {
                    if (c[0] > maxX) maxX=c[0];
                    if (c[1] > maxY) maxY=c[1];
                    if (c[0] < minX) minX=c[0];
                    if (c[1] < minY) minY=c[1];
                }

                int[][] shape = new int[maxY-minY+1][maxX-minX+1];

                for (int[] c : coords) {
                    shape[c[1]-minY][c[0]-minX] = 1;
                }

                if (shape.length == 1 && shape[0].length == 1) {
                    // SZÉKKKK
                    ProcessedMatrix[minY][minX] = ObjectType.CHAIR;
                } else if ((shape.length > 1 && shape[0].length == 1)  || (shape.length == 1 && shape[0].length > 1)) {
                    // KANAPÉ 1
                    int ww = shape[0].length;
                    int hh = shape.length;

                    for (int h = 0; h < hh; h++) {
                        for (int w = 0; w < ww; w++) {
                            if (shape[h][w] == 1) ProcessedMatrix[minY + h][minX + w] = ObjectType.SOFA;
                        }
                    }
                } else {
                    // Minta a négy sarokból egy L alakzat észleléséhez:
                    int p1 = shape[0][0];
                    int p2 = shape[shape.length - 1][0];
                    int p3 = shape[0][shape[0].length - 1];
                    int p4 = shape[shape.length - 1][shape[0].length - 1];

                    int ww = shape[0].length;
                    int hh = shape.length;

                    if (p1 == 1 && p2 == 1 && p3 == 1 && p4 == 1) {
                        // ASZTALLLL

                        for (int h = 0; h < hh; h++) {
                            for (int w = 0; w < ww; w++) {
                                ProcessedMatrix[minY + h][minX + w] = ObjectType.TABLE;
                            }
                        }

                    } else {
                        // KANAPÉÉÉÉ 2

                        for (int h = 0; h < hh; h++) {
                            for (int w = 0; w < ww; w++) {
                                if (shape[h][w] == 1) ProcessedMatrix[minY + h][minX + w] = ObjectType.SOFA;
                            }
                        }

                    }
                }


            }
        }

    }

    public int countRooms()
    {
        List<List<int[]>> rooms = new ArrayList<>();
        for(int y = 0; y<HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (matrix[y][x] == 1 || matrix[y][x] == 2) continue;
                generateNewIndexMatrix();
                List<int[]> coords = recursiveSearch(1,x,y);

                int n = coords.size();
                for (int i = 0; i < n-1; i++)
                    for (int j = 0; j < n-i-1; j++)
                        if (coords.get(j)[1] > coords.get(j+1)[1])
                        {
                            // swap arr[j+1] and arr[j]
                            int[] temp = coords.get(j);
                            coords.set(j, coords.get(j+1));
                            coords.set(j+1, temp);
                        } else if (coords.get(j)[1] == coords.get(j+1)[1] && coords.get(j)[0] > coords.get(j+1)[0]){
                            // swap arr[j+1] and arr[j]
                            int[] temp = coords.get(j);
                            coords.set(j, coords.get(j + 1));
                            coords.set(j + 1, temp);
                        }

                boolean exists = false;
                for (List<int[]> r : rooms) {
                    if (r.size() != coords.size()) continue;

                    boolean allEqual = true;
                    for (int i=0; i < r.size(); i++)
                    {
                        if (r.get(i)[0] != coords.get(i)[0] || r.get(i)[1] != coords.get(i)[1]) {
                            allEqual = false;
                            break;
                        }
                    }
                    if (allEqual) {
                        exists = true;
                        break;
                    }
                }
                if (! exists) {
                    rooms.add(coords);
                }

            }
        }
        return rooms.size();
    }

    public void printMatrix() {
        for(int y = 0; y<HEIGHT; y++) {
            for(int x = 0; x<WIDTH; x++) {
                System.out.print(matrix[y][x]);
            }
            System.out.println();
        }
    }

    public void printProcessedMatrix() {
        for(int y = 0; y<HEIGHT; y++) {
            for(int x = 0; x<WIDTH; x++) {
                if (ProcessedMatrix[y][x] != null) System.out.print(ProcessedMatrix[y][x].getType());
                else System.out.print(".");

            }
            System.out.println();
        }
    }
}
