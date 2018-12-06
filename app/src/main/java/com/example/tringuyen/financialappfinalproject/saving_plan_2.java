package com.example.tringuyen.financialappfinalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;

public class saving_plan_2 extends AppCompatActivity {

    EditText nameEditText, savingEditText, untilEditText;
    private SQLiteDatabase db = null;
    private dataBaseHelper dbHelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_plan_2);

        nameEditText = findViewById(R.id.nameEditTextPlan);
        savingEditText = findViewById(R.id.savingEditTextPlan);
        untilEditText = findViewById(R.id.untilEditTextPlan);
        dbHelper = new dataBaseHelper(this);
    }

    public void addMoreSaving(View v){
        String name = nameEditText.getText().toString();
        String saving = savingEditText.getText().toString();
        String until = untilEditText.getText().toString();

        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MainActivity.SAVING_NAME, name);
        values.put(MainActivity.SAVING_AMOUNT, saving);
        values.put(MainActivity.SAVING_DATE, until);
        values.put(MainActivity.SAVING_SO_FAR,"");

        db.insert(dbHelper.NAME_SAVING,null, values);

        db = dbHelper.getWritableDatabase();
        Cursor mCursor =  db.query(dbHelper.NAME_SAVING, MainActivity.all_columns_saving, null, null, null, null, null);
        mCursor.moveToFirst();

        String s = mCursor.getString(mCursor.getColumnIndex(MainActivity.SAVING_NAME));
        Toast.makeText(this, s,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, saving_plan.class);
        startActivity(intent);
    }
}
