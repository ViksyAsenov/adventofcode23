package day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WaitForItPart2 {
    public static List<String> getInput() {
        List<String> lines = new ArrayList<>();

        try {
            File starting = new File(System.getProperty("user.dir"));
            File fileToBeRead = new File(starting, "/inputs/input6.txt");
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
        int sum = 1;

        StringBuilder inputTime = new StringBuilder();
        StringBuilder inputRecord = new StringBuilder();
        for(int i = 0; i < lines.size(); i++) {
            List<String> data = Arrays.stream(lines.get(i).split(":")[1].split(" ")).filter(s -> !s.isEmpty()).toList();
            if(i % 2 == 0) {
                for(String number : data) {
                    inputTime.append(number);
                }
            } else {
                for(String number : data) {
                    inputRecord.append(number);
                }
            }
        }

        long time = Long.parseLong(inputTime.toString());
        long record = Long.parseLong(inputRecord.toString());
        int wastedTime = 1;

        int counter = 0;
        while (wastedTime < time) {
            long currentRecord = (time - wastedTime) * wastedTime;
            if(currentRecord > record) {
                counter++;
            }
            wastedTime++;
        }

        if(counter != 0) {
            sum *= counter;
        }

        return sum == 1 ? 0 : sum;
    }

    public static void main(String[] args) {
        List<String> lines = getInput();
        int output = getOutput(lines);

        System.out.println(output);
    }
}

