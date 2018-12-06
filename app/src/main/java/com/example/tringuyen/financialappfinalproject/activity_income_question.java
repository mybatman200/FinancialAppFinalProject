package com.example.tringuyen.financialappfinalproject;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.content.*;
import android.widget.*;
import android.database.*;

public class activity_income_question extends AppCompatActivity {

    boolean monthly = false, yearly = false, daily = false;
    EditText incomeQuest;
    Button nextBtnIncomeQuestion;
    TextView enterIncomeString;
    String incomeInText;
    private dataBaseHelper dbHelper = null;
    private SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_question);

        dbHelper = new dataBaseHelper(this);

        incomeQuest = (EditText) findViewById(R.id.incomeQuestion);
        nextBtnIncomeQuestion = (Button) findViewById(R.id.nextBtnIncomeQuestion);
        enterIncomeString = (TextView) findViewById(R.id.enterIncomeString);
        enterIncomeString.setVisibility(View.GONE);
        nextBtnIncomeQuestion.setVisibility(View.GONE);
        incomeQuest.setVisibility(View.GONE);
        incomeQuest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                nextBtnIncomeQuestion.setVisibility(View.VISIBLE);
                incomeInText=incomeQuest.getText().toString();
            }
        });
    }

    public void yearlyOnClick(View v){
        yearly=true;
        monthly=false;
        daily=false;
        incomeQuest.setVisibility(View.VISIBLE);
        enterIncomeString.setVisibility(View.VISIBLE);
    }

    public void monthlyOnClick(View v){
        yearly=false;
        monthly=true;
        daily= false;
        incomeQuest.setVisibility(View.VISIBLE);
        enterIncomeString.setVisibility(View.VISIBLE);
    }

    public void biweeklyOnClick(View v){
        yearly=false;
        monthly=false;
        daily=true;
        incomeQuest.setVisibility(View.VISIBLE);
        enterIncomeString.setVisibility(View.VISIBLE);
    }

    public void nextBtnIncomeQuestionOnClick(View v){
        String frequency ="";
        if(monthly==true){
            frequency = "monthly";
        }
        if(yearly == true){
            frequency = "yearly";
        }
        if(daily==true){
            frequency = "bi-weekly";
        }

        String income = incomeQuest.getText().toString();

        db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(MainActivity.USER_INCOME_TYPE, frequency);
        contentValues.put(MainActivity.USER_TOTAL_INCOME, income);

        db.update(dbHelper.NAME, contentValues, "_id="+1,null);

        ////////////////////////////////////////////////////////////////////////////////////////////////Test
        db = dbHelper.getWritableDatabase();
        Cursor cursor =  db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
        cursor =  db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
        cursor.moveToFirst();
        String nameTest = cursor.getString(cursor.getColumnIndex(MainActivity.USER_NAME));
        String freqTest = cursor.getString(cursor.getColumnIndex(MainActivity.USER_INCOME_TYPE));
        String incomeTest = cursor.getString(cursor.getColumnIndex(MainActivity.USER_TOTAL_INCOME));

        Toast.makeText(this, nameTest+ " " + freqTest+ " " + incomeTest, Toast.LENGTH_LONG).show();
        ////////////////////////////////////////////////////////////////////////////////////////////////Test


        Intent intent = new Intent(this,activity_saving_goal_question.class);
        startActivity(intent);


    }

}
