import org.example.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeckApiSerwisTestMockito {}

/*
    @Mock
    private HttpResponse<String> response;

    DeckApiServis deckApiServis=new DeckApiServis();

    @Test
    public void testDeckApiHappyPath() {

        //when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("{\n" +
                "    \"success\": true,\n" +
                "    \"deck_id\": \"3p40paa87x90\",\n" +
                "    \"shuffled\": true,\n" +
                "    \"remaining\": 52\n" +
                "}");

        //when(deckApiHandler.getShuffledDecks(1)).thenReturn(response);
        DeckToken deckToken=new DeckToken("3p40paa87x90");
        DeckApiServis deckApiServis=new DeckApiServis();

        Assertions.assertEquals(deckToken,deckApiServis.deckTokenMapper(response.body()));
        //Assertions.assertEquals(deckToken,deckApiServis.deckTokenMapper(response.body()));
    }
    @Test
    public void testDeckApiHappyPath1() {

        when(deckApiHandler.getShuffledDecks(1)).thenReturn(response);
        when(response.statusCode()).thenReturn(200);
        Assertions.assertEquals(200,deckApiHandler.getShuffledDecks(1).statusCode());
    }

    @Test
    public void testDeckApiHappyPath2() {
       // when(deckApiHandler.getShuffledDecks(2)).thenReturn(response);
        //when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("{\n" +
                "    \"success\": true,\n" +
                "    \"deck_id\": \"3p40paa87x90\",\n" +
                "    \"shuffled\": true,\n" +
                "    \"remaining\": 52\n" +
                "}");



        Assertions.assertEquals("3p40paa87x90",deckApiServis.deckTokenId(response.body()));

    }
    @Test
    public void testDeckApiHappyPath3() {

        when(response.body()).thenReturn("{\n" +
                "    \"success\": true,\n" +
                "    \"deck_id\": \"3p40paa87x90\",\n" +
                "    \"shuffled\": true,\n" +
                "    \"remaining\": 52\n" +
                "}");



        Assertions.assertEquals(52,deckApiServis.remainingCardMapper(response.body()));

    }
    @Test
    public void testDeckApiHappyPath4() {

        when(response.body()).thenReturn("{\n"+
                "\"success\": true,\n"+
                "\"deck_id\": \"kxozasf3edqu\",\n"+
                "\"cards\": [\n"+
        "{\n"+
            "\"code\": \"6H\",\n"+
                "\"image\": \"https://deckofcardsapi.com/static/img/6H.png\",\n"+
                "\"images\": {\n"+
            "\"svg\": \"https://deckofcardsapi.com/static/img/6H.svg\",\n"+
                    "\"png\": \"https://deckofcardsapi.com/static/img/6H.png\"\n"+
        "},\n"+
            "\"value\": \"6\",\n"+
                "\"suit\": \"HEARTS\"\n"+
        "},\n"+
        "{\n"+
            "\"code\": \"5S\",\n"+
                "\"image\": \"https://deckofcardsapi.com/static/img/5S.png\",\n"+
                "\"images\": {\n"+
            "\"svg\": \"https://deckofcardsapi.com/static/img/5S.svg\",\n"+
                    "\"png\": \"https://deckofcardsapi.com/static/img/5S.png\"\n"+
        "},\n"+
            "\"value\": \"5\",\n"+
                "\"suit\": \"SPADES\"\n"+
        "}\n"+
    "],\n"+
        "\"remaining\": 20\n"+
"}");


        Assertions.assertEquals(20,deckApiServis.remainingCardMapper(response.body()));

    }

    @Test
    void cardMapperTestNullJonson(){

        Assertions.assertEquals(null,deckApiServis.cardMapper(null));
    }
    @Test
    void cardMapperTestEmptyJonson(){

        Assertions.assertEquals(null,deckApiServis.cardMapper(""));
    }
    @Test
    void cardMapperTestInvalidJonson(){

        Assertions.assertEquals(null,deckApiServis.cardMapper("xxxxxx"));
    }


    @Test
    void cardMapperTestHappy1(){


        List<Card> cards=new ArrayList<>();
        cards.add(new Card("6",Suite.HEARTS));
        cards.add(new Card("5",Suite.SPADES));

        when(response.body()).thenReturn("{\n"+
                "\"success\": true,\n"+
                "\"deck_id\": \"kxozasf3edqu\",\n"+
                "\"cards\": [\n"+
                "{\n"+
                "\"code\": \"6H\",\n"+
                "\"image\": \"https://deckofcardsapi.com/static/img/6H.png\",\n"+
                "\"images\": {\n"+
                "\"svg\": \"https://deckofcardsapi.com/static/img/6H.svg\",\n"+
                "\"png\": \"https://deckofcardsapi.com/static/img/6H.png\"\n"+
                "},\n"+
                "\"value\": \"6\",\n"+
                "\"suit\": \"HEARTS\"\n"+
                "},\n"+
                "{\n"+
                "\"code\": \"5S\",\n"+
                "\"image\": \"https://deckofcardsapi.com/static/img/5S.png\",\n"+
                "\"images\": {\n"+
                "\"svg\": \"https://deckofcardsapi.com/static/img/5S.svg\",\n"+
                "\"png\": \"https://deckofcardsapi.com/static/img/5S.png\"\n"+
                "},\n"+
                "\"value\": \"5\",\n"+
                "\"suit\": \"SPADES\"\n"+
                "}\n"+
                "],\n"+
                "\"remaining\": 20\n"+
                "}");

        Assertions.assertEquals(cards,deckApiServis.cardMapper(response.body()));


    }

  /*  @Test
    public void cardMapperTest(){

        when(response.body()).thenReturn("{\n"+
                "\"success\": true,\n"+
                "\"deck_id\": \"kxozasf3edqu\",\n"+
                "\"cards\": [\n"+
                "{\n"+
                "\"code\": \"6H\",\n"+
                "\"image\": \"https://deckofcardsapi.com/static/img/6H.png\",\n"+
                "\"images\": {\n"+
                "\"svg\": \"https://deckofcardsapi.com/static/img/6H.svg\",\n"+
                "\"png\": \"https://deckofcardsapi.com/static/img/6H.png\"\n"+
                "},\n"+
                "\"value\": \"6\",\n"+
                "\"suit\": \"HEARTS\"\n"+
                "},\n"+
                "{\n"+
                "\"code\": \"5S\",\n"+
                "\"image\": \"https://deckofcardsapi.com/static/img/5S.png\",\n"+
                "\"images\": {\n"+
                "\"svg\": \"https://deckofcardsapi.com/static/img/5S.svg\",\n"+
                "\"png\": \"https://deckofcardsapi.com/static/img/5S.png\"\n"+
                "},\n"+
                "\"value\": \"5\",\n"+
                "\"suit\": \"SPADES\"\n"+
                "}\n"+
                "],\n"+
                "\"remaining\": 20\n"+
                "}");
        List<Card>cards=new ArrayList<>();
        cards.add(new Card(6, Suite.HEARTS));
        cards.add(new Card(5, Suite.SPADES));

        Assertions.assertEquals(cards.get(0),deckApiServis.cardMapper(response.body()).get(0));
    }
}*/
