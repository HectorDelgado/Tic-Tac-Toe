package com.hectordelgado;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {

    private final int NUM_ROWS = 4;
    private final int NUM_COLS = 4;
    private final String EMPTY_GAME_SPACE = "#";

    private String[][] gameBoard = new String[NUM_ROWS][NUM_COLS];
    private boolean player1Turn = true;

    /**
     * Constructor initializes all spaces in the
     * game board with the empty space character.
     * (Row and column headers will be displayed correctly at runtime).
     */
    public TicTacToe() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                gameBoard[row][col] = EMPTY_GAME_SPACE;
            }
        }
    }

    public void startGame() {
        // Create scanner for user input and get 2
        // random colors to help users identify themselves.
        Scanner sc = new Scanner(System.in);
        String player1Color = ConsoleColors.getRandomRegularColor();
        String player2Color = ConsoleColors.getRandomRegularColor();

        System.out.println("\n" + ConsoleColors.CYAN_BACKGROUND + "Welcome to Tic-Tac-Toe!" + ConsoleColors.RESET);

        // Prompt users for a name
        System.out.print(player1Color + "Enter name for player 1: " + ConsoleColors.RESET);
        String player1 = sc.nextLine();
        System.out.print(player2Color + "Enter name for player 2: " + ConsoleColors.RESET);
        String player2 = sc.nextLine();

        boolean gameIsActive = true;

        do {
            String currentPlayer = "";
            String currentPlayerColor = "";
            String currentGamePiece = "";
            String column = "";
            int row = 0;

            if (player1Turn) {
                currentPlayer = player1;
                currentPlayerColor = player1Color;
                currentGamePiece = "X";
            } else {
                currentPlayer = player2;
                currentPlayerColor = player2Color;
                currentGamePiece = "O";
            }

            // Keeps prompting the user to place a
            // game piece until the location is available,
            // valid, and the game is still active.
            while (true) {
                System.out.printf(currentPlayerColor + "%s's turn." + ConsoleColors.RESET, currentPlayer);
                System.out.println();
                displayBoard();

                System.out.print("Choose a column (A-C): ");
                column = sc.nextLine();

                System.out.print("Choose a row (1-3): ");

                try {
                    row = sc.nextInt();
                    sc.nextLine();
                } catch (InputMismatchException ex) {
                    sc.nextLine();
                    System.out.println("Wrong input for row, row must be a number (1-3)");
                    row = 0;
                }



                if (locationIsAvailable(column, row)) {
                    if (positionIsValid(column, row)) {
                        placeGamePiece(columnToPosition(column), row, currentGamePiece);

                        if (!gameStillActive(currentGamePiece)) {
                            displayWinningBoard(currentPlayer);
                            gameIsActive = false;
                        }
                        break;
                    } else {
                        System.out.println("Error, illegal location! Column must be A-C and row must be 1-3.");
                    }
                } else {
                    System.out.println("Error. Location is taken.");
                }
            }
            player1Turn = !player1Turn;
        } while (gameIsActive);
    }

    public void displayBoard() {
        char columnHeader = 'A';
        char rowHeaders = '1';

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if (row == 0 && col == 0)
                    System.out.print("  ");
                else if (row == 0) {
                    System.out.print(ConsoleColors.RED + columnHeader++ + " " + ConsoleColors.RESET);
                }
                else if (col == 0) {
                    System.out.print(ConsoleColors.RED + rowHeaders++ + " " + ConsoleColors.RESET);
                }
                else {
                    System.out.print(ConsoleColors.GREEN + gameBoard[row][col] + " " + ConsoleColors.RESET);
                }
            }
            System.out.println();
        }
    }

    private void placeGamePiece(int column, int row, String gamePiece) {
        gameBoard[row][column] = gamePiece;
    }

    private boolean locationIsAvailable(String column, int row) {
        return gameBoard[row][columnToPosition(column)].equals(EMPTY_GAME_SPACE);
    }

    private boolean positionIsValid(String column, int row) {
        return (columnToPosition(column) > 0 && columnToPosition(column) < NUM_COLS) && (row > 0 && row < NUM_ROWS);
    }

    private int columnToPosition(String col) {
        int position;

        switch (col) {
            case "A":
            case "a":
                position = 1;
                break;
            case "B":
            case "b":
                position = 2;
                break;
            case "C":
            case "c":
                position = 3;
                break;
            default:
                position = 0;
        }

        return position;
    }

    private boolean gameStillActive(String gamePiece) {
        boolean horizontalStrategy1 = gameBoard[1][1].equals(gamePiece) && gameBoard[1][2].equals(gamePiece) && gameBoard[1][3].equals(gamePiece);
        boolean horizontalStrategy2 = gameBoard[2][1].equals(gamePiece) && gameBoard[2][2].equals(gamePiece) && gameBoard[2][3].equals(gamePiece);
        boolean horizontalStrategy3 = gameBoard[3][1].equals(gamePiece) && gameBoard[3][2].equals(gamePiece) && gameBoard[3][3].equals(gamePiece);

        boolean verticalStrategy1 = gameBoard[1][1].equals(gamePiece) && gameBoard[2][1].equals(gamePiece) && gameBoard[3][1].equals(gamePiece);
        boolean verticalStrategy2 = gameBoard[1][2].equals(gamePiece) && gameBoard[2][2].equals(gamePiece) && gameBoard[3][2].equals(gamePiece);
        boolean verticalStrategy3 = gameBoard[1][3].equals(gamePiece) && gameBoard[2][3].equals(gamePiece) && gameBoard[3][3].equals(gamePiece);

        boolean diagonalStrategy1 = gameBoard[1][1].equals(gamePiece) && gameBoard[2][2].equals(gamePiece) && gameBoard[3][3].equals(gamePiece);
        boolean diagonalStrategy2 = gameBoard[1][3].equals(gamePiece) && gameBoard[2][2].equals(gamePiece) && gameBoard[3][1].equals(gamePiece);

        return !verticalStrategy1 && !verticalStrategy2 && !verticalStrategy3
                && !horizontalStrategy1 && !horizontalStrategy2 && !horizontalStrategy3
                && !diagonalStrategy1 && !diagonalStrategy2;
    }

    private void displayWinningBoard(String winningPlayer) {
        try {
            for (int i = 0; i < 16; i++) {
                System.out.print("!-!");
                Thread.sleep(100);
            }
            System.out.printf("\nCongratulations %s, you've won!\n", winningPlayer);
            displayBoard();
            Thread.sleep(400);
            for (int i = 0; i < 16; i++) {
                System.out.print("!-!");
                Thread.sleep(100);
            }

        } catch (InterruptedException e) {
            System.out.println("System interrupted");
        }
    }
}
