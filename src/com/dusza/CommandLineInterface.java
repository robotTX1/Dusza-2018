package com.dusza;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterface {

    private Path path;

    public CommandLineInterface(Path path) {
        this.path = path;
    }

    public Path start() {
        Scanner input = new Scanner(System.in);

        int command;

        List<Path> list = readDirectoryContent();
        int optionCount = list.size();
        printOptions(list);

        while(true) {
            try {
                command = input.nextInt();
                input.nextLine();

                if(command > 0 && command <= optionCount) return list.get(command - 1);
                if(command == list.size()+1) return null;
                System.out.println("Érvénytelen opció: " + command);
            } catch (NumberFormatException e) {
                System.out.println("Érvénytelen opció!");
            }
        }
    }

    private void printOptions(List<Path> list) {
        System.out.printf("%d darab fájl találat.\n", list.size());

        for(int i=0; i<list.size(); i++) {
            System.out.printf("%d. %s\n", i+1, list.get(i).getFileName());
        }
        System.out.printf("%d. Kilépés\n", list.size()+1);
    }

    private List<Path> readDirectoryContent() {
        List<Path> result = new ArrayList<>();
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for(Path p : stream) {
                result.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
