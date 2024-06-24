import org.example.gamePlay.DeckService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetJsonFromNewDeckTest {

    @Mock
    private DeckService deckServiceS;

    private final DeckService deckService = new DeckService();

    @Test
    void getJsonFromNewDeckZeroArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckService.getJsonFromNewDeck(0));
    }
    @Test
    void getJsonFromNewDeckTooBigArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckService.getJsonFromNewDeck(654));
    }
    @Test
    void getJsonFromNewDeckTooBigArgumentTest1() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> deckService.getJsonFromNewDeck(654));
        String expectedMesage = "Please provide the number of decks from the range of 4 to 8";
        Assertions.assertEquals(expectedMesage, exception.getMessage());
    }
    @Test
    void getJsonFromNewDeckEmptyArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckService.getNewDeckAndDeckId(""));
    }
    @Test
    void getJsonFromNewDeckIllegallArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckService.getNewDeckAndDeckId("xxxxx"));
    }
    @Test
    void getJsonFromNewDeckHappyPathTest() {
        String responseJson = "{" +
                "\"success\": true," +
                " \"deck_id\": \"3p40paa87x90\"," +
                " \"shuffled\": true," +
                " \"remaining\": 208" +
                "}";
        when(deckServiceS.getJsonFromNewDeck(4)).thenReturn(responseJson);
        String actualJson = deckServiceS.getJsonFromNewDeck(4);
        Assertions.assertEquals(responseJson, actualJson);
    }
    @Test
    void getJsonFromNewDeckRemainingCardsSetUpHappyPathTest() {
        String responseJson = "{\n" +
                "    \"success\": true,\n" +
                "    \"deck_id\": \"3p40paa87x90\",\n" +
                "    \"shuffled\": true,\n" +
                "    \"remaining\": 62\n" +
                "}";
        when(deckServiceS.getNumbersOfRemainigCardsFromHttpResponse(anyString())).thenReturn(62);
        int expectedR=62;
        int actual=deckServiceS.getNumbersOfRemainigCardsFromHttpResponse(responseJson);
        Assertions.assertEquals(expectedR, actual);
    }
}