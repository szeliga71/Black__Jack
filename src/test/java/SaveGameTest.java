import org.example.gameService.SaveGame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class SaveGameTest {

    private String testContent = "test text";
    private final SaveGame saveGame = new SaveGame();
    private LocalDate date = LocalDate.now();
    private String gameHistoryFilePathTest = "src/main/TestHistory/test history " + date + ".csv";
    private Path path = Paths.get(gameHistoryFilePathTest);
    private File file = path.toFile();
    private File testFile = new File(gameHistoryFilePathTest);

    @AfterEach
    void cleanUp() {
        deleteTemporaryDirectoryAndFiles(new File("src/main/TestHistory/"));
    }
    private void deleteTemporaryDirectoryAndFiles(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteTemporaryDirectoryAndFiles(f);
                }
            }
        }
        file.delete();
    }
    @Test
    void saveGameHistoryNullArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> saveGame.saveGameHistory(null));
    }
    @Test
    void saveGameHistoryEmptyArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> saveGame.saveGameHistory(""));
    }
    @Test
    void saveGameHistoryNullArgumentMessage() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> saveGame.saveGameHistory(null));
        String exceptionMessage = "Content added to file or for create ne file cannot be null or empty";
        Assertions.assertEquals(exceptionMessage, exception.getMessage());
    }
    @Test
    void saveGameHistoryEmptyArgumentMessage() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> saveGame.saveGameHistory(""));
        String exceptionMessage = "Content added to file or for create ne file cannot be null or empty";
        Assertions.assertEquals(exceptionMessage, exception.getMessage());
    }
    @Test
    void saveGameHistoryHappyPathCreateDirectory() {
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
    void saveGameHistoryHappyPathContentCompare() {
        saveGame.saveGameHistory(testContent);
        File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(testFile))) {
            bufferedWriter.append(testContent);
        } catch (IOException e) {
            throw new RuntimeException("Error writting to file", e);
        }
        String readedFileContent = "";
        try {
            readedFileContent = Files.readString(testFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(readedFileContent, testContent);
    }
}