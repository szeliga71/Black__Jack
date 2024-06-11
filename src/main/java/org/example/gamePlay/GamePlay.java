package org.example.gamePlay;


import org.example.model.Card;
import org.example.model.House;
import org.example.model.Player;

import java.util.Scanner;

public class GamePlay {

    private final DeckService deckService = new DeckService();
    private final Validators validators = new Validators(new Scanner(System.in));
    private final Player player;
    private final House house;
    private final Scanner scanner = new Scanner(System.in);
    private int wager = 0;
    private boolean wouldYouPlay = true;
    private int remainingCards = 0;

    public GamePlay(House house, Player player) {
        this.house = house;
        this.player = player;
    }

    public void start() {
        String deckId = startNewGame();
        while (wouldYouPlay && player.getPlayerPoints() > 0) {
            if (remainingCards < 2) {
                deckId = ifNotEnoughCardInDeck();
            }
            promptForNewGame(deckId);
        }
    }

    private String startNewGame() {
        player.setPlayerPoints(100);
        System.out.println("The player receives 100 points!");
        String json = deckService.getJsonFromNewDeck(validators.enterNumberOfDeck(new Scanner(System.in)));
        remainingCards = deckService.getNumbersOfRemainigCardsFromHttpResponse(json);
        return deckService.getNewDeckAndDeckId(json);

    }

    private void promptForNewGame(String deckId) {
        System.out.println(" Welcome to a new game! At this moment, you can end the game (press \"K\") or start betting (press \"G\").");
        String choice = scanner.nextLine().trim().toLowerCase();
        switch (choice) {
            case "g" -> {
                startNewRund(deckId);
                SaveGameService.saveDataAfterGame(player, house);
            }
            case "k" -> {
                endGame();
                SaveGameService.saveDataAfterGame(player, house);
            }
            default -> System.out.println("ERROR!!! Please enter the correct letter!");
        }
    }

    private void startNewRund(String deckId) {
        resetGameRund();
        wager = validators.getWager(player, new Scanner(System.in));
        drawInitialCards(deckId);
        boolean answer = true;
        while (answer) {
            if (remainingCards < 2) {
                deckId = ifNotEnoughCardInDeck();
            }
            String decision = validators.makeDecision(new Scanner(System.in));
            if (decision.equals("g")) {
                answer = handlePlayerDraw(deckId);
            } else if (decision.equals("p")) {
                answer = handlePlayerPass(deckId);
            }
        }
    }

    private String ifNotEnoughCardInDeck() {
        String json = deckService.getJsonFromNewDeck(validators.enterNumberOfDeck(new Scanner(System.in)));
        return deckService.getNewDeckAndDeckId(json);
    }

    private void playerDrawCard(String deckId) {
        String json = deckService.getJsonFromDeckAfterDrawCard(deckId);
        Card card = deckService.getCardFromJsonAfterDrawCardFromDeck(json);
        player.addCardToHand(card);
        player.setScore(player.countScore(player.getHand()));
        player.showScoreMessage();

        remainingCards = deckService.getNumbersOfRemainigCardsFromHttpResponse(json);
    }

    private void houseDrawCard(String deckId) {
        String json = deckService.getJsonFromDeckAfterDrawCard(deckId);
        Card card = deckService.getCardFromJsonAfterDrawCardFromDeck(json);
        house.addCardToHand(card);
        house.setScore(house.countScore(house.getHand()));
        house.showScoreMessage();

        remainingCards = deckService.getNumbersOfRemainigCardsFromHttpResponse(json);
    }

    private void drawInitialCards(String deckId) {
        playerDrawCard(deckId);
        houseDrawCard(deckId);
    }

    private boolean handlePlayerDraw(String deckId) {
        playerDrawCard(deckId);
        if (!(house.getScore() > 16 && house.getScore() < 21)) {
            houseDrawCard(deckId);
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
            houseDrawCard(deckId);
        }
        return checkGameOutcome();
    }

    private boolean checkGameOutcome() {
        boolean result = true;
        if (player.getScore() > 21 && house.getScore() > 21) {
            System.out.println("Both lost ! ");
            result = false;
        } else if (player.getScore() > 21) {
            System.out.println(" House win , playerScore>21");
            result = false;
        } else if (house.getScore() > 21) {
            System.out.println("Player win , houseScore>21");
            player.setPlayerPoints(player.getPlayerPoints() + (wager * 2));
            result = false;
        } else if (player.getScore() == house.getScore() && player.getScore() == 21) {
            if (player.getHand().size() > house.getHand().size()) {
                System.out.println("House win !!!");
            } else if (player.getHand().size() < house.getHand().size()) {
                System.out.println("Player is a Winner !!!");
                player.setPlayerPoints(player.getPlayerPoints() + (wager * 2));
            } else {
                System.out.println(" Draw  Tie ");
                player.setPlayerPoints(player.getPlayerPoints() + (wager));
            }
            result = false;
        } else if (player.getScore() == 21) {
            System.out.println("Player win with Blackjack!");
            player.setPlayerPoints(player.getPlayerPoints() + (wager * 2));
            result = false;
        } else if (house.getScore() == 21) {
            System.out.println("House win with Blackjack!");
            result = false;
        } else if (house.getScore() >= 17 && house.getScore() < player.getScore()) {
            System.out.println("Player win!");
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
        System.out.println("  END of Game  ");
        wouldYouPlay = false;
        player.setPlayerPoints(0);
    }
}