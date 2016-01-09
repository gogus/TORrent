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

import java.util.*;

public class Push extends ActionAbstract {
    public String getName() {
        return "push";
    }

    @Override
    public String getDescription() {
        return null;
    }

    public HashMap<String, Boolean> getParams() {
        HashMap<String, Boolean> params = new HashMap<>();
        params.put("fileName", true);

        return params;
    }

    public Result perform(HashMap<String, String> paramsMap) {
        Result result = new Result();

        result.setServerReturnType(Bootstrap.SERVER_RETURN_BYTE);
        result.saveRaw(paramsMap.get("fileName"));

        return result;
    }
}
