package com.example.tringuyen.financialappfinalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;

public class activity_user_profile extends AppCompatActivity {
    TextView savingText, incomeByFreq, userName;
    private SQLiteDatabase db = null;
    private dataBaseHelper dbHelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        dbHelper = new dataBaseHelper(this);

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
        cursor.moveToLast();
        String income = cursor.getString(cursor.getColumnIndex(MainActivity.USER_TOTAL_INCOME));
        String saving  = cursor.getString(cursor.getColumnIndex(MainActivity.USER_SAVING_GOAL));
        String name = cursor.getString(cursor.getColumnIndex(MainActivity.USER_NAME));

        userName = findViewById(R.id.userName);
        savingText = findViewById(R.id.savingText);
        incomeByFreq = findViewById(R.id.incomeByFreq);
        savingText.setText(saving+ "%");
        incomeByFreq.setText(income);
        userName.setText("Hi "+ name);
    }

    public void userOnClick(View v){
        Intent intent = new Intent(this, profile_user_edit.class);
        startActivity(intent);
    }

    public void backBtnPersonIncome(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void sumaryBtn(View v){
        Intent intent = new Intent(this, profile_user_summary.class);
        startActivity(intent);
    }
}
