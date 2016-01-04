/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent.actions;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class Result {
    private Object data;

    public void saveRaw(Object data) {
        this.data = data;
    }

    public void saveJson(ArrayList arrayList) {
        this.data = JSONValue.toJSONString(arrayList);
    }

    public JSONArray fromJson(String data) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(data);

            return (JSONArray) obj;
        } catch (Exception ex) {

        }

        return null;
    }

    public Object getData() {
        return data;
    }
}
