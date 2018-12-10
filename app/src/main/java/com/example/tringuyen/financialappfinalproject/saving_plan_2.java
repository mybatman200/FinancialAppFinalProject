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
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;

public class saving_plan_2 extends AppCompatActivity {

    EditText nameEditText, savingEditText, untilEditText;
    TextView savingGoal, amountOfDays;
    Button savingPLanBtn;
    private SQLiteDatabase db = null;
    private dataBaseHelper dbHelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_plan_2);

        nameEditText = findViewById(R.id.nameEditTextPlan);
        savingEditText = findViewById(R.id.savingEditTextPlan);
        savingEditText.setVisibility(View.GONE);
        savingGoal = findViewById(R.id.savingGoalIDText);
        savingGoal.setVisibility(View.GONE);

        untilEditText = findViewById(R.id.untilEditTextPlan);
        amountOfDays = findViewById(R.id.amountOfDays);
        untilEditText.setVisibility(View.GONE);
        amountOfDays.setVisibility(View.GONE);

        dbHelper = new dataBaseHelper(this);
        savingPLanBtn = findViewById(R.id.savingPlanBtn);
        savingPLanBtn.setVisibility(View.GONE);

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                savingGoal.setVisibility(View.VISIBLE);
                savingEditText.setVisibility(View.VISIBLE);
            }
        });

        savingEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                untilEditText.setVisibility(View.VISIBLE);
                amountOfDays.setVisibility(View.VISIBLE);
            }
        });

        untilEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                savingPLanBtn.setVisibility(View.VISIBLE);
            }
        });

    }

    public void addMoreSaving(View v){

        Date today = new Date();
        long time =today.getTime();

        String name = nameEditText.getText().toString();
        String saving = savingEditText.getText().toString();
        String until = untilEditText.getText().toString();
        double savingPerDateDouble = Double.parseDouble(saving)/Double.parseDouble(until);
        String savingPerDateString = String.valueOf(savingPerDateDouble);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MainActivity.SAVING_NAME, name);
        values.put(MainActivity.SAVING_AMOUNT, saving);
        values.put(MainActivity.SAVING_DATE, until);
        values.put(MainActivity.SAVING_PER_DATE,savingPerDateString);
        values.put(MainActivity.SAVING_SO_FAR, String.valueOf(time));

        db.insert(dbHelper.NAME_SAVING,null, values);

        Intent intent = new Intent(this, saving_plan.class);
        intent.putExtra("BOOLEAN_RETURN", true);
        startActivity(intent);
    }
}
