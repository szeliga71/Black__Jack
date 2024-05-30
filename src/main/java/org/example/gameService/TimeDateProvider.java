package org.example.gameService;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class TimeDateProvider {

    public LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    public LocalDate inputDate(Scanner scanner) {
        //Scanner scanner = new Scanner(System.in);
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
}
