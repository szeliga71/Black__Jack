import org.example.api.DeckApiHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.net.http.HttpClient;
public class DeckApiHandlerTest {

    private final HttpClient client = HttpClient.newHttpClient();
    private final DeckApiHandler handler = new DeckApiHandler();

    @Test
    void getDeckHappyPath() {
        Assertions.assertNotNull(handler.getShuffledDecks(5));
    }
    @Test
    void getDecksHappyPath1() {
        Assertions.assertEquals(200, handler.getShuffledDecks(6).statusCode());
    }
    @Test
    void getDecksTooManyNumbersOfDeck() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> handler.getShuffledDecks(10).statusCode());
    }
    @Test
    void getDecksArgumentZero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> handler.getShuffledDecks(0));
    }
    @Test
    void getDecksInvalidArgumentNegativeValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> handler.getShuffledDecks(-5));
    }
    @Test
    void getDecksInvalidArgumentTooBigValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> handler.getShuffledDecks(100));
    }
    @Test
    void drawCardsIncorrectFirstArgument() {
        Assertions.assertThrows(RuntimeException.class, () -> handler.drawCards("xxxx", 2));
    }
    @Test
    void drawCardsIncorrectBothArguments() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> handler.drawCards("xxxx", 5).statusCode());
    }
    @Test
    void drawCardsIncorrecFirstArgument1() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> handler.drawCards("", 1));
    }
    @Test
    void drawCardsIncorrectFirstArgument2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> handler.drawCards(null, 2));
    }
}