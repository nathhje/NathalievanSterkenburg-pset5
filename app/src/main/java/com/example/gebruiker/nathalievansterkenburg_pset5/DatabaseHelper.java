package com.example.gebruiker.nathalievansterkenburg_pset5;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Nathalie van Sterkenburg on 3-3-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;

    private static final String DB_NAME = "TODOLISTDATABASE";
    private static final int DB_VERSION = 1;

    public static final String _ID = "_id";
    public static final String TABLE_NAMEITEM = "todoitem";
    public static final String TABLE_NAMELIST = "todolist";
    public static final String SUBJECT = "subject";
    public static final String DONE = "done";
    public static final String PARENT = "parent";

    public static final String CROSS = "@mipmap/cross";
    public static final String CHECK = "@mipmap/checkmark";

    public static final String WELCOME = "Welcome";
    public static final String TOHAVEDONE = "Click on an item set it as done";
    public static final String TOREMOVE = "Hold an item remove it from the list";
    public static final String LETSTRY = "Try it yourself";

    // create table query
    private static final String CREATE_TABLELIST = "create table " + TABLE_NAMELIST + "(" + _ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT + " TEXT NOT NULL " + ");";

    private static final String CREATE_TABLEITEM = "create table " + TABLE_NAMEITEM + "(" + _ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUBJECT + " TEXT NOT NULL, " + DONE +
            " TEXT, " + PARENT + " TEXT NOT NULL " + ");";

    public static synchronized DatabaseHelper getInstance(Context context) {

        if(instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }

        return instance;
    }


    // constructor
    private DatabaseHelper(Context context) { super(context, DB_NAME, null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("hier zou", "ik wel moeten komen");
        db.execSQL(CREATE_TABLELIST);
        db.execSQL(CREATE_TABLEITEM);
//        db.execSQL("INSERT INTO " + TABLE_NAMEITEM + " (" + SUBJECT + ", " + DONE + ") VALUES (" +
//            WELCOME + ", " + CROSS + "), (" +
//            TOHAVEDONE + ", " + CROSS + "), (" +
//            TOREMOVE + ", " + CROSS + "), (" +
//            LETSTRY + ", " + CROSS + ");");

        Log.i("hier juist", "niet");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAMELIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMEITEM);
        onCreate(db);
    }

}
