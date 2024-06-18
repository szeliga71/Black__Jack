import org.example.api.DeckApiHandler;
import org.example.gamePlay.DeckService;
import org.example.model.Card;
import org.example.model.Suit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetCardFromJsonAfterDrawCardFromDeckTest {

    private final DeckService deckService = new DeckService();

    @Test
    void getCardFromJsonAfterDrawCardFromDeckHappyPathTest() {
        String responseJson = "{\n" +
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

        Card card = new Card(6, Suit.HEARTS);
        Assertions.assertEquals(card, deckService.getCardFromJsonAfterDrawCardFromDeck(responseJson));
    }

    @Test
    void getCardFromJsonAfterDrawCardFromDeckNullArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckService.getJsonFromDeckAfterDrawCard(null));
    }

    @Test
    void getCardFromJsonAfterDrawCardFromDeckEmptyArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckService.getJsonFromDeckAfterDrawCard(""));
    }

    @Test
    void getCardFromJsonAfterDrawCardFromDeckIllegalArgumentTest2() {
        String illegalJson = "vvvb,HHHH,ii,k";
        Assertions.assertThrows(RuntimeException.class, () -> deckService.getJsonFromDeckAfterDrawCard(illegalJson));
    }
}

