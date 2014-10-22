package com.bu.haw.vier.data;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by christian on 22.10.14.
 */
public class ExStorageController {

    private static final String TAG = ExStorageController.class.getName();
    private Boolean isWritable = false;
    private File directoryPath;
    private Boolean isPrivate;

    public ExStorageController(Context context, Boolean isPrivate) {
        this.isPrivate = isPrivate;
        isWritable = isExternalStorageWritable();

        if (isPrivate) {
            directoryPath = getPrivateStorageDir(context,"VIER");
        }else {
            directoryPath = getPublicStorageDir(context);
        }

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getPublicStorageDir(Context context) {
        if (isWritable){
                // Get the public directory
                File file = context.getExternalFilesDir(null);
            if (!file.mkdirs()) {
                Log.e(TAG, "Directory not created");
            }
            return file;
        }else {
            return null;
        }
    }

    public File getPrivateStorageDir(Context context, String albumName) {
        if (isWritable) {
            // Get the private directory
            File file = new File(context.getExternalFilesDir(
                    Environment.DIRECTORY_DOCUMENTS), albumName);
            if (!file.mkdirs()) {
                Log.e(TAG, "Directory not created");
            }
            return file;
        }else {
            return null;
        }
    }

    public File getFile(String fileName){
        File file = new File(directoryPath, fileName);
        return file;
    }
}
