package com.example.tringuyen.financialappfinalproject;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.*;
import android.view.*;

public class activity_add_daily extends AppCompatActivity {
    private SQLiteDatabase db = null;
    private dataBaseHelper dbHelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily);
    }

    public void addButtonActivityAddDaily(View view){

    }
}
