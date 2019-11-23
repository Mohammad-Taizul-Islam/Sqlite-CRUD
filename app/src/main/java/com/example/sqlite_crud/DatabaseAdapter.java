package com.example.sqlite_crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseAdapter {

    public static final String TAG=DatabaseAdapter.class.getName();

    public static final String KEY_ROWID="_id";
    public static final String KEY_NAME="name";
    public static final String KEY_OCCUPATION="occupation";
    public static final String KEY_COUNTRY="country";
    public static final String[] all_keys=new String[]{KEY_ROWID,KEY_NAME,KEY_OCCUPATION,KEY_COUNTRY};



    public static final int COL_ROWID=0;
    public static final int COL_NAME=1;
    public static final int COL_OCCUPATOON=2;
    public static final int COL_COUNTRY=3;


    public static final String DATABASE_NAME="UniversalDB";
    public static final String CIVILIAN_TABLE="civilian";

    public static final int DATABASE_VERSION=1;


    public static final String  QUERY_TABLE =" CREATE TABLE "  + CIVILIAN_TABLE + " ( " +
            KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            KEY_NAME + " TEXT NOT NULL ," +
            KEY_OCCUPATION + " TEXT NOT NULL , " +
            KEY_COUNTRY + " TEXT NOT NULL " + " ) ; ";



    private final Context context;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;


    public DatabaseAdapter(Context context){
        this.context=context;
        dbHelper=new DatabaseHelper(context);
    }

    public DatabaseAdapter open()
    {
        db=dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        dbHelper.close();
    }

    public long insertRow(String name,String ocuppation,String country)
    {
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_OCCUPATION,ocuppation);
        values.put(KEY_COUNTRY,country);

        return db.insert(CIVILIAN_TABLE,null,values);
    }

    public Cursor getRow(long rowId)
    {
        String WHERE= KEY_ROWID + " = " + rowId;
        Cursor c = db.query(true,CIVILIAN_TABLE,all_keys,WHERE,null,null,null,null,null);
        if(c!=null)
        {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getAllRows()
    {
        String WHERE = null;
        Cursor c =  db.query(true,CIVILIAN_TABLE,all_keys,WHERE,null,null,null,null,null);

        if(c!=null)
        {
            c.moveToFirst();
        }
        return c;
    }

    public boolean deleteRow(long rowId)
    {
        String WHERE = KEY_ROWID + " = " + rowId;
        return db.delete(CIVILIAN_TABLE,WHERE,null) != 0;
    }

    public void deleteAllRows()
    {
        Cursor c=getAllRows();
        long rowId=c.getColumnIndexOrThrow(KEY_ROWID);
        if( c.moveToFirst() )
        {
            do{
               deleteRow(c.getLong((int)rowId));
            }while (c.moveToNext());
        }
    }

    public boolean updateRow(long rowId, String name,String occupation,String country)
    {
        String WHERE = KEY_ROWID + " = " + rowId;

        ContentValues values=new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_OCCUPATION,occupation);
        values.put(KEY_COUNTRY,country);

        return db.update(CIVILIAN_TABLE,values,WHERE,null) != 0;

    }





    private static class  DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
                _db.execSQL(QUERY_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
                _db.execSQL(" DROP TABLE IF EXISTS " + CIVILIAN_TABLE);

                onCreate(_db);
        }
    }


}
