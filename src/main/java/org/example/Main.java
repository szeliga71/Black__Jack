package org.example;

import org.example.model.House;
import org.example.gamePlay.GamePlay;
import org.example.model.Player;

public class Main {
    public static void main(String[] args) {




        House house=new House();
        Player player=new Player("Tom");
        GamePlay gamePlay =new GamePlay(house,player);
        gamePlay.start();

    }

}