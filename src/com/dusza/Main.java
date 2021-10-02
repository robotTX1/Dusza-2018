package com.dusza;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        Path path = FileSystems.getDefault().getPath("Data", "alaprajz1.txt");

        IOHandler ioHandler = new IOHandler(path);

        int[][] result = ioHandler.readFile();

        for(int y = 0; y<result[0].length; y++) {
            for(int x = 0; x<result.length; x++) {
                System.out.print(result[x][y]);
            }
            System.out.println();
        }


    }
}
