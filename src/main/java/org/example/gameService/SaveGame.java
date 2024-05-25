package org.example.gameService;

import org.example.model.GameData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class SaveGame {

    private VCSFileCreator vcsFileCreator;
    private GameData gameData;
    private LocalDate date;
    private Path path;
    private File file;
    private String gameHistoryFilePath;
    private DateTimeFormatter formatter;
    private LocalTime time;

    public SaveGame(GameData gameData) {

        this.gameData = gameData;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.gameHistoryFilePath = "src/main/GameHistory/game history " + date + ".csv";
        this.path = Paths.get(gameHistoryFilePath);
        this.file = path.toFile();
        this.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.vcsFileCreator = new VCSFileCreator();
    }

    public void saveGameHistory(GameData gameData) {
        File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String content = vcsFileCreator.createVCSContent(gameData);

        if (file.exists()) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(gameHistoryFilePath, true))) {
                bufferedWriter.append(content);
            } catch (IOException e) {
                throw new RuntimeException("Error writting to file", e);
            }
        } else {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(gameHistoryFilePath));) {
                bufferedWriter.write(content);
            } catch (IOException e) {
                throw new RuntimeException("Error writting to new file", e);
            }
        }
    }


    public void setGameHistoryFilePath(String gameHistoryFilePath) {
        this.gameHistoryFilePath = gameHistoryFilePath;
    }
}
