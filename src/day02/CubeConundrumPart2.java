package day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CubeConundrumPart2 {
    public static List<String> getInput() {
        List<String> lines = new ArrayList<>();

        try {
            File starting = new File(System.getProperty("user.dir"));
            File fileToBeRead = new File(starting,"/inputs/input2.txt");
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
            String[] idAndSets = line.split(": ");

            String[] setsOfCubes = idAndSets[1].trim().split("; ");

            int requiredRedCubes = Integer.MIN_VALUE;
            int requiredGreenCubes = Integer.MIN_VALUE;
            int requiredBlueCubes = Integer.MIN_VALUE;

            for(String set : setsOfCubes) {
                String[] colors = set.trim().split(", ");

                for(int i = 0; i < colors.length; i++) {
                    if(colors[i].contains("red")) {
                        int currentRedCubes = Integer.parseInt(colors[i].replace(" red", "").trim());
                        if(currentRedCubes >= requiredRedCubes) {
                            requiredRedCubes = currentRedCubes;
                        }
                    }

                    if(colors[i].contains("green")) {
                        int currentGreenCubes = Integer.parseInt(colors[i].replace(" green", "").trim());
                        if(currentGreenCubes >= requiredGreenCubes) {
                            requiredGreenCubes = currentGreenCubes;
                        }
                    }

                    if(colors[i].contains("blue")) {
                        int currentBlueCubes = Integer.parseInt(colors[i].replace(" blue", "").trim());
                        if(currentBlueCubes >= requiredBlueCubes) {
                            requiredBlueCubes = currentBlueCubes;
                        }
                    }
                }
            }

            sum += (requiredRedCubes * requiredGreenCubes * requiredBlueCubes);
        }

        return sum;
    }

    public static void main(String[] args) {
        List<String> lines = getInput();
        int output = getOutput(lines);

        System.out.println(output);
    }
}
