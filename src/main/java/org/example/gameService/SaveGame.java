package org.example.gameService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class SaveGame {

    private final File file;
    private final String gameHistoryFilePath;

    public SaveGame() {
        String mainPath = "src/main/GameHistory/game history ";
        this.gameHistoryFilePath = createFileAndPathToFile(mainPath);
        this.file = new File(gameHistoryFilePath);
    }

    public void saveGameHistory(String content) {
        if (content != null && !content.isEmpty()) {
            writeToFile(content);
        } else {
            throw new IllegalArgumentException("Content added to file or for create ne file cannot be null or empty");
        }
    }

    private String createFileAndPathToFile(String mainPath) {
        LocalDate date = LocalDate.now();
        String dateInText = date.toString();
        return mainPath + dateInText + ".csv";
    }

    private void writeToFile(String content) {
        File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
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
}

