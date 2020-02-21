package com.hectordelgado;

import java.util.*;

public class TicTacToe {
    private final int NUM_ROWS = 4;
    private final int NUM_COLS = 4;
    private final String EMPTY_GAME_SPACE = "#";

    private List<String> GAME_PIECES = new LinkedList<>(Arrays.asList("X", "O"));
    private String[][] gameBoard = new String[NUM_ROWS][NUM_COLS];
    private boolean player1Turn = true;
    private Scanner sc = new Scanner(System.in);

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

    /**
     * Used as the main runner for the game.
     * When called, 2 players are created and the game begins.
     * The logic keeps iterating until a player wins or a draw is determined.
     */
    public void startGame() {
        System.out.println("\n" + ConsoleColors.CYAN_BACKGROUND +
                "Welcome to Tic-Tac-Toe!" + ConsoleColors.RESET);

        Player player01 = initializePlayer();
        Player player02 = initializePlayer();
        Player currentPlayer = new Player();
        boolean gameIsActive = true;

        do {
            // Switch current player
            currentPlayer.copy(player1Turn ? player01 : player02);

            // Get valid coordinates from user and place game piece on game board.
            GameCoordinates coordinates = getUserInput(currentPlayer);
            placeGamePiece(coordinates.getX(), coordinates.getY(), currentPlayer.getPlayerGamePiece());

            if (!gameStillActive(currentPlayer.getPlayerGamePiece())) {
                displayWinningBoard(currentPlayer.getPlayerName());
                gameIsActive = false;
            } else if (gameIsDraw()) {
                displayBoard();
                System.out.println("GAME IS A BUST. NO ONE WINS");
                gameIsActive = false;
            }

            player1Turn = !player1Turn;
        } while (gameIsActive);
    }

    private Player initializePlayer() {
        // Create new player
        String playerColor = ConsoleColors.getRandomRegularColor();
        int random = (int) (GAME_PIECES.size() * Math.random());
        String playerGamePiece = GAME_PIECES.get(random);
        GAME_PIECES.remove(random);
        System.out.print("Enter name for new player: ");
        String playerName = sc.nextLine();

        return new Player(playerName, playerColor, playerGamePiece);
    }

    private GameCoordinates getUserInput(Player currentPlayer) {
        int column = 0;
        int row = 0;
        boolean coordinatesAreValid = false;

        while (!coordinatesAreValid) {
            System.out.printf(currentPlayer.getPlayerColor() +  "%s's turn (your sign is '%s')." + ConsoleColors.RESET + "\n", currentPlayer.getPlayerName(), currentPlayer.getPlayerGamePiece());
            displayBoard();
            System.out.print("Choose a location (ex: A3, B1, C2): ");
            String[] coordinates = sc.nextLine().replaceAll("\\s+","").split("");

            if (coordinates.length == 2) {
                try {
                    column = columnToPosition(coordinates[0]);
                    row = Integer.parseInt(coordinates[1]);

                    if (positionIsValid(column, row)) {
                        if (locationIsAvailable(column, row)) {
                            coordinatesAreValid = true;
                        } else {
                            System.out.println("Error. Location is taken.");
                        }
                    } else {
                        System.out.println("Error, illegal location! Column must be A-C and row must be 1-3.");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Error: wrong input for coordinate.");
                }
            } else {
                System.out.println("Error, size not met: " + coordinates.length);
            }
        }

        return new GameCoordinates(column, row);
    }

    /**
     * Prints the current game board along with column and row headers.
     */
    private void displayBoard() {
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

    /**
     * Places a game piece at the specified location.
     * @param column column entered by user
     * @param row row entered by user
     * @param gamePiece the users game piece (X or O)
     */
    private void placeGamePiece(int column, int row, String gamePiece) {
        gameBoard[row][column] = gamePiece;
    }

    /**
     * Checks if a position is available (not occupied).
     * @param column column entered by user
     * @param row row entered by user
     * @return true if the location is not occupied
     */
    private boolean locationIsAvailable(int column, int row) {
        return gameBoard[row][column].equals(EMPTY_GAME_SPACE);
    }

    /**
     * Checks if a position entered is a valid position .
     * (column must be A-B, row must be 1-3)
     * @param column column entered by user
     * @param row row entered by user
     * @return true if the position is within a valid range
     */
    private boolean positionIsValid(int column, int row) {
        return (column > 0 && column < NUM_COLS) && (row > 0 && row < NUM_ROWS);
    }

    /**
     * Converts the users column character (A-C) into a
     * numerical position that can be used to access
     * the multi-dimensional game board.
     * Defaults to 0 (an invalid position) if the
     * character is not recognized as a valid position.
     * @param column the character the user entered
     * @return the index location of the column
     */
    private int columnToPosition(String column) {
        int position;

        switch (column) {
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

    /**
     * Checks if the game is active or over by seeing if
     * the current game piece connects in pre-determined patterns
     * (3 in a row going horizontally, vertically, or diagonally).
     * @param gamePiece the current game piece in place (X or O)
     * @return returns true if the game is still active, otherwise false.
     */
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

    /**
     * Checks if the game is a draw by iterating through
     * the valid positions and seeing if there is
     * at least one empty space available.
     * @return true if there are no more locations available
     */
    private boolean gameIsDraw() {
        boolean boardIsFull = true;

        for (int i = 1; i < NUM_ROWS; i++) {
            for (int j = 1; j < NUM_COLS; j++) {
                if (gameBoard[i][j].equals(EMPTY_GAME_SPACE)) {
                    boardIsFull = false;
                    break;
                }
            }
        }

        return boardIsFull;
    }

    /**
     * Displays a delayed message congratulating
     * the winner and showing the final game board.
     * @param winningPlayer the username of the winning player
     */
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

        } catch (InterruptedException ex) {
            System.out.println("System interrupted");
        }
    }
}
