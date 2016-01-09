/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent.command.actions;

import mikolaj.torrent.Bootstrap;
import mikolaj.torrent.actions.ActionAbstract;
import mikolaj.torrent.actions.Result;
import mikolaj.torrent.communication.server.Service;

import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;

public class Clients extends ActionAbstract {
    public String getName() {
        return "clients";
    }

    public String getDescription() {
        return String.format("List of the users in the %s network.", Bootstrap.APPLICATION_NAME);
    }

    public HashMap<String, Boolean> getParams() {
        return null;
    }

    public Result perform(HashMap<String, String> paramsMap) {
        try {
            System.out.println(String.format("Please wait to scan for available %s clients...", Bootstrap.APPLICATION_NAME));

            String ipAddress = Inet4Address.getLocalHost().getHostAddress();
            String[] ipBlocks = ipAddress.split("\\.");

            String subnet = String.format("%s.%s.%s", ipBlocks[0], ipBlocks[1], ipBlocks[2]);

            for (int i = 1; i < 255; i++) {
                String host = subnet + "." + i;
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(host, Service.getInstance().getServer().getPort()), 100);
                    socket.close();
                    System.out.println(String.format("    %s - %s available", host, Bootstrap.APPLICATION_NAME));
                } catch (Exception ex) {

                }
            }
        } catch (Exception ex) {
            System.out.println("Cant communicate with your network.");
        }

        return new Result();
    }
}
