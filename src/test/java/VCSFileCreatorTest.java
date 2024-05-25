import org.example.gameService.VCSFileCreator;
import org.example.model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class VCSFileCreatorTest {


    @Mock
    GameData localGameData = new GameData(1, 2, 3, 4, true);

    VCSFileCreator vcsFileCreator = new VCSFileCreator();


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

