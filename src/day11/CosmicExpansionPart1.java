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

    public static class Galaxy {
        public final int rowIndex;
        public final int colIndex;
        public List<Galaxy> connectedGalaxies = new ArrayList<>();

        public Galaxy(int rowIndex, int colIndex) {
            this.rowIndex = rowIndex;
            this.colIndex = colIndex;
        }

        @Override
        public String toString() {
            return "Galaxy{" +
                    "rowIndex=" + rowIndex +
                    ", colIndex=" + colIndex +
                    ", connectedGalaxies=" + connectedGalaxies +
                    '}' + "\n";
        }
    }

    public static char[][] expandSpace(char[][] spaceTemp, List<Integer> rowsWithoutGalaxiesList, List<Integer> colsWithoutGalaxiesList) {
        int height = spaceTemp.length;
        int width = spaceTemp[0].length;

        int newHeight = height + rowsWithoutGalaxiesList.size();
        int newWidth = width + colsWithoutGalaxiesList.size();

        char[][] space = new char[newHeight][newWidth];

        for(int tempRow = 0, currentRow = 0; tempRow < height; tempRow++) {
            for(int tempCol = 0, currentCol = 0; tempCol < width; tempCol++) {
                if(colsWithoutGalaxiesList.contains(tempCol)) {
                    for(int r = 0; r < newHeight; r++) {
                        space[r][currentCol] = '.';
                    }

                    currentCol++;
                }

                space[currentRow][currentCol] = spaceTemp[tempRow][tempCol];
                currentCol++;
            }

            if(rowsWithoutGalaxiesList.contains(tempRow)) {
                currentRow++;
                for(int c = 0; c < newWidth; c++) {
                    space[currentRow][c] = '.';
                }
            }

            currentRow++;
        }

        return space;
    }

    public static int getAllPathsSum(List<Galaxy> galaxies) {
        int sum = 0;

        for(int i = 0; i < galaxies.size(); i++) {
            for(int j = i + 1; j < galaxies.size(); j++) {
                {
                    Galaxy firstGalaxy = galaxies.get(i);
                    Galaxy secondGalaxy = galaxies.get(j);

                    if(!firstGalaxy.connectedGalaxies.contains(secondGalaxy) && !secondGalaxy.connectedGalaxies.contains(firstGalaxy)) {
                        sum += getDistanceBetweenGalaxies(firstGalaxy, secondGalaxy);

                        firstGalaxy.connectedGalaxies.add(secondGalaxy);
                        secondGalaxy.connectedGalaxies.add(firstGalaxy);
                    }
                }
            }
        }

        return sum;
    }

    public static int getDistanceBetweenGalaxies(Galaxy firstGalaxy, Galaxy secondGalaxy) {
        int rowDistance = Math.abs(firstGalaxy.rowIndex - secondGalaxy.rowIndex);
        int colDistance = Math.abs(firstGalaxy.colIndex - secondGalaxy.colIndex);

        return colDistance + rowDistance;
    }

    public static int getOutput(List<String> lines) {
        int height = lines.size();
        int width = lines.get(0).length();

        char[][] spaceTemp = new char[height][width];

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

                spaceTemp[row][col] = value;
            }

            if(isRowGalaxyFree) {
                rowsWithoutGalaxiesList.add(row);
            }
        }

        List<Integer> colsWithoutGalaxiesList = colsWithoutGalaxiesMap.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();

        char[][] space = expandSpace(spaceTemp, rowsWithoutGalaxiesList, colsWithoutGalaxiesList);

        List<Galaxy> galaxies = new ArrayList<>();
        for(int row = 0; row < space.length; row++) {
            for(int col = 0; col < space[row].length; col++) {
                if(space[row][col] == '#') {
                    galaxies.add(new Galaxy(row, col));
                }
            }
        }

        return getAllPathsSum(galaxies);
    }

    public static void main(String[] args) {
        List<String> lines = getInput();
        int output = getOutput(lines);

        System.out.println(output);
    }
}
