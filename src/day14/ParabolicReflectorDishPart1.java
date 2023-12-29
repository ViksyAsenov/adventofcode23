package day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParabolicReflectorDishPart1 {
    public static List<String> getInput() {
        List<String> lines = new ArrayList<>();

        try {
            File starting = new File(System.getProperty("user.dir"));
            File fileToBeRead = new File(starting, "/inputs/input14.txt");
            Scanner reader = new Scanner(fileToBeRead);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                lines.add(data);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        return lines;
    }

    public static int getLoadAmount(char[][] platform) {
        int load = 0;

        for(int col = 0; col < platform[0].length; col++) {
            for(int row = 1; row < platform.length; row++) {
                char current = platform[row][col];

                int currentRow = row;
                while (currentRow - 1 >= 0 && platform[currentRow][col] == 'O' && platform[currentRow - 1][col] == '.') {
                    platform[currentRow - 1][col] = 'O';
                    platform[currentRow][col] = '.';
                    currentRow--;
                }
            }
        }

        for(int row = 0; row < platform.length; row++) {
            for(int col = 0; col < platform[row].length; col++) {
                if(platform[row][col] == 'O') {
                    load += platform.length - row;
                }
            }
        }

        return load;
    }

    public static int getOutput(List<String> lines) {
        char[][] platform = new char[lines.size()][lines.get(0).length()];

        for(int row = 0; row < lines.size(); row++) {
            for(int col = 0; col < lines.get(row).length(); col++) {
                platform[row][col] = lines.get(row).charAt(col);
            }
        }

        return getLoadAmount(platform);
    }

    public static void main(String[] args) {
        List<String> lines = getInput();
        int output = getOutput(lines);

        System.out.println(output);
    }
}
