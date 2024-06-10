
import org.example.gamePlay.DeckService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetJsonFromDeckAfterDrawCardTest {

    @Mock
    private DeckService deckServiceS;

    private final DeckService deckService = new DeckService();

    @Test
    void getJsonFromDeckAfterDrawCardNullArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class,()->deckService.getJsonFromDeckAfterDrawCard(null));
    }
    @Test
    void getJsonFromDeckAfterDrawCardEmptyArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class,()->deckService.getJsonFromDeckAfterDrawCard(""));
    }
    @Test
    void getJsonFromDeckAfterDrawCardHappyPathTest() {
        String responseJson="{\n" +
                "\"success\": true,\n" +
                "\"deck_id\": \"kxozasf3edqu\",\n" +
                "\"cards\": [\n" +
                "{\n" +
                "\"code\": \"6H\",\n" +
                "\"image\": \"https://deckofcardsapi.com/static/img/6H.png\",\n" +
                "\"images\": {\n" +
                "\"svg\": \"https://deckofcardsapi.com/static/img/6H.svg\",\n" +
                "\"png\": \"https://deckofcardsapi.com/static/img/6H.png\"\n" +
                "},\n" +
                "\"value\": \"6\",\n" +
                "\"suit\": \"HEARTS\"\n" +
                "},\n" +
                "{\n" +
                "\"code\": \"5S\",\n" +
                "\"image\": \"https://deckofcardsapi.com/static/img/5S.png\",\n" +
                "\"images\": {\n" +
                "\"svg\": \"https://deckofcardsapi.com/static/img/5S.svg\",\n" +
                "\"png\": \"https://deckofcardsapi.com/static/img/5S.png\"\n" +
                "},\n" +
                "\"value\": \"5\",\n" +
                "\"suit\": \"SPADES\"\n" +
                "}\n" +
                "],\n" +
                "\"remaining\": 20\n" +
                "}";

        when(deckServiceS.getJsonFromDeckAfterDrawCard(anyString())).thenReturn(responseJson);

        Assertions.assertEquals(responseJson,deckServiceS.getJsonFromDeckAfterDrawCard(anyString()));

    }


}

/*public String getJsonFromDeckAfterDrawCard(String deckId) {
    return deckApiHandler.drawCards(deckId, 1).body();
}

    public HttpResponse<String> drawCards(String deckId, int count) {

        if (count > 2) {
            throw new IllegalArgumentException("chcesz za duzo kart !");

        } else if (deckId == null || deckId.isEmpty()) {
            throw new IllegalArgumentException("prosze podac wlasciwy identyfikator talii");
        } else {
            String drawCardsURL = drawCardsBaseURL + deckId + "/draw/?count=" + count;
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


 */