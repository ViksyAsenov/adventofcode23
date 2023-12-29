package day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PointOfIncidencePart1 {
    public static List<String> getInput() {
        List<String> lines = new ArrayList<>();

        try {
            File starting = new File(System.getProperty("user.dir"));
            File fileToBeRead = new File(starting, "/inputs/input13.txt");
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

    public static int getColumnsBeforeMirror(List<List<String>> currentPattern) {
        int columnsBeforeMirror = 0;

        for(int currentCol = 0; currentCol < currentPattern.get(0).size() - 1; currentCol++) {
            int rowsWithEqualColElements = 0;

            columnsBeforeMirror++;
            for(int currentRow = 0; currentRow < currentPattern.size(); currentRow++) {
                if(currentPattern.get(currentRow).get(currentCol).equals(currentPattern.get(currentRow).get(currentCol + 1))) {
                    rowsWithEqualColElements++;
                }
            }

            if(rowsWithEqualColElements == currentPattern.size()) {
                boolean isMirrored = isMirroredByColumns(currentPattern, currentCol);
                if(isMirrored) {
                    return columnsBeforeMirror;
                }
            }
        }

        return 0;
    }

    private static boolean isMirroredByColumns(List<List<String>> currentPattern, int mirrorStartIndex) {
        int beforeMirrorIndex = mirrorStartIndex - 1;
        int afterMirrorIndex = mirrorStartIndex + 2;

        while (beforeMirrorIndex >= 0 && afterMirrorIndex <= currentPattern.get(0).size() - 1) {
            for(int currentRow = 0; currentRow < currentPattern.size(); currentRow++) {
                if(!(currentPattern.get(currentRow).get(beforeMirrorIndex).equals(currentPattern.get(currentRow).get(afterMirrorIndex)))) {
                    return false;
                }
            }

            beforeMirrorIndex--;
            afterMirrorIndex++;
        }

        return true;
    }

    public static int getRowsBeforeMirror(List<List<String>> currentPattern) {
        int rowsBeforeMirror = 0;

        for(int currentRow = 0; currentRow < currentPattern.size() - 1; currentRow++) {
            int colsWithEqualRowElements = 0;

            rowsBeforeMirror++;
            for(int currentCol = 0; currentCol < currentPattern.get(0).size(); currentCol++) {
                if(currentPattern.get(currentRow).get(currentCol).equals(currentPattern.get(currentRow + 1).get(currentCol))) {
                    colsWithEqualRowElements++;
                }
            }

            if(colsWithEqualRowElements == currentPattern.get(0).size()) {
                boolean isMirrored = isMirroredByRows(currentPattern, currentRow);
                if(isMirrored) {
                    return rowsBeforeMirror * 100;
                }
            }
        }

        return 0;
    }

    private static boolean isMirroredByRows(List<List<String>> currentPattern, int mirrorStartIndex) {
        int beforeMirrorIndex = mirrorStartIndex - 1;
        int afterMirrorIndex = mirrorStartIndex + 2;

        while (beforeMirrorIndex >= 0 && afterMirrorIndex <= currentPattern.size() - 1) {
            for(int currentCol = 0; currentCol < currentPattern.get(0).size(); currentCol++) {
                if(!(currentPattern.get(beforeMirrorIndex).get(currentCol).equals(currentPattern.get(afterMirrorIndex).get(currentCol)))) {
                    return false;
                }
            }

            beforeMirrorIndex--;
            afterMirrorIndex++;
        }

        return true;
    }

    public static int getOutput(List<String> lines) {
        int sum = 0;

        List<List<String>> currentPattern = new ArrayList<>();

        boolean isFirst = true;
        for(int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            List<String> currentCol = Arrays.stream(line.split("")).toList();
            currentPattern.add(currentCol);

            if(i + 1 == lines.size() || lines.get(i + 1).isEmpty()) {
                if(!isFirst) {
                    currentPattern.removeFirst();
                }

                sum += getColumnsBeforeMirror(currentPattern);
                sum += getRowsBeforeMirror(currentPattern);

                isFirst = false;

                currentPattern.clear();
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        List<String> lines = getInput();
        int output = getOutput(lines);

        System.out.println(output);
    }
}
