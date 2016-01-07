/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent.actions;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class Result {
    private Object data;

    public void saveRaw(Object data) {
        this.data = data;
    }

    public void saveJson(Object array) {
        this.data = JSONValue.toJSONString(array);
    }

    public JSONObject fromJson(String data) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(data);

            return (JSONObject) obj;
        } catch (Exception ex) {

        }

        return null;
    }

    public JSONArray fromJsonArray(String data) {
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
