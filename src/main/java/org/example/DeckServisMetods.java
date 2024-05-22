package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeckServisMetods {

    private final ObjectMapper MAPPER = new ObjectMapper();

    public String deckTokenIdFromJson(String json) {
        try {
            JsonNode node = MAPPER.readTree(json);
            return node.get("deck_id").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void validateJson(String json) {
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("json jest nieprawidlowy ");
        }
        try {
            MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("json jest nieprawidlowy 2 ");
        }
    }

    public int parseRemainingCards(String json) {
        try {
            JsonNode node = MAPPER.readTree(json);
            return node.get("remaining").asInt();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Integer> cardValueSchema() {
        Map<String, Integer> cardValues = new HashMap<>();
        for (int i = 2; i < 11; i++) {
            cardValues.put(Integer.toString(i), i);
        }
        cardValues.put("jack", 11);
        cardValues.put("queen", 12);
        cardValues.put("king", 13);
        cardValues.put("ace", 14);

        return cardValues;
    }

    public int valueFromStringToInt(String valueFromJson) {
        if (valueFromJson == null || valueFromJson.isEmpty()) {
            throw new IllegalArgumentException();
        }
        valueFromJson = valueFromJson.toLowerCase();
        valueFromJson = valueFromJson.trim();
        if ("king".equals(valueFromJson) || "queen".equals(valueFromJson) || "jack".equals(valueFromJson) || "ace".equals(valueFromJson)) {

            return cardValueSchema().get(valueFromJson);
        }
        try {
            int numberValue = Integer.parseInt(valueFromJson.trim());
            if (numberValue > 1 && numberValue < 11) {
                return cardValueSchema().get(valueFromJson.trim());
            }
        } catch (NumberFormatException e) {
        }
        throw new IllegalArgumentException("Nieprawidlowe wartosci opisujace karty");
    }

    public DeckToken parseIdFromJson(String json) {
        try {
            JsonNode node = MAPPER.readTree(json);
            String nodeDeckId = node.get("deck_id").asText();
            return new DeckToken(nodeDeckId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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

    public List<Card> extractCardsFromJsonArray(JsonNode nodeArray) {
        List<Card> cards = new ArrayList<>();
        for (JsonNode node : nodeArray) {
            int value = valueFromStringToInt(node.get("value").asText());
            Suit suit = Suit.fromStringtoSuit(node.get("suit").asText());
            cards.add(new Card(value, suit));
        }
        return cards;
    }

}





