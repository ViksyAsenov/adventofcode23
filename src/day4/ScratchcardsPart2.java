package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ScratchcardsPart2 {
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

    public static class Card {
        public int id;
        public List<Integer> winningNumbers;
        public List<Integer> cardNumbers;

        public Card(int id, List<Integer> winningNumbers, List<Integer> cardNumbers) {
            this.id = id;
            this.winningNumbers = winningNumbers;
            this.cardNumbers = cardNumbers;
        }
    }
    public static int getOutput(List<String> lines) {
        List<Card> cards = new ArrayList<>();
        for(String line : lines) {
            int id = Integer.parseInt(line.split(": ")[0].trim().replace("Card ", "").trim());
            String[] winningNumbersAndCardNumbers = line.split(": ")[1].trim().split(" \\| ");

            List<Integer> winningNumbers = new ArrayList<>();
            List<Integer> cardNumbers = new ArrayList<>();
            for(int i = 0; i < winningNumbersAndCardNumbers.length; i++) {
                String[] numberStrings = winningNumbersAndCardNumbers[i].trim().split("\\s+");

                for (String numberString : numberStrings) {
                    int number = Integer.parseInt(numberString.trim());

                    if(i == 0) {
                        winningNumbers.add(number);
                    } else {
                        cardNumbers.add(number);
                    }
                }
            }
            cards.add(new Card(id, winningNumbers, cardNumbers));
        }

        int[] cardsCount = new int[cards.size()];
        Arrays.fill(cardsCount, 1);

        for(Card card : cards) {
            int cardCount = cardsCount[card.id - 1];

            int winningNumbers = 0;
            for(Integer number : card.winningNumbers) {
                if(card.cardNumbers.contains(number)) {
                    winningNumbers++;
                }
            }

            if(winningNumbers > 0) {
                for(int i = card.id; i < card.id + winningNumbers; i++) {
                    cardsCount[i] += cardCount;
                }
            }
        }

        return Arrays.stream(cardsCount).sum();
    }

    public static void main(String[] args) {
        List<String> lines = getInput();
        int output = getOutput(lines);

        System.out.println(output);
    }
}
