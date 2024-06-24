import org.example.gamePlay.DeckService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetNumbersOfRemainigCardsFromHttpResponseTest {

    private final DeckService deckService = new DeckService();

    @Test
    void getNumbersOfRemainingCardsFromHttpResponseNullJsonTest(){
        Assertions.assertThrows(IllegalArgumentException.class,()->deckService.getNumbersOfRemainigCardsFromHttpResponse(null));
    }
    @Test
    void getNumbersOfRemainingCardsFromHttpResponseEmptyJsonTest(){
        Assertions.assertThrows(IllegalArgumentException.class,()->deckService.getNumbersOfRemainigCardsFromHttpResponse(""));
    }
    @Test
    void getNumbersOfRemainingCardsFromHttpResponseIllegalJsonTest(){
        Assertions.assertThrows(IllegalArgumentException.class,()->deckService.getNumbersOfRemainigCardsFromHttpResponse("xxxxxx,ff,kk"));
    }
    @Test
    void getNumbersOfRemainingCardsFromHttpResponseHappyPathTest(){
        String responseJson="{\n" +
                "    \"success\": true,\n" +
                "    \"deck_id\": \"3p40paa87x90\",\n" +
                "    \"shuffled\": true,\n" +
                "    \"remaining\": 52\n" +
                "}";
        Assertions.assertEquals(52,deckService.getNumbersOfRemainigCardsFromHttpResponse(responseJson));
    }
}