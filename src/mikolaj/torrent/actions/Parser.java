/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license Proprietary License
 */
package mikolaj.torrent.actions;

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

    public Result perform() {
        String action = arguments;

        HashMap<String, Boolean> paramsMap = actionService.getAction(action).getParams();
        HashMap<String, String> paramsValues = null;

        if (actionService.getAction(action).getParams() != null) {
            if (this.parameterParserType.equals(PARAMETER_PARSER_SCANNER)) {
                paramsValues = this.parameterParseViaScanner(paramsMap);
            }
        }

        return actionService.getAction(action).perform(paramsValues);
    }
}
