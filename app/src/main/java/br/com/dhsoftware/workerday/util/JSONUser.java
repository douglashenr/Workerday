package br.com.dhsoftware.workerday.util;

import android.content.Context;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class JSONUser {
    private String USERPATH = "user.json";
    private JSONObject jsonObjectEmpty, jsonUser;
    private Context context;
    private SalaryUtil salaryUtil;

    public JSONUser(Context context) {
        this.context = context;
        jsonObjectEmpty = new JSONObject();
        salaryUtil = new SalaryUtil();
        createObjectJSONUserEmpty();
        if (isFilePresent()) {
            //System.out.println("JSON quando iniciado construtor: " + getObjectJSONUserFromStorage().toString());
        } else {
            create();
        }
    }

    private void createObjectJSONUserEmpty() {
        try {
            //jsonObjectEmpty.put("name", "");
            //jsonObjectEmpty.put("e-mail", "");
            jsonObjectEmpty.put("salary", "");
            jsonObjectEmpty.put("deduction", "0.0");
            jsonObjectEmpty.put("percentExtraSalary", "");
            jsonObjectEmpty.put("timeForWeek", "44");
            jsonObjectEmpty.put("salaryPerHour", "");

        } catch (Exception e) {
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

            //System.out.println("Lido pelo método read: " + sb.toString());
            return sb.toString();
        } catch (IOException ioException) {
            return null;
        }
    }

    public boolean create() {
        try {
            FileOutputStream fos = context.openFileOutput(USERPATH, Context.MODE_PRIVATE);
            if (jsonObjectEmpty.toString() != null) {
                fos.write(jsonObjectEmpty.toString().getBytes());
            }
            fos.close();
            return true;
        } catch (IOException ioException) {
            return false;
        }
    }

    private void writeFileJson(JSONObject object) {
        try {
            FileOutputStream fos = context.openFileOutput(USERPATH, Context.MODE_PRIVATE);

            fos.write(object.toString().getBytes());

            fos.close();
        } catch (IOException ioException) {
            ioException.getMessage();
        }
    }

    public boolean isFilePresent() {
        String path = context.getFilesDir().getAbsolutePath() + "/" + USERPATH;
        File file = new File(path);
        return file.exists();
    }

    public void setSalaryJSON(String salary) {
        try {
            writeFileJson(getObjectJSONUserFromStorage().put("salary", salary));
            setSalaryPerHour();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //System.out.println("Adicionado salario em JSON: " + read());
    }

    private void setSalaryPerHour() throws JSONException {
        //String salaryPerHour receive a String calculated from salaryUtil.calculateSalaryPerHour that return a double
        String salaryPerHour = String.valueOf(salaryUtil.calculateSalaryPerHour(getObjectJSONUserFromStorage().getString("salary"),
                Integer.parseInt(getObjectJSONUserFromStorage().getString("timeForWeek"))));

        //After calculate salaryPerHour, set the string to valueName salaryPerHour
        writeFileJson(getObjectJSONUserFromStorage().put("salaryPerHour", salaryPerHour));
    }

    public String getSalaryPerHour() {
        return getInfoFromJSON("salaryPerHour");
    }

    public void setDeductionJSON(String deduction) {
        try {
            writeFileJson(getObjectJSONUserFromStorage().put("deduction", deduction));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //System.out.println("Adicionado salario em JSON: " + read());
    }

    public void setTimeForWeekJSON(String timeForWeek) {
        try {
            writeFileJson(getObjectJSONUserFromStorage().put("timeForWeek", timeForWeek));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //System.out.println("Adicionado salario em JSON: " + read());
    }

    public void setPercentExtraSalaryJSON(String percentExtraSalary) {
        try {
            writeFileJson(getObjectJSONUserFromStorage().put("percentExtraSalary", percentExtraSalary));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //System.out.println("Adicionado salario em JSON: " + read());
    }

    public JSONObject getObjectJSONUserFromStorage() {
        try {
            jsonUser = new JSONObject(read());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonUser;
    }


    private String getInfoFromJSON(String info) {
        getObjectJSONUserFromStorage();
        try {
            return jsonUser.getString(info);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("USER", "Error to get salary from storage");
            return "";
        }
    }


    public String getSalary() {
        return getInfoFromJSON("salary");
    }

    public String getDeduction() {
        return getInfoFromJSON("deduction");
    }

    public String getPercentExtraSalary() {
        return getInfoFromJSON("percentExtraSalary");
    }

    public String getTimeForWeek() {
        return getInfoFromJSON("timeForWeek");
    }

}
