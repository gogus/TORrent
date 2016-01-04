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

    private static void runService() {
        new mikolaj.torrent.communication.server.Bootstrap(10101, System.getProperty("user.home"));
    }

    public static void main(String[] args) {
        System.out.println(String.format("%s v%s - Welcome!", APPLICATION_NAME, APPLICATION_VERSION));

        runService();

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
    }
}
