
import org.example.DeckApiHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;


public class DeckApiHandlerTest {


    private final HttpClient client = HttpClient.newHttpClient();
    DeckApiHandler handler = new DeckApiHandler();

    @Test
    void getDeckHappyPath() {
        Assertions.assertNotNull(handler.getShuffledDecks(2));
    }

    @Test
    void getDecksHappyPath1() {
        Assertions.assertEquals(200, handler.getShuffledDecks(2).statusCode());
    }

    @Test
    void getDecksArgumentZero() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> handler.getShuffledDecks(0));
    }

    @Test
    void getDecksnvalidArgumentNegativeValue() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> handler.getShuffledDecks(-5));
    }

    @Test
    void getDecksnvalidArgumentTooBigValue() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> handler.getShuffledDecks(100));
    }

    @Test
    void drawCardsIncorrectFirstArgument() {

        Assertions.assertEquals(404, handler.drawCards("xxxx", 2).statusCode());
    }

    @Test
    void drawCardsIncorrectBothArguments() {

        Assertions.assertEquals(404, handler.drawCards("xxxx", 0).statusCode());
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

