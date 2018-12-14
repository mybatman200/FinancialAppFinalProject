package com.example.tringuyen.financialappfinalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class saving_plan_3 extends AppCompatActivity {
    private dataBaseHelper dbHelper = null;
    private SQLiteDatabase db = null;
    TextView currentGoal, currentDate, savingNameUpdate,savingPerDateText, savingGoalText;

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_plan_3);

        Intent intent = getIntent();
        id = intent.getStringExtra(saving_plan.SAVING_ID_INTENT_1);
        final String name = intent.getStringExtra(saving_plan.SAVING_NAME_INTENT_1);
        final String amount = intent.getStringExtra(saving_plan.SAVING_AMOUNT_INTENT_1);
        final String date = intent.getStringExtra(saving_plan.SAVING_DATE_INTENT_1);
        final String savingPerDayString = intent.getStringExtra(saving_plan.SAVING_PER_DATE_INTENT_1);
        final String savingDayAdded = intent.getStringExtra(saving_plan.SAVING_DATE_ADDED_INTENT_1);

        savingNameUpdate = findViewById(R.id.savingNameUpdate);
        currentDate = findViewById(R.id.CurrentSavingDate);
        currentGoal = findViewById(R.id.CurrentSavingRate);
        savingPerDateText = findViewById(R.id.SavingPerDateText);
        savingGoalText = findViewById(R.id.SavingGoalText);

        dbHelper = new dataBaseHelper(this);

        savingNameUpdate.setText(name);
        currentGoal.setText("Current Saving Goal: "+ amount);
        currentDate.setText("Current Saving Date: "+ date);
        savingPerDateText.setText("Saving per day: "+savingPerDayString);
        savingGoalText.setText("Time added: "+ savingDayAdded);


    }

    public void updateValues(View v){

        Intent intent = getIntent();


        Intent intent1 = new Intent(this, saving_plan.class);
        startActivity(intent1);

    }
}

