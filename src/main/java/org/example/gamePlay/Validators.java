package org.example.gamePlay;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Validators {

    private final Scanner scanner = new Scanner(System.in);

    public  int enterNumberOfDeck() {
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
    public String makeDecision() {
        String decision = "";
        do {
            System.out.println(" czy gracz pasuje czy gra dalej ? P - pass , G - gra dalej ");
            decision = scanner.nextLine().trim().toLowerCase();
            if (!decision.equals("p") && !decision.equals("g")) {
                System.out.println("BLAD !!!, prosze podac prwidlowa litere aby dokonac wyboru opcji ");
            }
        } while (!decision.equals("p") && !decision.equals("g"));
        return decision;
    }

    public int getWager(Player player) {
        int wager = 0;
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
                    System.out.println("BLAD !!!, Your wager is incorrect.Maximum You can wager is : " + player.getPlayerPoints() + "\n");
                }
            } catch (InputMismatchException e) {
                System.out.println(" BLAD !!!, prosze wprowadzic liczbe naturalna nie wieksza od ilosci punktow na koncie");
                scanner.nextLine();
            }
        }
    }
}
