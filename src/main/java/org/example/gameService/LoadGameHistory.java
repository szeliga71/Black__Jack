package org.example.gameService;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class LoadGameHistory {

    private static TimeDateProvider timeDateProvider = new TimeDateProvider();
    private static final String mainPath = "src/main/GameHistory";

    public static void setTimeDateProvider(TimeDateProvider provider) {
        timeDateProvider = provider;
    }

    public static String allGameFiles(List<File> files) {
        StringBuilder sb = new StringBuilder();
        if (files == null) {
            throw new IllegalArgumentException("Please provide a valid argument");
        }
        if (files.isEmpty()) {
            return "Historia rozgrywek nie posiada zapisanych zadnych sesji w plikach";
        } else {
            int i = 1;
            for (File file : files) {
                sb.append(i).append(". ").append(file.getName());
                ++i;
            }
            return sb.toString();
        }
    }

    public static List<File> loadHistoryFile(String pathToDirectory) {
        File directory = new File(pathToDirectory);
        List<File> filesInDirectory = new ArrayList<>();
        if (directory.isDirectory()) {
            filesInDirectory = Arrays.asList(Objects.requireNonNull(directory.listFiles()));
            filesInDirectory.sort(Comparator.comparing(File::getName));
        } else {
            System.out.println("The path to directory or the directory is invalid");
        }
        return filesInDirectory;
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

    public static String gameResultFromSpecifiedDate(File searchedFile) {
        if (searchedFile != null && searchedFile.exists()) {
            return readFromFile(searchedFile);
        } else {
            try {
                throw new FileNotFoundException("Provided file does not exist");
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Provided file does not exist or used wrong path name to file", e);
            }
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


