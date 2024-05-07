
import org.example.DeckApiServis;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpResponse;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeckApiServisTest {

    @Mock
    HttpResponse<String> response;

    private final DeckApiServis deckApiServis = new DeckApiServis();



    @Test
    public void getNumberOfCardsInDeckFromResponseBodyNullArgument() {

        Assertions.assertThrows(IllegalArgumentException.class ,()->deckApiServis.remainingCardMapper(null));
    }

    @Test
    public void getNumberOfCardsInDeckFromResponseBodyIllegalArgument() {

        Assertions.assertThrows(IllegalArgumentException.class ,()->deckApiServis.remainingCardMapper("444444efrfg"));
    }

    @Test
    public void getNumberOfCardsInDeckFromResponseBodyNoRemainingFieldInJson() {

        String json = "{\"success\": true,\"xxxxx\": 4}";
        Assertions.assertThrows(RuntimeException.class,()-> deckApiServis.remainingCardMapper(json));

    }

   /* @Test
    public void getNumberOfCardsInDeckFromResponseBodyIllegalValueInRemainingFieldInJson() {

        String json = "{\"success\": true,\"remaining\": \"xxx\"}";
        Assertions.assertThrows(RuntimeException.class,()-> deckApiServis.remainingCardMapper(json));

    }*/

    @Test
    public void getNumberOfCardsInDeckFromResponseBodyEmptyJson() {

        String json = "";
        Assertions.assertThrows(IllegalArgumentException.class ,()->deckApiServis.remainingCardMapper(json));

    }
    @Test
    public void getNumberOfCadsHappyPath(){

        when(response.body()).thenReturn("{\n" +
                "    \"success\": true,\n" +
                "    \"deck_id\": \"3p40paa87x90\",\n" +
                "    \"shuffled\": true,\n" +
                "    \"remaining\": 52\n" +
                "}");
        Assertions.assertEquals(52,deckApiServis.remainingCardMapper(response.body()));
    }

   /* @Test
    public void getNumberOfCadsHappyPath1(){
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("{\n" +
                "    \"success\": true,\n" +
                "    \"deck_id\": \"3p40paa87x90\",\n" +
                "    \"shuffled\": true,\n" +
                "    \"remaining\": 52\n" +
                "}");

    }*/

   /* @Test
    public void getNumberOfCardsInDeckFromResponseBodyHappyTest() {

        responseTest = deckApiHandler.drawCards(deckId, 2);

        Assertions.assertEquals(50, deckApiServis.remainingCardMapper(responseTest.body()));

    }

    @Test
    public void getNumberOfCardsInDeckFromResponseBodyHappyTest1() {

        HttpResponse<String> response = deckApiHandler.getShuffledDecks(1);

        Assertions.assertNotEquals(0, deckApiServis.remainingCardMapper(response.body()));

    }
*/
    @Test
    public void getNumberOfCardsInDeckFromResponseBodyHappyTest2() {

        String json = "{\"success\": true,\"remaining\": 9}";
        Assertions.assertEquals(9, deckApiServis.remainingCardMapper(json));

    }


    @Test
    public void getDeckIdFromResponseBodyJsonNull() {

        String json = null;

        Assertions.assertThrows(IllegalArgumentException.class,()-> deckApiServis.deckTokenId(json));
    }

    @Test
    public void getDeckIdFromResponseBodyIllegalArgument() {

        String json = "cdggrt";

        Assertions.assertThrows(IllegalArgumentException.class,()-> deckApiServis.deckTokenId(json));
    }
/*
    @Test
    public void getDeckIdFromResponseBodyIllegalArgument1() {

        String json = "{\"success\": false, \"deck_id\": \"" + deckId + "\"}";

        Assertions.assertEquals(json,deckApiServis.deckTokenId(json));
    }

    @Test
    public void getDeckIdFromResponseBodyIllegalArgument2() {

        String json = "{\"xxxxxx\": true, \"deck_id\": \"" + deckId + "\"}";

        Assertions.assertEquals(json,deckApiServis.deckTokenId(json));
    }

    @Test
    public void getDeckIdFromResponseBodyIllegalArgument3() {

        String json = "{\"success\": true, \"xxxxx\": \"" + deckId + "\"}";

        Assertions.assertEquals(null,deckApiServis.deckTokenId(json));
    }


    @Test
    public void getDeckIdFromResponseBodyIllegalArgument4() {

        String json = "{\"success\": true, \"xxxxx\": \"" + deckId + "\"}";

        Assertions.assertNull(deckApiServis.deckTokenId(json));
    }
*/
    @Test
    public void getDeckIdFromResponseBodyEmptyArgument() {

        String json = "";

        Assertions.assertThrows(IllegalArgumentException.class, ()->deckApiServis.deckTokenId(json));
    }
/*
    @Test
    public void getDeckIdFromResponseBodyHappyTest() {

        Assertions.assertEquals(deckId, deckApiServis.deckTokenId(responseTest.body()));
    }

    @Test
    public void getDeckIdFromResponseBodyHappyTest1() {

        String json = "{\"success\": true, \"deck_id\": \"" + deckId + "\"}";

        Assertions.assertEquals(deckId, deckApiServis.deckTokenId(json));
    }
*/




}
