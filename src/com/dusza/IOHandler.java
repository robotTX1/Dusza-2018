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

    public List<String> readLines() {
        List<String> result = new ArrayList<>();

        try(Scanner input = new Scanner(path)) {
            while(input.hasNextLine()) {
                result.add(input.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int[][] readFile() {
        int[][] result;
        List<String> list = readLines();

        int w,h;

        String[] split = list.get(0).split(" ");
        list.remove(0);

        h = Integer.parseInt(split[0]);
        w = Integer.parseInt(split[1]);

        result = new int[h][w];

        for(int y = 0; y<list.size(); y++) {
            for(int x=0; x<list.get(y).length(); x++) {
                result[y][x] = Integer.parseInt(String.valueOf(list.get(y).charAt(x)));
            }
        }

        return result;
    }
}
