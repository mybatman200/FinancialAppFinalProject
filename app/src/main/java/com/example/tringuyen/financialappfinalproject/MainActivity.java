package com.example.tringuyen.financialappfinalproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.content.*;
import android.database.*;
import android.os.AsyncTask;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db = null;
    private dataBaseHelper dbHelper = null;
    SimpleCursorAdapter myAdapter;

    Cursor mCursor;
    android.app.AlertDialog actions;
    final static String _ID = "_id";

    final static String USER_NAME = "user_name";
    final static String DATE = "date";
    final static String USER_TOTAL_INCOME = "user_total_income";
    final static String USER_TOTAL_SAVING = "user_total_saving";
    final static String USER_INCOME_TYPE = "user_income_type";
    final static String USER_DAILY_LIMIT = "user_daily_limit";
    final static String USER_SAVING_GOAL = "user_saving_goal";
    final static String[] all_columns = { _ID, USER_NAME, DATE, USER_TOTAL_INCOME, USER_TOTAL_SAVING, USER_INCOME_TYPE,USER_DAILY_LIMIT, USER_SAVING_GOAL};

    TextView dailyLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new dataBaseHelper(this);

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity

            startActivity(new Intent(MainActivity.this, Name_Initial_Screen.class));
            Toast.makeText(
                    MainActivity.this, "First Run", Toast.LENGTH_LONG)
                    .show();
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

        dailyLimit = findViewById(R.id.income_activity_main);

    }

    public void onResume(){
        super.onResume();
        db = dbHelper.getWritableDatabase();
        mCursor =  db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
        mCursor.moveToLast();
        String dailyLimitString = mCursor.getString(mCursor.getColumnIndex(USER_DAILY_LIMIT));
        Toast.makeText(this, dailyLimitString,Toast.LENGTH_LONG).show();
        dailyLimit.setText(dailyLimitString);

    }

    public void onPause() {
        super.onPause();
        if (db != null) db.close();
        mCursor.close();
    }



    public void addButtonActivityMain(View v){

    }

    public void minusButtonActivityMain(View v){

    }

    public void SavingPlanBtn(View v){
        Intent intent = new Intent(this,activity_income_question.class);

    }

    public void userrBtn(View v){
        Intent intent = new Intent(this, activity_user_profile.class);
        startActivity(intent);
    }

}
