package model.classes.logging;

import java.util.ArrayList;

public interface Logger {
    ArrayList<Log> logs = new ArrayList<>();
    default void log(String logContent) {
        logs.add(new Log(logContent));
    }
    default ArrayList<Log> getLogs() {
        return logs;
    }
    default void clearLogs() { logs.clear(); }
}
