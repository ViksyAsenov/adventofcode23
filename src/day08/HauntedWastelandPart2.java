package day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class HauntedWastelandPart2 {
    public static List<String> getInput() {
        List<String> lines = new ArrayList<>();

        try {
            File starting = new File(System.getProperty("user.dir"));
            File fileToBeRead = new File(starting, "/inputs/input8.txt");
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

    public static class NetworkNode {
        private String point;
        private NetworkNode leftNode;
        private NetworkNode rightNode;
        public static List<NetworkNode> heads = new ArrayList<>();
        public static List<NetworkNode> allNetworkNodes = new ArrayList<>();

        private NetworkNode(String point) {
            this.point = point;
            this.leftNode = null;
            this.rightNode = null;
        }

        private NetworkNode(String point, String leftPoint, String rightPoint) {
            NetworkNode currentNode = getOrCreateNode(point);
            addNodeToNetworkIfAbsent(currentNode);

            currentNode.leftNode = getOrCreateNode(leftPoint);
            addNodeToNetworkIfAbsent(currentNode.leftNode);

            currentNode.rightNode = getOrCreateNode(rightPoint);
            addNodeToNetworkIfAbsent(currentNode.rightNode);

            if(currentNode.getPoint().endsWith("A")) {
                heads.add(currentNode);
            }
        }

        private NetworkNode getOrCreateNode(String targetPoint) {
            for (NetworkNode node : NetworkNode.allNetworkNodes) {
                if (node.getPoint().equals(targetPoint)) {
                    return node;
                }
            }

            return new NetworkNode(targetPoint);
        }

        private void addNodeToNetworkIfAbsent(NetworkNode node) {
            boolean exists = false;
            for(NetworkNode currentNode : NetworkNode.allNetworkNodes) {
                if (currentNode.getPoint().equals(node.getPoint())) {
                    exists = true;
                    break;
                }
            }

            if(!exists) {
                NetworkNode.allNetworkNodes.add(node);
            }
        }

        public static void createNode(String point, String leftPoint, String rightPoint) {
            new NetworkNode(point, leftPoint, rightPoint);
        }

        public String getPoint() {
            return point;
        }

        public NetworkNode getLeftNode() {
            return leftNode;
        }

        public NetworkNode getRightNode() {
            return rightNode;
        }
    }

    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    private static long findLCM(List<Integer> numbers) {
        long result = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            result = lcm(result, numbers.get(i));
        }

        return result;
    }

    public static long getOutput(List<String> lines) {
        String[] instructions = lines.remove(0).split("");
        for(String line : lines) {
            if(line.isEmpty()) {
                continue;
            }

            String[] pointAndNodes = line.split(" = ");
            String[] nodes = pointAndNodes[1].substring(1, pointAndNodes[1].length() - 1).split(", ");

            NetworkNode.createNode(pointAndNodes[0], nodes[0], nodes[1]);
        }

        List<NetworkNode> currentNodes = NetworkNode.heads;
        List<Integer> numberOfStepsForEachHead = new ArrayList<>();

        for(NetworkNode currentNode : currentNodes) {
            boolean isEndReached = false;
            int sum = 0;

            while (!isEndReached) {
                for(String instruction : instructions) {
                    if(currentNode.getPoint().endsWith("Z")) {
                        isEndReached = true;
                        break;
                    }

                    if(instruction.equals("L")) {
                        currentNode = currentNode.getLeftNode();
                    } else {
                        currentNode = currentNode.getRightNode();
                    }

                    sum++;
                }
            }

            numberOfStepsForEachHead.add(sum);
        }

        return findLCM(numberOfStepsForEachHead);
    }

    public static void main(String[] args) {
        List<String> lines = getInput();
        long output = getOutput(lines);

        System.out.println(output);
    }
}
