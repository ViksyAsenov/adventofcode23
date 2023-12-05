package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ScratchcardsPart1 {
    public static List<String> getInput() {
        List<String> lines = new ArrayList<>();

        try {
            File starting = new File(System.getProperty("user.dir"));
            File fileToBeRead = new File(starting,"/inputs/input4.txt");
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

    public static int getOutput(List<String> lines) {
        int sum = 0;

        for(String line : lines) {
            String[] winningNumbersAndCardNumbers = line.split(": ")[1].trim().split(" \\| ");

            Set<Integer> numbers = new HashSet<>();
            int winningNumberCount = -1;
            for(String cardNumbers : winningNumbersAndCardNumbers) {
                String[] numberStrings = cardNumbers.trim().split("\\s+");

                for (String numberString : numberStrings) {
                    int number = Integer.parseInt(numberString.trim());

                    if (!numbers.add(number)) {
                        winningNumberCount++;
                    }
                }
            }

            if(winningNumberCount != -1) {
                sum += (int) Math.pow(2, winningNumberCount);
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
