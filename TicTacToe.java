import java.util.*;

public class TicTacToe {
    static final char HUMAN = 'X';
    static final char AI = 'O';
    static final char EMPTY = ' ';

    static char[][] board = {
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
    };

    // Print the board
    static void printBoard() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    // Check if moves left
    static boolean isMovesLeft(char[][] b) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (b[i][j] == EMPTY)
                    return true;
        return false;
    }

    // Evaluate board
    static int evaluate(char[][] b) {
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (b[row][0] == b[row][1] && b[row][1] == b[row][2]) {
                if (b[row][0] == AI) return +10;
                else if (b[row][0] == HUMAN) return -10;
            }
        }

        // Check columns
        for (int col = 0; col < 3; col++) {
            if (b[0][col] == b[1][col] && b[1][col] == b[2][col]) {
                if (b[0][col] == AI) return +10;
                else if (b[0][col] == HUMAN) return -10;
            }
        }

        // Check diagonals
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == AI) return +10;
            else if (b[0][0] == HUMAN) return -10;
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == AI) return +10;
            else if (b[0][2] == HUMAN) return -10;
        }

        return 0;
    }

    // Minimax function with recursion + backtracking
    static int minimax(char[][] b, int depth, boolean isMax) {
        int score = evaluate(b);

        // If AI won
        if (score == 10) return score - depth;

        // If HUMAN won
        if (score == -10) return score + depth;

        // If draw
        if (!isMovesLeft(b)) return 0;

        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (b[i][j] == EMPTY) {
                        b[i][j] = AI;
                        best = Math.max(best, minimax(b, depth + 1, false));
                        b[i][j] = EMPTY; // backtrack
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (b[i][j] == EMPTY) {
                        b[i][j] = HUMAN;
                        best = Math.min(best, minimax(b, depth + 1, true));
                        b[i][j] = EMPTY; // backtrack
                    }
                }
            }
            return best;
        }
    }

    // Find best move for AI
    static int[] findBestMove(char[][] b) {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (b[i][j] == EMPTY) {
                    b[i][j] = AI;
                    int moveVal = minimax(b, 0, false);
                    b[i][j] = EMPTY;

                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    // Main game loop
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Tic Tac Toe (You = X, AI = O)");
        printBoard();

        while (true) {
            // Human move
            System.out.print("Enter your move (row[1-3] col[1-3]): ");
            int row = sc.nextInt() - 1;
            int col = sc.nextInt() - 1;

            if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != EMPTY) {
                System.out.println("Invalid move! Try again.");
                continue;
            }
            board[row][col] = HUMAN;
            printBoard();

            if (evaluate(board) == -10) {
                System.out.println("You win!");
                break;
            }
            if (!isMovesLeft(board)) {
                System.out.println("It's a draw!");
                break;
            }

            // AI move
            int[] bestMove = findBestMove(board);
            board[bestMove[0]][bestMove[1]] = AI;
            System.out.println("AI has played:");
            printBoard();

            if (evaluate(board) == 10) {
                System.out.println("AI wins!");
                break;
            }
            if (!isMovesLeft(board)) {
                System.out.println("It's a draw!");
                break;
            }
        }

        sc.close();
    }
}
