package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DeckApiHandler {


    private final HttpClient client = HttpClient.newHttpClient();
    private HttpResponse<String> response;


    public HttpResponse<String> getShuffledDecks(int numberOfDecks) {

        if (!(numberOfDecks > 0 && numberOfDecks <= 20)) {
            throw new IllegalArgumentException("Proszę podać ilość talii z przedziału od 1 do 20");
        }
        String drawDeckURL = "https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=";
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(drawDeckURL + numberOfDecks)).GET().build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public HttpResponse<String> drawCards(String deckId, int count) {

        if (count > 2) {
            throw new IllegalArgumentException("chcesz za duzo kart !");

        } else if (deckId == null || deckId.isEmpty()) {
            throw new IllegalArgumentException("prosze podac wlasciwy identyfikator talii");
        } else {

            String drawCardsURL = "https://deckofcardsapi.com/api/deck/" + deckId + "/draw/?count=" + count;
            try {
                HttpRequest request = HttpRequest.newBuilder(new URI(drawCardsURL)).GET().build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 200) {
                    System.out.println("Blad podczas pobierania :" + response.statusCode());
                }
                return response;
            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}


