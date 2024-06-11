package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class House {

    private List<Card> hand;
    private int score;

    public House() {
        hand = new ArrayList<>();
    }

    public void addCardToHand(Card card){
        hand.add(card);
    }

    public int countScore(List<Card> hand) {
        return (int) sumValuesOfCards(hand);

    }

    private long sumValuesOfCards(List<Card> cards) {
        return cards.stream().collect(Collectors.summingInt(this::numberValueFromCard));

    }

    private int numberValueFromCard(Card card) {
        int value = card.getValue();
        return switch (value) {
            case 11, 12, 13 -> 10;
            case 14 -> 11;
            default -> value;
        };
    }
    public void showScoreMessage() {
        System.out.println(getClass().getSimpleName() + " otrzymal : " + hand);
        System.out.println(getClass().getSimpleName() + " Score : " + score);
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

}
