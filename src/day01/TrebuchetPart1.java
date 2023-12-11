package day1;

import java.io.*;
import java.util.*;

public class TrebuchetPart1 {
    public static List<String> getInput() {
        List<String> lines = new ArrayList<>();

        try {
            File starting = new File(System.getProperty("user.dir"));
            File fileToBeRead = new File(starting,"/inputs/input1.txt");
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

        for (String line : lines) {
            int left = 0;
            int right = line.length() - 1;

            boolean foundLeftNumber = false;
            boolean foundRightNumber = false;
            while (!foundLeftNumber || !foundRightNumber) {
                if (!Character.isDigit(line.charAt(left))) {
                    left++;
                } else {
                    foundLeftNumber = true;
                }

                if (!Character.isDigit(line.charAt(right))) {
                    right--;
                } else {
                    foundRightNumber = true;
                }
            }

            sum += Integer.parseInt(String.valueOf(line.charAt(left)) + line.charAt(right));
        }

        return sum;
    }

    public static void main(String[] args) {
        List<String> lines = getInput();
        int output = getOutput(lines);

        System.out.println(output);
    }
}
