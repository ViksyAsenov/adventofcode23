package day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GearRatiosPart2 {
    public static Set<Integer> foundNumbers = new HashSet<>();
    public static List<List<String>> getInput() {
        List<List<String>> lines = new ArrayList<>();

        try {
            File starting = new File(System.getProperty("user.dir"));
            File fileToBeRead = new File(starting,"/inputs/input3.txt");
            Scanner reader = new Scanner(fileToBeRead);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();

                List<String> list = new ArrayList<>();
                for(Character c : data.toCharArray()) {
                    list.add(String.valueOf(c));
                }

                lines.add(list);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        return lines;
    }

    public static boolean isASymbol(String s) {
        return s.equals("*");
    }

    public static int extractConsecutiveDigits(List<String> line, int c, String row) {
        String indexHashOfCurrentNumber = "";

        StringBuilder leftDigits = new StringBuilder();
        int i = c - 1;
        while (i >= 0 && line.get(i).matches("\\d+")) {
            leftDigits.insert(0, line.get(i));
            i--;
        }
        indexHashOfCurrentNumber += i;

        StringBuilder rightDigits = new StringBuilder();
        i = c + 1;
        while (i < line.size() && line.get(i).matches("\\d+")) {
            rightDigits.append(line.get(i));
            i++;
        }
        indexHashOfCurrentNumber += i;
        indexHashOfCurrentNumber += row;

        if(!foundNumbers.add(Integer.parseInt(indexHashOfCurrentNumber))) {
            return -1;
        }

        String resultString = leftDigits + line.get(c) + rightDigits;
        return Integer.parseInt(resultString);
    }

    public static List<Integer> getAdjacentNumbers(List<List<String>> lines, int row, int col) {
        List<Integer> adjacentNumbers = new ArrayList<>();

        for(int r = row - 1; r <= row + 1; r++) {
            for(int c = col - 1; c <= col + 1; c++) {
                if(r < 0 || c < 0 || r >= lines.size() || c >= lines.get(row).size()) {
                    continue;
                }

                if(r == row && c == col) {
                    continue;
                }

                if(lines.get(r).get(c).matches("\\d+")) {
                    int num = extractConsecutiveDigits(lines.get(r), c, String.valueOf(r));
                    if(num != -1) {
                        adjacentNumbers.add(num);
                    }
                }
            }
        }

        return adjacentNumbers.size() == 2 ? adjacentNumbers : new ArrayList<>();
    }

    public static int getOutput(List<List<String>> lines) {
        int sum = 0;

        for(int row = 0; row < lines.size(); row++) {
            for(int col = 0; col < lines.get(row).size(); col++) {
                if(isASymbol(lines.get(row).get(col))) {
                    List<Integer> numbers = getAdjacentNumbers(lines, row, col);

                    int gearMultiplication = 1;
                    for (Integer number : numbers) {
                        gearMultiplication *= number;
                    }

                    if(gearMultiplication > 1) {
                        sum += gearMultiplication;
                    }
                }
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        List<List<String>> lines = getInput();
        int output = getOutput(lines);

        System.out.println(output);
    }
}

