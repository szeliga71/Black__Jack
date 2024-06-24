import org.example.gamePlay.DeckService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.net.http.HttpResponse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetNewDeckAndDeckIdTest {

    @Mock
    private HttpResponse<String> response;

    private final DeckService deckService=new DeckService();

    @Test
    void getNewDeckAndDeckIdNullArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckService.getNewDeckAndDeckId(null));
    }
    @Test
    void getNewDeckAndDeckIdEmptyArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckService.getNewDeckAndDeckId(""));
    }
    @Test
    void getNewDeckAndDeckIdIllegallArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckService.getNewDeckAndDeckId("xxxxx"));
    }
    @Test
    void getNewDeckAndDeckHappyPathTest(){
        String responseJson="{\n" +
                "    \"success\": true,\n" +
                "    \"deck_id\": \"3p40paa87x90\",\n" +
                "    \"shuffled\": true,\n" +
                "    \"remaining\": 52\n" +
                "}";
        Assertions.assertEquals("3p40paa87x90",deckService.getNewDeckAndDeckId(responseJson));
    }
    @Test
    void getNewDeckAndDeckRemainingCardsSetUpHappyPathTest(){
        String responseJson="{\n" +
                "    \"success\": true,\n" +
                "    \"deck_id\": \"3p40paa87x90\",\n" +
                "    \"shuffled\": true,\n" +
                "    \"remaining\": 62\n" +
                "}";
        String expectedDeckId="3p40paa87x90";
        String atualDeckId=deckService.getNewDeckAndDeckId(responseJson);
        Assertions.assertEquals(expectedDeckId,atualDeckId);
    }
    @Test
    void getNewDeckAndDeckRemainingCardsSetUpHappyPathTest2(){
        String responseJson="{\n" +
                "    \"success\": true,\n" +
                "    \"deck_id\": \"3p40paa87x90\",\n" +
                "    \"shuffled\": true,\n" +
                "    \"remaining\": 62\n" +
                "}";
        when(response.body()).thenReturn(responseJson);
        int expectingRemainingCards=62;
        Assertions.assertEquals(expectingRemainingCards,deckService.getNumbersOfRemainigCardsFromHttpResponse(response.body()));
    }
}