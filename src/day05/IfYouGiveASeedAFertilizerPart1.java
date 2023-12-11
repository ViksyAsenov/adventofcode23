package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class IfYouGiveASeedAFertilizerPart1 {
    public static List<String> getInput() {
        List<String> lines = new ArrayList<>();

        try {
            File starting = new File(System.getProperty("user.dir"));
            File fileToBeRead = new File(starting, "/inputs/input5.txt");
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

    public static class Mapper {
        public long destination;
        public long start;
        public long count;

        public Mapper(List<Long> data) {
            this.destination = data.get(0);
            this.start = data.get(1);
            this.count = data.get(2);
        }
    }

    public static long mapStartToDestination(List<Mapper> mappers, long currentStart) {
        long currentDestination = -1;
        for(Mapper mapper : mappers) {
            long destination = mapper.destination;
            long start = mapper.start;
            long count = mapper.count;

            if(currentStart >= start && currentStart <= start + count) {
                currentDestination = destination - start + currentStart;
                break;
            }
        }

        return currentDestination != -1 ? currentDestination : currentStart;
    }

    public static long getOutput(List<String> lines) {
        List<Long> seeds = new ArrayList<>();
        List<Mapper> seedToSoil = new ArrayList<>();
        List<Mapper> soilToFertilizer = new ArrayList<>();
        List<Mapper> fertilizerToWater = new ArrayList<>();
        List<Mapper> waterToLight = new ArrayList<>();
        List<Mapper> lightToTemperature = new ArrayList<>();
        List<Mapper> temperatureToHumidity = new ArrayList<>();
        List<Mapper> humidityToLocation = new ArrayList<>();

        for(String seed : lines.remove(0).trim().split(": ")[1].split(" ")) {
            seeds.add(Long.parseLong(seed));
        }

        for (String line : lines) {
            switch (line) {
                case "seed-to-soil map:": {
                    for(int i = lines.indexOf("seed-to-soil map:") + 1; i < lines.size() && !Objects.equals(lines.get(i), ""); i++) {
                        List<Long> soilData = Arrays.stream(lines.get(i).trim().split(" ")).map(Long::parseLong).toList();
                        seedToSoil.add(new Mapper(soilData));
                    }
                    break;
                }
                case "soil-to-fertilizer map:": {
                    for(int i = lines.indexOf("soil-to-fertilizer map:") + 1; i < lines.size() && !Objects.equals(lines.get(i), ""); i++) {
                        List<Long> fertilizerData = Arrays.stream(lines.get(i).trim().split(" ")).map(Long::parseLong).toList();
                        soilToFertilizer.add(new Mapper(fertilizerData));
                    }
                    break;
                }
                case "fertilizer-to-water map:": {
                    for(int i = lines.indexOf("fertilizer-to-water map:") + 1; i < lines.size() && !Objects.equals(lines.get(i), ""); i++) {
                        List<Long> waterData = Arrays.stream(lines.get(i).trim().split(" ")).map(Long::parseLong).toList();
                        fertilizerToWater.add(new Mapper(waterData));
                    }
                    break;
                }
                case "water-to-light map:": {
                    for(int i = lines.indexOf("water-to-light map:") + 1; i < lines.size() && !Objects.equals(lines.get(i), ""); i++) {
                        List<Long> lightData = Arrays.stream(lines.get(i).trim().split(" ")).map(Long::parseLong).toList();
                        waterToLight.add(new Mapper(lightData));
                    }
                    break;
                }
                case "light-to-temperature map:": {
                    for(int i = lines.indexOf("light-to-temperature map:") + 1; i < lines.size() && !Objects.equals(lines.get(i), ""); i++) {
                        List<Long> temperatureData = Arrays.stream(lines.get(i).trim().split(" ")).map(Long::parseLong).toList();
                        lightToTemperature.add(new Mapper(temperatureData));
                    }
                    break;
                }
                case "temperature-to-humidity map:": {
                    for(int i = lines.indexOf("temperature-to-humidity map:") + 1; i < lines.size() && !Objects.equals(lines.get(i), ""); i++) {
                        List<Long> humidityData = Arrays.stream(lines.get(i).trim().split(" ")).map(Long::parseLong).toList();
                        temperatureToHumidity.add(new Mapper(humidityData));
                    }
                    break;
                }
                case "humidity-to-location map:": {
                    for(int i = lines.indexOf("humidity-to-location map:") + 1; i < lines.size() && !Objects.equals(lines.get(i), ""); i++) {
                        List<Long> locationData = Arrays.stream(lines.get(i).trim().split(" ")).map(Long::parseLong).toList();
                        humidityToLocation.add(new Mapper(locationData));
                    }
                    break;
                }
            }
        }

        long lowestLocation = Long.MAX_VALUE;
        for(Long seed : seeds) {
            long soil = mapStartToDestination(seedToSoil, seed);
            long fertilizer = mapStartToDestination(soilToFertilizer, soil);
            long water = mapStartToDestination(fertilizerToWater, fertilizer);
            long light = mapStartToDestination(waterToLight, water);
            long temperature = mapStartToDestination(lightToTemperature, light);
            long humidity = mapStartToDestination(temperatureToHumidity, temperature);
            long location = mapStartToDestination(humidityToLocation, humidity);

            if(lowestLocation >= location) {
                lowestLocation = location;
            }
        }

        return lowestLocation;
    }

    public static void main(String[] args) {
        List<String> lines = getInput();
        long output = getOutput(lines);

        System.out.println(output);
    }
}
