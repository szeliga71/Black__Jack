import org.example.gameService.LoadGameHistory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Scanner;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LoadGameHistoryTest {

    @Mock
    private Scanner scanner;

    @Mock
    private BufferedReader bufferedReader;

    @Mock
    private FileReader fileReader;

    @Mock
    private File file;




    @Test
    void inputDateHappyPath() {

        when(scanner.nextLine()).thenReturn("15", "5", "2004");

        LocalDate testDate = LoadGameHistory .inputDate();

        Assertions.assertEquals(LocalDate.of(2004, 5, 15), testDate);
    }

    @Test
    void inputDateInvalidDay() {

        when(scanner.nextLine()).thenReturn("10", "5", "2004");

        LocalDate testDate = LoadGameHistory.inputDate();

        Assertions.assertNotEquals(LocalDate.of(2004, 5, 15), testDate);
    }

    @Test
    void showAllGameFilesTestNullArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> LoadGameHistory .showAllGameFiles(null));
    }

   /* @Test
    void showAllGameFilesNameTestNullArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> LoadGameHistory .showAllGameFilesName(null));
    }*/

    @Test
    void showGameResultFromSpecifiedDateTestIllegalYearArgument() {
        when(scanner.nextLine()).thenReturn("15", "5", "2222");
        LocalDate testDate = LoadGameHistory .inputDate();
        String testResult = "";
        Assertions.assertEquals(testResult, LoadGameHistory.showGameResultFromSpecifiedDate());
    }
}