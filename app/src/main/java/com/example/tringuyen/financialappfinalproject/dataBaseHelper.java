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
    private static final String DATABASE_NAME = "database_name";
    final private static Integer VERSION = 1;
    final private Context context;
    final static String[] all_columns = { MainActivity._ID, USER_NAME, DATE, USER_TOTAL_INCOME, USER_TOTAL_SAVING, USER_INCOME_TYPE,USER_DAILY_LIMIT, USER_SAVING_GOAL};

    final private static String CREATE_CMD = "CREATE TABLE "+NAME+" (" + MainActivity._ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + MainActivity.USER_NAME + " TEXT NOT NULL," +
            MainActivity.DATE + " TEXT NOT NULL," + MainActivity.USER_TOTAL_INCOME + " TEXT NOT NULL,"+
            MainActivity.USER_TOTAL_SAVING + " TEXT NOT NULL," + MainActivity.USER_INCOME_TYPE + " TEXT NOT NULL," +
            MainActivity.USER_DAILY_LIMIT +  " TEXT NOT NULL," + MainActivity.USER_SAVING_GOAL + " TEXT NOT NULL)";


    final static String NAME_SAVING = "todo_saving";
    final static String EVENT_NAME = "event_name";
    final static String SAVING_AMOUNT = "saving_amount";
    final static String SAVING_SO_FAR = "saving_so_far";

    final static String SAVING_DATE = "saving_date";

    final private static String CREATE_SAVING = "CREATE TABLE "+NAME_SAVING+" (" + MainActivity._ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + MainActivity.SAVING_NAME + " TEXT NOT NULL," +
            MainActivity.SAVING_AMOUNT + " TEXT NOT NULL," + MainActivity.SAVING_SO_FAR + " TEXT NOT NULL,"+ MainActivity.SAVING_PER_DATE + " TEXT NOT NULL," + MainActivity.SAVING_DATE + " TEXT NOT NULL)";



    public dataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_CMD);
        db.execSQL(CREATE_SAVING);

        ContentValues contentValues = new ContentValues();
        contentValues.put(MainActivity.USER_NAME, "a");
        contentValues.put(MainActivity.DATE, "1");
        contentValues.put(MainActivity.USER_DAILY_LIMIT, "1");
        contentValues.put(MainActivity.USER_SAVING_GOAL, "1");
        contentValues.put(MainActivity.USER_INCOME_TYPE, "1");
        contentValues.put(MainActivity.USER_TOTAL_INCOME, "1");
        contentValues.put(MainActivity.USER_TOTAL_SAVING, "1");
        db.insert(NAME, null, contentValues);


        ContentValues content = new ContentValues();
        content.put(MainActivity.SAVING_NAME, "Hello");
        content.put(MainActivity.SAVING_AMOUNT, "500");
        content.put(MainActivity.SAVING_SO_FAR,"100");
        content.put(MainActivity.SAVING_PER_DATE,"100");
        content.put(MainActivity.SAVING_DATE,"123123");
        db.insert(NAME_SAVING,null, content);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    void deleteDatabase ( ) {
        context.deleteDatabase(NAME);
        context.deleteDatabase(NAME_SAVING);
    }

}
