package day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MirageMaintenancePart1 {
    public static List<String> getInput() {
        List<String> lines = new ArrayList<>();

        try {
            File starting = new File(System.getProperty("user.dir"));
            File fileToBeRead = new File(starting, "/inputs/input9.txt");
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

    public static long getNextNumber(List<Long> numbers, boolean isFirst) {
        if (numbers.stream().limit(numbers.size() - 1).allMatch(n -> n == 0)) {
            return numbers.getLast();
        }

        List<Long> updatedNumbers = new ArrayList<>();
        for (int i = 0; i < numbers.size() - 1; i++) {
            if(i < numbers.size() - 2 || isFirst) {
                updatedNumbers.add(numbers.get(i + 1) - numbers.get(i));
            }
        }

        updatedNumbers.add(numbers.getLast() + updatedNumbers.getLast());

        return getNextNumber(updatedNumbers, false);
    }

    public static long getOutput(List<String> lines) {
        long sum = 0;

        for(String line : lines) {
            List<Long> numbers = Arrays.stream(line.split(" ")).map(Long::parseLong).toList();
            long nextNumber = getNextNumber(numbers, true);
            sum += nextNumber;
        }

        return sum;
    }

    public static void main(String[] args) {
        List<String> lines = getInput();
        long output = getOutput(lines);

        System.out.println(output);
    }
}
