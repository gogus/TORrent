/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent.command.actions;

import mikolaj.torrent.actions.ActionAbstract;
import mikolaj.torrent.actions.Result;
import mikolaj.torrent.communication.Client;
import mikolaj.torrent.communication.server.Service;

import java.util.HashMap;

public class Pull extends ActionAbstract {
    public String getName() {
        return "pull";
    }

    public String getDescription() {
        return "Pull file from user.";
    }

    public HashMap<String, Boolean> getParams() {
        HashMap<String, Boolean> params = new HashMap<>();

        params.put("host", true);
        params.put("port", true);
        params.put("fileName", true);

        return params;
    }

    public Result perform(HashMap<String, String> paramsMap) {
        String host = paramsMap.get("host");
        String port = paramsMap.get("port");

        Result result = new Result();
        result.saveRaw(paramsMap.get("fileName"));

        Client client = new Client(host, new Integer(port), Client.SERVER_PULL_BYTE);
        client.setResult(result);
        client.sendMessage(
            String.format("%s %s=%s",
                    new mikolaj.torrent.communication.server.actions.Push().getName(),
                    "fileName",
                    paramsMap.get("fileName")
            )
        );

        return new Result();
    }
}
