package com.hectordelgado;

/**
 * Represents a pair of coordinates in the Tic Tac Toe game.
 * Coordinates are represented by an x and y value (ex: 1,2 or 3,3).
 */
public class GameCoordinates {
    private final int x;    // The first value of the coordinates
    private final int y;    // The second value of the coordinates

    /**
     * Two-parameter constructor.
     * Initializes the immutable values of the coordinates.
     * @param x the first value to store
     * @param y the second value to store
     */
    public GameCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
