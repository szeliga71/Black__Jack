package org.example.gamePlay;

import org.example.api.DeckApiHandler;
import org.example.api.DeckMapperTriggers;
import org.example.model.Card;

public class DeckService {

    private final DeckApiHandler deckApiHandler = new DeckApiHandler();
    private final DeckMapperTriggers deckMapperTriggers = new DeckMapperTriggers();


    public String getJsonFromDeckAfterDrawCard(String deckId) {
        return deckApiHandler.drawCards(deckId, 1).body();
    }
    public String getJsonFromNewDeck(int numberOfDeck) {
        return deckApiHandler.getShuffledDecks(numberOfDeck).body();
    }


    public Card getCardFromJsonAfterDrawCardFromDeck(String cardJson) {
        return deckMapperTriggers.cardMapper(cardJson);
    }


    public String getNewDeckAndDeckId(String jsonFromNewDeck) {
        return deckMapperTriggers.deckTokenId(jsonFromNewDeck);
    }

    public int getNumbersOfRemainigCardsFromHttpResponse(String jsonFromNewDeck) {
        return deckMapperTriggers.remainingCardMapper(jsonFromNewDeck);
    }
}
