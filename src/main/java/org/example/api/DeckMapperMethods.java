package org.example.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Card;
import org.example.model.DeckToken;
import org.example.model.Suit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeckMapperMethods {

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
            throw new IllegalArgumentException(" The provided JSON is empty or null ");
        }
        try {
            MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(" Failed to parse JSON: invalid file or invalid file format ");
        }
    }

    public int parseRemainingCards(String json) {
        try {
            JsonNode node = MAPPER.readTree(json);
            return node.get("remaining").asInt();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON: invalid file format or missing \"remaining\" field ", e) {
            };
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
            throw new IllegalArgumentException(" The provided file is empty or null ");
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
            System.out.println(" Failed to parse the value to an integer ");
        }
        throw new IllegalArgumentException("Invalid values describing cards in the provided JSON");
    }

    public DeckToken parseIdFromJson(String json) {
        try {
            JsonNode node = MAPPER.readTree(json);
            String nodeDeckId = node.get("deck_id").asText();
            return new DeckToken(nodeDeckId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON: invalid file format or missing \"deck_id\" field ", e);
        }
    }

    public Card parseCard(String json) {
        try {
            JsonNode rootNode = MAPPER.readTree(json);
            JsonNode nodeArray = rootNode.get("cards");
            if (!nodeArray.isArray()) {
                throw new IllegalArgumentException(" Failed to parse JSON: invalid file format or missing \"cards\" field ");
            } else {
                return extractCardFromJSONArray(nodeArray);
            }
        } catch (IOException e) {
            throw new RuntimeException(" Failed to parse JSON ", e);
        }
    }

    public Card extractCardFromJSONArray(JsonNode nodeArray) {
        if (nodeArray.isArray() && !nodeArray.isEmpty()) {
            JsonNode firstNodeInArray = nodeArray.get(0);
            int value = valueFromStringToInt(firstNodeInArray.get("value").asText());
            Suit suit = Suit.fromStringtoSuit(firstNodeInArray.get("suit").asText());
            return new Card(value, suit);
        } else {
            throw new IllegalArgumentException(" Failed to parse JSON: invalid nodeArray, or nodeArray is empty ");
        }
    }

    public List<Card> parseCardsList(String json) {
        try {
            JsonNode rootNode = MAPPER.readTree(json);
            JsonNode nodeArray = rootNode.get("cards");
            if (!nodeArray.isArray()) {
                throw new IllegalArgumentException(" Failed to parse JSON: invalid file format or missing \"cards\" field ");
            } else {
                return extractCardsFromJsonArray(nodeArray);
            }
        } catch (IOException e) {
            throw new RuntimeException(" Failed to parse JSON ", e);
        }
    }

    public List<Card> extractCardsFromJsonArray(JsonNode nodeArray) {
        if (nodeArray.isArray() && !nodeArray.isEmpty()) {
            List<Card> cards = new ArrayList<>();
            for (JsonNode node : nodeArray) {
                int value = valueFromStringToInt(node.get("value").asText());
                Suit suit = Suit.fromStringtoSuit(node.get("suit").asText());
                cards.add(new Card(value, suit));
            }
            return cards;
        } else {
            throw new IllegalArgumentException(" Failed to parse JSON: invalid nodeArray, or nodeArray is empty ");
        }
    }
}






