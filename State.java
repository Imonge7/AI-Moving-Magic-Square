//Makaluza
//Imonge
//4008241
//CSC311 2023 AI Pratical



import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class State implements Comparable<State> {
    private int hValue;

    private int pos9;
    private int[] board = new int[9];

    public State(int position9, int[] aboard) {
        this.pos9 = position9;
        this.board = aboard;
        Set_hValue();

    }

    @Override
    public int compareTo(State o) {
        if (this.hValue < o.hValue)
            return 1;
        else if (this.hValue > o.hValue)
           return -1;
        else
            return 0;
    }

    public boolean risGoalState(State board) {
        // The goal state is when the board is sorted in ascending order
        if (board.get_hValue() == 0)
            return true;
        else
            return false;
    }
    public boolean isGoalState(int[] board) {
        // The goal state is when the board is sorted in ascending order
        if (((board[0] + board[1] + board[2]) == 15) && ((board[3] + board[4] + board[5]) == 15) && ((board[6] + board[7] + board[8]) == 15) &&
                ((board[0] + board[3] + board[6]) == 15) && ((board[1] + board[4] + board[7]) == 15) && ((board[2] + board[5] + board[8]) == 15) && ((board[0] + board[4] + board[8]) == 15)&&((board[2] + board[4] + board[6])==15)){
            return true;}
        else
            return false;
    }
    public boolean isGoalStat2e() {
        // The goal state is when the board is sorted in ascending order
        for (int i = 0; i < 8; i++) {
            if (board[i] != i + 1) {
                return false;
            }
        }
        return board[8] == 9;
    }
    public int[] getBoardState() {
        return board;
    }

    // The four moves of cell 9 follow below


    //Methods to perform moves (i.e. generate children states)
    public List<State> generateSuccessorStates() {
        List<State> successorStates = new ArrayList<State>();

        //Move up
        if (pos9 > 2) {
           successorStates.add(createNewBoard(pos9, pos9 - 3));
        }
        //Move Down
        if (pos9 < 6) {
            successorStates.add(createNewBoard(pos9, pos9 + 3));
        }
        //Move left
        if (pos9 % 3 != 0) {
            successorStates.add(createNewBoard(pos9, pos9 - 1));
        }
        //Move Right
        if ((pos9 + 1) % 3 != 0) {
            successorStates.add(createNewBoard(pos9, pos9 + 1));
        }
        return successorStates;
    }

    private State swapIWithBlank(int i, int j) {
        int[] newBoard = Arrays.copyOf(board, board.length);
       // int[] newBoard = this.board;
        int temp = newBoard[i];
        newBoard[i] = newBoard[j];
        newBoard[j] = temp;
        return new State(temp, newBoard);
    }
    private State createNewBoard(int i, int j) {
        int[] newBoard = Arrays.copyOf(board, 9);
        int temp = newBoard[i];
        newBoard[i] = newBoard[j];
        newBoard[j] = temp;
        return new State(newBoard[j] == 9 ? j : i,newBoard);
    }


    public int Set_hValue() {
        int rows = Math.abs(15-(board[0] + board[1] + board[2])) + Math.abs(15-(board[3] + board[4] + board[5])) + Math.abs(15-(board[6] + board[7] + board[8]));
        int colums = Math.abs(15-(board[0] + board[3] + board[6])) + Math.abs(15-(board[1] + board[4] + board[7])) + Math.abs(15-(board[2] + board[5] + board[8]));
        int diagnal1 = Math.abs(15-(board[0] + board[4] + board[8]));
        int diagnal2 = Math.abs(15-(board[2] + board[4] + board[6]));
        return this.hValue = rows + colums + diagnal1 + diagnal2;
    }

    public int get_hValue() {
        return hValue;
    }

    //End of State class
    public static void main(String[] args) {
        State initialState = readBoardFromFile("C:\\Users\\Lordm\\Downloads\\inputA.txt");
        PriorityQueue<State> frontier = new PriorityQueue<State>();

        frontier.add(initialState);
        
        int limit = 0;
        while (!frontier.isEmpty() && limit != 700) {
            State currentState = frontier.poll();

            if (currentState.isGoalState(currentState.getBoardState())) {
                System.out.println("Goal State found");
                printBoardState(currentState.getBoardState());
                System.out.println("Goal State Heuristic value: " + currentState.get_hValue());
                return;
            }

            for (State successorState : currentState.generateSuccessorStates()) {
                //successorState.calculateHeuristicValue();
                frontier.add(successorState);
            }

            System.out.println("Board state:");
            printBoardState(currentState.getBoardState());
            System.out.println("Heuristic value: " + currentState.get_hValue());
            limit++;
        }

        System.out.println("Failed to find a solution.");
    }

    private static State readBoardFromFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            int[] board = new int[9];
            int positionOf9 = 0;

            for (int i = 0; i < 9; i++) {
                board[i] = scanner.nextInt();
                System.out.println(board[i]);
                if (board[i] == 9) {
                    positionOf9 = i;
                }
            }
            System.out.println("");
            System.out.println(positionOf9);


            return new State(positionOf9, board);
        } catch (FileNotFoundException e) {
            System.out.println("Error from reading Board in the file");
            System.exit(1);
            return null;
        }
    }
    private static void printBoardState(int[] board) {
        for (int i = 0; i < 9; i++) {
            System.out.print(board[i] + " ");
            if (i == 2 || i == 5 || i == 8) {
                System.out.println();
            }
        }
    }
}

