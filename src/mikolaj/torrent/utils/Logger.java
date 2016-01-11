/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Stack;

public class Logger {
    private static Logger instance = null;
    public Stack<String> logs = new Stack<String>();

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }

        return instance;
    }

    public void addToLog(String message) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        logs.push(String.format("[%s] %s", timeStamp, message));
    }
}
