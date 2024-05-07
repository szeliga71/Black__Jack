package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeckApiServis {

    private final ObjectMapper MAPPER = new ObjectMapper();
    private DeckToken deckToken = new DeckToken();

    public DeckToken deckTokenMapper(String json) {
        validateJson(json);
        return parseIdFromJson(json);
    }

    public String deckTokenId(String json) {
        validateJson(json);
        return deckTokenIdFronJson(json);
    }

    public int remainingCardMapper(String json) {
        validateJson(json);
        return parseRemainingCards(json);

    }

    public List<Card> cardMapper(String json) {

        validateJson(json);
        return parseCards(json);
    }


    private String deckTokenIdFronJson(String json) {

        try {
            JsonNode node = MAPPER.readTree(json);
            return node.get("deck_id").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private DeckToken parseIdFromJson(String json) {
        try {
            JsonNode node = MAPPER.readTree(json);
            deckToken.setDeckID(node.get("deck_id").asText());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return deckToken;
    }

    private List<Card> parseCards(String json) {
        try {
            JsonNode rootNode = MAPPER.readTree(json);
            JsonNode nodeArray = rootNode.get("cards");
            if (!nodeArray.isArray()) {
                throw new IllegalArgumentException("To nie jest tablica kart");
            } else {
                return extractCardsFromJsonArray(nodeArray);
            }
        } catch (IOException e) {
            throw new RuntimeException("Blad w arsowaniu Json", e);
        }

    }

    private void validateJson(String json) {
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("json jest nieprawidlowy ");
        }
        if (!json.contains("\"success\": true")) {
            throw new IllegalArgumentException("json nie zawiera wymaganego klucza i pozytywnej wartosci");
        }
    }

    private List<Card> extractCardsFromJsonArray(JsonNode nodeArray) {
        List<Card> cards = new ArrayList<>();
        for (JsonNode node : nodeArray) {
            int value = node.get("value").asInt();
            Suit suit = Suit.fromStringtoSuit(node.get("suit").asText());
            cards.add(new Card(value, suit));
        }
        return cards;
    }

    private int parseRemainingCards(String json) {
        try {
            JsonNode node = MAPPER.readTree(json);
            return node.get("remaining").asInt();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}

