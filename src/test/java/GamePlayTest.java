import org.example.gamePlay.DeckService;
import org.example.gamePlay.GamePlay;
import org.example.gamePlay.Validators;
import org.example.model.House;
import org.example.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class GamePlayTest {

    @Mock
    DeckService deckService;
    @Mock
    Validators validators;
    @Mock
    Player player;
    @Mock
    House house;

    @Mock
    Scanner scanner;


    GamePlay gamePlay = new GamePlay(house, player);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(gamePlay.getNewDeckId()).thenReturn("xxx");
    }
}

/*
    @Test
    void startTestPlayerPoints() {


        gamePlay.start();
        Assertions.assertEquals(100,player.getPlayerPoints());
    }
}


    public class GamePlayTest {

    private House house;
    private Player player;
    private GamePlay gamePlay;

    @BeforeEach
    void setUp() {
        house = mock(House.class); // Stworzenie mocka obiektu House
        player = mock(Player.class); // Stworzenie mocka obiektu Player
        gamePlay = new GamePlay(house, player);
    }

    @Test
    void testSetStartingPoints() {
        gamePlay.setStartingPoints();
        verify(player).setPlayerPoints(100); // Sprawdzenie, czy metoda setPlayerPoints została wywołana z argumentem 100
        Assertions.assertEquals(100, player.getPlayerPoints()); // To zakłada, że istnieje metoda getPlayerPoints do pobierania punktów
    }
}







    @BeforeEach
    void setUp(){
      gamePlay = new GamePlay(house,player);
    }*/

  /*  @Test
    void gamePlayStartTest1() {
        when(gamePlay.getNewDeckId()).thenReturn("deckId");

            when(deckService.getJsonFromNewDeck(anyInt())).thenReturn("{\"remaining\": 52}");
            when(deckService.getNumbersOfRemainigCardsFromHttpResponse(anyString())).thenReturn(52);
            //when(deckService.getNewDeckAndDeckId(anyString())).thenReturn("new_deck_id");

        //GamePlay gamePlay = new GamePlay(house, player);
        //gamePlay.setWouldYouPlay(false);
        gamePlay.start();

    }
}*/
  /*  @Test
    void testStart_NewGame() {
        // Mock the Scanner behavior
        //when(scanner.nextInt()).thenReturn(4);
       //when(scanner.nextLine()).thenReturn("");

        // Mock the Validators behavior to use the mocked Scanner
        when(validators.enterAmountOfDeck(any(Scanner.class))).thenReturn(4);


        when(deckService.getJsonFromNewDeck(anyInt())).thenReturn("{\"remaining\": 52}");
        when(deckService.getNumbersOfRemainigCardsFromHttpResponse(anyString())).thenReturn(52);
        when(deckService.getNewDeckAndDeckId(anyString())).thenReturn("new_deck_id");

        gamePlay.start();

       verify(player).setPlayerPoints(100);
        verify(validators).enterAmountOfDeck(any(Scanner.class));
        verify(deckService).getJsonFromNewDeck(anyInt());
        verify(deckService).getNumbersOfRemainigCardsFromHttpResponse(anyString());
        verify(deckService).getNewDeckAndDeckId(anyString());
    }
    @Test
    void testPromptForNewGame_EndGame() {
        when(scanner.nextLine()).thenReturn("k");

        gamePlay.start();

        verify(player).setPlayerPoints(100);
        verify(scanner, times(1)).nextLine();
        // Verify that endGame method is called
        verify(player, times(1)).setPlayerPoints(0);
    }

    @Test
    void gamePlayStartTest() {


       // when(getNewDeckId()).thenReturn("deckId");


       // Assertions.assertEquals(100, player1.getPlayerPoints());
    }


//  Przetestowac klase

}*/


/*    public void start() {
        String deckId = startingPointsAndNewCards();
        while (wouldYouPlay && player.getPlayerPoints() > 0) {
            if (remainingCards < 2) {
                deckId = getNewDeckId();
            }
            promptForNewGame(deckId);
        }
    }

    private String startingPointsAndNewCards() {
        player.setPlayerPoints(100);
        System.out.println("The player receives 100 points!");
        return getNewDeckId();
    }
}*/