package org.example.gameService;
import org.example.model.GameData;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class VCSFileCreator {

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
}