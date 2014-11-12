package com.bu.haw.vier.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by christian on 22.10.14.
 */
public class DataStorage {
    private File storageFile;

    public DataStorage(File storageFile) {
        this.storageFile = storageFile;
    }

    public void writeToFile(ArrayList<dataObject> dataList){


        PrintWriter writer = null;
            try {
                writer = new PrintWriter(storageFile);
            for (dataObject dataSet : dataList){
                String line = dataSet.getAx() + ";" + dataSet.getAy() + ";" + dataSet.getAz() + "\n";
                writer.append(line);
                writer.flush();

            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setStorageFile(File storageFile) {
        this.storageFile = storageFile;
    }

    // TODO: is needed any more?
    public Boolean isFileValid() {
        return storageFile != null;
    }

}
