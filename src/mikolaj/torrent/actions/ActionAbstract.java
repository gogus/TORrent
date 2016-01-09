/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent.actions;

import java.util.HashMap;

abstract public class ActionAbstract {
    /**
     * Get name of the command
     *
     * @return String
     */
    abstract public String getName();

    /**
     * Get description of the command
     *
     * @return String
     */
    abstract public String getDescription();

    /**
     * Get map to params of action
     * Key is for name of the parameter
     * Value (boolean) is to determine that parameter is required
     *
     * @return Map
     */
    abstract public HashMap<String, Boolean> getParams();

    /**
     * Perform an action from command
     *
     * @return Result
     */
    abstract public Result perform(HashMap<String, String> paramsMap);
}
