/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license Proprietary License
 */
package mikolaj.torrent.command.actions;

import mikolaj.torrent.Bootstrap;
import mikolaj.torrent.actions.ActionAbstract;
import mikolaj.torrent.actions.Result;

import java.util.HashMap;

public class Init extends ActionAbstract {
    public String getName() {
        return "init";
    }

    public String getDescription() {
        return String.format("Joins to %s", Bootstrap.APPLICATION_NAME);
    }

    public HashMap<String, Boolean> getParams() {
        HashMap<String, Boolean> params = new HashMap<>();
        params.put("port", true);

        return params;
    }

    public Result perform(HashMap<String, String> paramsMap) {
        return new Result();
    }
}
