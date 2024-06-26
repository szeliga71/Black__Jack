package org.example.gameService;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class LoadGameHistory {

    private static final String mainPath = "src/main/GameHistory";

    public static List<File> loadHistoryFile(String mainPath) {
        File directory = new File(mainPath);
        List<File> filesInDirectory;
        if (directory.isDirectory()) {
            filesInDirectory = Arrays.asList(Objects.requireNonNull(directory.listFiles()));
            filesInDirectory.sort(Comparator.comparing(File::getName));
        } else {
            throw new IllegalArgumentException("The path to directory or the directory is invalid");
        }
        return filesInDirectory;
    }
    public static String createPathToFile(LocalDate date) {
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
    public static String showGameResultFromSpecifiedDate(File searchedFile) throws FileNotFoundException {
        if (searchedFile != null && searchedFile.exists()) {
            return readFromFile(searchedFile);
        } else {
                throw new FileNotFoundException("Provided file does not exist or used wrong path name to file");
        }
    }
    private static String readFromFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = bufferedReader.readLine()) != null) {
                if (!firstLine) {
                    stringBuilder.append("\n");
                }
                stringBuilder.append(line);
                firstLine = false;
            }
        } catch (IOException e) {
            System.err.println(" An error occured while reading the file");
        }
        return stringBuilder.toString();
    }
}