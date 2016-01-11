/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent.communication.server.actions;

import mikolaj.torrent.actions.ActionAbstract;
import mikolaj.torrent.actions.Result;
import mikolaj.torrent.communication.server.Bootstrap;
import mikolaj.torrent.utils.Logger;

import java.util.*;

public class HttpLog extends ActionAbstract {
    public String getName() {
        return "GET";
    }

    @Override
    public String getDescription() {
        return null;
    }

    public HashMap<String, Boolean> getParams() {
        return null;
    }

    public Result perform(HashMap<String, String> paramsMap) {
        Result result = new Result();

        result.setServerReturnType(Bootstrap.SERVER_RETURN_STRING);

        String logs = "TORrent Logs \n\n";
        Iterator<String> iterator = Logger.getInstance().logs.iterator();

        while (iterator.hasNext()) {
            logs += iterator.next() + "\n";
        }

        result.saveRaw(logs);

        return result;
    }
}
