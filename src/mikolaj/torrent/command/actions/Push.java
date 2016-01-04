/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license Proprietary License
 */
package mikolaj.torrent.command.actions;

import mikolaj.torrent.actions.ActionAbstract;
import mikolaj.torrent.actions.Result;

import java.util.HashMap;

public class Push extends ActionAbstract {
    public String getName() {
        return null;
    }

    public String getDescription() {
        return null;
    }

    public HashMap<String, Boolean> getParams() {
        return null;
    }

    public Result perform(HashMap<String, String> paramsMap) {
        return new Result();
    }
}
