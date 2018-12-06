package com.example.tringuyen.financialappfinalproject;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class profile_user_summary extends AppCompatActivity {

    private SQLiteDatabase db = null;
    private dataBaseHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user_summary);
    }
}
