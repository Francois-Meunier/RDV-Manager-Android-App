package com.example.rdv_app_meunier_nicolas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase database;

    // Table Name
    public static final String TABLE_NAME = "MOMENTS";
    // Table columns
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String MDATE = "date";
    public static final String TIME = "time";
    public static final String ADDRESS = "adress";
    public static final String PHONE = "phone";
    public static final String CONTACT = "contact";
    public static final String DONE = "done";

    // Database Information
    static final String DB_NAME = "PreciousMoments.DB";
    // database version
    static final int DB_VERSION = 1;
    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT NOT NULL, "+ MDATE +
            " TEXT NOT NULL, " + TIME + " TEXT, " + ADDRESS + " TEXT, " + PHONE + " TEXT, "+ CONTACT + " TEXT, "+ DONE + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }
    public void close() {
        database.close();
    }
//Ajoutez les éléments à la database
    public void add(Moment moment) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, moment.getTitle());
        contentValues.put(MDATE, moment.getDate());
        contentValues.put(TIME, moment.getTime());
        contentValues.put(ADDRESS, moment.getAdress());
        contentValues.put(PHONE, moment.getPhone());
        contentValues.put(CONTACT, moment.getContact());
        contentValues.put(DONE, moment.getDone());
        database.insert(TABLE_NAME, null, contentValues);
    }
//permet de récupérer les éléments de la database
    public Cursor getAllMoments() {
        String[] projection = {_ID,TITLE,MDATE,TIME,ADDRESS,PHONE,CONTACT,DONE};
        Cursor cursor = database.query(TABLE_NAME, projection, null, null, null, null, null, null);
        return cursor;
    }
// Pour mettre a jour la database
    public int update(Moment moment) {
        Long _id = moment.getId();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, moment.getTitle());
        contentValues.put(MDATE, moment.getDate());
        contentValues.put(TIME, moment.getTime());
        contentValues.put(ADDRESS, moment.getAdress());
        contentValues.put(PHONE, moment.getPhone());
        contentValues.put(CONTACT, moment.getContact());
        contentValues.put(DONE, moment.getDone());
        int count = database.update(TABLE_NAME, contentValues, this._ID + " = " + _id, null);
        return count;
    }

    public void delete(long _id) {
        database.delete(TABLE_NAME, _ID + "=" + _id, null);
    }
}

