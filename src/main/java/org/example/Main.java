package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!Data model mappers");

        String st = "ace";

        DeckApiServis deckApiServis = new DeckApiServis();

        System.out.println(deckApiServis.valueFromStringToInt(st));
    }
}