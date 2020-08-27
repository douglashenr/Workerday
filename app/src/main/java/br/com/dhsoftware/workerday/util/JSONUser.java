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
import java.util.Objects;


public class JSONUser {
    private String USERPATH = "user.json";
    private JSONObject jsonObjectEmpty, jsonUser;
    private Context context;
    private SalaryUtil salaryUtil;
    private FileUtil fileUtil;

    public JSONUser(Context context) {
        this.context = context;
        jsonObjectEmpty = new JSONObject();
        salaryUtil = new SalaryUtil();
        fileUtil = new FileUtil(context);

        if (!fileUtil.isFilePresent(context.getFilesDir().getAbsolutePath() + "/" + USERPATH)) {
            create();
        }
    }

    private void createObjectJSONUserEmpty() {
        try {
            jsonObjectEmpty.put("salary", "");
            jsonObjectEmpty.put("deduction", "0.0");
            jsonObjectEmpty.put("percentExtraSalary", "");
            jsonObjectEmpty.put("timeForWeek", "44");
            //jsonObjectEmpty.put("transportation", "false");
            jsonObjectEmpty.put("compTime", "false");

        } catch (JSONException e) {
            e.getMessage();
        }
    }

    private String read() {
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
        } catch (IOException ioException) {
            return null;
        }
    }

    private void create() {
        createObjectJSONUserEmpty();
        fileUtil.writeFileFromJSON(jsonObjectEmpty);
    }

    private void writeFileJson(JSONObject object) {
        fileUtil.writeFileFromJSON(object);

    }



    public void setSalaryJSON(String salary) {
        try {
            writeFileJson(getObjectJSONUserFromStorage().put("salary", salary));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //System.out.println("Adicionado salario em JSON: " + read());
    }

//    private void setSalaryPerHour() throws JSONException {
//        //String salaryPerHour receive a String calculated from salaryUtil.calculateSalaryPerHour that return a double
//        String salaryPerHour = String.valueOf(salaryUtil.calculateSalaryPerHour(getObjectJSONUserFromStorage().getString("salary"),
//                Integer.parseInt(getObjectJSONUserFromStorage().getString("timeForWeek"))));
//
//        //After calculate salaryPerHour, set the string to valueName salaryPerHour
//        writeFileJson(getObjectJSONUserFromStorage().put("salaryPerHour", salaryPerHour));
//    }

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

    public void setCompTime(String compTime) {
        try {
            writeFileJson(getObjectJSONUserFromStorage().put("compTime", compTime));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //System.out.println("Adicionado salario em JSON: " + read());
    }

    private JSONObject getObjectJSONUserFromStorage() {
        try {
            jsonUser = new JSONObject(Objects.requireNonNull(read()));
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

    /*public boolean getTransportation(){
        if(getInfoFromJSON("transportation") == "true")
            return true;

        return false;
    }*/

    public boolean getCompTime() {
        if (getInfoFromJSON("compTime").equals("")) {
            setCompTime("false");
        }

        System.out.println(jsonUser.toString());
        return getInfoFromJSON("compTime").equals("true");
    }


    public String getSalary() {
        return getInfoFromJSON("salary");
    }

    public String getDeduction() {
        if(getInfoFromJSON("deduction").equals(""))
            return "0,00";

        return getInfoFromJSON("deduction");
    }

    public String getPercentExtraSalary() {
        return getInfoFromJSON("percentExtraSalary");
    }

    public String getTimeForWeek() {
        return getInfoFromJSON("timeForWeek");
    }

}
