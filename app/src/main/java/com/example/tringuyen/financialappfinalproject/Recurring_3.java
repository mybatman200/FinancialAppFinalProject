package com.example.tringuyen.financialappfinalproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;

import java.util.*;

public class Recurring_3 extends AppCompatActivity {
    private dataBaseHelper dbHelper = null;
    private SQLiteDatabase db = null;
    TextView currentGoal, savingNameUpdate,savingPerDateText;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring_3);

        Intent intent = getIntent();
        id = intent.getStringExtra(Recuring.RECURRING_ID_INTENT_1);
        final String name = intent.getStringExtra(Recuring.RECURRING_NAME_INTENT_1);
        final String amount = intent.getStringExtra(Recuring.RECURRING_AMOUNT_INTENT_1);
        final String recurringPerDayString = intent.getStringExtra(Recuring.RECURRING_PER_DATE_INTENT_1);

        savingNameUpdate = findViewById(R.id.savingNameUpdateRecurring);
        currentGoal = findViewById(R.id.CurrentSavingRateRecurring);
        savingPerDateText = findViewById(R.id.SavingGoalTextRecurring);

        dbHelper = new dataBaseHelper(this);

        savingNameUpdate.setText(name);
        currentGoal.setText("Current Saving Goal: "+ amount);
        savingPerDateText.setText("Saving per day: "+Math.round(Double.parseDouble(recurringPerDayString)*100.00)/100.00);
    }


    public void updateValues(View view){
        Intent intent1 = new Intent(this, Recuring.class);
        startActivity(intent1);
    }
}
