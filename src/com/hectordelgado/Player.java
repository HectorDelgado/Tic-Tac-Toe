package com.hectordelgado;

public class Player {
    private String playerName = "";
    private String playerColor = "";
    private String playerGamePiece = "";

    public Player() { }

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
