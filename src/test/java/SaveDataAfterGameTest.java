import org.example.gamePlay.SaveGameService;
import org.example.model.House;
import org.example.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class SaveDataAfterGameTest {

    @Test
    void saveDataAfterGameNullFirstArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SaveGameService.saveDataAfterGame(null, new House()));
    }

    @Test
    void saveDataAfterGameNullSecondArgumentTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SaveGameService.saveDataAfterGame(new Player("Tom"), null));
    }

    @Test
    void saveDataAfterGameNullArgumentsTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> SaveGameService.saveDataAfterGame(null, null));
    }
}

/*
private static String createContentAfterGame(Player player, House house) {
    VCSFileCreator vcsFileCreator = new VCSFileCreator();
    return vcsFileCreator.createVCSContent(setGameDataAfterGame(player, house));
}


    public static void saveDataAfterGame(Player player,House house) {
        if (player != null && house != null) {
            SaveGame saveGame = new SaveGame();
            saveGame.saveGameHistory(createContentAfterGame(player, house));
        }else{
            throw new IllegalArgumentException("privided arguments : player or house is null");
        }
    }

private static GameData setGameDataAfterGame(Player player, House house) {
    return new GameData(house.getScore(), player.getScore(), player.getPlayerPoints(), 100 - player.getPlayerPoints(), player.isPlayerWin());
}
}
    private static Map<String, Integer> VCSFormatSchema() {
        Map<String, Integer> schema = new HashMap<>();
        schema.put("houseScore", 10);
        schema.put("playerScore", 11);
        schema.put("playerPoints", 12);
        schema.put("changeInPoints", 14);
        schema.put("playerWin", 9);
        schema.put("sesja z godziny", 26);
        return schema;
    }

    public String createVCSContent(GameData gameData) {
        LocalTime time = TimeDateProvider.getCurrentTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (gameData != null) {
            String header = "sesja z godziny  " + time.format(formatter) + ": " + "houseScore,playerScore,playerPoints,changeInPoints,playerWin\n";
            String data = formatField("", "sesja z godziny") + "," +
                    formatField(gameData.getHouseScore(), "houseScore") + "," +
                    formatField(gameData.getPlayerScore(), "playerScore") + "," +
                    formatField(gameData.getPlayerPoints(), "playerPoints") + "," +
                    formatField(gameData.getChangeInPoints(), "changeInPoints") + "," +
                    formatField(gameData.isPlayerWin(), "playerWin") + ",";
            return header + data + "\n";
        } else {
            throw new IllegalArgumentException("provided object gameData is null");
        }
    }

    private static String formatField(Object value, String fieldName) {
        int fieldLength = VCSFormatSchema().get(fieldName);
        String stringValue = String.valueOf(value);
        return stringValue + " ".repeat(fieldLength - stringValue.length());
    }
*/
