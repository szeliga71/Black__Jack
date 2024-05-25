import org.example.gameService.SaveGame;
import org.example.gameService.VCSFileCreator;
import org.example.model.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
public class SaveGameTest {


    @Mock
    private final GameData gameData = new GameData(1, 2, 3, 4, true);
    @Mock
    private final SaveGame saveGame = new SaveGame(gameData);
    @Mock
    VCSFileCreator fileCreator = new VCSFileCreator();
    @AfterEach
    void cleanUp() {
        deleteTemporaryDirectoryAndFiles(new File("src/main/TestHistory/"));
    }

    LocalDate date = LocalDate.now();
    String gameHistoryFilePathTest = "src/main/TestHistory/test history " + date + ".csv";
    Path path = Paths.get(gameHistoryFilePathTest);
    File file = path.toFile();
    File testFile = new File(gameHistoryFilePathTest);

    private void deleteTemporaryDirectoryAndFiles(File file){
        if(file.isDirectory()){
          File[]files = file.listFiles();
          if(files != null){
              for(File f : files){
                  deleteTemporaryDirectoryAndFiles(f);
              }
          }
        }
        file.delete();
    }
    @Test
    void saveGameHistorNullArgument() {
        SaveGame saveGame1 = new SaveGame(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> saveGame1.saveGameHistory(null));
    }
    @Test
    void saveGameHistorNullArgumentMessage() {
        SaveGame saveGame1 = new SaveGame(null);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> saveGame1.saveGameHistory(null));
        String exceptionMessage = "provided object gameData is null";
        Assertions.assertEquals(exceptionMessage, exception.getMessage());
    }
    @Test
    void saveGameHistorHappyPathCreateDirectory() {

        String testContent = "test text";
        File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(testFile))) {
            bufferedWriter.append(testContent);
        } catch (IOException e) {
            throw new RuntimeException("Error writting to file", e);
        }
        Assertions.assertTrue(testFile.exists());
    }
    @Test
    void saveGameHistorHappyPathContentCompare() {

        GameData gameData = new GameData(1, 2, 3, 4, true);
        String testContent = "test text";
        saveGame.saveGameHistory(gameData);

        File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(testFile))) {
            bufferedWriter.append(testContent);
        } catch (IOException e) {
            throw new RuntimeException("Error writting to file", e);
        }

        String fileContent = "";
        try {
            fileContent = Files.readString(testFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(fileContent, testContent);
    }
}
