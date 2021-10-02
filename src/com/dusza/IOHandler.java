package com.dusza;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOHandler {
    private Path path;

    public IOHandler(Path path) {
        this.path = path;
    }

    public int[][] readFile() {
        int[][] result = null;
        List<String> list = new ArrayList<>();
        try(Scanner input = new Scanner(path)) {
            int x, y;
            y = input.nextInt();
            x = input.nextInt();
            input.nextLine();

            result = new int[x][y];

            while(input.hasNextLine()) {
                list.add(input.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int y = 0; y<list.size(); y++) {
            for(int x=0; x<list.get(y).length(); x++) {
                result[x][y] = Integer.parseInt(String.valueOf(list.get(y).charAt(x)));
            }
        }

        return result;
    }
}
