

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.Card;
import org.example.DeckApiServis;
import org.example.DeckToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeckApiServisTest {

    @Mock
    HttpResponse<String> response;



    private final DeckApiServis deckApiServis = new DeckApiServis();



    @Test
     void getNumberOfCardsInDeckFromResponseBodyNullArgument() {

        Assertions.assertThrows(IllegalArgumentException.class ,()->deckApiServis.remainingCardMapper(null));
    }

    @Test
     void getNumberOfCardsInDeckFromResponseBodyIllegalArgument() {

        Assertions.assertThrows(IllegalArgumentException.class ,()->deckApiServis.remainingCardMapper("444444efrfg"));
    }

    @Test
     void getNumberOfCardsInDeckFromResponseBodyNoRemainingFieldInJson() {

        String json = "{\"success\": true,\"xxxxx\": 4}";
        Assertions.assertThrows(RuntimeException.class,()-> deckApiServis.remainingCardMapper(json));

    }


    @Test
     void getNumberOfCardsInDeckFromResponseBodyEmptyJson() {

        String json = "";
        Assertions.assertThrows(IllegalArgumentException.class ,()->deckApiServis.remainingCardMapper(json));

    }
    @Test
     void getNumberOfCadsHappyPath(){

        when(response.body()).thenReturn("{\n" +
                "    \"success\": true,\n" +
                "    \"deck_id\": \"3p40paa87x90\",\n" +
                "    \"shuffled\": true,\n" +
                "    \"remaining\": 52\n" +
                "}");
        Assertions.assertEquals(52,deckApiServis.remainingCardMapper(response.body()));
    }
    @Test
     void getNumberOfCardsInDeckFromResponseBodyHappyPath2() {

        String json = "{\"success\": true,\"remaining\": 9}";
        Assertions.assertEquals(9, deckApiServis.remainingCardMapper(json));

    }

    @Test
        void validateJsonTestNullArgument(){
            Assertions.assertThrows(IllegalArgumentException.class ,()->deckApiServis.validateJson(null));
        }
    @Test
    void validateJsonTestIllegalArgument(){
        Assertions.assertThrows(IllegalArgumentException.class ,()->deckApiServis.validateJson("xxxSSSSvvvv"));
    }
    @Test
    void validateJsonTestInvalidJson(){
            String correctJson="{\"XXXXX\": true,\"remaining\": 9}";
            Exception exception=Assertions.assertThrows(IllegalArgumentException.class,()->deckApiServis.validateJson(correctJson));
        Assertions.assertEquals("json nie zawiera wymaganego klucza i pozytywnej wartosci",exception.getMessage());
    }
    @Test
    void validateJsonTestEmptyJson(){
        String emptyJson="";
        Exception exception=Assertions.assertThrows(IllegalArgumentException.class,()->deckApiServis.validateJson(emptyJson));
        Assertions.assertEquals("json jest nieprawidlowy ",exception.getMessage());
    }

    @Test
    void validateJsonTestEmptyArgument(){
        Assertions.assertThrows(IllegalArgumentException.class ,()->deckApiServis.validateJson(""));
    }

    @Test
    void parseIdFromJsonArgumentNull(){
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.parseIdFromJson(null));
    }
    @Test
    void parseIdFromJsonTestIllegalArgument(){
        String illegalJson="XXXXXXXX";
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.parseIdFromJson(illegalJson));
    }

    @Test
    void parseIdFromJsonHappyPath(){
        when(response.body()).thenReturn("{\"success\": true,\"deck_id\": \"3p40paa87x90\",\"shuffled\": true,\"remaining\": 52}");
        DeckToken deckToken=new DeckToken("3p40paa87x90");
        Assertions.assertEquals(deckToken.getDeckID(),deckApiServis.deckTokenId(response.body()));
    }

    @Test
    void deckTokenMapperTestHappyPath(){
        when(response.body()).thenReturn("{\"success\": true,\"deck_id\": \"3p40paa87x90\",\"shuffled\": true,\"remaining\": 52}");
        DeckToken deckToken=new DeckToken("3p40paa87x90");
        String responseJson= response.body();
        Assertions.assertEquals(deckToken.getDeckID(),deckApiServis.deckTokenId(responseJson));
    }
    @Test
    void deckTokenMapperTestIllegalArgument() {
        when(response.body()).thenReturn("{\"success\": true,\"xxxx\": \"3p40paa87x90\",\"shuffled\": true,\"remaining\": 52}");
        String responseJson= response.body();
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.deckTokenMapper(responseJson));
    }
    @Test
    void deckTokenMapperTestEmptyArgument() {
        when(response.body()).thenReturn("");
        String responseJson= response.body();
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.deckTokenMapper(responseJson));
    }
    @Test
    void deckTokenMapperTestNullArgument() {
        when(response.body()).thenReturn(null);
        String responseJson= response.body();
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.deckTokenMapper(responseJson));
    }

    @Test
    void deckTokenIdFromJsonTestNullArgument(){
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.deckTokenIdFromJson(null));
    }
    @Test
    void deckTokenIdFromJsonTestEmptyArgument(){
        String emptyString="";
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.deckTokenIdFromJson(emptyString));
    }
    @Test
    void deckTokenIdFromJsonTestIllegalArgument(){
        when(response.body()).thenReturn("{\"success\": true,\"xxxx\": \"3p40paa87x90\",\"shuffled\": true,\"remaining\": 52}");
        String responseJson= response.body();
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.deckTokenIdFromJson(responseJson));
    }
    @Test
    void deckTokenIdFromJsonTestHappyPath(){
        when(response.body()).thenReturn("{\"success\": true,\"deck_id\": \"3p40paa87x90\",\"shuffled\": true,\"remaining\": 52}");
        String responseJson= response.body();
        Assertions.assertEquals("3p40paa87x90",deckApiServis.deckTokenIdFromJson(responseJson));
    }
    @Test
    void deckTokenIdTestNullArgument(){
        Assertions.assertThrows(IllegalArgumentException.class,()->deckApiServis.deckTokenId(null));
    }
    @Test
    void deckTokenIdTestEmptyArgument(){
        Assertions.assertThrows(IllegalArgumentException.class,()->deckApiServis.deckTokenId(""));
    }
    @Test
    void deckTokenIdTestIllegalArgument(){
        Assertions.assertThrows(IllegalArgumentException.class,()->deckApiServis.deckTokenId("vvd hz"));
    }

   @Test
   void parseRemainingCardsHappyPath(){
       when(response.body()).thenReturn("{\"success\": true,\"remaining\": 52}");
       String happyJson= response.body();
       Assertions.assertEquals(52,deckApiServis.parseRemainingCards(happyJson));
   }
    @Test
    void parseRemainingCardsNotHappyPath(){
        when(response.body()).thenReturn("{\"success\": true,\"remaining\": 52}");
        String happyJson= response.body();
        Assertions.assertNotEquals(5,deckApiServis.parseRemainingCards(happyJson));
    }
    @Test
    void parseRemainingCardsEmptyArgument(){
        when(response.body()).thenReturn("");
        String happyJson= response.body();
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.parseRemainingCards(happyJson));
    }
    @Test
    void parseRemainingCardsNullArgument(){
        when(response.body()).thenReturn(null);
        String happyJson= response.body();
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.parseRemainingCards(happyJson));
    }
    @Test
    void parseRemainingCardsIllegalArgument(){
        when(response.body()).thenReturn("gggGGTTmn");
        String happyJson= response.body();
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.parseRemainingCards(happyJson));
    }
    @Test
    void parseRemainingCardsIllegalJson(){
        when(response.body()).thenReturn("{\"xxxxx\": true,\"remaining\": 52}");
        String happyJson= response.body();
        Assertions.assertNotEquals(5,deckApiServis.parseRemainingCards(happyJson));
    }
    @Test
    void parseRemainingCardsIllegalJson1(){
        when(response.body()).thenReturn("{\"success\": true,\"xxxxxxxx\": 52}");
        String happyJson= response.body();
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.parseRemainingCards(happyJson));
    }
    @Test
    void parseRemainingCardsIllegalRemainingValueInJson(){
        when(response.body()).thenReturn("{\"success\": true,\"remaining\": nn}");
        String happyJson= response.body();
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.parseRemainingCards(happyJson));
    }


    @Test
    void remainingCardMapperTestNullArgument(){
       Assertions.assertThrows(RuntimeException.class,()->deckApiServis.remainingCardMapper(null));
    }
    @Test
    void remainingCardMapperTestEmptyArgument(){
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.remainingCardMapper(""));
    }
    @Test
    void remainingCardMapperTestIllegalArgument(){
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.remainingCardMapper("ffZTRnnK"));
    }
    @Test
    void remainingCardMapperTestHappyPath(){
       when(response.body()).thenReturn("{\"success\": true,\"remaining\": 78}");
       String happyJson= response.body();
        Assertions.assertEquals(78,deckApiServis.remainingCardMapper(happyJson));
    }
    @Test
    void remainingCardMapperTestNotHappyPath(){
        when(response.body()).thenReturn("{\"success\": true,\"remaining\": 78}");
        String happyJson= response.body();
        Assertions.assertNotEquals(3,deckApiServis.remainingCardMapper(happyJson));
    }





    /*  public List<Card> cardMapper(String json) {

        validateJson(json);
        return parseCards(json);
    }


    public List<Card> parseCards(String json) {
        try {
            JsonNode rootNode = MAPPER.readTree(json);
            JsonNode nodeArray = rootNode.get("cards");
            if (!nodeArray.isArray()) {
                throw new IllegalArgumentException("To nie jest tablica kart");
            } else {
                return extractCardsFromJsonArray(nodeArray);
            }
        } catch (IOException e) {
            throw new RuntimeException("Blad w parsowaniu Json", e);
        }

    }
       public List<Card> extractCardsFromJsonArray(JsonNode nodeArray) {
        List<Card> cards = new ArrayList<>();
        for (JsonNode node : nodeArray) {
            int value = valueFromStringToInt(node.get("value").asText());
            Suit suit = Suit.fromStringtoSuit(node.get("suit").asText());
            cards.add(new Card(value, suit));
        }
        return cards;
    }*/
    @Test
    void parseCardsNullArgument(){
        Assertions.assertThrows(IllegalArgumentException.class,()->deckApiServis.parseCards(null));
    }
    @Test
    void parseCardsIllegalArgument(){
        String illegalJson="XXXXXXXX";
        Assertions.assertThrows(RuntimeException.class,()->deckApiServis.parseCards(illegalJson));
    }

    @Test
    void extractCardsFronJsonArrayTestEmptyArgument(){
        Assertions.assertThrows(NullPointerException.class,()->deckApiServis.extractCardsFromJsonArray(null));
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

    }

   @Test
    public void getNumberOfCardsInDeckFromResponseBodyHappyTest() {

        responseTest = deckApiHandler.drawCards(deckId, 2);

        Assertions.assertEquals(50, deckApiServis.remainingCardMapper(responseTest.body()));

    }

    @Test
    public void getNumberOfCardsInDeckFromResponseBodyHappyTest1() {

        HttpResponse<String> response = deckApiHandler.getShuffledDecks(1);

        Assertions.assertNotEquals(0, deckApiServis.remainingCardMapper(response.body()));

    }
      @Test
    public void getNumberOfCardsInDeckFromResponseBodyIllegalValueInRemainingFieldInJson() {

        String json = "{\"success\": true,\"remaining\": \"xxx\"}";
        Assertions.assertThrows(RuntimeException.class,()-> deckApiServis.remainingCardMapper(json));

    }
*/
//==============================   get deck id
    @Test
    public void getDeckIdFromResponseBodyEmptyArgument() {

        String json = "";

        Assertions.assertThrows(IllegalArgumentException.class, ()->deckApiServis.deckTokenId(json));
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
