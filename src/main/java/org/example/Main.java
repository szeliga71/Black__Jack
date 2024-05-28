package org.example;

import org.example.api.DeckApiHandler;
import org.example.api.DeckMapperMethods;
import org.example.api.DeckMapperTriggers;
import org.example.gameService.SaveGame;
import org.example.gameService.TimeProvider;
import org.example.gameService.VCSFileCreator;
import org.example.model.GameData;

import java.time.LocalDate;

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

        SaveGame saveGame=new SaveGame();
        GameData gameData=new GameData(11,222,3,444,false);

        VCSFileCreator vcsFileCreator=new VCSFileCreator(new TimeProvider());
        String content=vcsFileCreator.createVCSContent(gameData);
        saveGame.saveGameHistory(content);

        LocalDate date=LocalDate.now();

        String today=date.toString();
        System.out.println(today);
    }
}