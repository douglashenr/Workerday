package br.com.dhsoftware.workerday.util;

import android.content.Context;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtil {

    private Context context;
    private String USERPATH = "user.json";

    public FileUtil(Context context) {
        this.context = context;
    }

    public void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }


    public String getNewPath() {
        return context.getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpeg";
    }



    public void writeFileFromJSON(JSONObject json){
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(USERPATH, Context.MODE_PRIVATE);

            fos.write(json.toString().getBytes());

            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public boolean isFilePresent(String path) {
        File file = new File(path);
        return file.exists();
    }



}
