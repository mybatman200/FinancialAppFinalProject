package com.example.tringuyen.financialappfinalproject;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dataBaseHelper extends SQLiteOpenHelper {

    final static String NAME = "todo";
    final static String USER_NAME = "user_name";
    final static String DATE = "date";
    final static String USER_TOTAL_INCOME = "user_total_income";
    final static String USER_TOTAL_SAVING = "user_total_saving";
    final static String USER_INCOME_TYPE = "user_income_type";
    final static String USER_DAILY_LIMIT = "user_daily_limit";
    final static String USER_SAVING_GOAL = "user_saving_goal";

    final private static Integer VERSION = 1;
    final private Context context;
    final static String[] all_columns = { MainActivity._ID, USER_NAME, DATE, USER_TOTAL_INCOME, USER_TOTAL_SAVING, USER_INCOME_TYPE,USER_DAILY_LIMIT, USER_SAVING_GOAL};

    final private static String CREATE_CMD = "CREATE TABLE "+NAME+" (" + MainActivity._ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + MainActivity.USER_NAME + " TEXT NOT NULL," +
            MainActivity.DATE + " TEXT NOT NULL," + MainActivity.USER_TOTAL_INCOME + " TEXT NOT NULL,"+
            MainActivity.USER_TOTAL_SAVING + " TEXT NOT NULL," + MainActivity.USER_INCOME_TYPE + " TEXT NOT NULL," +
            MainActivity.USER_DAILY_LIMIT +  " TEXT NOT NULL," + MainActivity.USER_SAVING_GOAL + " TEXT NOT NULL)";




    public dataBaseHelper(Context context){
        super(context, NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_CMD);

        ContentValues contentValues = new ContentValues();
        contentValues.put(MainActivity.USER_NAME, "");
        contentValues.put(MainActivity.DATE, "");
        contentValues.put(MainActivity.USER_DAILY_LIMIT, "");
        contentValues.put(MainActivity.USER_SAVING_GOAL, "");
        contentValues.put(MainActivity.USER_INCOME_TYPE, "");
        contentValues.put(MainActivity.USER_TOTAL_INCOME, "");
        contentValues.put(MainActivity.USER_TOTAL_SAVING, "");
        db.insert(NAME, null, contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    void deleteDatabase ( ) {
        context.deleteDatabase(NAME);
    }

}
