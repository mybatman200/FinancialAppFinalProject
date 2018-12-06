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

        ContentValues contentValues = new ContentValues();

        contentValues.put(MainActivity.USER_SAVING_GOAL, savingGoal);
        contentValues.put(MainActivity.USER_DAILY_LIMIT, "0");

        db.update(dbHelper.NAME, contentValues, "_id="+1,null);

        ////////////////////////////////////////////////////////////////////////////////////////////////Test
        db = dbHelper.getWritableDatabase();
        Cursor cursor =  db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);

        cursor =  db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
        cursor.moveToFirst();
        String nameTest = cursor.getString(cursor.getColumnIndex(MainActivity.USER_NAME));
        String freqTest = cursor.getString(cursor.getColumnIndex(MainActivity.USER_INCOME_TYPE));
        String incomeTest = cursor.getString(cursor.getColumnIndex(MainActivity.USER_TOTAL_INCOME));
        String savingGoalTest = cursor.getString(cursor.getColumnIndex(MainActivity.USER_SAVING_GOAL));
        Toast.makeText(this, nameTest+ " " + freqTest+ " " + incomeTest+ " " + savingGoalTest, Toast.LENGTH_LONG).show();
        ////////////////////////////////////////////////////////////////////////////////////////////////Test

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

}
