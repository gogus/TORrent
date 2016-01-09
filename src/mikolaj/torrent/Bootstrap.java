/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent;

import mikolaj.torrent.actions.Parser;
import mikolaj.torrent.command.Service;

import java.util.Scanner;

public class Bootstrap {
    public static final String APPLICATION_NAME = "TORrent";
    public static final String APPLICATION_VERSION = "0.1";

    private static void runCommunicationPackage() {
        try {
            new mikolaj.torrent.communication.server.Bootstrap(System.getProperty("user.home")).start();
        } catch (Exception ex) {
            System.out.println("* ERROR *");
            System.out.println("Error with starting the communication server.");
            System.out.println("Please check that port 10101 is free or if you have permission to run the app.");
            System.exit(1);
        }
    }

    private static void runCommunicationPackage(int port) {
        try {
            mikolaj.torrent.communication.server.Bootstrap communicationBootstrap = new mikolaj.torrent.communication.server.Bootstrap(System.getProperty("user.home"));
            communicationBootstrap.setPort(port);
            communicationBootstrap.start();
        } catch (Exception ex) {
            System.out.println("* ERROR *");
            System.out.println("Error with starting the communication server.");
            System.out.println(
                    String.format("Please check that port %s is free or if you have permission to run the app.", port)
            );
            System.exit(1);
        }
    }

    private static void runCommandPackage() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Type your commmand or type help to list all commands: ");
                Parser cliParser = new Parser(scanner.next(), Service.getInstance(), Parser.PARAMETER_PARSER_SCANNER);

                if (!cliParser.isValid()) {
                    Service.getInstance().printHelp();
                } else {
                    cliParser.perform();
                }
            }
        } catch (Exception ex) {
            System.out.println("Application got an error with execute the command.");
            runCommandPackage();
        }
    }

    public static void main(String[] args) {
        System.out.println(String.format("%s v%s - Welcome!", APPLICATION_NAME, APPLICATION_VERSION));

        runCommunicationPackage();
        runCommandPackage();
    }
}
