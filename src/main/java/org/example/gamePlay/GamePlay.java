package org.example.gamePlay;


import java.util.Scanner;

public class GamePlay {

    private final Validators validators = new Validators();
    private final Player player;
    private final House house;
    private final Scanner scanner = new Scanner(System.in);
    private int wager = 0;
    private boolean wouldYouPlay = true;


    public GamePlay(House house, Player player) {
        this.house = house;
        this.player = player;
    }

    public void start() {
        String deckId = startNewGame();
        while (wouldYouPlay && player.getPlayerPoints() > 0) {
            if (player.getRemainigCards() < 2) {
                deckId = isEnoughCardInDeck();
            }
            promptForNewGame(deckId);
        }
    }

    private String isEnoughCardInDeck() {
        return player.getNewDeckAndDeckId(player.getJsonFromNewDeck(validators.enterNumberOfDeck()));
    }

    private String startNewGame() {
        player.setPlayerPoints(100);
        System.out.println(" Player otrzymuje 100 punktow ! ");
        wouldYouPlay = true;
        String json = house.getJsonFromNewDeck(validators.enterNumberOfDeck());
        return house.getNewDeckAndDeckId(json);
    }


    private void promptForNewGame(String deckId) {
        System.out.println(" Witaj w nowej rozgrywce ! W tym momencie mozesz zakonczyc gre (press \"K\") lub rozpoczac obstawianie (press \"G\")");
        String choice = scanner.nextLine().trim().toLowerCase();
        switch (choice) {
            case "g" -> {
                startNewRund(deckId);
                SaveGameService.saveDataAfterGame(player,house);
            }
            case "k" -> {
                endGame();
                SaveGameService.saveDataAfterGame(player,house);}
            default -> System.out.println("BLAD!!!, prosze wprowadzic prawidlowa litere ! ");
        }
    }

    private void startNewRund(String deckId) {
        resetGameRund();
        wager = validators.getWager(player);
        drawInitialCards(deckId);
        boolean answer = true;
        while (answer) {

            if (player.getRemainigCards() < 2) {
                deckId = isEnoughCardInDeck();
            }

            String decision = validators.makeDecision();
            if (decision.equals("g")) {
                answer = handlePlayerDraw(deckId);
            } else if (decision.equals("p")) {
                answer = handlePlayerPass(deckId);
            }
        }
    }

    private void drawInitialCards(String deckId) {
        player.drawCard(deckId);
        house.drawCard(deckId);
    }


    private boolean handlePlayerDraw(String deckId) {
        player.drawCard(deckId);
        if (!(house.getScore() > 16 && house.getScore() < 21)) {
            house.drawCard(deckId);
        }
        return checkGameOutcome();
    }

    private boolean handlePlayerPass(String deckId) {
        if (house.getScore() >= 17 && house.getScore() < 21 && player.getScore() == house.getScore()) {
            house.setScore(21);
            player.setScore(21);
        } else if (house.getScore() >= 17 && house.getScore() < 21 && player.getScore() < house.getScore()) {
            house.setScore(21);
        } else if (!(house.getScore() > 16 && house.getScore() < 21)) {
            house.drawCard(deckId);
        }
        return checkGameOutcome();
    }

    private boolean checkGameOutcome() {
        boolean result = true;
        if (player.getScore() > 21 && house.getScore() > 21) {
            System.out.println("OBIE  FURY both lost");
            result = false;
        } else if (player.getScore() > 21) {
            System.out.println(" House win playerScore>21");
            result = false;
        } else if (house.getScore() > 21) {
            System.out.println("Player win houseScore>21");
            player.setPlayerPoints(player.getPlayerPoints() + (wager * 2));
            result = false;
        } else if (player.getScore() == house.getScore() && player.getScore() == 21) {
            if (player.getHand().size() > house.getHand().size()) {
                System.out.println("House wins!");
            } else if (player.getHand().size() < house.getHand().size()) {
                System.out.println("Player wins!");
                player.setPlayerPoints(player.getPlayerPoints() + (wager * 2));
            } else {
                System.out.println("Push (tie).");
                player.setPlayerPoints(player.getPlayerPoints() + (wager));
            }
            result = false;
        } else if (player.getScore() == 21) {
            System.out.println("Player wins with Blackjack!");
            player.setPlayerPoints(player.getPlayerPoints() + (wager * 2));
            result = false;
        } else if (house.getScore() == 21) {
            System.out.println("House wins with Blackjack!");
            result = false;
        } else if (house.getScore() >= 17 && house.getScore() < player.getScore()) {
            System.out.println("Player wins!");
            player.setPlayerPoints(player.getPlayerPoints() + (wager * 2));
            result = false;
        }
        return result;
    }

    private void resetGameRund() {
        wager = 0;
        house.setScore(0);
        player.setScore(0);
        house.getHand().clear();
        player.getHand().clear();
        player.setPlayerWin(false);
    }

    private void endGame() {
        System.out.println("koniec gry ");
        wouldYouPlay = false;
        player.setPlayerPoints(0);
    }
}










