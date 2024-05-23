package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!Data model mappers");

        String st = "ace";

        DeckMapperMethods deckMapperMethods = new DeckMapperMethods();
        DeckMapperTriggers deckMapperTriggers=new DeckMapperTriggers();

        System.out.println(deckMapperMethods.valueFromStringToInt(st));


        DeckApiHandler deckApiHandler=new DeckApiHandler();
        String response1=deckApiHandler.getShuffledDecks(5).body();
        String deckID=deckMapperTriggers.deckTokenId(response1);
        String response2=deckApiHandler.drawCards(deckID,2).body();

        System.out.println(deckMapperTriggers.cardMapper(response2));

        System.out.println(deckMapperTriggers.cardsMapperList(response2));
    }
}