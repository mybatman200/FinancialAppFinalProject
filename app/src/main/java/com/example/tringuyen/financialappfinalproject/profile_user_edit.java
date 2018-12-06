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
        Toast.makeText(this,id,Toast.LENGTH_LONG).show();
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

        Intent intent = new Intent(this, activity_user_profile.class);
        startActivity(intent);




    }
}
