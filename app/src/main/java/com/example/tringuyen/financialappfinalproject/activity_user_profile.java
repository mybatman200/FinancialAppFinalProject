package com.example.tringuyen.financialappfinalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class activity_user_profile extends AppCompatActivity {
    TextView savingText, incomeByFreq, userName;
    private SQLiteDatabase db = null;
    private dataBaseHelper dbHelper = null;
    boolean userEdit =false;
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

        Intent intent = getIntent();
        userEdit = intent.getBooleanExtra("RETURN_BOOLEAN",false);
    }
    public void onResume(){
        super.onResume();
        if(userEdit == true) {
            int daysOfMonth;
            Calendar calendar = Calendar.getInstance();
            daysOfMonth= calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            Cursor mCursor =  db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
            mCursor.moveToLast();
            //Toast.makeText(this, mCursor.getString(mCursor.getColumnIndex(MainActivity.USER_DAILY_LIMIT)),Toast.LENGTH_LONG).show();
                double totalPlannedSavingPerDay=0;
                Cursor cursor = db.query(dbHelper.NAME_SAVING, MainActivity.all_columns_saving, null, null, null, null, null);
                while(cursor.moveToNext()==true){
                    totalPlannedSavingPerDay += Double.parseDouble(cursor.getString(cursor.getColumnIndex(MainActivity.SAVING_PER_DATE)));
                }

                String totalIncome = mCursor.getString(mCursor.getColumnIndex(MainActivity.USER_TOTAL_INCOME));
                String savingPercentage = mCursor.getString(mCursor.getColumnIndex(MainActivity.USER_SAVING_GOAL));
                String totalRecurring = "0";
                String frequency = mCursor.getString(mCursor.getColumnIndex(MainActivity.USER_INCOME_TYPE));
                DailyIncomeCalculator dailyIncomeCalculator = new DailyIncomeCalculator(totalIncome, savingPercentage, totalPlannedSavingPerDay, totalRecurring, frequency, daysOfMonth);

                db = dbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MainActivity.USER_DAILY_LIMIT, dailyIncomeCalculator.dailyReturn());
            Cursor cursor1 = db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
            int id =0;

            db.update(dbHelper.NAME, contentValues, "_id="+MainActivity._ID, null);
                Toast.makeText(this, MainActivity._ID, Toast.LENGTH_LONG).show();
                userEdit=false;
        }
    }

    public void userOnClick(View v){
        Intent intent = new Intent(this, profile_user_edit.class);
        intent.putExtra("EXTRA", true);
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
