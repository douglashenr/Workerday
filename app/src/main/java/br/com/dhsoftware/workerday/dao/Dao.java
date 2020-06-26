package br.com.dhsoftware.workerday.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import br.com.dhsoftware.workerday.model.Registry;

public class Dao extends SQLiteOpenHelper {

    public Dao(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Registry (id_registry INTEGER PRIMARY KEY, dayWorked TEXT NOT NULL, entrance TEXT, " +
                "leave TEXT," + " entranceLunch TEXT, leaveLunch TEXT, percent INTEGER, " +
                "requiredTimeToWork TEXT, timeDeclaration TEXT, observation TEXT NOT NULL)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public void insertRegistryToDao(Registry registry) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues datas = PegaDadosDoUsuario(registry);

        db.insert("Registry", null, datas);
    }

    @NonNull
    private ContentValues PegaDadosDoUsuario(Registry registry) {
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
        return datas;
    }
}
