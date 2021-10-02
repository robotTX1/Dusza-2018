package com.dusza;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        Path basePath = FileSystems.getDefault().getPath("Data");

        CommandLineInterface cli = new CommandLineInterface(basePath);

        while(true) {
            Path path = cli.start();

            if(path == null) {
                System.exit(0);
            }

            IOHandler ioHandler = new IOHandler(path);

            int[][] matrix = ioHandler.readFile();

            HouseProcessor houseProcessor = new HouseProcessor(matrix);
            houseProcessor.printHousePlan();
            System.out.println("\n\n");
        }
    }
}
