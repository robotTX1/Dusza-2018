package com.dusza;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        Path basePath = FileSystems.getDefault().getPath("Data");

        CommandLineInterface cli = new CommandLineInterface(basePath);
        Path path = cli.start();

        if(path == null) {
            System.exit(0);
        }

        IOHandler ioHandler = new IOHandler(path);

        int[][] matrix = ioHandler.readFile();

        for(int y = 0; y<matrix[0].length; y++) {
            for(int x = 0; x<matrix.length; x++) {
                System.out.print(matrix[x][y]);
            }
            System.out.println();
        }
    }
}
