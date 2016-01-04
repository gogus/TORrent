/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license Proprietary License
 */
package mikolaj.torrent.command.actions;

import mikolaj.torrent.actions.ActionAbstract;
import mikolaj.torrent.actions.Result;
import mikolaj.torrent.communication.Client;
import org.json.simple.JSONArray;

import java.util.HashMap;

public class Shares extends ActionAbstract {
    public String getName() {
        return "shares";
    }

    public String getDescription() {
        return "Get shares of user";
    }

    public HashMap<String, Boolean> getParams() {
        HashMap<String, Boolean> params = new HashMap<>();
        params.put("host", true);

        return params;
    }

    public Result perform(HashMap<String, String> paramsMap) {
        String host = paramsMap.get("host");

        Client client = new Client(host, 10101);

        JSONArray jsonArray = new Result().fromJson(client.sendMessage(this.getName()));

        for (Object file : jsonArray) {
            System.out.println(String.format("    %s", file.toString()));
        }

        return new Result();
    }
}
