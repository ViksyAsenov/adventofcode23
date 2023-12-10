package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PipeMazePart1 {
    public static List<String> getInput() {
        List<String> lines = new ArrayList<>();

        try {
            File starting = new File(System.getProperty("user.dir"));
            File fileToBeRead = new File(starting, "/inputs/input10.txt");
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

    public static class Pipe {
        public char value;
        private final int row;
        private final int col;

        public List<Pipe> adjacentPipes = new ArrayList<>();
        public static Pipe startPipe = null;

        public Pipe(char value, int row, int col) {
            this.value = value;
            this.row = row;
            this.col = col;

            if(this.value == 'S') {
                startPipe = this;
            }
        }

        public void addConnectedPipes(Pipe[][] graph) {
            switch (value) {
                case '|':
                    putIfExist(getUp(graph));
                    putIfExist(getDown(graph));
                    break;
                case '-':
                    putIfExist(getLeft(graph));
                    putIfExist(getRight(graph));
                    break;
                case 'J':
                    putIfExist(getLeft(graph));
                    putIfExist(getUp(graph));
                    break;
                case 'L':
                    putIfExist(getRight(graph));
                    putIfExist(getUp(graph));
                    break;
                case 'F':
                    putIfExist(getDown(graph));
                    putIfExist(getRight(graph));
                    break;
                case '7':
                    putIfExist(getLeft(graph));
                    putIfExist(getDown(graph));
                    break;
                case '.', 'S':
                    break;
            }
        }

        private void putIfExist(Pipe pipe) {
            if (pipe != null) {
                adjacentPipes.add(pipe);
            }
        }

        private Pipe getUp(Pipe[][] graph) {
            return row > 0 ? graph[row - 1][col] : null;
        }
        private Pipe getDown(Pipe[][] graph) {
            return row < graph.length -1 ? graph[row + 1][col] : null;
        }
        private Pipe getLeft(Pipe[][] graph) {
            return col > 0 ? graph[row][col - 1] : null;
        }
        private Pipe getRight(Pipe[][] graph) {
            return col < graph.length - 1 ? graph[row][col + 1] : null;
        }
    }


    public static int getOutput(List<String> lines) {
        int height = lines.size();
        int width = lines.get(0).length();

        Pipe[][] pipeGraph = new Pipe[height][width];
        //List<Pipe> allPipes = new ArrayList<>();

        for(int row = 0; row < lines.size(); row++) {
            for(int col = 0; col < lines.get(row).length(); col++) {
                char currentValue = lines.get(row).charAt(col);
                
                Pipe pipe = new Pipe(currentValue, row, col);
                pipeGraph[row][col] = pipe;
                //allPipes.add(pipe);
            }
        }

        for(int row = 0; row < pipeGraph.length; row++) {
            for(int col = 0; col < pipeGraph[row].length; col++) {
                Pipe pipe = pipeGraph[row][col];
                pipe.addConnectedPipes(pipeGraph);

                for(Pipe currentPipe : pipe.adjacentPipes) {
                    if(currentPipe.value == 'S') {
                        currentPipe.adjacentPipes.add(pipe);
                    }
                }
            }
        }

        //TODO: MAKE IT WORK
        return getLongestDistanceFromStart();
    }

    public static int getLongestDistanceFromStart() {
        Queue<Pipe> queueOfPipes = new LinkedList<>();
        Set<Pipe> visitedPipes = new HashSet<>();

        Pipe startPipe = Pipe.startPipe;

        queueOfPipes.offer(startPipe);
        visitedPipes.add(startPipe);


        while (!queueOfPipes.isEmpty()) {
            Pipe pipe = queueOfPipes.poll();
            List<Pipe> adjacentPipes = pipe.adjacentPipes;

            for(Pipe currentPipe : adjacentPipes) {
                if(!visitedPipes.contains(currentPipe)) {
                    queueOfPipes.offer(currentPipe);
                    visitedPipes.add(currentPipe);
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        List<String> lines = getInput();
        int output = getOutput(lines);

        System.out.println(output);
    }
}
