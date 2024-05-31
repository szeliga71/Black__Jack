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
    void showGetAllGameFilesTestNullArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> LoadGameHistory.getAllGameFiles(null));
    }

    @Test
    void showGetAllGameFilesTestNullArgument2() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> LoadGameHistory.getAllGameFiles(null));
        String exceptMessage = "Please provide a valid argument";
        Assertions.assertEquals(exceptMessage, exception.getMessage());
    }

    @Test
    void showGetAllGameFilesTestEmptyList() {
        List<File> files = new ArrayList<>();
        String info = "Historia rozgrywek nie posiada zapisanych zadnych sesji w plikach";
        Assertions.assertEquals(info, LoadGameHistory.getAllGameFiles(files));
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
        LocalDate actualDate = LocalDate.of(2024, 5, 15);
        when(scanner.nextLine()).thenReturn("15", "5", "2024");
        LocalDate testDate = TimeDateProvider.inputDate(scanner);
        Assertions.assertEquals(testDate, actualDate);
    }

    @Test
    void inputDateInvalidDay() {
        when(scanner.nextLine()).thenReturn("30", "5", "2024");
        LocalDate givenDate = LocalDate.of(2024, 5, 15);
        LocalDate testDate = TimeDateProvider.inputDate(scanner);
        Assertions.assertNotEquals(givenDate, testDate);
    }

    @Test
    void inputDateInvalidMonth() {
        when(scanner.nextLine()).thenReturn("15", "10", "2024");
        LocalDate givenDate = LocalDate.of(2024, 5, 15);
        LocalDate testDate = TimeDateProvider.inputDate(scanner);
        Assertions.assertNotEquals(givenDate, testDate);
    }

    @Test
    void inputDateInvalidYear() {
        when(scanner.nextLine()).thenReturn("15", "5", "2004");
        LocalDate givenDate = LocalDate.of(2024, 5, 15);
        LocalDate testDate = TimeDateProvider.inputDate(scanner);
        Assertions.assertNotEquals(givenDate, testDate);
    }

    @Test
    void gameResultFromSpecifiedDateTestIlnvalidArgument() {
        File invalidFile = new File("src/main/TestHistory/invalid.txt");
        Assertions.assertThrows(FileNotFoundException.class, () -> LoadGameHistory.gameResultFromSpecifiedDate(invalidFile));
    }

    @Test
    void gameResultFromSpecifiedDateTestINullArgument() {
        Assertions.assertThrows(FileNotFoundException.class, () -> LoadGameHistory.gameResultFromSpecifiedDate(null));
    }

    @Test
    void gameResultFromSpecifiedDateTestNullArgument2() {
        FileNotFoundException exception = Assertions.assertThrows(FileNotFoundException.class, () -> LoadGameHistory.gameResultFromSpecifiedDate(null));
        String exceptMessage = "Provided file does not exist or used wrong path name to file";
        Assertions.assertEquals(exceptMessage, exception.getMessage());
    }

    @Test
    void gameResultFromSpecifiedDateTestInvalidArgument2() {
        File invalidFile = new File("src/main/TestHistory/invalid.txt");
        FileNotFoundException exception = Assertions.assertThrows(FileNotFoundException.class, () -> LoadGameHistory.gameResultFromSpecifiedDate(invalidFile));
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
        try {
            Assertions.assertEquals(content, LoadGameHistory.gameResultFromSpecifiedDate(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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