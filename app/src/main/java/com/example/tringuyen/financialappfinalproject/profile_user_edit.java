package com.example.tringuyen.financialappfinalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.database.*;
import android.widget.EditText;
import android.widget.Toast;
import java.util.*;


public class profile_user_edit extends AppCompatActivity {

    private SQLiteDatabase db = null;
    private dataBaseHelper dbHelper = null;
    EditText newIncome, newSavingQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user_edit);
        dbHelper = new dataBaseHelper(this);
        newIncome = findViewById(R.id.newIncomeQuestion);
        newSavingQuestion = findViewById(R.id.newSavingQuestion);
    }

    public void backBtn(View v){
        Intent intent = new Intent(this, activity_user_profile.class);
        startActivity(intent);
    }

    public void saveNewValue(View v){
        String newIncomeString = newIncome.getText().toString();
        String newSavingString = newSavingQuestion.getText().toString();

        db = dbHelper.getWritableDatabase();
        Cursor cursor =  db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
        cursor.moveToLast();
        String id = cursor.getString(cursor.getColumnIndex(MainActivity._ID));

        ////////////////////////////////////////////////////////////////////////////////////////////////Test
        //Toast.makeText(this,id,Toast.LENGTH_LONG).show();
        ////////////////////////////////////////////////////////////////////////////////////////////////Test

        db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        if(!newSavingString.equals("")) {
            contentValues.put(MainActivity.USER_SAVING_GOAL, newSavingString);
            db.update(dbHelper.NAME, contentValues, "_id="+id,null);
        }
        if(!newIncomeString.equals("")) {
            contentValues.put(MainActivity.USER_TOTAL_INCOME, newIncomeString);
            db.update(dbHelper.NAME, contentValues, "_id="+id,null);
        }

        db = dbHelper.getWritableDatabase();
        Cursor mCursor =  db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
        mCursor.moveToLast();
        Date today = new Date();
        long getTimeToday = today.getTime();

        Calendar calendar = Calendar.getInstance();
        int daysOfMonth= calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            double totalPlannedSavingPerDay=0;
            Cursor cursor1 = db.query(dbHelper.NAME_SAVING, MainActivity.all_columns_saving, null, null, null, null, null);
            while(cursor1.moveToNext()==true){
                totalPlannedSavingPerDay += Double.parseDouble(cursor1.getString(cursor1.getColumnIndex(MainActivity.SAVING_PER_DATE)));
            }

            String totalIncome = mCursor.getString(mCursor.getColumnIndex(MainActivity.USER_TOTAL_INCOME));
            String savingPercentage = mCursor.getString(mCursor.getColumnIndex(MainActivity.USER_SAVING_GOAL));
            String totalRecurring = "0";
            String frequency = mCursor.getString(mCursor.getColumnIndex(MainActivity.USER_INCOME_TYPE));
            DailyIncomeCalculator dailyIncomeCalculator = new DailyIncomeCalculator(totalIncome, savingPercentage, totalPlannedSavingPerDay, totalRecurring, frequency, daysOfMonth);

            db = dbHelper.getWritableDatabase();

            ContentValues contentValues1 = new ContentValues();
            contentValues1.put(MainActivity.USER_DAILY_LIMIT, dailyIncomeCalculator.dailyReturnString());


            db.update(dbHelper.NAME, contentValues, "_id="+MainActivity._ID, null);



        Intent intent = new Intent(this, activity_user_profile.class);
        intent.putExtra("RETURN_BOOLEAN", true);
        startActivity(intent);




    }
}
