package com.example.tringuyen.financialappfinalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;

import java.util.*;

public class Recurring_2 extends AppCompatActivity {

    EditText nameEditText, savingEditText;
    private SQLiteDatabase db = null;
    private dataBaseHelper dbHelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurring_2);
        nameEditText = findViewById(R.id.nameEditTextRecurring);
        savingEditText = findViewById(R.id.savingEditTextRecurring);
        dbHelper = new dataBaseHelper(this);
    }

    public void addMoreRecurring(View view){
        Date today = new Date();
        long time =today.getTime();
        Calendar calendar = Calendar.getInstance();
        int daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Toast.makeText(this, ""+ daysOfMonth, Toast.LENGTH_LONG).show();
        String name = nameEditText.getText().toString();
        String saving = savingEditText.getText().toString();

        double savingPerDateDouble = Math.round(Double.parseDouble(saving)/daysOfMonth);
        String savingPerDateString = String.valueOf(savingPerDateDouble);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MainActivity.RECURRING_NAME, name);
        values.put(MainActivity.RECURRING_AMOUNT, saving);
        values.put(MainActivity.RECURRING_PER_DATE, savingPerDateString);

        db.insert(dbHelper.NAME_RECURRING,null, values);

        Intent intent = new Intent(this, Recuring.class);
        intent.putExtra("BOOLEAN_RETURN_RECURRING", true);
        startActivity(intent);
    }
}
