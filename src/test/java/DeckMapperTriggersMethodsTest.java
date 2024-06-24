import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Card;
import org.example.api.DeckMapperMethods;
import org.example.api.DeckMapperTriggers;
import org.example.model.DeckToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import static org.example.model.Suit.HEARTS;
import static org.example.model.Suit.SPADES;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeckMapperTriggersMethodsTest {

    @Mock
    private HttpResponse<String> response;

    private final DeckMapperMethods deckMapperMethods = new DeckMapperMethods();
    private final DeckMapperTriggers deckMapperTriggers = new DeckMapperTriggers();

    @Test
    void getNumberOfCardsInDeckFromResponseBodyNullArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperTriggers.remainingCardMapper(null));
    }
    @Test
    void getNumberOfCardsInDeckFromResponseBodyIllegalArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperTriggers.remainingCardMapper("444444efrfg"));
    }
    @Test
    void getNumberOfCardsInDeckFromResponseBodyNoRemainingFieldInJson() {
        String json = "{\"success\": true,\"xxxxx\": 4}";
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperTriggers.remainingCardMapper(json));
    }
    @Test
    void getNumberOfCardsInDeckFromResponseBodyEmptyJson() {
        String json = "";
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperTriggers.remainingCardMapper(json));
    }
    @Test
    void getNumberOfCadsHappyPath() {
        when(response.body()).thenReturn("{\n" +
                "    \"success\": true,\n" +
                "    \"deck_id\": \"3p40paa87x90\",\n" +
                "    \"shuffled\": true,\n" +
                "    \"remaining\": 52\n" +
                "}");
        Assertions.assertEquals(52, deckMapperTriggers.remainingCardMapper(response.body()));
    }
    @Test
    void getNumberOfCardsInDeckFromResponseBodyHappyPath2() {
        String json = "{\"success\": true,\"remaining\": 9}";
        Assertions.assertEquals(9, deckMapperTriggers.remainingCardMapper(json));
    }
    @Test
    void validateJsonTestNullArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperMethods.validateJson(null));
    }
    @Test
    void validateJsonTestIllegalArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperMethods.validateJson("xxxSSSSvvvv"));
    }
    @Test
    void validateJsonTestInvalidJson() {
        String incorrectJson = "{: true,\"remaining\": 9}";
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperMethods.validateJson(incorrectJson));
        Assertions.assertEquals(" Failed to parse JSON: invalid file or invalid file format ", exception.getMessage());
    }
    @Test
    void validateJsonTestEmptyJson() {
        String emptyJson = "";
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperMethods.validateJson(emptyJson));
        Assertions.assertEquals(" The provided JSON is empty or null ", exception.getMessage());
    }
    @Test
    void validateJsonTestEmptyArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperMethods.validateJson(""));
    }
    @Test
    void parseIdFromJsonArgumentNull() {
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperMethods.parseIdFromJson(null));
    }
    @Test
    void parseIdFromJsonTestIllegalArgument() {
        String illegalJson = "XXXXXXXX";
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperMethods.parseIdFromJson(illegalJson));
    }
    @Test
    void parseIdFromJsonHappyPath() {
        when(response.body()).thenReturn("{\"success\": true,\"deck_id\": \"3p40paa87x90\",\"shuffled\": true,\"remaining\": 52}");
        DeckToken deckToken = new DeckToken("3p40paa87x90");
        Assertions.assertEquals(deckToken.getDeckID(), deckMapperTriggers.deckTokenId(response.body()));
    }
    @Test
    void deckTokenMapperTestHappyPath() {
        when(response.body()).thenReturn("{\"success\": true,\"deck_id\": \"3p40paa87x90\",\"shuffled\": true,\"remaining\": 52}");
        DeckToken deckToken = new DeckToken("3p40paa87x90");
        String responseJson = response.body();
        Assertions.assertEquals(deckToken.getDeckID(), deckMapperTriggers.deckTokenId(responseJson));
    }
    @Test
    void deckTokenMapperTestIllegalArgument() {
        when(response.body()).thenReturn("{\"success\": true,\"xxxx\": \"3p40paa87x90\",\"shuffled\": true,\"remaining\": 52}");
        String responseJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperTriggers.deckTokenMapper(responseJson));
    }
    @Test
    void deckTokenMapperTestEmptyArgument() {
        when(response.body()).thenReturn("");
        String responseJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperTriggers.deckTokenMapper(responseJson));
    }
    @Test
    void deckTokenMapperTestNullArgument() {
        when(response.body()).thenReturn(null);
        String responseJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperTriggers.deckTokenMapper(responseJson));
    }
    @Test
    void deckTokenIdFromJsonTestNullArgument() {
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperMethods.deckTokenIdFromJson(null));
    }
    @Test
    void deckTokenIdFromJsonTestEmptyArgument() {
        String emptyString = "";
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperMethods.deckTokenIdFromJson(emptyString));
    }
    @Test
    void deckTokenIdFromJsonTestIllegalArgument() {
        when(response.body()).thenReturn("{\"success\": true,\"xxxx\": \"3p40paa87x90\",\"shuffled\": true,\"remaining\": 52}");
        String responseJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperMethods.deckTokenIdFromJson(responseJson));
    }
    @Test
    void deckTokenIdFromJsonTestHappyPath() {
        when(response.body()).thenReturn("{\"success\": true,\"deck_id\": \"3p40paa87x90\",\"shuffled\": true,\"remaining\": 52}");
        String responseJson = response.body();
        Assertions.assertEquals("3p40paa87x90", deckMapperMethods.deckTokenIdFromJson(responseJson));
    }
    @Test
    void deckTokenIdTestNullArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperTriggers.deckTokenId(null));
    }
    @Test
    void deckTokenIdTestEmptyArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperTriggers.deckTokenId(""));
    }
    @Test
    void deckTokenIdTestIllegalArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperTriggers.deckTokenId("vvd hz"));
    }
    @Test
    void parseRemainingCardsHappyPath() {
        when(response.body()).thenReturn("{\"success\": true,\"remaining\": 52}");
        String happyJson = response.body();
        Assertions.assertEquals(52, deckMapperMethods.parseRemainingCards(happyJson));
    }
    @Test
    void parseRemainingCardsNotHappyPath() {
        when(response.body()).thenReturn("{\"success\": true,\"remaining\": 52}");
        String happyJson = response.body();
        Assertions.assertNotEquals(5, deckMapperMethods.parseRemainingCards(happyJson));
    }
    @Test
    void parseRemainingCardsEmptyArgument() {
        when(response.body()).thenReturn("");
        String happyJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperMethods.parseRemainingCards(happyJson));
    }
    @Test
    void parseRemainingCardsNullArgument() {
        when(response.body()).thenReturn(null);
        String happyJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperMethods.parseRemainingCards(happyJson));
    }
    @Test
    void parseRemainingCardsIllegalArgument() {
        when(response.body()).thenReturn("gggGGTTmn");
        String happyJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperMethods.parseRemainingCards(happyJson));
    }
    @Test
    void parseRemainingCardsIllegalJson() {
        when(response.body()).thenReturn("{\"xxxxx\": true,\"remaining\": 52}");
        String happyJson = response.body();
        Assertions.assertNotEquals(5, deckMapperMethods.parseRemainingCards(happyJson));
    }
    @Test
    void parseRemainingCardsIllegalJson1() {
        when(response.body()).thenReturn("{\"success\": true,\"xxxxxxxx\": 52}");
        String happyJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperMethods.parseRemainingCards(happyJson));
    }
    @Test
    void parseRemainingCardsIllegalRemainingValueInJson() {
        when(response.body()).thenReturn("{\"success\": true,\"remaining\": nn}");
        String happyJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperMethods.parseRemainingCards(happyJson));
    }
    @Test
    void remainingCardMapperTestNullArgument() {
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperTriggers.remainingCardMapper(null));
    }
    @Test
    void remainingCardMapperTestEmptyArgument() {
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperTriggers.remainingCardMapper(""));
    }
    @Test
    void remainingCardMapperTestIllegalArgument() {
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperTriggers.remainingCardMapper("ffZTRnnK"));
    }
    @Test
    void remainingCardMapperTestHappyPath() {
        when(response.body()).thenReturn("{\"success\": true,\"remaining\": 78}");
        String happyJson = response.body();
        Assertions.assertEquals(78, deckMapperTriggers.remainingCardMapper(happyJson));
    }
    @Test
    void remainingCardMapperTestNotHappyPath() {
        when(response.body()).thenReturn("{\"success\": true,\"remaining\": 78}");
        String happyJson = response.body();
        Assertions.assertNotEquals(3, deckMapperTriggers.remainingCardMapper(happyJson));
    }
    @Test
    void parseCardNullArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperMethods.parseCard(null));
    }
    @Test
    void parseCardsListNullArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperMethods.parseCardsList(null));
    }
    @Test
    void parseCardIllegalArgument() {
        String illegalJson = "XXXXXXXX";
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperMethods.parseCard(illegalJson));
    }
    @Test
    void parseCardsListIllegalArgument() {
        String illegalJson = "XXXXXXXX";
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperMethods.parseCardsList(illegalJson));
    }
    @Test
    void extractCardsFromJsonArrayTestNullArgument() {
        Assertions.assertThrows(NullPointerException.class, () -> deckMapperMethods.extractCardsFromJsonArray(null));
    }
    @Test
    public void extractCardsFromJsonArrayHappyPath() {
        ObjectMapper MAPPER = new ObjectMapper();
        when(response.body()).thenReturn("{\n" +
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
                "}");
        JsonNode jsonArray = null;
        try {
            JsonNode jsonNode = MAPPER.readTree(response.body());
            jsonArray = jsonNode.get("cards");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(6, HEARTS));
        cards.add(new Card(5, SPADES));
        Assertions.assertArrayEquals(cards.toArray(), deckMapperMethods.extractCardsFromJsonArray(jsonArray).toArray());
    }
    @Test
    public void extractCardsFromJSONArrayHappyPath() {
        ObjectMapper MAPPER = new ObjectMapper();
        when(response.body()).thenReturn("{\n" +
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
                "}");
        JsonNode jsonArray = null;
        try {
            JsonNode jsonNode = MAPPER.readTree(response.body());
            jsonArray = jsonNode.get("cards");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(6, HEARTS));
        cards.add(new Card(5, SPADES));
        Assertions.assertEquals(cards.get(0), deckMapperMethods.extractCardFromJSONArray(jsonArray));
    }
    @Test
    void extractCardsFromJsonArrayTestEmptyArgument() {
        ObjectMapper MAPPER = new ObjectMapper();
        when(response.body()).thenReturn("{\n" +
                "\"success\": true,\n" +
                "\"deck_id\": \"kxozasf3edqu\",\n" +
                "\"cards\": [\n" +
                "],\n" +
                "\"remaining\": 20\n" +
                "}");
        JsonNode jsonArray;
        try {
            JsonNode jsonNode = MAPPER.readTree(response.body());
            jsonArray = jsonNode.get("cards");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            deckMapperMethods.extractCardsFromJsonArray(jsonArray);
        });
        String exceptionText = " Failed to parse JSON: invalid nodeArray, or nodeArray is empty ";
        Assertions.assertEquals(exceptionText, exception.getMessage());
    }
    @Test
    void cardMapperTestNullArgument() {
        Assertions.assertThrows(RuntimeException.class, () -> deckMapperTriggers.cardMapper(null));
    }
    @Test
    void cardMapperTestEmptyJsonListArgument() {
        when(response.body()).thenReturn("{\n" +
                "\"success\": true,\n" +
                "\"deck_id\": \"kxozasf3edqu\",\n" +
                "\"cards\": [\n" +
                "],\n" +
                "\"remaining\": 20\n" +
                "}");
        String illegalJson = response.body();
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperTriggers.cardMapper(illegalJson));
    }
    @Test
    public void cardMapperTestHappyPath() {
        when(response.body()).thenReturn("{\n" +
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
                "}");
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(6, HEARTS));
        cards.add(new Card(5, SPADES));
        String happyJson = response.body();
        Assertions.assertEquals(cards.get(0), deckMapperTriggers.cardMapper(happyJson));
    }
    @Test
    void cardMapperTestIllegalArgument() {
        String illegalJson = "xxxxgggtrr";
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperTriggers.cardMapper(illegalJson));
    }
    @Test
    void valueFromStringToIntLowerCaseString() {
        String cardValue = "jack";
        Assertions.assertEquals(11, deckMapperMethods.valueFromStringToInt(cardValue));
    }
    @Test
    void valueFromStringToIntUpperCaseString() {
        String cardValue = "QUEEN";
        Assertions.assertEquals(12, deckMapperMethods.valueFromStringToInt(cardValue));
    }
    @Test
    void valueFromStringToIntMixCaseString() {
        String cardValue = "JaCk";
        Assertions.assertEquals(11, deckMapperMethods.valueFromStringToInt(cardValue));
    }
    @Test
    void valueFromStringToIntNullString() {
        String cardValue = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperMethods.valueFromStringToInt(cardValue));
    }
    @Test
    void valueFromStringToIntEmptyString() {
        String cardValue = "";
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperMethods.valueFromStringToInt(cardValue));
    }
    @Test
    void valueFromStringToIntIllegalString() {
        String cardValue = "625";
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperMethods.valueFromStringToInt(cardValue));
    }
    @Test
    void valueFromStringToIntIllegalString1() {
        String cardValue = "xxxxxHHHHHHHqq";
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperMethods.valueFromStringToInt(cardValue));
    }
    @Test
    public void getDeckIdFromResponseBodyEmptyArgument() {
        String json = "";
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperTriggers.deckTokenId(json));
    }
    @Test
    public void getDeckIdFromResponseBodyJsonNull() {
        String json = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperTriggers.deckTokenId(json));
    }
    @Test
    public void getDeckIdFromResponseBodyIllegalArgument() {
        String json = "cdggrt";
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapperTriggers.deckTokenId(json));
    }
    @Test
    void getDeckIdFromResponseBodyHappyTest1() {
        String deckId = "deckId";
        String json = "{\"success\": true, \"deck_id\": \"" + deckId + "\"}";
        Assertions.assertEquals(deckId, deckMapperTriggers.deckTokenId(json));
    }
}