package com.example.intel.myapplication.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by manikkam on 19/3/18.
 */

public class DBHandler extends SQLiteOpenHelper {

    private final static String TAG="ConnectDB";
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "sample";

    // Contacts table name
    private static final String TABLE_MASTER= "employee";
    private static final String TABLE_DETAIL= "emp_mobile";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_MASTER_TABLE="create table employee (id TEXT,name TEXT,email TEXT)";
        String CREATE_DETAIL_TABLE="create table emp_mobile (id TEXT,mobile TEXT)";
        db.execSQL(CREATE_MASTER_TABLE);
        db.execSQL(CREATE_DETAIL_TABLE);

        Log.d(TAG,"Table Create Successfully");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAIL);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Data [Insert Function]
    public void addEmployeeData(String keyId,String keyName,
                                   String keyEmail) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", keyId);
        values.put("name", keyName);
        values.put("email", keyEmail);

        // Inserting Row
        db.insert(TABLE_MASTER, null, values);
        db.close(); // Closing database connection
        Log.d(TAG,"Insert Successfully");
    }

    public void addMobileData(String keyId,String keyMobile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", keyId);
        values.put("mobile", keyMobile);

        // Inserting Row
        db.insert(TABLE_DETAIL, null, values);
        db.close(); // Closing database connection
        Log.d(TAG,"Insert Successfully");
    }

    // Getting All Contacts [Get All Records from Table]
    public Cursor getEmployeeData() {
        // Select All Query
        String selectQuery = "SELECT id,name,email FROM employee";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // return contact list
        return cursor;
    }

    // Getting All Contacts [Get All Records from Table]
    public Cursor getEmployeeDetail(String id) {
        // Select All Query
        String selectQuery = "SELECT id,name,email FROM employee Where id='" + id +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // return contact list
        return cursor;
    }

    public Cursor getMobileData(String id) {
        // Select All Query
        String selectQuery = "SELECT mobile FROM emp_mobile Where id='" + id +"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // return contact list
        return cursor;
    }

    // Delete Record
    public void deleteMobileData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from emp_mobile Where id='" + id +"'");
        db.close();
        Log.d(TAG,"Count:"+"Record Deleted");
    }

    // Delete Record
    public void updateEmployeeData(String id,String keyName,
                                 String keyEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("update employee set name='" + keyName + "',email='"+ keyEmail + "' Where id='" + id +"'");
        db.close();
        Log.d(TAG,"Count:"+"Record Updated");
    }

    public int getEmployeeCount() {
        // Select All Query
        String selectQuery = "SELECT id,name,email FROM employee";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // return contact list
        return cursor.getCount();
    }

}
