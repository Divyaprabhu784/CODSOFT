import java.util.Scanner;

public class TicTacToe {

    static char[][] board = { {' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '} };
    public static void main(String[] args) {
        System.out.println("Welcome to Tic-Tac-Toe! Game..1 Human player is 'X' and the AI is 'O'.");
        print_Board();
        while (true) {
            playerMove();
            if (checkWinner('X')) {
                print_Board();
                System.out.println("You win!");
                break;
            } else if (isDraw()) {
                print_Board();
                System.out.println("It's a draw!");
                break;
            }

            ai_Move();
            print_Board();
            if (checkWinner('O')) {
                System.out.println("AI is the winner!");
                break;
            } else if (isDraw()) {
                System.out.println("It's a draw!");
                break;
            }
        }
    }

    // Print the board
    static void print_Board() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + "|");
            }
            System.out.println("\b"); // Remove the extra '|'
            if (row != board[board.length - 1]) {
                System.out.println("-----");
            }
        }
    }

    // Check if a player has won
    static boolean checkWinner(char player) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    // Check if the board is full (draw)
    static boolean isDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') return false;
            }
        }
        return true;
    }

    // Player move
    static Scanner scanner = new Scanner(System.in);
    static void playerMove() {
        int row, col;
        while (true) {
            System.out.print("Player:X, make your move (row and column: 0, 1, or 2): ");
            row = scanner.nextInt();
            col = scanner.nextInt();
            if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
                board[row][col] = 'X';
                break;
            } else {
                System.out.println("Space is already filled try another way");
            }
        }
    }

    // AI move using Minimax with Alpha-Beta Pruning
    static void ai_Move() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'O';
                    int score = minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        board[bestMove[0]][bestMove[1]] = 'O';
        System.out.println("AI chose position (" + bestMove[0] + ", " + bestMove[1] + ")");
    }

    // Minimax function with Alpha-Beta Pruning
    static int minimax(char[][] board, int depth, boolean isMaximizing, int alpha, int beta) {
        // Check for terminal states:
        if (checkWinner('O')) return 10 - depth;
        if (checkWinner('X')) return depth - 10;
        if (isDraw()) return 0;

        // If it's the maximizing player's turn ('O')
        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        int eval = minimax(board, depth + 1, false, alpha, beta);
                        board[i][j] = ' ';
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha) return maxEval;
                    }
                }
            }
            return maxEval;
        } else {
             // If it's the minimizing player's turn ('X')
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        int eval = minimax(board, depth + 1, true, alpha, beta);
                        board[i][j] = ' ';
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        if (beta <= alpha) return minEval;
                    }
                }
            }
            return minEval;
        }
    }
}