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
import android.widget.Toast;

import java.util.Calendar;

public class saving_plan extends AppCompatActivity {


    public final static String SAVING_ID_INTENT_1 = "SAVING_ID_INTENT_1";
    public final static String SAVING_NAME_INTENT_1 = "SAVING_NAME_INTENT_1";

    public final static String SAVING_AMOUNT_INTENT_1 = "SAVING_AMOUNT_INTENT_1";
    public final static String SAVING_DATE_INTENT_1 = "SAVING_DATE_INTENT_1";
    public final static String SAVING_PER_DATE_INTENT_1 = "SAVING_PER_DATE_INTENT_1";
    public final static String SAVING_DATE_ADDED_INTENT_1 = "SAVING_DATE_ADDED_INTENT_1";

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
        setContentView(R.layout.activity_saving_plan);

        mlist = findViewById(R.id.listView);
        dbHelper = new dataBaseHelper(this);

        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "Touched " + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                Object temp = myAdapter.getItem(position);
                String tempView = ((TextView) view).getText().toString();

                mCursor.moveToPosition(position);

                String tempPos = Integer.toString(position);
                //get values from database
                String savingID = mCursor.getString(mCursor.getColumnIndex(MainActivity._ID));
                String savingName = mCursor.getString( mCursor.getColumnIndex(MainActivity.SAVING_NAME));
                String savingAmount =  mCursor.getString( mCursor.getColumnIndex(MainActivity.SAVING_AMOUNT));
                String savingDate =  mCursor.getString( mCursor.getColumnIndex(MainActivity.SAVING_DATE));
                String savingPerDate =  mCursor.getString( mCursor.getColumnIndex(MainActivity.SAVING_PER_DATE));
                String savingDateAdded = mCursor.getString(mCursor.getColumnIndex(MainActivity.SAVING_SO_FAR));

                Intent intent = new Intent(saving_plan.this, saving_plan_3.class);
                intent.putExtra(SAVING_ID_INTENT_1,savingID );
                intent.putExtra(SAVING_NAME_INTENT_1, savingName);
                intent.putExtra(SAVING_AMOUNT_INTENT_1, savingAmount);
                intent.putExtra(SAVING_DATE_INTENT_1, savingDate);
                intent.putExtra(SAVING_PER_DATE_INTENT_1,savingPerDate);
                intent.putExtra(SAVING_DATE_ADDED_INTENT_1, savingDateAdded);

                startActivity(intent);

            }
        });

        mlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "Long touched " + id, Toast.LENGTH_SHORT).show();

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
                        Cursor cursorSaving = db.query(dbHelper.NAME_SAVING, MainActivity.all_columns_saving, null,null, null, null,null);
                        cursorSaving.moveToPosition(positionListener);

                        double savingPerDay = Double.parseDouble(cursorSaving.getString(cursorSaving.getColumnIndex(MainActivity.SAVING_PER_DATE)));
                        double dailyLimit = Double.parseDouble(cursor.getString(cursor.getColumnIndex(MainActivity.USER_DAILY_LIMIT)));
                        dailyLimit = dailyLimit+savingPerDay;

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MainActivity.USER_DAILY_LIMIT, dailyLimit);
                        db.update(dbHelper.NAME, contentValues, "_id="+MainActivity._ID, null);

                        db.delete(dbHelper.NAME_SAVING, "_id=?", new String[]{longClickString});
                        Toast.makeText(getApplicationContext(), "Delete" + longClickString, Toast.LENGTH_LONG).show();

                        db = dbHelper.getWritableDatabase();
                        mCursor = db.query(dbHelper.NAME_SAVING, MainActivity.all_columns_saving, null, null, null, null, null);
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
            mCursor = db.query(dbHelper.NAME_SAVING, MainActivity.all_columns_saving, null, null, null, null,
                    null);
            return mCursor;
        }

        @Override
        protected void onPostExecute(Cursor result){
            super.onPostExecute(result);
            myAdapter = new SimpleCursorAdapter(saving_plan.this,
                    android.R.layout.simple_list_item_1,
                    mCursor,
                    new String[] {MainActivity.SAVING_NAME, MainActivity.SAVING_AMOUNT, MainActivity.SAVING_SO_FAR, MainActivity.SAVING_PER_DATE, MainActivity.SAVING_DATE },
                    new int[] { android.R.id.text1});

            mlist.setAdapter(myAdapter);
        }

    }

    public void onResume(){
        super.onResume();
        new LoadNewLists().execute();
    }

    @Override
    public void onBackPressed(){
        if(bool == true) {
            int daysOfMonth;
            Calendar calendar = Calendar.getInstance();
            daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            db = dbHelper.getWritableDatabase();

            Cursor cursorName = db.query(dbHelper.NAME, MainActivity.all_columns, null, null, null, null, null);
            cursorName.moveToLast();
            //Toast.makeText(this, cursorName.getString(cursorName.getColumnIndex(MainActivity.USER_DAILY_LIMIT)), Toast.LENGTH_LONG).show();
            double totalPlannedSavingPerDay = 0;
            Cursor cursor = db.query(dbHelper.NAME_SAVING, MainActivity.all_columns_saving, null, null, null, null, null);
            while (cursor.moveToNext() == true) {
                totalPlannedSavingPerDay += Double.parseDouble(cursor.getString(cursor.getColumnIndex(MainActivity.SAVING_PER_DATE)));
            }
//
            String totalIncome = cursorName.getString(cursorName.getColumnIndex(MainActivity.USER_TOTAL_INCOME));
            String savingPercentage = cursorName.getString(cursorName.getColumnIndex(MainActivity.USER_SAVING_GOAL));
            String totalRecurring = "0";
            String frequency = cursorName.getString(cursorName.getColumnIndex(MainActivity.USER_INCOME_TYPE));
            double userDailyUpdated = Double.parseDouble(cursorName.getString(cursorName.getColumnIndex(MainActivity.USER_DAILY_LIMIT))) - totalPlannedSavingPerDay;


            //DailyIncomeCalculator dailyIncomeCalculator = new DailyIncomeCalculator(totalIncome, savingPercentage, totalPlannedSavingPerDay, totalRecurring, frequency, daysOfMonth);

            db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MainActivity.USER_DAILY_LIMIT, String.valueOf(userDailyUpdated));
            db.update(dbHelper.NAME, contentValues, "_id=" + MainActivity._ID, null);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void onPause() {
        super.onPause();
        if (db != null) db.close();
        mCursor.close();
    }

    public void addSaving(View v){
        Intent intent = new Intent(this, saving_plan_2.class);
        startActivity(intent);
    }


}
