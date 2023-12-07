package day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class CamelCardsPart1 {
    public static List<String> getInput() {
        List<String> lines = new ArrayList<>();

        try {
            File starting = new File(System.getProperty("user.dir"));
            File fileToBeRead = new File(starting, "/inputs/input7.txt");
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

    private static class Hand implements Comparable<Hand> {
        private final int strength;
        public int points;
        private final List<String> cards;

        public Hand(List<String> cards, int points) {
            this.cards = cards;
            this.points = points;
            this.strength = calculateStrength();
        }

        private enum HandStrengthEnum {
            FIVE_OF_A_KIND(6),
            FOUR_OF_A_KIND(5),
            FULL_HOUSE(4),
            THREE_OF_A_KIND(3),
            TWO_PAIR(2),
            ONE_PAIR(1),
            HIGH_CARD(0);

            private final int intValue;

            HandStrengthEnum(int intValue) {
                this.intValue = intValue;
            }

            public int getIntValue() {
                return intValue;
            }
        }

        private int calculateStrength() {
            if(isFiveOfAKind(cards)) {
                return HandStrengthEnum.FIVE_OF_A_KIND.getIntValue();
            }

            if(isFourOfAKind(cards)) {
                return HandStrengthEnum.FOUR_OF_A_KIND.getIntValue();
            }

            if(isAFullHouse(cards)) {
                return HandStrengthEnum.FULL_HOUSE.getIntValue();
            }

            if(isThreeOfAKind(cards)) {
                return HandStrengthEnum.THREE_OF_A_KIND.getIntValue();
            }

            if(isTwoPair(cards)) {
                return HandStrengthEnum.TWO_PAIR.getIntValue();
            }

            if(isOnePair(cards)) {
                return HandStrengthEnum.ONE_PAIR.getIntValue();
            }

            return HandStrengthEnum.HIGH_CARD.getIntValue();
        }

        private boolean isFiveOfAKind(List<String> list) {
            Map<String, Long> counts = list.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
            return counts.containsValue(5L) && counts.size() == 1;
        }

        private boolean isFourOfAKind(List<String> list) {
            Map<String, Long> counts = list.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
            return counts.containsValue(4L) && counts.size() == 2;
        }

        private boolean isAFullHouse(List<String> list) {
            Map<String, Long> counts = list.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
            return counts.containsValue(3L) && counts.size() == 2;
        }

        private boolean isThreeOfAKind(List<String> list) {
            Map<String, Long> counts = list.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
            return counts.containsValue(3L) && counts.size() == 3;
        }

        private boolean isTwoPair(List<String> list) {
            Map<String, Long> counts = list.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
            return counts.containsValue(2L) && counts.size() == 3;
        }

        private boolean isOnePair(List<String> list) {
            Map<String, Long> counts = list.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
            return counts.containsValue(2L) && counts.size() == 4;
        }

        private final Map<String, Integer> cardStrengthMap = Map.ofEntries(
                entry("2", 2),
                entry("3", 3),
                entry("4", 4),
                entry("5", 5),
                entry("6", 6),
                entry("7", 7),
                entry("8", 8),
                entry("9", 9),
                entry("T", 10),
                entry("J", 11),
                entry("Q", 12),
                entry("K", 13),
                entry("A", 14)
        );

        @Override
        public int compareTo(Hand o) {
            int strengthComparison = Integer.compare(this.strength, o.strength);

            if (strengthComparison != 0) {
                return strengthComparison;
            } else {
                for (int i = 0; i < cards.size(); i++) {
                    String thisCard = cards.get(i);
                    String otherCard = o.cards.get(i);

                    int thisCardStrength = cardStrengthMap.get(thisCard);
                    int otherCardStrength = cardStrengthMap.get(otherCard);

                    int cardComparison = Integer.compare(thisCardStrength, otherCardStrength);

                    if (cardComparison != 0) {
                        return cardComparison;
                    }
                }

                return 0;
            }
        }
    }

    public static int getOutput(List<String> lines) {
        int sum = 0;

        List<Hand> hands = new ArrayList<>();

        for(String line : lines) {
            String[] handAndPoints = line.split(" ");

            List<String> cards = Arrays.stream(handAndPoints[0].split("")).toList();
            int points = Integer.parseInt(handAndPoints[1]);

            hands.add(new Hand(cards, points));
        }

        Collections.sort(hands);

        for(int i = 0; i < hands.size(); i++) {
            sum += (i + 1) * hands.get(i).points;
        }

        return sum;
    }

    public static void main(String[] args) {
        List<String> lines = getInput();
        int output = getOutput(lines);

        System.out.println(output);
    }
}
