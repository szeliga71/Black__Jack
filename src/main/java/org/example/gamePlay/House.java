package org.example.gamePlay;

import org.example.api.DeckApiHandler;
import org.example.api.DeckMapperTriggers;
import org.example.model.Card;

import java.util.ArrayList;
import java.util.List;

public class House {

    private final DeckApiHandler deckApiHandler = new DeckApiHandler();
    private final DeckMapperTriggers deckMapperTriggers = new DeckMapperTriggers();
    private List<Card> hand;
    private int score;
    private static int remainigCards;

    public House() {
        hand = new ArrayList<>();
        remainigCards=0;
    }

    public void drawCard(String deckId) {
        String json = getJsonFromDeckAfterDrawCard(deckId);
        remainigCards=deckMapperTriggers.remainingCardMapper(json);
        addCardToHand(getCardFromJsonDeck(json));
        setScore(countScore(hand));
        showScoreMessage();
    }

    private String getJsonFromDeckAfterDrawCard(String deckId) {
        return deckApiHandler.drawCards(deckId, 1).body();
    }

    private Card getCardFromJsonDeck(String cardJson) {
        return deckMapperTriggers.cardMapper(cardJson);
    }

    private void addCardToHand(Card card) {
        hand.add(card);
    }

    private int countScore(List<Card> hand) {
        return (int) sumValuesOfCards(hand);
    }

    private long sumValuesOfCards(List<Card> cards) {
        return cards.stream().mapToInt(this::numberValueFromCard).sum();
    }

    private int numberValueFromCard(Card card) {
        int value = card.getValue();
        return switch (value) {
            case 11, 12, 13 -> 10;
            case 14 -> 11;
            default -> value;
        };
    }

    private void showScoreMessage() {
        System.out.println(getClass().getSimpleName() + " otrzymal : " + hand);
        System.out.println(getClass().getSimpleName() + " Score : " + score);
    }

    public String getJsonFromNewDeck(int numberOfDeck) {
        return deckApiHandler.getShuffledDecks(numberOfDeck).body();
    }

    public String getNewDeckAndDeckId(String jsonFromNewDeck) {
        remainigCards=deckMapperTriggers.remainingCardMapper(jsonFromNewDeck);
        return deckMapperTriggers.deckTokenId(jsonFromNewDeck);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getRemainigCards() {
        return remainigCards;
    }

}
