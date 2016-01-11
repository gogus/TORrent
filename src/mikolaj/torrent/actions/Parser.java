/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license Proprietary License
 */
package mikolaj.torrent.actions;

import mikolaj.torrent.utils.Logger;

import java.util.*;

public class Parser {
    public static final String PARAMETER_PARSER_SCANNER = "scanner";
    public static final String PARAMETER_PARSER_TRADITIONAL = "traditional";

    private String arguments;
    private ActionService actionService;
    private String parameterParserType;

    public Parser(String arguments, ActionService actionService, String parameterParserType) {
        this.arguments = arguments;
        this.actionService = actionService;
        this.parameterParserType = parameterParserType;
    }

    public boolean isValid() {
        return !arguments.isEmpty() && actionService.getAction(arguments) != null;
    }

    private HashMap<String, String> parameterParseViaScanner(HashMap<String, Boolean> paramsMap) {
        Iterator it = paramsMap.entrySet().iterator();
        Scanner scanner = new Scanner(System.in);
        HashMap<String, String> populatedParameters = new HashMap<String, String>();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.print(String.format("Type the value of %s parameter:", pair.getKey()));
            Boolean valid = false;
            while (!valid) {
                Boolean required = false;
                String value;

                if (pair.getValue().equals(true)) {
                    required = true;
                    System.out.print(" (parameter is required) ");
                }

                value = scanner.nextLine();

                if (!required && value.isEmpty() || required && !value.isEmpty()) {
                    populatedParameters.put((String) pair.getKey(), value);
                    valid = true;
                }
            }
            it.remove(); // avoids a ConcurrentModificationException
        }

        return populatedParameters;
    }

    private HashMap<String, String> parameterParseViaTraditional(HashMap<String, Boolean> paramsMap, String cliRaw) {
        HashMap<String, String> populatedParameters = new HashMap<String, String>();

        String allCli[] = cliRaw.split("\\s+");

        for (int i = 1; i <= allCli.length - 1; i++) {
            String argument[] = allCli[i].split("=");
            populatedParameters.put(argument[0], argument[1]);
        }

        return populatedParameters;
    }

    private String getMainAction(String arguments) {
        switch (this.parameterParserType) {
            case PARAMETER_PARSER_SCANNER:
                return arguments;
            case PARAMETER_PARSER_TRADITIONAL:
                return arguments.split("\\s+")[0];
        }

        return null;
    }

    public Result perform() {
        HashMap<String, Boolean> paramsMap = actionService.getAction(getMainAction(arguments)).getParams();
        HashMap<String, String> paramsValues = null;

        if (paramsMap != null) {
            switch (this.parameterParserType) {
                case PARAMETER_PARSER_SCANNER:
                    paramsValues = this.parameterParseViaScanner(paramsMap);
                    break;
                case PARAMETER_PARSER_TRADITIONAL:
                    paramsValues = this.parameterParseViaTraditional(paramsMap, arguments);
                    break;
            }
        }

        if (paramsValues != null) {
            String toLog = getMainAction(arguments) + ": ";

            Iterator it = paramsValues.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                toLog += pair.getKey() + "=" + pair.getValue();
            }

            Logger.getInstance().addToLog(toLog);
        } else {
            Logger.getInstance().addToLog(getMainAction(arguments));
        }

        return actionService.getAction(getMainAction(arguments)).perform(paramsValues);
    }
}
