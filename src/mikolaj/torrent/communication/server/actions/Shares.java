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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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

//        showFiles(files);
        ArrayList<String> fileList = new ArrayList<>();

        for (File file : files) {
            if (file.isDirectory()) {
                //TODO: !!
//                System.out.println("Directory: " + file.getName());
//                showFiles(file.listFiles());
            } else {
                fileList.add(file.getName());
            }
        }

        Result result = new Result();
        result.saveJson(fileList);

        return result;
    }
//
//    private ArrayList<String> showFiles(File[] files) {
//
//
//        return new ArrayList<String>();
//    }
}
