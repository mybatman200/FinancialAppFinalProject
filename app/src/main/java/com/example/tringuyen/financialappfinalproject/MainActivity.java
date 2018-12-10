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
import java.util.*;


public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db = null;
    private dataBaseHelper dbHelper = null;
    SimpleCursorAdapter myAdapter;
    boolean deductBoolean = false;
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

    final static String SAVING_NAME = "event_name";
    final static String SAVING_AMOUNT = "saving_amount";
    final static String SAVING_SO_FAR = "saving_so_far";
    final static String SAVING_DATE = "saving_date";
    final static String SAVING_PER_DATE= "saving_per_date";

    final static String RECURRING_NAME = "recurring_name";
    final static String RECURRING_AMOUNT = "recurring_amount";
    final static String RECURRING_PER_DATE = "recurring_per_date";

    final static String[] all_columns = { _ID, USER_NAME, DATE, USER_TOTAL_INCOME, USER_TOTAL_SAVING, USER_INCOME_TYPE,USER_DAILY_LIMIT, USER_SAVING_GOAL};
    final static String[] all_columns_saving = {_ID, SAVING_NAME, SAVING_AMOUNT,SAVING_SO_FAR,SAVING_PER_DATE, SAVING_DATE};
    final static String[] all_columns_recurring = {_ID, RECURRING_NAME, RECURRING_AMOUNT, RECURRING_PER_DATE};

    TextView dailyLimit;
    int daysOfMonth;
    Boolean isFirstRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new dataBaseHelper(this);

        isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
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
        Calendar calendar = Calendar.getInstance();
        daysOfMonth= calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    //String totalIncome, String savingPercentage, String totalPlannedSavingPerDay,  String totalRecurring, String frequency, String daysOfMonth
    public void onResume(){
        super.onResume();

        db = dbHelper.getWritableDatabase();
        mCursor =  db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
        mCursor.moveToLast();

        double dailyLimitString = Math.round(Double.parseDouble(mCursor.getString(mCursor.getColumnIndex(USER_DAILY_LIMIT)))*100.00)/100.00;
        dailyLimit.setText(String.valueOf(dailyLimitString));

    }

    public void onStart(){
        super.onStart();

        db = dbHelper.getWritableDatabase();
        mCursor =  db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
        mCursor.moveToLast();

        double dailyLimitString = Math.round(Double.parseDouble(mCursor.getString(mCursor.getColumnIndex(USER_DAILY_LIMIT)))*100.00)/100.00;
        dailyLimit.setText(String.valueOf(dailyLimitString));


    }

    public void onPause() {
        super.onPause();
        if (db != null) db.close();
        mCursor.close();
    }



    public void addButtonActivityMain(View v){
        Intent intent = new Intent(this, activity_add_daily.class);
        startActivity(intent);

    }

    public void minusButtonActivityMain(View v){
        deductBoolean = true;
        Intent intent = new Intent(this, activity_deduct_daily.class);
        startActivity(intent);
    }

    public void SavingPlanBtn(View v){
        Intent intent = new Intent(this,saving_plan.class);
        startActivity(intent);
    }

    public void userrBtn(View v){
        Intent intent = new Intent(this, activity_user_profile.class);
        startActivity(intent);
    }

    public void RecurringBtn(View v){
        Intent intent = new Intent(this, Recuring.class);
        startActivity(intent);
    }

}
