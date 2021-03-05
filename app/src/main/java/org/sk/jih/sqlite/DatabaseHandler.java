package org.sk.jih.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.sk.jih.modal.Vehicle;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vehicleManager";
    private static final String TABLE_CONTACTS = "vehicles";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
//                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";
//        db.execSQL(CREATE_CONTACTS_TABLE);
//    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_NAME + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new vehicle
    public void addVehicle(Vehicle vehicle) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, vehicle.getVehicleNumber()); // Vehicle Number

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get all contacts in a list view
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleNumber(cursor.getString(0));
                // Adding contact to list
                vehicleList.add(vehicle);
            } while (cursor.moveToNext());
        }

        // return contact list
        return vehicleList;
    }

    // Deleting
    public void deleteVehicles() {
        SQLiteDatabase db = this.getWritableDatabase();;
        String countQuery = "DELETE FROM " + TABLE_CONTACTS;
        db.execSQL(countQuery);
        db.close();
    }

    // add multiple records
    public void addMultipleRecords(String data) {
        SQLiteDatabase db = this.getWritableDatabase();;
        String countQuery = "INSERT INTO " + TABLE_CONTACTS + " VALUES " + data;
        db.execSQL(countQuery);
        db.close();
    }


}
