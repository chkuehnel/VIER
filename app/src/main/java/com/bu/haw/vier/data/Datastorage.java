package com.bu.haw.vier.data;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by jan on 22.10.14.
 */
public class Datastorage {

    private static final String CSV_SEPARATOR = ",";
    private static void writeToCSV(ArrayList<dataObject> dataObj)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(safeFile), "UTF-8"));
            for (dataObject dataSet : dataObj)
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(dataSet.getAx());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(dataSet.getAy());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(dataSet.getAy());
                oneLine.append(CSV_SEPARATOR);
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }


        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
