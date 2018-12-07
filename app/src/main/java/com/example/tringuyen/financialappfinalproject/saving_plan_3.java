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
    EditText savingGoal, untilDate;
    TextView currentGoal, currentDate, savingNameUpdate;

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

        savingNameUpdate = findViewById(R.id.savingNameUpdate);
        currentDate = findViewById(R.id.CurrentSavingDate);
        currentGoal = findViewById(R.id.CurrentSavingRate);

        dbHelper = new dataBaseHelper(this);

        savingNameUpdate.setText(name);
        currentGoal.setText("Current Saving Goal: "+ amount);
        currentDate.setText("Current Saving Date: "+ date);


    }

    public void updateValues(View v){

        Intent intent = getIntent();
        //int id = intent.getIntExtra("idNumber", 0);

        savingGoal = findViewById(R.id.weightUpdate);
        untilDate = findViewById(R.id.repsUpdate);

        String savingGoalString = savingGoal.getText().toString();
        String untilDateString = untilDate.getText().toString();

        db = dbHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        if(!savingGoalString.equals("")){
            contentValues.put(MainActivity.SAVING_AMOUNT,savingGoalString);
            db.update(dbHelper.NAME_SAVING,contentValues,"_id="+id,null);

        }
        if(!untilDateString.equals("")){
            contentValues.put(MainActivity.SAVING_DATE,untilDateString);
            db.update(dbHelper.NAME_SAVING,contentValues,"_id="+id,null);

        }


        Cursor cursor =  db.query(dbHelper.NAME_SAVING, MainActivity.all_columns_saving, null, null, null, null, null);
        cursor.moveToPosition(Integer.parseInt(id));

        Intent intent1 = new Intent(this, saving_plan.class);
        startActivity(intent1);

    }
}

