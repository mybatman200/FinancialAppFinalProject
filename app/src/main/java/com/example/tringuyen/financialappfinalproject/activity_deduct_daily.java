package com.example.tringuyen.financialappfinalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class activity_deduct_daily extends AppCompatActivity {
    private SQLiteDatabase db = null;
    private dataBaseHelper dbHelper = null;
    EditText deductIncomeDaily;
    Button deductBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deduct_daily);
        dbHelper = new dataBaseHelper(this);
        deductIncomeDaily = findViewById(R.id.deductIncomeDaily);
        deductBtn = findViewById(R.id.submitDeductBtn);
        deductBtn.setVisibility(View.GONE);

        deductIncomeDaily.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!deductIncomeDaily.getText().toString().equals("")){
                    deductBtn.setVisibility(View.VISIBLE);
                }else {
                    deductBtn.setVisibility(View.GONE);
                }
            }
        });
    }

    public void submitDeductActivityDeductDaily(View view){

        Date today = new Date();
        long time =today.getTime();

        String deductAmount = deductIncomeDaily.getText().toString();
        db = dbHelper.getWritableDatabase();

        Cursor mCursor = db.query(dataBaseHelper.NAME, MainActivity.all_columns,null, null, null, null, null);
        mCursor.moveToLast();
        String dailyExpenseBefore = mCursor.getString(mCursor.getColumnIndex(MainActivity.USER_DAILY_LIMIT));
        double dailyExpenseBeforeDouble = Double.parseDouble(dailyExpenseBefore);
        double dailyExpenseAfterDouble = dailyExpenseBeforeDouble - Double.parseDouble(deductAmount);
        String dailyExpenseAfter = String.valueOf(dailyExpenseAfterDouble);


       //{USER_NAME, DATE, USER_TOTAL_INCOME, USER_TOTAL_SAVING, USER_INCOME_TYPE,USER_DAILY_LIMIT, USER_SAVING_GOAL};
        String userName = mCursor.getString(mCursor.getColumnIndex(MainActivity.USER_NAME));
        String date = String.valueOf(time);
        String userTotalSaving = mCursor.getString(mCursor.getColumnIndex(MainActivity.USER_TOTAL_SAVING));
        String userTotalIncome = mCursor.getString(mCursor.getColumnIndex(MainActivity.USER_TOTAL_INCOME));
        String userIncomeType = mCursor.getString(mCursor.getColumnIndex(MainActivity.USER_INCOME_TYPE));
        String userSavingGoal = mCursor.getString(mCursor.getColumnIndex(MainActivity.USER_SAVING_GOAL));

        ContentValues values = new ContentValues();
        values.put(MainActivity.USER_NAME, userName);
        values.put(MainActivity.DATE, date);
        values.put(MainActivity.USER_INCOME_TYPE, userIncomeType);
        values.put(MainActivity.USER_TOTAL_INCOME, userTotalIncome);
        values.put(MainActivity.USER_TOTAL_SAVING, userTotalSaving);
        values.put(MainActivity.USER_DAILY_LIMIT, dailyExpenseAfter);
        values.put(MainActivity.USER_SAVING_GOAL, userSavingGoal);

        db.insert(dbHelper.NAME, null, values);

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(dataBaseHelper.NAME, MainActivity.all_columns,null, null, null, null, null);
        cursor.moveToLast();
        //Toast.makeText(this, ""+ cursor.getString(cursor.getColumnIndex(MainActivity.USER_DAILY_LIMIT)), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
