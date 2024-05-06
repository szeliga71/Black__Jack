package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class DeckApiServis {

    private final ObjectMapper MAPPER = new ObjectMapper();
    private  DeckToken deckToken=new DeckToken();


    public DeckToken deckTokenMapper(String json) {

        try {
            JsonNode node = MAPPER.readTree(json);
            deckToken.setDeckID(node.get("deck_id").asText());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return deckToken;
    }

    public String deckTokenId(String json) {

        if (json == null || json.isEmpty()) {
            System.out.println("odpowiedz jest pusta lub jej brak");
            return json;
        } else if (json.contains("\"success\": true")) {
            DeckToken deckToken = new DeckToken();
            try {
                JsonNode node = MAPPER.readTree(json);
                if (node != null && node.isObject() && node.has("deck_id") && node.get("deck_id").isTextual()) {
                    deckToken.setDeckID(node.get("deck_id").asText());
                }
                return deckToken.getDeckID();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }
        return json;
    }

    public List<Card> cardMapper(String json) {

        if(json!=null){
            try {
                if (json.contains("\"success\": true")) {

                    DeckResponseAfterDrawCards dradc = MAPPER.readValue(json, DeckResponseAfterDrawCards.class);
                    return dradc.getCards();
                }else {return null;}

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else
        {return null;}

    }

    public int remainingCardMapper(String json) {
        if (json == null || json.isEmpty()) {
            throw new IllegalArgumentException("odpowiedz jest pusta lub jej brak");

        } else if (json.contains("\"success\": true")) {
            try {
                JsonNode node = MAPPER.readTree(json);
                if (node != null && node.isObject() && node.has("remaining") && node.get("remaining").isInt()) {
                    return node.get("remaining").asInt();
                } else {
                    System.out.println("Niepoprawny format danych w JSON");
                    return 0;
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Blad w danych JSON", e);
            }
        } throw new IllegalArgumentException("odpowiedz jest nieprawidlowa ");
    }

}

