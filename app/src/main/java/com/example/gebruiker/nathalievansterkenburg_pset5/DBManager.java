package com.example.gebruiker.nathalievansterkenburg_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by Gebruiker on 3-3-2017.
 */

public class DBManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    // constructor
    public DBManager(Context c) { context = c; }

    // opening the database
    public DBManager open() throws SQLiteException {
        dbHelper = DatabaseHelper.getInstance(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    // closing the database
    public void close() { dbHelper.close();
        database.close();}

    // insert item into database
    public void insert(String name, String tableName, String parent) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SUBJECT, name);

        if(tableName.equals(DatabaseHelper.TABLE_NAMEITEM)) {
            contentValue.put(DatabaseHelper.DONE, DatabaseHelper.CROSS);
            contentValue.put(DatabaseHelper.PARENT, parent);
        }

        database.insert(tableName, null, contentValue);
        fetchItem(null);
    }

    // retrieve item from database
    public Cursor fetchItem(String condition) {

        String[] columns = new String[] {DatabaseHelper._ID, DatabaseHelper.SUBJECT,
                DatabaseHelper.DONE, DatabaseHelper.PARENT};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAMEITEM, columns, condition, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Log.d("cursor", Integer.toString(cursor.getCount()));
        return cursor;
    }

    public Cursor fetchList(String condition) {

        String[] columns = new String[] {DatabaseHelper._ID, DatabaseHelper.SUBJECT};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAMEITEM, columns, condition, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Log.d("cursor", Integer.toString(cursor.getCount()));
        return cursor;
    }

    // update database
    public int update(long _id, String checked) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.DONE, checked);
        int i = database.update(DatabaseHelper.TABLE_NAMEITEM, contentValues,
                DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    // deletes item from database
    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAMEITEM, DatabaseHelper._ID + " = " + _id, null);
    }
}
