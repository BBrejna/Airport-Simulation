package model.classes.logging;

import java.util.ArrayList;

public interface Logger {
    ArrayList<Log> getLogs();
    default void log(String logContent) {
        getLogs().add(new Log(logContent));
    }
    default void clearLogs() { getLogs().clear(); }
}
