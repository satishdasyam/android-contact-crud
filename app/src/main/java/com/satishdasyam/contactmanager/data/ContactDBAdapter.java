package com.satishdasyam.contactmanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;


/**
 * Created by root on 13/9/15.
 */
public class ContactDBAdapter extends DBAdapter {

    public static final String TAG = "ContactDBAdapter";
    public static final String TABLE_CONTACTS = "contact_details";
    public static final String TABLE_FTS = "contact_fts";

    public static final String ROW_ID = BaseColumns._ID;
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String CONTACT_NUMBER = "contact_number";
    public static final String ADDRESS = "address";


    private static final String[] ALL_COLUMNS = {ROW_ID, NAME, EMAIL, CONTACT_NUMBER,ADDRESS};

    //private final Context mCtx;

    public ContactDBAdapter(Context ctx) {
        super(ctx);
    }

    public String setContactData(ContentValues values) {
        long insertId = getSQLiteDatabase().insert(TABLE_CONTACTS, null, values);
        //long ftsId =getSQLiteDatabase().insert(TABLE_FTS, null, values);
       // Log.e(TAG, insertId + ", ftsid:"+ftsId);
        return Long.toString(insertId);
    }

    public Cursor getContactCursor() {

        Cursor cursor = getSQLiteDatabase().query(TABLE_CONTACTS, ALL_COLUMNS, null, null, null,
                null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Log.e(TAG, cursor.getString(cursor.getColumnIndex(ROW_ID)) + "");
            Log.e(TAG, cursor.getString(cursor.getColumnIndex(NAME)) + "");
            Log.e(TAG, cursor.getString(cursor.getColumnIndex(EMAIL)) + "");
            Log.e(TAG, cursor.getString(cursor.getColumnIndex(CONTACT_NUMBER)) + " ");
            cursor.moveToNext();
        }
        return cursor;
    }

    public boolean updateProfile(String id, ContentValues values) {
        int result = getSQLiteDatabase().update(TABLE_CONTACTS, values, ROW_ID + "=?",
                new String[]{id});
        return result > 0;
    }

    public void truncateTable() {
        getSQLiteDatabase().delete(TABLE_CONTACTS, null, null);
    }

    public void deleteTableRow(String id) {
        getSQLiteDatabase().delete(TABLE_CONTACTS, ROW_ID + "=?", new String[]{id});
    }
}
