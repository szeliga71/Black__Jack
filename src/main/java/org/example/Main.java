package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!Data model mappers");

        String st = "ace";

        DeckServisMetods deckServisMetods = new DeckServisMetods();

        System.out.println(deckServisMetods.valueFromStringToInt(st));
    }
}