package org.example;


import org.example.gameService.LoadGameHistory;
import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        List<File>gameFiles=LoadGameHistory.loadHistoryFile("src/main/GameHistory");

        System.out.println(LoadGameHistory.getAllGameFiles(gameFiles));

        //System.out.println(LoadGameHistory.gameResultFromSpecifiedDate());
        //LoadGameHistory.inputDate();
    }

    }
