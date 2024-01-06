package model.classes.logging;

import java.util.ArrayList;

public interface Logger {
    ArrayList<Log> getLogs();
    default void log(String logContent) {
        log(logContent, true);
    }
    default void log(String logContent, boolean addTime) {
        getLogs().add(new Log(logContent, addTime));
    }
    default void clearLogs() { getLogs().clear(); }
}
