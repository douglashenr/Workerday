package br.com.dhsoftware.workerday.util;

import android.content.Context;
import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class JSONUser {

    private String USERPATH = "user.json";
    private JSONObject jsonObjectEmpty, jsonUser;
    private Context context;

    public JSONUser(Context context) {
        this.context = context;
        jsonObjectEmpty = new JSONObject();
        createObjectJSONUserEmpty();
        if(isFilePresent())
            System.out.println("JSON quando iniciado construtor: " + getObjectJSONUserFromStorage().toString());
    }

    private void createObjectJSONUserEmpty(){
        try {
            jsonObjectEmpty.put("name", "");
            jsonObjectEmpty.put("e-mail", "");
            jsonObjectEmpty.put("salary", "");
            jsonObjectEmpty.put("deduction", "");
            jsonObjectEmpty.put("percentExtraSalary", "");

        }catch (Exception e){
            e.getMessage();
        }
    }


    public String read() {
        try {
            FileInputStream fis = context.openFileInput(USERPATH);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            System.out.println("Lido pelo método read: " + sb.toString());
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }



    public boolean create(){
        try {
            FileOutputStream fos = context.openFileOutput(USERPATH,Context.MODE_PRIVATE);
            if (jsonObjectEmpty.toString() != null) {
                fos.write(jsonObjectEmpty.toString().getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }
    }

    private void writeFileJson(JSONObject object){
        try {
            FileOutputStream fos = context.openFileOutput(USERPATH,Context.MODE_PRIVATE);

            fos.write(object.toString().getBytes());

            fos.close();
        } catch (FileNotFoundException fileNotFound) {
        } catch (IOException ioException) {

        }
    }



    public boolean isFilePresent() {
        String path = context.getFilesDir().getAbsolutePath() + "/" + USERPATH;
        File file = new File(path);
        return file.exists();
    }

    public void setSalaryJSON(String salary){
        try {
            writeFileJson(getObjectJSONUserFromStorage().put("salary", salary));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Adicionado salario em JSON: " + read());
    }

    public void setDeductionJSON(String deduction){
        try {
            writeFileJson(getObjectJSONUserFromStorage().put("deduction", deduction));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Adicionado salario em JSON: " + read());
    }

    public void setPercentExtraSalaryJSON(String percentExtraSalary){
        try {
            writeFileJson(getObjectJSONUserFromStorage().put("percentExtraSalary", percentExtraSalary));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Adicionado salario em JSON: " + read());
    }

    public JSONObject getObjectJSONUserFromStorage(){
        try {
            jsonUser = new JSONObject(read());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonUser;
    }

}
