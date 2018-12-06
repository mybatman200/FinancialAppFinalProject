package com.example.tringuyen.financialappfinalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.*;

public class Name_Initial_Screen extends AppCompatActivity {
    EditText user_name;
    private dataBaseHelper dbHelper = null;
    private SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name__initial__screen);

        dbHelper = new dataBaseHelper(this);
        user_name = (EditText)findViewById(R.id.userNameText);

    }

    public void saveOnClickUser(View v){
        db = dbHelper.getWritableDatabase();
        String name = user_name.getText().toString();

        Date currentTime = Calendar.getInstance().getTime();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MainActivity.USER_NAME, name);
        contentValues.put(MainActivity.DATE, currentTime.toString());
        db.update(dbHelper.NAME, contentValues, "_id="+1,null);

        ////////////////////////////////////////////////////////////////////////////////////////////////Test
        db = dbHelper.getWritableDatabase();
        Cursor cursor =  db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
        cursor.moveToFirst();

        String testing = cursor.getString( cursor.getColumnIndex(MainActivity.USER_NAME) );
        Toast.makeText(this,   name+ currentTime.toString(), Toast.LENGTH_LONG).show();
        ////////////////////////////////////////////////////////////////////////////////////////////////Test

        Intent intent = new Intent(this,activity_income_question.class);
        startActivity(intent);
    }

}
