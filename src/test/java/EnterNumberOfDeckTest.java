import org.example.gamePlay.Validators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.InputMismatchException;
import java.util.Scanner;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnterNumberOfDeckTest {

    @Mock
    private Scanner scanner;

    private final Validators validators=new Validators(scanner);

    @Test
    void enterNumberOfDeckHappyPathTest(){
        when(scanner.nextInt())
                .thenReturn(5);
        int expectedNumber=validators.enterAmountOfDeck(scanner);
        int actualNumber=5;
        Assertions.assertEquals(actualNumber,expectedNumber);
    }
    @Test
    void enterNumberOfDeckTwoTimerWrongNumberTest(){
        when(scanner.nextInt())
                .thenReturn(0,18,6);
        int expectedNumber=validators.enterAmountOfDeck(scanner);
        int actualNumber=6;
        Assertions.assertEquals(actualNumber,expectedNumber);
    }
    @Test
    void enterNumberOfDeckMismatchInputTest(){
        when(scanner.nextInt())
                .thenThrow(new InputMismatchException())
                .thenReturn(5);
        int expectedNumber=validators.enterAmountOfDeck(scanner);
        int actualNumber=5;
        Assertions.assertEquals(actualNumber,expectedNumber);
    }
}