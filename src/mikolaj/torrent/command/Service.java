/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent.command;

import mikolaj.torrent.Bootstrap;
import mikolaj.torrent.actions.ActionAbstract;
import mikolaj.torrent.actions.ActionService;
import mikolaj.torrent.command.actions.Clients;
import mikolaj.torrent.command.actions.Shares;

import java.util.ArrayList;

public class Service extends ActionService {
    private static Service instance = null;

    public ArrayList<ActionAbstract> getActions() {
        ArrayList<ActionAbstract> commandsList = new ArrayList<ActionAbstract>();

        commandsList.add(new Clients());
        commandsList.add(new Shares());

        return commandsList;
    }

    public void printHelp() {
        System.out.println(String.format("Usage %s:", Bootstrap.APPLICATION_NAME));
        for (ActionAbstract command : this.getActions()) {
            System.out.println(String.format("    %s - %s", command.getName(), command.getDescription()));
        }
    }

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }

        return instance;
    }
}
