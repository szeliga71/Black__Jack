import org.example.gameService.MenuService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.*;
import java.util.Scanner;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMenuTest {

    @Mock
    Scanner scanner;

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
                throw new RuntimeException("Error writting to file1", e);
            }
        } else {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(gameHistoryFilePathTest));) {
                bufferedWriter.write(content);
            } catch (IOException e) {
                throw new RuntimeException("Error writting to new file2", e);
            }
        }
    }
    @Test
    void enterNickHappyathTest() {
        when(scanner.nextLine()).thenReturn("alex");
        String nick = "alex";
        Assertions.assertEquals(nick, MenuService.enterNickName(scanner));
    }
    @Test
    void testEnterNickName_ValidNickname() {
        when(scanner.nextLine()).thenReturn("JackDaniels");
        String nickname = MenuService.enterNickName(scanner);
        Assertions.assertEquals("JackDaniels", nickname);
    }
    @Test
    void testEnterNickName_ValidNickname1() {
        when(scanner.nextLine()).thenReturn("a", "JackDaniels");
        String nickname = MenuService.enterNickName(scanner);
        Assertions.assertEquals("JackDaniels", nickname);
    }
    @Test
    void testEnterNickName_InvalidThenValidNickname() {
        when(scanner.nextLine()).thenReturn("A", "JohnDoe");
        String nickname = MenuService.enterNickName(scanner);
        Assertions.assertEquals("JohnDoe", nickname);
    }
    @Test
    void getResultFromSprcifiedFileNullArgumentTest() {
        Assertions.assertThrows(RuntimeException.class, () -> MenuService.getResultFromSpecifiedFile(null));
    }
    @Test
    void getResultFromSprcifiedFileTest() {
        File invalidFile = new File("invalidPath");
        Assertions.assertThrows(RuntimeException.class, () -> MenuService.getResultFromSpecifiedFile(invalidFile));
    }
    @Test
    void getResultFromSprcifedFileHappyPathTest() {
        if (!directory.exists()) {
            directory.mkdir();
        }
        File file = new File("src/main/TestHistory/testHistory1.txt");
        String content = "test text";
        writeToFile(content, "src/main/TestHistory/testHistory1.txt");
        Assertions.assertEquals(content, MenuService.getResultFromSpecifiedFile(file));
    }
}