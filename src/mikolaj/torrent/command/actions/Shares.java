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
import mikolaj.torrent.communication.server.Service;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class Shares extends ActionAbstract {
    public String getName() {
        return "shares";
    }

    public String getDescription() {
        return "Get shares of user.";
    }

    public HashMap<String, Boolean> getParams() {
        HashMap<String, Boolean> params = new HashMap<>();
        params.put("host", true);
        params.put("port", true);

        return params;
    }

    public Result perform(HashMap<String, String> paramsMap) {
        String host = paramsMap.get("host");
        String port = paramsMap.get("port");

        Client client = new Client(host, new Integer(port), Client.SERVER_PULL_STRING);

        JSONArray jsonArray = new Result().fromJsonArray(client.sendMessage(this.getName()));

        System.out.println(String.format("Shared files of %s:", host));

        for (Object file : jsonArray) {
            JSONObject fileObject = (JSONObject) file;
            System.out.println(String.format("     %s (checksum %s)", fileObject.get("fileName"), fileObject.get("checkSum")));
        }

        return new Result();
    }
}
