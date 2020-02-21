package com.hectordelgado;

/**
 * Represents a player in the Tic Tac Toe game.
 * A Player is defined as having a name, color, and a game piece.
 */
public class Player {
    private String playerName;      // The username for this Player
    private String playerColor;     // The text color for this Player
    private String playerGamePiece; // The game piece for this Player (X or O)

    /**
     * Default constructor.
     */
    public Player() {
        this("", "", "");
    }

    /**
     * Three-parameter constructor.
     * @param playerName the players username
     * @param playerColor the players text color
     * @param playerGamePiece the players game piece
     */
    public Player(String playerName, String playerColor, String playerGamePiece) {
        this.playerName = playerName;
        this.playerColor = playerColor;
        this.playerGamePiece = playerGamePiece;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public String getPlayerGamePiece() {
        return playerGamePiece;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    public void setPlayerGamePiece(String playerGamePiece) {
        this.playerGamePiece = playerGamePiece;
    }

    /**
     * Copies the properties of a Player object into this one.
     * @param player the Player object that data will be copied from
     */
    public void copy(Player player) {
        playerName = player.playerName;
        playerColor = player.playerColor;
        playerGamePiece = player.playerGamePiece;
    }

    @Override
    public String toString() {
        return "Player {\n" +
                "\tplayerName = " + playerName + "\n" +
                "\tplayerGamePiece = " + playerGamePiece + "\n" +
                "}";
    }
}
