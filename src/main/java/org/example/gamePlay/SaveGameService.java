package org.example.gamePlay;

import org.example.gameService.SaveGame;
import org.example.gameService.VCSFileCreator;
import org.example.model.GameData;

public class SaveGameService {

    private static String createContentAfterGame(Player player,House house) {
        VCSFileCreator vcsFileCreator = new VCSFileCreator();
        return vcsFileCreator.createVCSContent(setGameDataAfterGame(player, house));
    }

    public static void saveDataAfterGame(Player player,House house) {
        SaveGame saveGame = new SaveGame();
        saveGame.saveGameHistory(createContentAfterGame(player,house));
    }

    private static GameData setGameDataAfterGame(Player player,House house) {
        return new GameData(house.getScore(), player.getScore(), player.getPlayerPoints(), 100 - player.getPlayerPoints(), player.isPlayerWin());
    }
}
