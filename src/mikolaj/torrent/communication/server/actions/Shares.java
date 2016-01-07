/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent.communication.server.actions;

import mikolaj.torrent.actions.ActionAbstract;
import mikolaj.torrent.actions.Result;
import mikolaj.torrent.communication.server.Service;
import mikolaj.torrent.utils.Checksum;

import org.json.simple.JSONObject;
import java.io.File;
import java.util.*;

public class Shares extends ActionAbstract {
    public String getName() {
        return "shares";
    }

    @Override
    public String getDescription() {
        return null;
    }

    public HashMap<String, Boolean> getParams() {
        return null;
    }

    public Result perform(HashMap<String, String> paramsMap) {
        File[] files = new File(Service.getInstance().getServer().getShareDirectory()).listFiles();

        List<JSONObject> fileCollection = new ArrayList<>();

        try {
            for (File file : files) {
                if (!file.isDirectory()) {
                    JSONObject fileObject = new JSONObject();
                    fileObject.put("fileName", file.getName().replace(" ", "__"));
                    fileObject.put("checkSum", Checksum.getMD5Checksum(file.getAbsolutePath()));
                    fileCollection.add(fileObject);
                }
            }
        } catch (Exception ex) {
            System.out.println("Problem with share you files list, check your permission to application. Exiting...");
            System.exit(1);
        }

        Result result = new Result();
        result.saveJson(fileCollection);

        return result;
    }
}
