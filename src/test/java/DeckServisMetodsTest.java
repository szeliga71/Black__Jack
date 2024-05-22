

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Card;
import org.example.DeckServisMetods;
import org.example.DeckMapper;
import org.example.DeckToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.example.Suit.HEARTS;
import static org.example.Suit.SPADES;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeckServisMetodsTest {

    @Mock
    private HttpResponse<String> response;


    private final DeckServisMetods deckServisMetods = new DeckServisMetods();
    private final DeckMapper deckMapper = new DeckMapper();


    @Test
    void getNumberOfCardsInDeckFromResponseBodyNullArgument() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapper.remainingCardMapper(null));
    }

    @Test
    void getNumberOfCardsInDeckFromResponseBodyIllegalArgument() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapper.remainingCardMapper("444444efrfg"));
    }

    @Test
    void getNumberOfCardsInDeckFromResponseBodyNoRemainingFieldInJson() {

        String json = "{\"success\": true,\"xxxxx\": 4}";
        Assertions.assertThrows(RuntimeException.class, () -> deckMapper.remainingCardMapper(json));

    }


    @Test
    void getNumberOfCardsInDeckFromResponseBodyEmptyJson() {

        String json = "";
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapper.remainingCardMapper(json));

    }

    @Test
    void getNumberOfCadsHappyPath() {

        when(response.body()).thenReturn("{\n" +
                "    \"success\": true,\n" +
                "    \"deck_id\": \"3p40paa87x90\",\n" +
                "    \"shuffled\": true,\n" +
                "    \"remaining\": 52\n" +
                "}");
        Assertions.assertEquals(52, deckMapper.remainingCardMapper(response.body()));
    }

    @Test
    void getNumberOfCardsInDeckFromResponseBodyHappyPath2() {

        String json = "{\"success\": true,\"remaining\": 9}";
        Assertions.assertEquals(9, deckMapper.remainingCardMapper(json));

    }

    @Test
    void validateJsonTestNullArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckServisMetods.validateJson(null));
    }

    @Test
    void validateJsonTestIllegalArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckServisMetods.validateJson("xxxSSSSvvvv"));
    }

    @Test
    void validateJsonTestInvalidJson() {
        String incorrectJson = "{: true,\"remaining\": 9}";
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> deckServisMetods.validateJson(incorrectJson));
        Assertions.assertEquals("json jest nieprawidlowy 2 ", exception.getMessage());
    }

    @Test
    void validateJsonTestEmptyJson() {
        String emptyJson = "";
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> deckServisMetods.validateJson(emptyJson));
        Assertions.assertEquals("json jest nieprawidlowy ", exception.getMessage());
    }

    @Test
    void validateJsonTestEmptyArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckServisMetods.validateJson(""));
    }

    @Test
    void parseIdFromJsonArgumentNull() {
        Assertions.assertThrows(RuntimeException.class, () -> deckServisMetods.parseIdFromJson(null));
    }

    @Test
    void parseIdFromJsonTestIllegalArgument() {
        String illegalJson = "XXXXXXXX";
        Assertions.assertThrows(RuntimeException.class, () -> deckServisMetods.parseIdFromJson(illegalJson));
    }

    @Test
    void parseIdFromJsonHappyPath() {
        when(response.body()).thenReturn("{\"success\": true,\"deck_id\": \"3p40paa87x90\",\"shuffled\": true,\"remaining\": 52}");
        DeckToken deckToken = new DeckToken("3p40paa87x90");
        Assertions.assertEquals(deckToken.getDeckID(), deckMapper.deckTokenId(response.body()));
    }

    @Test
    void deckTokenMapperTestHappyPath() {
        when(response.body()).thenReturn("{\"success\": true,\"deck_id\": \"3p40paa87x90\",\"shuffled\": true,\"remaining\": 52}");
        DeckToken deckToken = new DeckToken("3p40paa87x90");
        String responseJson = response.body();
        Assertions.assertEquals(deckToken.getDeckID(), deckMapper.deckTokenId(responseJson));
    }

    @Test
    void deckTokenMapperTestIllegalArgument() {
        when(response.body()).thenReturn("{\"success\": true,\"xxxx\": \"3p40paa87x90\",\"shuffled\": true,\"remaining\": 52}");
        String responseJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckMapper.deckTokenMapper(responseJson));
    }

    @Test
    void deckTokenMapperTestEmptyArgument() {
        when(response.body()).thenReturn("");
        String responseJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckMapper.deckTokenMapper(responseJson));
    }

    @Test
    void deckTokenMapperTestNullArgument() {
        when(response.body()).thenReturn(null);
        String responseJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckMapper.deckTokenMapper(responseJson));
    }

    @Test
    void deckTokenIdFromJsonTestNullArgument() {
        Assertions.assertThrows(RuntimeException.class, () -> deckServisMetods.deckTokenIdFromJson(null));
    }

    @Test
    void deckTokenIdFromJsonTestEmptyArgument() {
        String emptyString = "";
        Assertions.assertThrows(RuntimeException.class, () -> deckServisMetods.deckTokenIdFromJson(emptyString));
    }

    @Test
    void deckTokenIdFromJsonTestIllegalArgument() {
        when(response.body()).thenReturn("{\"success\": true,\"xxxx\": \"3p40paa87x90\",\"shuffled\": true,\"remaining\": 52}");
        String responseJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckServisMetods.deckTokenIdFromJson(responseJson));
    }

    @Test
    void deckTokenIdFromJsonTestHappyPath() {
        when(response.body()).thenReturn("{\"success\": true,\"deck_id\": \"3p40paa87x90\",\"shuffled\": true,\"remaining\": 52}");
        String responseJson = response.body();
        Assertions.assertEquals("3p40paa87x90", deckServisMetods.deckTokenIdFromJson(responseJson));
    }

    @Test
    void deckTokenIdTestNullArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapper.deckTokenId(null));
    }

    @Test
    void deckTokenIdTestEmptyArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapper.deckTokenId(""));
    }

    @Test
    void deckTokenIdTestIllegalArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapper.deckTokenId("vvd hz"));
    }

    @Test
    void parseRemainingCardsHappyPath() {
        when(response.body()).thenReturn("{\"success\": true,\"remaining\": 52}");
        String happyJson = response.body();
        Assertions.assertEquals(52, deckServisMetods.parseRemainingCards(happyJson));
    }

    @Test
    void parseRemainingCardsNotHappyPath() {
        when(response.body()).thenReturn("{\"success\": true,\"remaining\": 52}");
        String happyJson = response.body();
        Assertions.assertNotEquals(5, deckServisMetods.parseRemainingCards(happyJson));
    }

    @Test
    void parseRemainingCardsEmptyArgument() {
        when(response.body()).thenReturn("");
        String happyJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckServisMetods.parseRemainingCards(happyJson));
    }

    @Test
    void parseRemainingCardsNullArgument() {
        when(response.body()).thenReturn(null);
        String happyJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckServisMetods.parseRemainingCards(happyJson));
    }

    @Test
    void parseRemainingCardsIllegalArgument() {
        when(response.body()).thenReturn("gggGGTTmn");
        String happyJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckServisMetods.parseRemainingCards(happyJson));
    }

    @Test
    void parseRemainingCardsIllegalJson() {
        when(response.body()).thenReturn("{\"xxxxx\": true,\"remaining\": 52}");
        String happyJson = response.body();
        Assertions.assertNotEquals(5, deckServisMetods.parseRemainingCards(happyJson));
    }

    @Test
    void parseRemainingCardsIllegalJson1() {
        when(response.body()).thenReturn("{\"success\": true,\"xxxxxxxx\": 52}");
        String happyJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckServisMetods.parseRemainingCards(happyJson));
    }

    @Test
    void parseRemainingCardsIllegalRemainingValueInJson() {
        when(response.body()).thenReturn("{\"success\": true,\"remaining\": nn}");
        String happyJson = response.body();
        Assertions.assertThrows(RuntimeException.class, () -> deckServisMetods.parseRemainingCards(happyJson));
    }


    @Test
    void remainingCardMapperTestNullArgument() {
        Assertions.assertThrows(RuntimeException.class, () -> deckMapper.remainingCardMapper(null));
    }

    @Test
    void remainingCardMapperTestEmptyArgument() {
        Assertions.assertThrows(RuntimeException.class, () -> deckMapper.remainingCardMapper(""));
    }

    @Test
    void remainingCardMapperTestIllegalArgument() {
        Assertions.assertThrows(RuntimeException.class, () -> deckMapper.remainingCardMapper("ffZTRnnK"));
    }

    @Test
    void remainingCardMapperTestHappyPath() {
        when(response.body()).thenReturn("{\"success\": true,\"remaining\": 78}");
        String happyJson = response.body();
        Assertions.assertEquals(78, deckMapper.remainingCardMapper(happyJson));
    }

    @Test
    void remainingCardMapperTestNotHappyPath() {
        when(response.body()).thenReturn("{\"success\": true,\"remaining\": 78}");
        String happyJson = response.body();
        Assertions.assertNotEquals(3, deckMapper.remainingCardMapper(happyJson));
    }

    @Test
    void parseCardsNullArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckServisMetods.parseCards(null));
    }

    @Test
    void parseCardsIllegalArgument() {
        String illegalJson = "XXXXXXXX";
        Assertions.assertThrows(RuntimeException.class, () -> deckServisMetods.parseCards(illegalJson));
    }

    @Test
    void extractCardsFromJsonArrayTestNullArgument() {
        Assertions.assertThrows(NullPointerException.class, () -> deckServisMetods.extractCardsFromJsonArray(null));
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
        Assertions.assertEquals(cards.get(1), deckServisMetods.extractCardsFromJsonArray(jsonArray).get(1));

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

        List<Card> cards = new ArrayList<>();

        Assertions.assertEquals(cards, deckServisMetods.extractCardsFromJsonArray(jsonArray));

    }

    @Test
    void cardMapperTestNullArgument() {

        Assertions.assertThrows(RuntimeException.class, () -> deckMapper.cardMapper(null));
    }

    @Test
    void cardMapperTestEmptyJsonListArgument() {

        ObjectMapper MAPPER = new ObjectMapper();

        when(response.body()).thenReturn("{\n" +
                "\"success\": true,\n" +
                "\"deck_id\": \"kxozasf3edqu\",\n" +
                "\"cards\": [\n" +
                "],\n" +
                "\"remaining\": 20\n" +
                "}");

        List<Card> cards = new ArrayList<>();

        String illegalJson = response.body();

        Assertions.assertEquals(cards, deckMapper.cardMapper(illegalJson));

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

        Assertions.assertEquals(cards.get(1), deckMapper.cardMapper(happyJson).get(1));

    }

    @Test
    void cardMapperTestIllegalArgument() {
        String illegalJson = "xxxxgggtrr";
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapper.cardMapper(illegalJson));
    }

    @Test
    void valueFromStringToIntLowerCaseString() {

        String cardValue = "jack";
        Assertions.assertEquals(11, deckServisMetods.valueFromStringToInt(cardValue));
    }

    @Test
    void valueFromStringToIntUpperCaseString() {

        String cardValue = "QUEEN";
        Assertions.assertEquals(12, deckServisMetods.valueFromStringToInt(cardValue));
    }

    @Test
    void valueFromStringToIntMixCaseString() {

        String cardValue = "JaCk";
        Assertions.assertEquals(11, deckServisMetods.valueFromStringToInt(cardValue));
    }

    @Test
    void valueFromStringToIntNullString() {

        String cardValue = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckServisMetods.valueFromStringToInt(cardValue));
    }

    @Test
    void valueFromStringToIntEmptyString() {

        String cardValue = "";
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckServisMetods.valueFromStringToInt(cardValue));
    }

    @Test
    void valueFromStringToIntIllegalString() {

        String cardValue = "625";
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckServisMetods.valueFromStringToInt(cardValue));

    }

    @Test
    void valueFromStringToIntIllegalString1() {

        String cardValue = "xxxxxHHHHHHHqq";
        Assertions.assertThrows(IllegalArgumentException.class, () -> deckServisMetods.valueFromStringToInt(cardValue));
    }

    @Test
    public void getDeckIdFromResponseBodyEmptyArgument() {

        String json = "";

        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapper.deckTokenId(json));
    }

    @Test
    public void getDeckIdFromResponseBodyJsonNull() {

        String json = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapper.deckTokenId(json));
    }

    @Test
    public void getDeckIdFromResponseBodyIllegalArgument() {

        String json = "cdggrt";

        Assertions.assertThrows(IllegalArgumentException.class, () -> deckMapper.deckTokenId(json));
    }

    @Test
    void getDeckIdFromResponseBodyHappyTest1() {

        String deckId = "deckId";
        String json = "{\"success\": true, \"deck_id\": \"" + deckId + "\"}";

        Assertions.assertEquals(deckId, deckMapper.deckTokenId(json));
    }


}
