package org.example.gameService;

import org.example.gamePlay.GamePlay;
import org.example.gamePlay.Validators;
import org.example.model.House;
import org.example.model.Player;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final Validators validators = new Validators(scanner);
    private final House house = new House();

    public void lauchMenu() {

        boolean menu = true;
        while (menu) {
            MenuService.showMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> {
                    String nick = MenuService.enterNickName(scanner);
                    Player player = new Player(nick);
                    GamePlay gameplay = new GamePlay(scanner, validators, house, player);
                    gameplay.start();
                }
                case "2" -> {
                    final String mainPath = "src/main/GameHistory";
                    List<File> files = LoadGameHistory.loadHistoryFile(mainPath);
                    System.out.println("List of files containing game histories :");
                    MenuService.showListFile(files);
                }
                case "3" -> {
                    System.out.println("Please provide the date for which you want to view the game history ");
                    LocalDate date = TimeDateProvider.inputDate(scanner);
                    System.out.println("\n");
                    File searchedFile = LoadGameHistory.createFile(date);
                    String gameResult = MenuService.getResultFromSpecifiedFile(searchedFile);
                    MenuService.displayHeaderOfGameHistoryFromSpecifiedDate(date);
                    MenuService.displayScoresFromFileSpecifiedDate(gameResult);
                }
                case "4" -> {
                    System.out.println(" END ");
                    menu = false;
                }
                default -> System.out.println("Incorrect choice, please try again ");
            }
        }
        scanner.close();
    }
}