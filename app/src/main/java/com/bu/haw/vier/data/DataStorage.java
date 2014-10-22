package com.bu.haw.vier.data;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(storageFile);
            for (dataObject dataSet : dataList){
                String line = dataSet.getAx() + ";" + dataSet.getAy() + ";" + dataSet.getAz() + "\n";
                byte[] data = line.getBytes();
                fos.write(data);
                fos.flush();
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStorageFile(File storageFile) {
        this.storageFile = storageFile;
    }

}
