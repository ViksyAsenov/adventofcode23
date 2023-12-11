package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CosmicExpansionPart1 {
    public static List<String> getInput() {
        List<String> lines = new ArrayList<>();

        try {
            File starting = new File(System.getProperty("user.dir"));
            File fileToBeRead = new File(starting, "/inputs/input11.txt");
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

    public static void insertRow(char[][] galaxies, int rowIndex) {
        int height = galaxies.length;
        int width = galaxies[0].length;

        for (int row = height - 1; row > rowIndex; row--) {
            for (int col = 0; col < width; col++) {
                galaxies[row][col] = galaxies[row - 1][col];
            }
        }

        for (int j = 0; j < width; j++) {
            galaxies[rowIndex][j] = '.';
        }
    }

    public static int getOutput(List<String> lines) {
        int height = lines.size();
        int width = lines.get(0).length();

        char[][] galaxiesTemp = new char[height][width];

        List<Integer> rowsWithoutGalaxiesList = new ArrayList<>();
        Map<Integer, Boolean> colsWithoutGalaxiesMap = new HashMap<>();
        for(int row = 0; row < lines.size(); row++) {
            boolean isRowGalaxyFree = true;

            for(int col = 0; col < lines.get(row).length(); col++) {
                char value = lines.get(row).charAt(col);

                if(!colsWithoutGalaxiesMap.containsKey(col)) {
                    colsWithoutGalaxiesMap.put(col, true);
                }

                if(value == '#') {
                    isRowGalaxyFree = false;

                    if(colsWithoutGalaxiesMap.containsKey(col) && colsWithoutGalaxiesMap.get(col)) {
                        colsWithoutGalaxiesMap.put(col, false);
                    }
                }

                galaxiesTemp[row][col] = value;
            }

            if(isRowGalaxyFree) {
                rowsWithoutGalaxiesList.add(row);
            }
        }

        List<Integer> colsWithoutGalaxiesList = colsWithoutGalaxiesMap.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();

        int newHeight = height + rowsWithoutGalaxiesList.size();
        int newWidth = width + colsWithoutGalaxiesList.size();
        char[][] galaxies = new char[newHeight][newWidth];

        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                galaxies[row][col] = galaxiesTemp[row][col];
            }
        }

        for(Integer rowIndex : rowsWithoutGalaxiesList) {
            insertRow(galaxies, rowIndex + 1);
        }

        for (char[] x : galaxiesTemp)
        {
            for (char y : x)
            {
                System.out.print(y + " ");
            }
            System.out.println();
        }

        System.out.println("---------------------------");

        for (char[] x : galaxies)
        {
            for (char y : x)
            {
                System.out.print(y + "");
            }
            System.out.println();
        }

        return 0;
    }

    public static void main(String[] args) {
        List<String> lines = getInput();
        int output = getOutput(lines);

        System.out.println(output);
    }
}
