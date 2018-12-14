package com.example.tringuyen.financialappfinalproject;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

public class SpendingSummary extends AppCompatActivity {



    private SQLiteDatabase db = null;
    private dataBaseHelper dbHelper = null;
    SimpleCursorAdapter myAdapter;
    ListView mlist;
    EditText elem;
    Cursor mCursor;
    String longClickString;
    android.app.AlertDialog actions;
    long idListener = 0;
    boolean bool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_summary);

        mlist = findViewById(R.id.listViewSummary);
        dbHelper = new dataBaseHelper(this);

        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        mlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String tempString = ((TextView) view).getText().toString();
                longClickString = Long.toString(id);
                setPosition(position);
                setID(id);
                actions.show();

                return true;
            }}
        );

        DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Cursor cursor = db.query(dbHelper.NAME, MainActivity.all_columns, null,null, null, null,null);
                        cursor.moveToLast();
                        Cursor cursorSaving = db.query(dbHelper.NAME_RECURRING, MainActivity.all_columns_recurring, null,null, null, null,null);
                        cursorSaving.moveToPosition(positionListener);

                        double recurringPerDay = Double.parseDouble(cursorSaving.getString(cursorSaving.getColumnIndex(MainActivity.RECURRING_PER_DATE)));
                        double dailyLimit = Double.parseDouble(cursor.getString(cursor.getColumnIndex(MainActivity.USER_DAILY_LIMIT)));
                        dailyLimit = dailyLimit+recurringPerDay;

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MainActivity.USER_DAILY_LIMIT, dailyLimit);
                        db.update(dbHelper.NAME, contentValues, "_id="+MainActivity._ID, null);

                        db.delete(dbHelper.NAME_RECURRING, "_id=?", new String[]{longClickString});
                        //Toast.makeText(getApplicationContext(), "Delete" + longClickString, Toast.LENGTH_LONG).show();

                        db = dbHelper.getWritableDatabase();
                        mCursor = db.query(dbHelper.NAME_RECURRING, MainActivity.all_columns_recurring, null, null, null, null, null);
                        myAdapter.swapCursor(mCursor);
                        mlist.setAdapter(myAdapter);


                        break;

                    default:
                        break;
                }
            }
        };
        android.app.AlertDialog.Builder builder = new
                android.app.AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete this item?");
        String[] options = {"Delete"};
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();

        Intent intent = getIntent();
        bool = intent.getBooleanExtra("BOOLEAN_RETURN", false);
    }

    int positionListener;
    public void setPosition(int position){
        positionListener = position;
    }
    public int getPosition(){
        return positionListener;
    }
    public void setID(long id){
        idListener = id;
    }
    public long getID(){
        return idListener;
    }


    private class LoadNewLists extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... params){
            db = dbHelper.getWritableDatabase();
            mCursor = db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null,
                    null);
            return mCursor;
        }

        @Override
        protected void onPostExecute(Cursor result){
            super.onPostExecute(result);
            myAdapter = new SimpleCursorAdapter(SpendingSummary.this,
                    android.R.layout.simple_list_item_1,
                    mCursor,
                    new String[] {MainActivity.USER_DAILY_LIMIT},
                    new int[] { android.R.id.text1});

            mlist.setAdapter(myAdapter);
        }

    }

    public void onResume(){
        super.onResume();
        new LoadNewLists().execute();
    }

    public void onBackPressed(){
        if(bool == true) {
            int daysOfMonth;
            Calendar calendar = Calendar.getInstance();
            daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            db = dbHelper.getWritableDatabase();

            Cursor cursorName = db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
            cursorName.moveToLast();
            // Toast.makeText(this, cursorName.getString(cursorName.getColumnIndex(MainActivity.USER_DAILY_LIMIT)), Toast.LENGTH_LONG).show();
            double totalRecurringSavingPerDay = 0;
            Cursor cursor = db.query(dbHelper.NAME_SAVING, MainActivity.all_columns_recurring, null, null, null, null, null);
            while (cursor.moveToNext() == true) {
                totalRecurringSavingPerDay += Double.parseDouble(cursor.getString(cursor.getColumnIndex(MainActivity.RECURRING_PER_DATE)));
            }

            double totalPlannedSavingPerDay = 0;
            Cursor cursor12 = db.query(dbHelper.NAME_SAVING, MainActivity.all_columns_saving, null, null, null, null, null);
            while (cursor.moveToNext() == true) {
                totalPlannedSavingPerDay += Double.parseDouble(cursor12.getString(cursor12.getColumnIndex(MainActivity.SAVING_PER_DATE)));
            }
//
            String totalIncome = cursorName.getString(cursorName.getColumnIndex(MainActivity.USER_TOTAL_INCOME));
            String savingPercentage = cursorName.getString(cursorName.getColumnIndex(MainActivity.USER_SAVING_GOAL));
            String frequency = cursorName.getString(cursorName.getColumnIndex(MainActivity.USER_INCOME_TYPE));
            double userDailyUpdated = Double.parseDouble(cursorName.getString(cursorName.getColumnIndex(MainActivity.USER_DAILY_LIMIT))) - totalRecurringSavingPerDay - totalPlannedSavingPerDay ;

            db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MainActivity.USER_DAILY_LIMIT, String.valueOf(userDailyUpdated));
            db.update(dbHelper.NAME, contentValues, "_id=" + MainActivity._ID, null);
        }
        Intent intent = new Intent(this, activity_user_profile.class);
        startActivity(intent);
    }



    public void onPause() {
        super.onPause();
        if (db != null) db.close();
        mCursor.close();
    }

}
