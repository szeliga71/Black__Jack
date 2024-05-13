package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeckApiServis {

    private final ObjectMapper MAPPER = new ObjectMapper();
    private DeckToken deckToken = new DeckToken();

    public DeckToken deckTokenMapper(String json) {
        validateJson(json);
        return parseIdFromJson(json);
    }

    public String deckTokenId(String json) {
        validateJson(json);
        return deckTokenIdFromJson(json);
    }

    public int remainingCardMapper(String json) {
        validateJson(json);
        return parseRemainingCards(json);

    }

    public List<Card> cardMapper(String json) {

        validateJson(json);
        return parseCards(json);
    }


    public String deckTokenIdFromJson(String json) {

        try {
            JsonNode node = MAPPER.readTree(json);
            return node.get("deck_id").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

     public DeckToken parseIdFromJson(String json) {
        try {
            JsonNode node = MAPPER.readTree(json);
            deckToken.setDeckID(node.get("deck_id").asText());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return deckToken;
    }

    public List<Card> parseCards(String json) {
        try {
            JsonNode rootNode = MAPPER.readTree(json);
            JsonNode nodeArray = rootNode.get("cards");
            if (!nodeArray.isArray()) {
                throw new IllegalArgumentException("To nie jest tablica kart");
            } else {
                return extractCardsFromJsonArray(nodeArray);
            }
        } catch (IOException e) {
            throw new RuntimeException("Blad w parsowaniu Json", e);
        }

    }

    public void validateJson(String json) {
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("json jest nieprawidlowy ");
        }
        if (!json.contains("\"success\": true")) {
            throw new IllegalArgumentException("json nie zawiera wymaganego klucza i pozytywnej wartosci");
        }
    }

    public List<Card> extractCardsFromJsonArray(JsonNode nodeArray) {
        List<Card> cards = new ArrayList<>();
        for (JsonNode node : nodeArray) {
            int value = valueFromStringToInt(node.get("value").asText());
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
//============  Value mapper ====================
    private static Map<String, Integer> cardValueSchema() {

        Map<String, Integer> cardValues = new HashMap<>();
        for (int i = 2; i < 11; i++) {
            cardValues.put(Integer.toString(i), i);
        }
        cardValues.put("JACK", 11);
        cardValues.put("QUEEN", 12);
        cardValues.put("KING", 13);
        cardValues.put("ACE", 14);

        return cardValues;
    }

    private int valueFromStringToInt(String valueFromJson){
        return cardValueSchema().get(valueFromJson);
    }
}

