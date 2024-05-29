package org.example.model;

public class GameData {

    private int houseScore;
    private int playerScore;
    private int playerPoints;
    private int changeInPoints;
    private boolean playerWin;

    public GameData(int houseScore, int playerScore, int playerPoints, int changeInPoints, boolean playerWin) {
        this.houseScore = houseScore;
        this.playerScore = playerScore;
        this.playerPoints = playerPoints;
        this.changeInPoints = changeInPoints;
        this.playerWin = playerWin;
    }
    public int getHouseScore() {
        return houseScore;
    }

    public void setHouseScore(int houseScore) {
        this.houseScore = houseScore;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(int playerPoints) {
        this.playerPoints = playerPoints;
    }

    public int getChangeInPoints() {
        return changeInPoints;
    }

    public void setChangeInPoints(int changeInPoints) {
        this.changeInPoints = changeInPoints;
    }

    public boolean isPlayerWin() {
        return playerWin;
    }

    public void setPlayerWin(boolean playerWin) {
        this.playerWin = playerWin;
    }
}

