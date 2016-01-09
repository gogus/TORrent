/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent.actions;

import mikolaj.torrent.communication.server.Bootstrap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class Result {
    private Object data;
    private String serverReturnType = Bootstrap.SERVER_RETURN_STRING;

    public String getServerReturnType() {
        return serverReturnType;
    }

    public void setServerReturnType(String serverReturnType) {
        this.serverReturnType = serverReturnType;
    }

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
            ex.printStackTrace();
        }

        return null;
    }

    public JSONArray fromJsonArray(String data) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(data);

            return (JSONArray) obj;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    public Object getData() {
        return data;
    }
}
