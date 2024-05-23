package org.example;

import java.util.List;

public class DeckMapper {

    private final DeckServisMetods deckServisMetods = new DeckServisMetods();

    public DeckToken deckTokenMapper(String json) {
        deckServisMetods.validateJson(json);
        return deckServisMetods.parseIdFromJson(json);
    }
    public Card cardMapper(String json) {
        deckServisMetods.validateJson(json);
        return deckServisMetods.parseCards(json);
    }
    public String deckTokenId(String json) {
        deckServisMetods.validateJson(json);
        return deckServisMetods.deckTokenIdFromJson(json);
    }
    public int remainingCardMapper(String json) {
        deckServisMetods.validateJson(json);
        return deckServisMetods.parseRemainingCards(json);
    }

}
