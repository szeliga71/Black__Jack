import org.example.gamePlay.GamePlay;
import org.example.gamePlay.SaveGameService;
import org.example.gameService.SaveGame;
import org.example.model.House;
import org.example.model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class SaveDataAfterGameTest {

    private final String testContent = "test text after saving file with game results ";
    private LocalDate date = LocalDate.now();
    private final String gameHistoryFilePathTest = "src/main/TestSaveDataAfterGameTest/test history " + date + ".csv";
    private final Path path = Paths.get(gameHistoryFilePathTest);
    private final File file = path.toFile();
    private final File testFile = new File(gameHistoryFilePathTest);
    private static final String mainPath = "src/main/GameHistory";

    @Mock
    private Player player;
    @Mock
    private House house;
    @Mock
    SaveGame saveGame;


   @BeforeEach
    void setUp() {MockitoAnnotations.openMocks(this);
    }


    @AfterEach
    void cleanUp() {
        deleteTemporaryDirectoryAndFiles(new File("src/main/TestSaveDataAfterGameTest/"));
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

    private static String createPathToFile(LocalDate date) {
        if (date != null) {
            String dateInText = date.toString();
            return mainPath + "/game history " + dateInText + ".csv";
        } else {
            throw new IllegalArgumentException("Please provide a valid argument");
        }
    }

    public static File createFile(LocalDate date) {
        return new File(createPathToFile(date));
    }

    public static String gameResultFromSpecifiedDate(File searchedFile) throws FileNotFoundException {
        if (searchedFile != null && searchedFile.exists()) {
            return readLastTwoLinesFromFile(searchedFile);
        } else {
            throw new FileNotFoundException("Provided file does not exist or used wrong path name to file");
        }
    }

    private static String readLastTwoLinesFromFile(File file) {
        List<String> lastTwoLines = new LinkedList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (lastTwoLines.size() == 1) {
                    lastTwoLines.remove(0);
                }
                lastTwoLines.add(line);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file");
        }

        return String.join("\n", lastTwoLines);
    }

    private static void deleteLastTwoLinesFromFile(File file) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file");
            return;
        }

        if (lines.size() >= 2) {
            lines.remove(lines.size() - 1);
            lines.remove(lines.size() - 1);
        } else if (lines.size() == 1) {
            lines.remove(lines.size() - 1);
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file");
        }
    }


    @Test
    void saveDataAfterGameNullFirstArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SaveGameService.saveDataAfterGame(null, new House()));
    }

    @Test
    void saveDataAfterGameNullSecondArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SaveGameService.saveDataAfterGame(new Player("Tom"), null));
    }

    @Test
    void saveDataAfterGameNullArgumentsTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SaveGameService.saveDataAfterGame(null, null));
    }

    @Test
    void saveDataAfterGameHappyPathCreateContentTest() {

        when(player.getScore()).thenReturn(1);
        when(player.getPlayerPoints()).thenReturn(2);
        when(player.isPlayerWin()).thenReturn(true);
        when(house.getScore()).thenReturn(3);

        SaveGameService.saveDataAfterGame(player, house);

        String expectedString = "                          ,3         ,1          ,2           ,98            ,true     ,";
        createPathToFile(LocalDate.now());
        String result = "";
        try {
            result = gameResultFromSpecifiedDate(createFile(LocalDate.now()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        deleteLastTwoLinesFromFile(createFile(LocalDate.now()));
        Assertions.assertEquals(expectedString, result);

    }



    //  To Tylko wstep !!!
    @Test
    void saveDataAfterGameHappyPathContentCompare() {

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



