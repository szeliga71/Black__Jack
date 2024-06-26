package org.example.gameService;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuService {

    public static void showMenu() {
        System.out.println("\n");
        System.out.println("        MENU BLACK JACK !!!\n");
        System.out.println(" Please choose the correct number :\n");
        System.out.println(" 1. Start New Game ");
        System.out.println(" 2. Display game history files ");
        System.out.println(" 3. Display Scores from specified date");
        System.out.println(" 4. Exit");
    }
    public static void showListFile(List<File> files) {
        for (File file : files) {
            System.out.println(file.getName());
        }
    }
    public static String getResultFromSpecifiedFile(File searchedFile) {
        try {
            return LoadGameHistory.showGameResultFromSpecifiedDate(searchedFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void displayHeaderOfGameHistoryFromSpecifiedDate(LocalDate date) {
        System.out.println("Games from the day " + date + " " + "\n");
    }
    public static void displayScoresFromFileSpecifiedDate(String gameResult) {
        System.out.println(gameResult + "\n");
    }
    public static String enterNickName(Scanner scanner) {
        System.out.println(" Please enter your nick name : ");
        while (true) {
            String nickname = scanner.nextLine();
            if (nickname.length() > 1) {
                return nickname;
            } else {
                System.out.println("Your nickname is too short; it must be at least two characters long. Please try again.");
            }
        }
    }
}