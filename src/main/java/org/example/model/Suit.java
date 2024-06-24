package org.example.model;

public enum Suit {

    HEARTS, DIAMONDS, CLUBS, SPADES;

    public static Suit fromStringtoSuit(String text) {
        return switch (text.toLowerCase()) {
            case "hearts" -> HEARTS;
            case "diamonds" -> DIAMONDS;
            case "clubs" -> CLUBS;
            case "spades" -> SPADES;
            default -> throw new IllegalArgumentException("Cannot parse " + text);
        };
    }
}