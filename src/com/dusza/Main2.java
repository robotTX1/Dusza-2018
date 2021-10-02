package com.dusza;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Main2 {

    public static void main(String[] args) {
        Path basePath = FileSystems.getDefault().getPath("Data");

        CommandLineInterface cli = new CommandLineInterface(basePath);
        Path path = cli.start();

        if(path == null) {
            System.exit(0);
        }

        IOHandler ioHandler = new IOHandler(path);

        int[][] result = ioHandler.readFile();

        for (int[] ints : result) {
            for (int x = 0; x < result[0].length; x++) {
                System.out.print(ints[x]);
            }
            System.out.println();
        }

        System.out.println("#####################");

        HouseProcessor2_0 magic2 = new HouseProcessor2_0(result);
        int rooms = magic2.countRooms();


        magic2.findWalls();
        System.out.println();
        magic2.printProcessedMatrix();
        System.out.println();
        magic2.printMatrix();
        System.out.println();

        magic2.findObjects();
        magic2.printProcessedMatrix();
        System.out.println("A szobák száma: " + rooms);

/*

        */


    }
}
