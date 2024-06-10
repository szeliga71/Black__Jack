package org.example.gamePlay;

import org.example.model.Player;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Validators {


    private Scanner scanner;

    public Validators(Scanner scanner) {
        this.scanner = scanner;
    }

    public  int enterNumberOfDeck(Scanner scanner) {
        while (true) {
            try {
                System.out.println("enter correct number of decks (4 - 8) ");
                int numberOfDeck = scanner.nextInt();
                if (numberOfDeck >= 4 && numberOfDeck <= 8) {
                    scanner.nextLine();
                    return numberOfDeck;
                } else {
                    System.out.println(" BLAD !!!, prosze wprowadzic liczbe naturalna z zakresu 4 do 8");
                    scanner.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println(" BLAD !!!, prosze wprowadzic liczbe ");
                scanner.nextLine();
            }
        }
    }
    public String makeDecision(Scanner scanner) {
        String decision;
        do {
            System.out.println(" czy gracz pasuje czy gra dalej ? P - pass , G - gra dalej ");
            decision = scanner.nextLine().trim().toLowerCase();
            if (!decision.equals("p") && !decision.equals("g")) {
                System.out.println("BLAD !!!, prosze podac prwidlowa litere aby dokonac wyboru opcji ");
            }
        } while (!decision.equals("p") && !decision.equals("g"));
        return decision;
    }

    public int getWager(Player player,Scanner scanner) {
        int wager;
        if (player != null) {
            while (true) {
                try {
                    System.out.println("How many points would you like to wager?");
                    wager = scanner.nextInt();
                    if (wager > 0 && wager <= player.getPlayerPoints()) {
                        scanner.nextLine();
                        player.setPlayerPoints(player.getPlayerPoints() - wager);
                        System.out.println("gracz ma punktow : " + player.getPlayerPoints());
                        return wager;
                    } else {
                        System.out.println("BLAD !!!, Your wager is incorrect.Minimum You can wager is 1 and Maximum You can wager is : " + player.getPlayerPoints() + "\n");
                    }
                } catch (InputMismatchException e) {
                    System.out.println(" BLAD !!!, prosze wprowadzic liczbe naturalna nie wieksza od ilosci punktow na koncie");
                    scanner.nextLine();
                }
            }
        }else{throw new IllegalArgumentException("player is null");}
    }
}
