import org.example.gamePlay.Validators;
import org.example.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.InputMismatchException;
import java.util.Scanner;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetWagerTest {

    @Mock
    private Scanner scanner;
    @Mock
    private Player player;

    private final Validators validators = new Validators(scanner);

    @Test
    void getWagerNullPlayerTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> validators.getWager(null, new Scanner(System.in)));
    }
    @Test
    void getWagerHappyPathMinimumWagerTest() {
        when(player.getPlayerPoints()).thenReturn(100);
        when(scanner.nextInt()).thenReturn(1);
        Assertions.assertEquals(1, validators.getWager(player, scanner));
    }
    @Test
    void getWagerHappyPathMaximumWagerTest() {
        int valueOfWager = 100;
        when(player.getPlayerPoints()).thenReturn(valueOfWager);
        when(scanner.nextInt()).thenReturn(valueOfWager);
        Assertions.assertEquals(valueOfWager, validators.getWager(player, scanner));
    }
    @Test
    void getWagerZeroWagerTest() {
        int valueOfWager = 100;
        when(player.getPlayerPoints()).thenReturn(valueOfWager);
        when(scanner.nextInt()).thenReturn(0, 5);
        Assertions.assertEquals(5, validators.getWager(player, scanner));
    }
    @Test
    void getWagerTooBigWagerTest() {
        int valueOfWager = 100;
        when(player.getPlayerPoints()).thenReturn(valueOfWager);
        when(scanner.nextInt()).thenReturn(101, 100);
        Assertions.assertNotEquals(99, validators.getWager(player, scanner));
    }
    @Test
    void getWagerMismatchTest() {
        int valueOfWager = 100;
        when(player.getPlayerPoints()).thenReturn(valueOfWager);
        when(scanner.nextInt())
                .thenThrow(new InputMismatchException())
                .thenReturn(20);
        Assertions.assertEquals(20, validators.getWager(player, scanner));
    }
}