package org.example.gameService;

import java.time.LocalTime;

public class TimeProvider {
    public LocalTime getCurrentTime() {
        return LocalTime.now();
    }
}
