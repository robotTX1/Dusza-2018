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

        for(int h = 0; h < matrix.length; h++) {
            for(int w = 0; w< matrix[0].length; w++) {
                System.out.print(matrix[h][w]);
            }
            System.out.println();
        }

        Magic magic = new Magic(matrix);
        magic.getPlan();

    }
}
