package com.example.sasa10.mljac.baza;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.sasa10.mljac.klasa.Obrok;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class Database extends SQLiteOpenHelper {




    private static Database sInstance;
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "mljacDB";

    private static final String TABLE_OBROK = "obrok";
    private static final String OBROK_ID = "id";
    private static final String OBROK_NAZIV = "naziv";
    private static final String OBROK_RECEPT = "recept";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_OBROK + "(" +
                OBROK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + //AUTOINCREMENT
                OBROK_NAZIV + " TEXT," +
                OBROK_RECEPT + " TEXT)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBROK);



            onCreate(db);
        }
    }

    public static synchronized Database getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new Database(context.getApplicationContext());
        }
        return sInstance;
    }

    public int createObrok(Obrok a) {
        int greska=0;
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.

        db.beginTransaction();
        try {

            // The user might already exist in the database (i.e. the same user created multiple posts).
            ContentValues contentValues = new ContentValues();


            contentValues.put(OBROK_NAZIV, a.getNaziv());
            contentValues.put(OBROK_RECEPT, a.getRecept());

            // Notice how we haven'a specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_OBROK, null, contentValues);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Greska!");
            greska=1;
        } finally {
            db.endTransaction();
        }
        return greska;
    }

    public List<Obrok> readObrok() {

        ArrayList<Obrok> obrokList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_OBROK;

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Obrok a = new Obrok();
                    a.setId(Integer.parseInt((cursor.getString(0))));
                    a.setNaziv(cursor.getString(1));
                    a.setRecept(cursor.getString(2));

                    obrokList.add(a);


                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return obrokList;
    }

   /* public void updateObrok(Obrok o) {

            deleteRow(o.getId());
            createObrok(o);


    }*/

    public void deleteRow(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

            db.delete(TABLE_OBROK, OBROK_ID + " =?", new String[]{String.valueOf(id)});


        db.close();
    }



}
