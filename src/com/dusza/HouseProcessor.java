package com.dusza;

public class HouseProcessor {
    private final int[][] matrix;
    private final ObjectType[][] processedMatrix;
    private final int HEIGHT;
    private final int WIDTH;

    public HouseProcessor(int[][] matrix){
        this.matrix = matrix;
        HEIGHT = matrix.length;
        WIDTH = matrix[0].length;
        processedMatrix = new ObjectType[HEIGHT][WIDTH];

    }

    public void printHousePlan() {
        recursive(0,0, ObjectType.WALL);
        searchChair();
        searchTable();
        searchSofa();
        int roomCount = countRooms();
        for(int y=0; y<HEIGHT; y++) {
            for(int x=0; x<WIDTH; x++) {
                System.out.print(processedMatrix[y][x].getType());
            }
            System.out.println();
        }
        System.out.println("Szobák száma: " + roomCount);
    }

    private void recursive(int x, int y, ObjectType type, int value) {
        if(x < 0) return;
        if(y < 0) return;
        if(x >= WIDTH) return;
        if(y >= HEIGHT) return;

        if(processedMatrix[y][x] != null) return;

        if(matrix[y][x] == value) processedMatrix[y][x] = type;
        else return;

        recursive(x + 1, y, type, value);
        recursive(x - 1, y, type, value);
        recursive(x, y + 1, type, value);
        recursive(x, y - 1, type, value);
    }

    private void recursive(int x, int y, ObjectType type) {
        recursive(x, y, type, 1);
    }

    private void searchChair() {
        for(int h = 0; h < HEIGHT; h++) {
            for(int w = 0; w < WIDTH; w++) {
                if(processedMatrix[h][w] == null && matrix[h][w] == 1)
                    if(checkChairNeighbours(w, h)) processedMatrix[h][w] = ObjectType.CHAIR;
            }
        }
    }

    private boolean checkChairNeighbours(int x, int y) {
        return emptySpace(x + 1, y) && emptySpace(x - 1, y) && emptySpace(x, y + 1) && emptySpace(x, y - 1);
    }

    private boolean emptySpace(int x, int y) {
        if(x < 0) return true;
        if(y < 0) return true;
        if(x >= WIDTH) return true;
        if(y >= HEIGHT) return true;
        return processedMatrix[y][x] == null && matrix[y][x] == 0;
    }

    private void searchTable() {
        for(int h = 0; h < HEIGHT; h++) {
            for(int w = 0; w < WIDTH; w++) {
                if(isTable(w, h)) recursive(w, h, ObjectType.TABLE);
            }
        }
    }

    private boolean isTable(int x, int y) {
        for(int i = y; i <= y+1; i++) {
            for(int j = x; j <= x+ 1; j++) {
                if(emptySpace(j,i) || processedMatrix[i][j] != null) return false;
            }
        }
        return true;
    }

    private void searchSofa() {
        for(int h = 0; h < HEIGHT; h++) {
            for(int w = 0; w < WIDTH; w++) {
                if(matrix[h][w] == 1 && processedMatrix[h][w] == null) processedMatrix[h][w] = ObjectType.SOFA;
            }
        }
    }

    private int countRooms() {
        int counter = 0;

        for(int h = 0; h < HEIGHT; h++) {
            for(int w = 0; w < WIDTH; w++) {
                if((h == 0 || h == HEIGHT - 1) || (w == 0 || w == WIDTH - 1)) {
                    if(matrix[h][w] == 0) recursive(w, h, ObjectType.FLOOR, 0);
                }
            }
        }

        for(int h = 0; h < HEIGHT; h++) {
            for(int w = 0; w < WIDTH; w++) {
                if(matrix[h][w] == 0 && processedMatrix[h][w] == null) {
                    recursive(w, h, ObjectType.FLOOR, 0);
                    counter++;
                }
            }
        }

        return counter;
    }
}
