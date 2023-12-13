package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class HotSpringsPart1 {
    public static List<String> getInput() {
        List<String> lines = new ArrayList<>();

        try {
            File starting = new File(System.getProperty("user.dir"));
            File fileToBeRead = new File(starting, "/inputs/input12.txt");
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

    public static int getAllPossibleVariants(String springs, int[] arrOfNonWorkingSprings) {
        if (arrOfNonWorkingSprings.length == 0) {
            return springs.contains("#") ? 0 : 1;
        }

        if (springs.isEmpty()) {
            return 0;
        }

        char currentSpring = springs.charAt(0);
        int currentNumberOfNonWorkingSprings = arrOfNonWorkingSprings[0];

        int currentVariants = 0;
        switch (currentSpring) {
            case '.' -> {
                currentVariants = handleDot(springs, arrOfNonWorkingSprings);
            }
            case '#' -> {
                currentVariants = handlePound(springs, currentNumberOfNonWorkingSprings, arrOfNonWorkingSprings);
            }
            case '?' -> {
                currentVariants = handleDot(springs, arrOfNonWorkingSprings) + handlePound(springs, currentNumberOfNonWorkingSprings, arrOfNonWorkingSprings);
            }
        }

        return currentVariants;
    }

    private static int handlePound(String springs, int currentNumberOfNonWorkingSprings, int[] arrOfNonWorkingSprings) {
        String newSprings = springs.substring(0, Math.min(currentNumberOfNonWorkingSprings, springs.length())).replace("?", "#");

        if (!newSprings.equals("#".repeat(Math.min(currentNumberOfNonWorkingSprings, springs.length())))) {
            return 0;
        }

        if (springs.length() == currentNumberOfNonWorkingSprings) {
            if (arrOfNonWorkingSprings.length == 1) {
                return 1;
            } else {
                return 0;
            }
        }

        if (currentNumberOfNonWorkingSprings <= springs.length() - 1 && (springs.charAt(currentNumberOfNonWorkingSprings) == '?' || springs.charAt(currentNumberOfNonWorkingSprings) == '.')) {
            return getAllPossibleVariants(springs.substring(currentNumberOfNonWorkingSprings + 1), Arrays.copyOfRange(arrOfNonWorkingSprings, 1, arrOfNonWorkingSprings.length));
        }

        return 0;
    }

    private static int handleDot(String springs, int[] arrOfNonWorkingSprings) {
        return getAllPossibleVariants(springs.length() > 1 ? springs.substring(1) : "", arrOfNonWorkingSprings);
    }

    public static int getOutput(List<String> lines) {
        int sum = 0;

        for(String line : lines) {
            String[] springsAndNumbers = line.split(" ");

            String springRow = springsAndNumbers[0];
            int[] nonWorkingSpringsInOrder = Arrays.stream(springsAndNumbers[1].split(",")).mapToInt(Integer::parseInt).toArray();

            sum += getAllPossibleVariants(springRow, nonWorkingSpringsInOrder);
        }

        return sum;
    }

    public static void main(String[] args) {
        List<String> lines = getInput();
        int output = getOutput(lines);

        System.out.println(output);
    }
}
