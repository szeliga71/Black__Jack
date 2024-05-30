import org.example.gameService.LoadGameHistory;
import org.example.gameService.TimeDateProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LoadGameHistoryTest {

    @Mock
    private Scanner scanner;
    @Mock
    TimeDateProvider mockTimeDateProvider;

    private File directory = new File("src/main/TestHistory/");


    @AfterEach
    public void cleanUp() {
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

    private void writeToFile(String content, String gameHistoryFilePathTest) {
        File file = new File(gameHistoryFilePathTest);
        if (!file.exists()) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(gameHistoryFilePathTest, true))) {
                bufferedWriter.append(content);
            } catch (IOException e) {
                throw new RuntimeException("Error writting to file", e);
            }
        } else {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(gameHistoryFilePathTest));) {
                bufferedWriter.write(content);
            } catch (IOException e) {
                throw new RuntimeException("Error writting to new file", e);
            }
        }
    }

    @Test
    void showAllGameFilesTestNullArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> LoadGameHistory.allGameFiles(null));
    }

    @Test
    void showAllGameFilesTestNullArgument2() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> LoadGameHistory.allGameFiles(null));
        String exceptMessage = "Please provide a valid argument";
        Assertions.assertEquals(exceptMessage, exception.getMessage());
    }

    @Test
    void showAllGameFilesTestEmptyList() {
        List<File> files = new ArrayList<>();
        String info = "Historia rozgrywek nie posiada zapisanych zadnych sesji w plikach";
        Assertions.assertEquals(info, LoadGameHistory.allGameFiles(files));
    }

    @Test
    void loadHistoryFileTestHappyPath1() {

        if (!directory.exists()) {
            directory.mkdir();
        }
        File file1 = new File("src/main/TestHistory/testHistory1.txt");
        File file2 = new File("src/main/TestHistory/testHistory2.txt");

        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);

        String gameHistoryFilePathTest = file1.getPath();
        String content1 = "test text 1";
        writeToFile(content1, gameHistoryFilePathTest);
        gameHistoryFilePathTest = file2.getPath();
        String content2 = "test text 2";
        writeToFile(content2, gameHistoryFilePathTest);

        Assertions.assertEquals(files.get(0), LoadGameHistory.loadHistoryFile("src/main/TestHistory/").get(0));
    }

    @Test
    void loadHistoryFileTestHappyPath2() {

        if (!directory.exists()) {
            directory.mkdir();
        }
        File file1 = new File("src/main/TestHistory/testHistory1.txt");
        File file2 = new File("src/main/TestHistory/testHistory2.txt");

        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);

        String gameHistoryFilePathTest = file1.getPath();
        String content1 = "test text 1";
        writeToFile(content1, gameHistoryFilePathTest);
        gameHistoryFilePathTest = file2.getPath();
        String content2 = "test text 2";
        writeToFile(content2, gameHistoryFilePathTest);

        Assertions.assertArrayEquals(files.toArray(), LoadGameHistory.loadHistoryFile("src/main/TestHistory/").toArray());
    }

    @Test
    void inputDateHappyPath() {
        LocalDate mockDate = LocalDate.of(2004, 5, 15);
        when(mockTimeDateProvider.inputDate(scanner)).thenReturn(mockDate);
        LocalDate testDate = mockTimeDateProvider.inputDate(scanner);
        Assertions.assertEquals(testDate, mockDate);
    }

    @Test
    void inputDateInvalidDay() {
        LocalDate testDate = mockTimeDateProvider.inputDate(scanner);
        Assertions.assertNotEquals(LocalDate.of(2004, 5, 15), testDate);
    }

    @Test
    void inputDateInvalidMonth() {
        LocalDate testDate = mockTimeDateProvider.inputDate(scanner);
        Assertions.assertNotEquals(LocalDate.of(2004, 12, 10), testDate);
    }

    @Test
    void inputDateInvalidYear() {
        LocalDate testDate = mockTimeDateProvider.inputDate(scanner);
        Assertions.assertNotEquals(LocalDate.of(2024, 5, 15), testDate);
    }

    @Test
    void gameResultFromSpecifiedDateTestIlnvalidArgument() {
        File invalidFile = new File("src/main/TestHistory/invalid.txt");
        Assertions.assertThrows(RuntimeException.class, () -> LoadGameHistory.gameResultFromSpecifiedDate(invalidFile));
    }

    @Test
    void gameResultFromSpecifiedDateTestINullArgument() {
        Assertions.assertThrows(RuntimeException.class, () -> LoadGameHistory.gameResultFromSpecifiedDate(null));
    }

    @Test
    void gameResultFromSpecifiedDateTestNullArgument2() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> LoadGameHistory.gameResultFromSpecifiedDate(null));
        String exceptMessage = "Provided file does not exist or used wrong path name to file";
        Assertions.assertEquals(exceptMessage, exception.getMessage());
    }

    @Test
    void gameResultFromSpecifiedDateTestInvalidArgument2() {
        File invalidFile = new File("src/main/TestHistory/invalid.txt");
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> LoadGameHistory.gameResultFromSpecifiedDate(invalidFile));
        String exceptMessage = "Provided file does not exist or used wrong path name to file";
        Assertions.assertEquals(exceptMessage, exception.getMessage());
    }

    @Test
    void gameResultFromSpecifiedDateTestHappyPath() {

        if (!directory.exists()) {
            directory.mkdir();
        }
        File file = new File("src/main/TestHistory/testHistory1.txt");
        String gameHistoryFilePathTest = file.getPath();
        String content = "test text TEST";
        writeToFile(content, gameHistoryFilePathTest);
        Assertions.assertEquals(content, LoadGameHistory.gameResultFromSpecifiedDate(file));
    }

    @Test
    void createFileTestHappyPath() {
        File file = new File("src/main/TestHistory/game history 2004-05-15.csv");
        LocalDate mockDate = LocalDate.of(2004, 5, 15);
        File createdFile = LoadGameHistory.createFile(mockDate);
        Assertions.assertEquals(createdFile.getName(), file.getName());
    }

    @Test
    void createFileTestHappyPath1() {
        File file = new File("src/main/GameHistory/game history 2004-05-15.csv");
        LocalDate mockDate = LocalDate.of(2004, 5, 15);
        File createdFile = LoadGameHistory.createFile(mockDate);
        Assertions.assertEquals(createdFile.getPath(), file.getPath());
    }

    @Test
    void createFileTestNullLocalDate() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> LoadGameHistory.createFile(null));
    }


}