/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent;

import mikolaj.torrent.actions.Parser;
import mikolaj.torrent.command.Service;
import mikolaj.torrent.utils.ApplicationIdentifier;
import mikolaj.torrent.utils.OperatingSystem;

import java.io.File;
import java.util.Scanner;

public class Bootstrap {
    public static final String APPLICATION_NAME = "TORrent";
    public static final String APPLICATION_VERSION = "0.1";
    private static String sharingDirectory = null;

    private static String getSharingDirectory(String args[]) {
        if (sharingDirectory != null) {
            return sharingDirectory;
        }

        if (args.length == 2) {
            sharingDirectory = args[1];
        } else {
            if (OperatingSystem.getOS() == OperatingSystem.OS.WINDOWS) {
                sharingDirectory = "D:\\TORrent_" + ApplicationIdentifier.getIdentifier();
            } else {
                sharingDirectory = System.getProperty("user.home") + "/" + "TORrent_" + ApplicationIdentifier.getIdentifier();
            }
        }

        if (!new File(sharingDirectory).exists()) {
            new File(sharingDirectory).mkdir();
        }

        return sharingDirectory;
    }

    private static void runCommunicationPackage() {
        try {
            new mikolaj.torrent.communication.server.Bootstrap(sharingDirectory).start();
        } catch (Exception ex) {
            System.out.println("* ERROR *");
            System.out.println("Error with starting the communication server.");
            System.out.println("Please check that port 10101 is free or if you have permission to run the app.");
            System.exit(1);
        }
    }

    private static void runCommunicationPackage(int port) {
        try {
            mikolaj.torrent.communication.server.Bootstrap communicationBootstrap = new mikolaj.torrent.communication.server.Bootstrap(sharingDirectory);
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
                Parser cliParser = new Parser(scanner.nextLine(), Service.getInstance(), Parser.PARAMETER_PARSER_SCANNER);

                if (!cliParser.isValid()) {
                    Service.getInstance().printHelp();
                } else {
                    cliParser.perform();
                }
            }
        } catch (Exception ex) {
            System.out.println("Application got an error with execute the command.");
            ex.printStackTrace();
            runCommandPackage();
        }
    }

    public static void main(String[] args) {
        System.out.println(String.format("%s v%s - Welcome!", APPLICATION_NAME, APPLICATION_VERSION));

        if (args.length == 2) {
            runCommunicationPackage(new Integer(args[0]));
        }

        getSharingDirectory(args);
        runCommunicationPackage();
        runCommandPackage();
    }
}
