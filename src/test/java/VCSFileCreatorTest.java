import org.example.gameService.TimeDateProvider;
import org.example.gameService.VCSFileCreator;
import org.example.model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@ExtendWith(MockitoExtension.class)
public class VCSFileCreatorTest {

    @InjectMocks
    private VCSFileCreator vcsFileCreator;

    @Test
    void createVCSContentHappyPath() {
        GameData gameData = new GameData(1, 2, 3, 4, true);
        LocalTime time = TimeDateProvider.getCurrentTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeInString = time.format(dateTimeFormatter);
        String expectedHeader = "sesja z godziny  " + timeInString + ": houseScore,playerScore,playerPoints,changeInPoints,playerWin\n";
        String expectedData = "                          ,1         ,2          ,3           ,4             ,true     ,";
        String expectedContent = expectedHeader + expectedData + "\n";
        String actualContent = vcsFileCreator.createVCSContent(gameData);
        Assertions.assertEquals(expectedContent, actualContent);
    }
    @Test
    void createVCSContentEdgeValuesHappyPath1() {
        GameData gameData = new GameData(0, 0, 0, 0, false);
        LocalTime time = TimeDateProvider.getCurrentTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeInString = time.format(dateTimeFormatter);
        String expectedHeader = "sesja z godziny  " + timeInString + ": houseScore,playerScore,playerPoints,changeInPoints,playerWin\n";
        String expectedData = "                          ,0         ,0          ,0           ,0             ,false    ,";
        String expectedContent = expectedHeader + expectedData + "\n";
        String actualContent = vcsFileCreator.createVCSContent(gameData);
        Assertions.assertEquals(expectedContent, actualContent);
    }
    @Test
    void createVCSContentEdgeValuesHappyPath2() {
        GameData gameData = new GameData(2147483647, 2147483647, 2147483647, 2147483647, true);
        LocalTime time = TimeDateProvider.getCurrentTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeInString = time.format(dateTimeFormatter);
        String expectedHeader = "sesja z godziny  " + timeInString + ": houseScore,playerScore,playerPoints,changeInPoints,playerWin\n";
        String expectedData = "                          ,2147483647,2147483647 ,2147483647  ,2147483647    ,true     ,";
        String expectedContent = expectedHeader + expectedData + "\n";
        String actualContent = vcsFileCreator.createVCSContent(gameData);
        Assertions.assertEquals(expectedContent, actualContent);
    }
    @Test
    void createVCSContentNullArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> vcsFileCreator.createVCSContent(null));
    }
    @Test
    void createVCSContentNullArgumentExceptionMessage() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> vcsFileCreator.createVCSContent(null));
        String exceptionMessage = "provided object gameData is null";
        Assertions.assertEquals(exceptionMessage, exception.getMessage());
    }
}