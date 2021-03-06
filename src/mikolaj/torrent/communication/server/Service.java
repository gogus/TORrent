/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent.communication.server;

import mikolaj.torrent.actions.ActionAbstract;
import mikolaj.torrent.actions.ActionService;
import mikolaj.torrent.communication.server.actions.HttpLog;
import mikolaj.torrent.communication.server.actions.Push;
import mikolaj.torrent.communication.server.actions.Shares;

import java.util.ArrayList;

public class Service extends ActionService {
    private static Service instance = null;
    private Bootstrap server;

    public ArrayList<ActionAbstract> getActions() {
        ArrayList<ActionAbstract> commandsList = new ArrayList<ActionAbstract>();

        commandsList.add(new Shares());
        commandsList.add(new Push());
        commandsList.add(new HttpLog());

        return commandsList;
    }

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }

        return instance;
    }

    public Bootstrap getServer() {
        return server;
    }

    public void setServer(Bootstrap server) {
        this.server = server;
    }
}
