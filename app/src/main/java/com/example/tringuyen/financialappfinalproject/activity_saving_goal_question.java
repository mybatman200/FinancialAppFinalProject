package com.example.tringuyen.financialappfinalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.*;

public class activity_saving_goal_question extends AppCompatActivity {
    EditText savingQuestion;
    Button nextBtnActivitySavingQuestion;

    private dataBaseHelper dbHelper = null;
    private SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_goal_question);

        dbHelper = new dataBaseHelper(this);

        savingQuestion = findViewById(R.id.savingQuestion);


    }

    public void nextBtnActivitySavingQuestionOnClick(View v){
        String savingGoal = savingQuestion.getText().toString();

        db = dbHelper.getWritableDatabase();
        Cursor cursor =  db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
        cursor =  db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
        cursor.moveToFirst();



        String nameTest = cursor.getString(cursor.getColumnIndex(MainActivity.USER_NAME));
        String frequency = cursor.getString(cursor.getColumnIndex(MainActivity.USER_INCOME_TYPE));
        String totalIncome = cursor.getString(cursor.getColumnIndex(MainActivity.USER_TOTAL_INCOME));



        Calendar calendar = Calendar.getInstance();
        int daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        DailyIncomeCalculator dailyIncomeCalculator = new DailyIncomeCalculator(totalIncome, Double.parseDouble(savingGoal), 0, "0", frequency, daysOfMonth);


        ContentValues contentValues = new ContentValues();

        contentValues.put(MainActivity.USER_SAVING_GOAL, savingGoal);
        contentValues.put(MainActivity.USER_DAILY_LIMIT, dailyIncomeCalculator.dailyReturnString());
        //contentValues.put(MainActivity.USER_DAILY_LIMIT, "");

        db.update(dbHelper.NAME, contentValues, "_id="+1,null);

        ////////////////////////////////////////////////////////////////////////////////////////////////Test
        //db = dbHelper.getWritableDatabase();

        cursor =  db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
        cursor.moveToFirst();
        String savingGoalTest = cursor.getString(cursor.getColumnIndex(MainActivity.USER_SAVING_GOAL));
        String dailyLimitTest = cursor.getString(cursor.getColumnIndex(MainActivity.USER_DAILY_LIMIT));

        //Toast.makeText(this, " " + dailyLimitTest, Toast.LENGTH_LONG).show();



        ////////////////////////////////////////////////////////////////////////////////////////////////Test

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

}
