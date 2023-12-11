package day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MirageMaintenancePart2 {
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

    public static long getNextNumber(List<Long> numbers) {
        List<List<Long>> previousNumbers = new ArrayList<>(List.of(new ArrayList<>(numbers)));

        while (!allZeros(previousNumbers.getLast())) {
            List<Long> currentNumbers = getDifferences(previousNumbers.getLast());
            previousNumbers.add(currentNumbers);
        }
        
        for (int i = previousNumbers.size() - 1; i > 0; i--) {
            long currentNewNumber = previousNumbers.get(i - 1).getFirst() - previousNumbers.get(i).getFirst();
            previousNumbers.get(i - 1).addFirst(currentNewNumber);
        }

        return previousNumbers.getFirst().getFirst();
    }

    public static boolean allZeros(List<Long> numbers) {
        return numbers.stream().allMatch(n -> n == 0);
    }

    public static List<Long> getDifferences(List<Long> numbers) {
        List<Long> differences = new ArrayList<>();

        for(int i = 0; i < numbers.size() - 1; i++) {
            differences.add(numbers.get(i + 1) - numbers.get(i));
        }

        return differences;
    }

    public static long getOutput(List<String> lines) {
        long sum = 0;

        for(String line : lines) {
            List<Long> numbers = Arrays.stream(line.split(" ")).map(Long::parseLong).toList();
            long nextNumber = getNextNumber(numbers);
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
