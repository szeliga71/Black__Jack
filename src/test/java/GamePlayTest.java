
import org.example.gamePlay.GamePlay;
import org.example.gamePlay.Validators;
import org.example.model.House;
import org.example.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Scanner;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GamePlayTest {
    @Mock
    Scanner scanner;
    @Mock
    Validators validators;
    @Mock
    House house;
    @Mock
    GamePlay gamePlay;

    @Test
    void startTestRemainingCardsHappyTest() {
        Player playerTest = new Player("testPlayer");
        gamePlay = new GamePlay(scanner, validators, house, playerTest);
        when(validators.enterAmountOfDeck(scanner)).thenReturn(8);
        gamePlay.setWouldYouPlay(false);
        gamePlay.start();
        Assertions.assertEquals(416, gamePlay.getRemainingCards());
    }
    @Test
    void startTestWagerHappyTest1() {
        Player playerTest = new Player("testPlayer");
        gamePlay = new GamePlay(scanner, validators, house, playerTest);
        when(validators.enterAmountOfDeck(scanner)).thenReturn(8);
        gamePlay.setWouldYouPlay(false);
        gamePlay.start();
        Assertions.assertEquals(0, gamePlay.getWager());
    }
    @Test
    void startTestPlayerPointsHappyTest() {
        Player playerTest = new Player("testPlayer");
        gamePlay = new GamePlay(scanner, validators, house, playerTest);
        when(validators.enterAmountOfDeck(scanner)).thenReturn(8);
        gamePlay.setWouldYouPlay(false);
        gamePlay.start();
        Assertions.assertEquals(100, playerTest.getPlayerPoints());
    }
    @Test
    void testGameEndsWhenPlayerPointsAreZero() {
        gamePlay.start();
        Assertions.assertFalse(gamePlay.isWouldYouPlay());
    }
    @Test
    void testGameEndsWhenPlayerChose_K() {
        Player playerTest = new Player("testPlayer");
        gamePlay = new GamePlay(scanner, validators, house, playerTest);
        when(validators.enterAmountOfDeck(scanner)).thenReturn(8);
        gamePlay.setWouldYouPlay(true);
        when(scanner.nextLine()).thenReturn("k");
        gamePlay.start();
        Assertions.assertFalse(gamePlay.isWouldYouPlay());
    }
    @Test
    void testGameEndsWhenPlayerChose_K1() {
        Player playerTest = new Player("testPlayer");
        gamePlay = new GamePlay(scanner, validators, house, playerTest);
        when(validators.enterAmountOfDeck(scanner)).thenReturn(8);
        gamePlay.setWouldYouPlay(true);
        when(scanner.nextLine()).thenReturn("K");
        gamePlay.start();
        Assertions.assertEquals(0, playerTest.getPlayerPoints());
    }
  /*  @Test
    void testGameEndsWhenPlayerChose_g() {
        Player playerTest = new Player("testPlayer");
        gamePlay = new GamePlay(scanner, validators, house, playerTest);
        when(validators.enterAmountOfDeck(scanner)).thenReturn(8);
        gamePlay.setWouldYouPlay(true);
        when(scanner.nextLine()).thenReturn("g");
        when(validators.makeDecisionToPlayOrPass(scanner)).thenReturn("g");
        gamePlay.start();
        Assertions.assertEquals(0, playerTest.getPlayerPoints());
    }*/
}
