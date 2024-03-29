package br.com.dhsoftware.workerday.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import br.com.dhsoftware.workerday.model.Registry;
import br.com.dhsoftware.workerday.util.DateUtil;

public class Dao extends SQLiteOpenHelper {

    public Dao(@Nullable Context context) {
        super(context, "WorkerDay", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Registry (id_registry INTEGER PRIMARY KEY, dayWorked TEXT NOT NULL, entrance TEXT, " +
                "leave TEXT," + " entranceLunch TEXT, leaveLunch TEXT, percent INTEGER, " +
                "requiredTimeToWork TEXT, timeDeclaration TEXT, observation TEXT NOT NULL, imageEntrance TEXT," +
                "imageLeave TEXT, imageEntranceLunch TEXT, imageLeaveLunch TEXT)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void insertRegistryToDao(Registry registry) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues datas = getDataFromRegistry(registry);

        db.insert("Registry", null, datas);
    }

    @NonNull
    private ContentValues getDataFromRegistry(Registry registry) {
        ContentValues datas = new ContentValues();
        datas.put("dayWorked", registry.getDayString());
        datas.put("entrance", registry.getEntranceString());
        datas.put("leave", registry.getLeaveString());
        datas.put("entranceLunch", registry.getEntranceLunchString());
        datas.put("leaveLunch", registry.getLeaveLunchString());
        datas.put("percent", registry.getPercent());
        datas.put("requiredTimeToWork", registry.getRequiredTimeToWorkString());
        datas.put("timeDeclaration", registry.getTimeDeclarationString());
        datas.put("observation", registry.getObservationString());
        datas.put("imageEntrance", registry.getImageEntrance());
        datas.put("imageLeave", registry.getImageLeave());
        datas.put("imageEntranceLunch", registry.getImageEntranceLunch());
        datas.put("imageLeaveLunch", registry.getImageLeaveLunch());
        return datas;
    }


    public ArrayList<Registry> getRegistryFromDao() {
        String sql = "SELECT * FROM Registry;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        DateUtil dateUtil = new DateUtil();

        ArrayList<Registry> listRegistry = new ArrayList<>();
        while (c.moveToNext()) {
            Registry registry = new Registry();
            registry.setId(c.getLong(c.getColumnIndex("id_registry")));
            registry.setDay(dateUtil.convertStringDateToCalendar(c.getString(c.getColumnIndex("dayWorked"))));
            registry.setEntrance(dateUtil.convertStringTimeToCalendar(c.getString(c.getColumnIndex("entrance"))));
            registry.setLeave(dateUtil.convertStringTimeToCalendar(c.getString(c.getColumnIndex("leave"))));
            registry.setEntranceLunch(dateUtil.convertStringTimeToCalendar(c.getString(c.getColumnIndex("entranceLunch"))));
            registry.setLeaveLunch(dateUtil.convertStringTimeToCalendar(c.getString(c.getColumnIndex("leaveLunch"))));
            registry.setPercent(c.getInt(c.getColumnIndex("percent")));
            registry.setRequiredTimeToWork(dateUtil.convertStringTimeToCalendar(c.getString(c.getColumnIndex("requiredTimeToWork"))));
            registry.setTimeDeclaration(dateUtil.convertStringTimeToCalendar(c.getString(c.getColumnIndex("timeDeclaration"))));
            registry.setObservation(c.getString(c.getColumnIndex("observation")));
            registry.setImageEntrance(c.getString(c.getColumnIndex("imageEntrance")));
            registry.setImageLeave(c.getString(c.getColumnIndex("imageLeave")));
            registry.setImageEntranceLunch(c.getString(c.getColumnIndex("imageEntranceLunch")));
            registry.setImageLeaveLunch(c.getString(c.getColumnIndex("imageLeaveLunch")));


            listRegistry.add(registry);
        }

        c.close();


        return listRegistry;
    }


    public void deleteRegistryFromDao(Registry registry) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {String.valueOf(registry.getId())};
        db.delete("Registry", "id_registry = ?", params);
    }

    public boolean isDateNotSet(String date){
        ArrayList<String> stringArrayList;
        stringArrayList = getArrayStringDateFromDao();

        for(int i = 0; i < stringArrayList.size(); i++){
            if(stringArrayList.get(i).equals(date)){
                return false;
            }
        }
        return true;
    }

    private ArrayList<String> getArrayStringDateFromDao(){
        String sql = "SELECT dayWorked FROM Registry;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);



        ArrayList<String> listString = new ArrayList<>();
        while (c.moveToNext()) {
            String string;
            string = c.getString(c.getColumnIndex("dayWorked"));


            listString.add(string);
        }

        c.close();
        return listString;
    }



    public void saveModificationRegistry(Registry registry) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues datas = getDataFromRegistry(registry);

        String[] params = {registry.getId().toString()};
        db.update("Registry", datas,"id_registry = ?", params);
    }


}
