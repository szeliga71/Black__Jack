package org.example.gamePlay;

import org.example.model.Player;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Validators {

    private final Scanner scanner;

    public Validators(Scanner scanner) {
        this.scanner = scanner;
    }

    public int enterAmountOfDeck(Scanner scanner) {
        while (true) {
            try {
                System.out.println("enter correct number of decks (4 - 8) ");
                int numberOfDeck = scanner.nextInt();
                if (numberOfDeck >= 4 && numberOfDeck <= 8) {
                    scanner.nextLine();
                    return numberOfDeck;
                } else {
                    System.out.println(" ERROR!!! Please enter a natural number between 4 and 8 ");
                    scanner.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println(" ERROR!!! Please enter a number ");
                scanner.nextLine();
            }
        }
    }

    public String makeDecisionToPlayOrPass(Scanner scanner) {
        String decision;
        do {
            System.out.println(" Does the player pass or continue? P - pass, G - continue ");
            decision = scanner.nextLine().trim().toLowerCase();
            if (!decision.equals("p") && (!decision.equals("g"))) {
                System.out.println("ERROR!!! Please provide the correct letter to make a choice");
            }
        } while (!decision.equals("p") && !decision.equals("g"));
        return decision;
    }

    public int getWager(Player player, Scanner scanner) {
        int wager;
        if (player != null) {
            while (true) {
                try {
                    System.out.println("How many points would you like to wager?");
                    wager = scanner.nextInt();
                    if (wager > 0 && wager <= player.getPlayerPoints()) {
                        scanner.nextLine();
                        player.setPlayerPoints(player.getPlayerPoints() - wager);
                        System.out.println("The player has points : " + player.getPlayerPoints());
                        return wager;
                    } else {
                        System.out.println("ERROR !!!, Your wager is incorrect.Minimum You can wager is 1 and Maximum You can wager is : " + player.getPlayerPoints() + "\n");
                    }
                } catch (InputMismatchException e) {
                    System.out.println(" ERROR!!!, please enter a natural number not exceeding the number of points in your account");
                    scanner.nextLine();
                }
            }
        } else {
            throw new IllegalArgumentException("object player is null");
        }
    }
}
