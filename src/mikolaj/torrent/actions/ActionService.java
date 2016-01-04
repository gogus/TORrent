/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent.actions;

import java.util.ArrayList;
import java.util.Iterator;

abstract public class ActionService {
    abstract public ArrayList<ActionAbstract> getActions();

    public ActionAbstract getAction(String command) {
        Iterator<ActionAbstract> iterator = this.getActions().iterator();

        while (iterator.hasNext()) {
            ActionAbstract actionObject = iterator.next();
            if (actionObject.getName().equals(command)) {
                return actionObject;
            }
        }

        return null;
    }
}
