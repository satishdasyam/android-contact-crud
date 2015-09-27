package com.satishdasyam.contactmanager.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 13/9/15.
 */
public class DBAdapter {
    public static final String DATABASE_NAME = "my_contacts";
    public static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_CONTACTS =
            "create table " + ContactDBAdapter.TABLE_CONTACTS +
                    " (" + ContactDBAdapter.ROW_ID
                    + " integer primary key autoincrement, "
                    + ContactDBAdapter.NAME + " TEXT,"
                    + ContactDBAdapter.EMAIL + " TEXT,"
                    + ContactDBAdapter.CONTACT_NUMBER + " TEXT,"
                    + ContactDBAdapter.ADDRESS + " TEXT"
                    + ");";

    /*private static final String CREATE_TABLE_FTS =
            "create virtual table " + ContactDBAdapter.TABLE_FTS +
                    " using fts3 (" + ContactDBAdapter.ROW_ID+","
                    + ContactDBAdapter.NAME + " ,"
                    + ContactDBAdapter.EMAIL + " ,"
                    + ContactDBAdapter.CONTACT_NUMBER + " ,"
                    + ContactDBAdapter.ADDRESS
                    + ");";*/


    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.DBHelper = new DatabaseHelper(ctx);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_CONTACTS);
            //db.execSQL(CREATE_TABLE_FTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            // Adding any table mods to this guy here
        }

    }

    public DBAdapter open() throws SQLException {
        this.db = this.DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.db = null;
        this.DBHelper.close();
    }

    public SQLiteDatabase getSQLiteDatabase() throws SQLException {
        if (db == null)
            this.db = this.DBHelper.getWritableDatabase();
        return db;
    }
}
