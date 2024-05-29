package org.example.gameService;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class LoadGameHistory {

    private static final String mainPath = "src/main/GameHistory";


    public static void showAllGameFiles(List<File> files) {
        if (files == null) {
            throw new IllegalArgumentException("Please provide a valid argument");
        }
        if (files.isEmpty()) {
            System.out.println("Historia rozgrywek nie posiada zapisanych zadnych sesji w plikach");
        }
        int i = 1;
        for (File file : files) {
            System.out.println(i + ". " + file.getName());
            ++i;
        }
    }

    public static List<File> loadHistoryFile() {
        File directory = new File(mainPath);
        List<File> filesInDirectory = new ArrayList<>();
        if (directory.isDirectory()) {
            filesInDirectory = Arrays.asList(Objects.requireNonNull(directory.listFiles()));
            filesInDirectory.sort(Comparator.comparing(File::getName));
        }
        return filesInDirectory;
    }

    public static LocalDate inputDate() {
        Scanner scanner = new Scanner(System.in);
        LocalDate date;
        while (true) {
            try {
                System.out.println("Please enter a day (1-31): ");
                int day = Integer.parseInt(scanner.nextLine());

                System.out.println("Please enter a month (1-12): ");
                int month = Integer.parseInt(scanner.nextLine());

                System.out.println("Please enter a year(e.g,2023): ");
                int year = Integer.parseInt(scanner.nextLine());

                date = LocalDate.of(year, month, day);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid number");
            } catch (IllegalArgumentException | DateTimeException e) {
                System.out.println("Invalid date. Please enter a valid date.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred." + e.getMessage());
            }
        }
        return date;
    }

    private static String createPathToFile() {
        String dateInText = inputDate().toString();
        return mainPath + "/game history " + dateInText + ".csv";
    }

    private static File createFile() {
        return new File(createPathToFile());
    }

    public static String showGameResultFromSpecifiedDate() {
        File searchedFile = createFile();
        if (searchedFile.exists()) {
            return readFromFile(searchedFile);
        } else {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String readFromFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println(" An error occured while reading the file");
        }
        return stringBuilder.toString();
    }
}


