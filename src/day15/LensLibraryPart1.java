package day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LensLibraryPart1 {
    public static List<String> getInput() {
        List<String> lines = new ArrayList<>();

        try {
            File starting = new File(System.getProperty("user.dir"));
            File fileToBeRead = new File(starting, "/inputs/input15.txt");
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

    public static int getValueFromHash(String hash) {
        int currentValue = 0;

        for(int i = 0; i < hash.length(); i++) {
            char currentChar = hash.charAt(i);

            currentValue += currentChar;
            currentValue *= 17;
            currentValue %= 256;
        }

        return currentValue;
    }

    public static int getOutput(List<String> lines) {
        int sum = 0;

        String[] hashes = lines.get(0).split(",");

        for(String hash : hashes) {
            sum += getValueFromHash(hash);
        }

        return sum;
    }

    public static void main(String[] args) {
        List<String> lines = getInput();
        int output = getOutput(lines);

        System.out.println(output);
    }
}
