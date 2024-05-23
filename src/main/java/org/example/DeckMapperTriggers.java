package org.example;

import java.util.List;

public class DeckMapperTriggers {

    private final DeckMapperMethods deckMapperMethods = new DeckMapperMethods();

    public DeckToken deckTokenMapper(String json) {
        deckMapperMethods.validateJson(json);
        return deckMapperMethods.parseIdFromJson(json);
    }
    public Card cardMapper(String json) {
        deckMapperMethods.validateJson(json);
        return deckMapperMethods.parseCard(json);
    }
    public List<Card> cardsMapperList(String json) {
        deckMapperMethods.validateJson(json);
        return deckMapperMethods.parseCardsList(json);
    }
    public String deckTokenId(String json) {
        deckMapperMethods.validateJson(json);
        return deckMapperMethods.deckTokenIdFromJson(json);
    }
    public int remainingCardMapper(String json) {
        deckMapperMethods.validateJson(json);
        return deckMapperMethods.parseRemainingCards(json);
    }

}
