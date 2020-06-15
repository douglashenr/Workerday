package br.com.dhsoftware.workerday.util;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import br.com.dhsoftware.workerday.model.User;

public class JSONUser {

    private String USERPATH = "user.json";
    private JSONObject jsonObject;

    public void createJSONUser(){
        jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "");
            jsonObject.put("e-mail", "");
            jsonObject.put("salary", "");
            jsonObject.put("deduction", "");
            jsonObject.put("percentExtraSalary", "");

            System.out.println(jsonObject.getString("name") + "-----   teste  ------- " + jsonObject.toString());
        }catch (Exception e){
            e.getMessage();
        }
    }


    public String read(Context context) {
        try {
            FileInputStream fis = context.openFileInput(USERPATH);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }

    public boolean create(Context context){
        try {
            FileOutputStream fos = context.openFileOutput(USERPATH,Context.MODE_PRIVATE);
            if (jsonObject.toString() != null) {
                fos.write(jsonObject.toString().getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }
    }

    public boolean isFilePresent(Context context) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + USERPATH;
        File file = new File(path);
        return file.exists();
    }

}
