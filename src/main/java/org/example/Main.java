package org.example;

import org.example.gamePlay.DeckService;
import org.example.gamePlay.Validators;
import org.example.model.House;
import org.example.gamePlay.GamePlay;
import org.example.model.Player;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        House house=new House();
        Player player=new Player("Tom");
        Scanner scanner=new Scanner(System.in);
        Validators validators=new Validators(scanner);
        DeckService deckService=new DeckService();
        GamePlay gamePlay =new GamePlay(scanner,validators,house,player);
        gamePlay.start();
    }
}